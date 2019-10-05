## Monitor Kubernetes with Prometheus

------------

*By James Turnbull* Forward from [this website](https://mp.weixin.qq.com/s/gSmuDNdOvChATGj9L2lt5g)

------------

### 1. Monitor Kubernetes
Let us first talk about monitoring Kubernetes itself. Although it may update iterations frequently, it is a subject, and much of the knowledge and content is generic. Kubernetes is a container orchestration and scheduler with many flexible components. We will show how to use the Prometheus job to monitor various aspects of Kubernetes and match those jobs with some logging rules and alert rules.

This article will explain how to handle each component, how to collect time series, and the rules and alerts generated from these time series. We are not going to provide a clear monitoring method, but rather to touch on some key points, especially in the extended Prometheus concept.

In order to determine what needs to be monitored, we will also use Prometheus' built-in service discovery mechanism for Kubernetes. Let's start by monitoring the nodes running Kubernetes.

### 2. Monitor Kubernetes nodes
Our Kubernetes cluster consists of nine AWS EC2 instances. In order to monitor them, we will use the Node Exporter, which can be deployed to these instances in a variety of ways. The Node Exporter can be installed on the instance at configuration time, or the Node Exporter can be installed into the Kubernetes pod on each node. We can use the Kubernetes DaemonSet controller to automatically deploy pods on each node in the cluster. This method is useful when you have no control over instances, for example, if you use a managed Kubernetes solution.

Warning: Use this method requires special attention! The Node Exporter accesses many root-level resources. To run it in a Docker container, you need to mount these resources to the container. For the systemd collector, you need to run the container as root. This poses a potential security risk, and if you are unable to accept the risk, you should install the Node Exporter directly on the instance.

#### 2.1 Node Exporter DaemonSet
DaemonSet uses tolerance to ensure that the pod runs on all nodes and possibly the primary node. It is ideal for projects such as monitoring or logging agents. Let's take a look at the elements of DaemonSet.

Note: The full configuration of the Node Exporter can be found on GitHub.

Listing 2.1 Node Exporter DaemonSet toleration

    apiVersion: extensions/v1beta1
    kind: DaemonSet
    metadata:
    name: node-exporter
    namespace: monitoring
    . . .
    spec:
    tolerations:
    - key: node-role.kubernetes.io/master
    effect: NoSchedule
    hostNetwork: true
    hostPID: true
    hostIPC: true
    securityContext:
    runAsUser: 0

First, we can see that we have assigned a name node-exporter to DaemonSet and use toleration to ensure that the pod is also dispatched to the Kubernetes master node, not just the worker node.

Here you can see the situation mentioned in the warning. We run pod with user 0 or root (this allows access to systemd), and also enables hostNetwork, hostPID, and hostIPC to specify that the instance's network, process, and IPC namespace are available in the container. These are potential security risks and you must consider whether you can take on this risk. If this risk is unacceptable, it might be a better idea to put the Node Exporter in the image of the instance.

Let's take a look at the container in the pod.

Listing 2.2 Node Exporter DaemonSet container

    containers:
    - image: prom/node-exporter:latest
    name: node-exporter
    volumeMounts:
    - mountPath: /run/systemd/private
    name: systemd-socket
    readOnly: true
    args:
    - "--collector.systemd"
    - "--collector.systemd.unit-whitelist=(docker|ssh|
    rsyslog|kubelet).service"
    ports:
    - containerPort: 9100
    hostPort: 9100
    name: scrape

Here, we use the Node Exporter image in DockerHub to mirror prom/node_exporter and get the latest version. We also mount a volume for /run/systemd/private with the same directory on the host, which allows the Node Exporter to access systemd and collect the service state of the systemd management service on the instance.

We also specify some parameters for the node_exporter binary, which are described in Chapter 4: Enable the systemd collector and specify the regular expression for the particular service to be monitored, not all services on the host.

We also specified port 9100, which is expected to expose the metric, which is also the default port.

To help keep Node Exporter pods healthy and improve their uptime, we also added liveness and readiness probes to the Node Exporter container, where the liveness probe detects the state of the application inside the container.

Listing 2.3 Node Exporter's liveness and readiness probes

    livenessProbe:
    httpGet:
    path: /metrics
    port: 9100
    initialDelaySeconds: 30
    timeoutSeconds: 10
    periodSeconds: 1
    readinessProbe:
    failureThreshold: 5
    httpGet:
    path: /metrics
    port: 9100
    initialDelaySeconds: 10
    timeoutSeconds: 10
    periodSeconds: 2

In the example, we use HTTP GET probe for the /metrics path on port 9100 to confirm that the Node Exporter is working. The probe runs once every periodSeconds time, in the example 1 second. If the liveness check fails, Kubernetes will restart the container.

Note: We will also see these probes in the monitored application. They help manage the health of the application by reducing possible monitoring false positives, such as triggering an alert if the service does not meet the readiness requirements at startup. These checks can also restart the problem container and may help fix the problem before the alert is triggered.

The Readiness probe confirms that the application is up and running. This means that the HTTP GET can be connected to the /metrics path on port 9100 before marking the container as available and sending traffic. The rest of the settings control the behavior of the probe, which waits 10 seconds (initialDelaySecond) before the check is ready, and then it checks every 2 seconds (periodSeconds). If the probe times out more than 5 times after 10 seconds (timeoutSeconds), the container will be marked as Unready.

#### 2.2 Node Exporter Service
We also need to create a service to expose the Node Exporter for metric crawling.

Listing 2.4 Node Exporter Service

    apiVersion: v1
    kind: Service
    metadata:
    annotations:
    prometheus.io/scrape: 'true'
    labels:
    app: node-exporter
    name: node-exporter
    name: node-exporter
    namespace: monitoring
    spec:
    clusterIP: None
    ports:
    - name: scrape
    port: 9100
    protocol: TCP
    selector:
    app: node-exporter
    type: ClusterIP

The service is relatively simple. We added an annotation prometheus.io/scrape: 'true' as the metadata for the service, which tells Prometheus that it should crawl the service. Later we will see how it is used to crawl Node Exporter in Prometheus jobs.

We also expose port 9100 as ClusterIP, which means it is only used for internal cluster networks. Since Prometheus is on the local Kubernetes cluster, it will be able to crawl the Node Exporter internally, so there is no need to expose it.

#### 2.3 Deploy Node Exporter
Let's create a DaemonSet and service on the Kubernetes cluster using the kubectl command. We will create them in the monitoring namespace.

Listing 2.5 Deploying the Node Exporter DaemonSet and services

    $ kubectl create -f ./node-exporter.yml -n monitoring
    daemonset "node-exporter" created
    service "node-exporter" created

If you don't want to continue specifying the -n monitoring namespace, you can specify it as the default.

Listing 2.6 Default namespace

    $ kubectl config set-context $(kubectl config current-context) --
    namespace=monitoring

Now check to see if our pod is running.

Listing 2.7 Checking the Node Exporter pane

    $ kubectl get pods -n monitoring
    NAME READY STATUS RESTARTS AGE
    alertmanager-6854b5d59b-jvjcw 1/1 Running 0 7d
    node-exporter-4fx57 1/1 Running 0 5s
    node-exporter-4nzfk 1/1 Running 0 5s
    node-exporter-5n7kl 1/1 Running 0 5s
    node-exporter-f2mvb 1/1 Running 0 5s
    node-exporter-km7sc 1/1 Running 0 5s
    node-exporter-lvrsq 1/1 Running 0 5s
    node-exporter-mvstg 1/1 Running 0 5s
    node-exporter-tj4cs 1/1 Running 0 5s
    node-exporter-wh56c 1/1 Running 0 5s
    prometheus-core-785bc8584b-7vfr4 1/1 Running 0 8d

You can see that there are a total of 9 pods, one for each instance in the cluster: 3 primary nodes and 6 working nodes. We can also see the Prometheus service and Alertmanager's pod:prometheus-core and alertmanager.
We can check if the Node Exporter pod is working by capturing the log.

Listing 2.8 Node Exporter pod log

    $ kubectl logs node-exporter-4fx57 -n monitoring
    time="2018-01-18T22:46:05Z" level=info msg="Starting
    node_exporter (version=0.15.2, branch=HEAD, revision=98
    bc64930d34878b84a0f87dfe6e1a6da61e532d)" source="node_exporter.
    go:43"
    time="2018-01-18T22:46:05Z" level=info msg="Build context (go=
    go1.9.2, user=root@d5c4792c921f, date=20171205-14:50:53)" source
    ="node_exporter.go:44"
    . . .

You can see that our Node Exporter daemon is running and you can confirm that the service is ready.

Listing 2.9 Checking the Node Exporter service

    $ kubectl get services -n monitoring
    NAME TYPE CLUSTER-IP EXTERNAL-IP PORT(S) AGE
    node-exporter ClusterIP None <none> 9100/TCP 8s

Here, you can see that the Node Exporter service is of the ClusterIP type, and exposes the 9100 port to the Kubernetes cluster, which can be crawled at any time. However, we have not started crawling because there is no job to add Prometheus.

#### 2.4 Node Exporter job
In the Prometheus configuration, we now want to add a job to grab the Node Exporter endpoint. This will be done by defining a job that crawls all the service endpoints exposed by Kubernetes. We also control Prometheus to only grab endpoints with the specific annotation prometheus.io/scrape set to true. We then use the built-in Kubernetes service discovery to find the endpoints and return them as potential targets for Prometheus.

Note: All of this work is based on the Kubernetes job case that comes with Prometheus. Thanks to the contributors of the project for developing them.

Let us now take a look at this job.

Listing 2.10 Kubernetes service endpoint job

	    - job_name: 'kubernetes-service-endpoints'
    kubernetes_sd_configs:
    - role: endpoints
    relabel_configs:
    - source_labels: [
    __meta_kubernetes_service_annotation_prometheus_io_scrape]
    action: keep
    regex: true
    - source_labels: [
    __meta_kubernetes_service_annotation_prometheus_io_scheme]
    action: replace
    target_label: __scheme__
    regex: (https?)
    - source_labels: [
    __meta_kubernetes_service_annotation_prometheus_io_path]
    action: replace
    target_label: __metrics_path__
    regex: (.+)
    - source_labels: [__address__,
    __meta_kubernetes_service_annotation_prometheus_io_port]
    action: replace
    target_label: __address__
    regex: ([^:]+)(?::\d+)?;(\d+)
    replacement: $1:$2
    - action: labelmap
    regex: __meta_kubernetes_service_label_(.+)
    - source_labels: [__meta_kubernetes_namespace]
    action: replace
    target_label: kubernetes_namespace
    - source_labels: [__meta_kubernetes_service_name]
    action: replace
    target_label: kubernetes_name

We call this job kubernetes-service-endpoints, the service discovery uses the kubernetes_sd_discovery mechanism, which is a built-in service discovery mechanism designed to monitor Kubernetes. It queries the Kubernetes API for targets that match specific search criteria.

Since the Prometheus server runs inside Kubernetes, we are able to automatically get Kubernetes targets that match specific roles with minimal configuration. Nodes, pods, services, and portals all have different roles, specified by the role parameter, and we require the service discovery to return all Kubernetes endpoints. The Endpoint role role returns the target of all listed endpoints of the service, and each port of each endpoint address is a target. If the endpoint is also provided by the pod, just like my Node Exporter service, then any other container port will be discovered as the target. In the example, we only exposed the 9100 port.

Service discovery also populates various metadata, and metadata can be used to re-tag and identify each endpoint. First let's look at the role of re-marking rules and further explore the metadata.

The first rule checks the annotation prometheus.io/scrape: 'true' we set in the Node Exporter service. During the service discovery process, the prometheus.io/scrape annotation will be converted to prometheus_io_scrape to create a valid tag name because the points and slashes are not legal characters in the Prometheus indicator tag. Since this is an annotation to the Kubernetes service, the Prometheus service process also adds the __meta_kubernetes_service_annotation_ prefix to the tag.

Our job only keeps the target with the metadata tag, ie __meta_kubernetes_service_annotation_ is set to true. All other targets are discarded, which allows you to grab only the endpoints you need.

The next three rules check for other annotations: prometheus.io/scheme, prometheus.io/path, and prometheus.io/port. If these tags exist, it will use the contents of these annotations as schemes, paths, and ports to grab. This enables us to precisely control the content to be grabbed from the service endpoint, thus making the job more flexible.

Our next rule uses the labelmap operation to map the labels on the service to Prometheus tags of the same name. In the example, this maps the __meta_kubernetes_service_label_app metadata tag to a simple app tag. The next rule copies the __meta_kubernetes_namespace tag to kubernetes_namespace and the _meta_kubernetes_service_name metadata tag to kubernetes_name.

We now add the job to the ConfigMap for the Prometheus server configuration and then replace the existing configuration.

Listing 2.11 replaces ConfigMap

    $ kubectl replace -f ./prom-config-map-v1.yml -n monitoring

Usually we have to remove the Prometheus pod to recreate and load the new configuration. We will see some new targets on the Prometheus expression browser later.

![](https://cdn.img.wenhairu.com/images/2019/10/03/8tm4A.png)

We can see that we have 13 targets listed, 9 of which are Node Exporter endpoints on the instance, and 10th and 11th are Prometheus and Alertmanager. Prometheus and Alertmanager targets are automatically discovered because their interfaces are exposed as services.

Listing 2.12 Monitoring service

    $ kubectl get services -n monitoring
    NAME TYPE CLUSTER-IP EXTERNAL-IP PORT(S) AGE
    alertmanager LoadBalancer 100.68.82.44 a6f953a641191...
    9093:32288/TCP 27m
    node-exporter ClusterIP None <none> 9100/TCP 15h
    prometheus LoadBalancer 100.68.154.121 a953a66970c13...
    9090:30604/TCP 4d

This job is very valuable because we only need to define it once and all future Kubernetes service endpoints will be automatically discovered and monitored.

We will see these values in later chapters and in the next chapter of this chapter. After the job is loaded, we will also see that the node_time series begins to appear in the expression browser.

#### 2.5 Node Exporter rules
We will not add any new records or alert rules to the Kubernetes node. Instead, we add the rules created in Chapter 4 to ConfigMap to populate the Prometheus rules file. So we added all the CPU, memory, and disk rules we created earlier, and we also added some availability alert rules for the Kubernetes service. Let's take a look at these rules.

Listing 2.13 Kubernetes Availability Alert Rules

    - alert: KubernetesServiceDown
    expr: up{job="kubernetes-service-endpoints"} == 0
    for: 10m
    labels:
    severity: critical
    annotations:
    summary: Pod {{ $labels.instance }} is down!
    - alert: KubernetesServicesGone
    expr: absent(up{job="kubernetes-service-endpoints"})
    for: 10m
    labels:
    severity: critical
    annotations:
    summary: No Kubernetes services are reporting!
    description: Werner Heisenberg says - OMG Where are my
    servicez?

When the up indicator value of the kubernetes-service-endpoints job is 0, the first alert is triggered, indicating that Prometheus failed to fetch the service. The second alert checks if the service has disappeared and uses the absent function to check for the presence of the up metric.

We also use the node_systemd_unit_state metric to add an alert rule for the services monitored on each node, which tracks the status of the systemd service.

Listing 2.14 Kubernetes Availability Alert Rules

    - alert: CriticalServiceDown
    expr: node_systemd_unit_state{state="active"} != 1
    for: 2m
    labels:
    severity: critical
    annotations:
    summary = {{ $labels.instance }}: Service {{ $labels.name }}
    failed to start.
    description = {{ $labels.instance }} failed to (re)start
    service {{ $labels.name }}.

It will alert you when it detects that any of the services that the Node Exporter is monitoring (Docker, Kubelet, RSyslog, and SSH) are in a failed state.

There are other rules and alerts in the configuration that you can explore further to learn more about node monitoring.

#### 2.6 Summary
In this article, we began to study how to monitor the application stack, starting with the computing platform Kubernetes. We deployed Prometheus to the Kubernetes cluster for easier monitoring. We looked at how to deploy Node Exporter in Kubernetes and how to use the Node Exporter to monitor Kubernetes nodes.

We created several Prometheus jobs, including several jobs that use Kubernetes service discovery to automatically discover the nodes, API servers, and services that make up our environment. By using annotations to select the correct address, port, and path, Service Discovery also allows us to configure jobs and automatically start crawling specific Kubernetes or application services.
