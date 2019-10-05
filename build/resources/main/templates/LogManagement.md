## Log management for cloud native observability

------------

*Forward from [this website](https://mp.weixin.qq.com/s/E7bFp58OL6gjvBxLTKpbJQ)*

------------

In recent years, with the rise of cloud native technology represented by Kubernetes, Observability, as a new concept, has gradually come into people's view. Cloud native foundation (CNCF) has classified observability in its Landscape as a separate category, mainly including monitoring, logging and tracking in a narrow sense, and warning, event and audit in a broad sense. Numerous new open source software has emerged in this field, such as Prometheus, Grafana, Fluentd, Loki, Jaeger, etc.

![https://landscape.cncf.io/](https://cdn.img.wenhairu.com/images/2019/10/04/8tO1B.png)

As an important part of observability, log plays a very important role in development, operation, testing, auditing and so on. One of the famous 12 elements of application development states: "logging makes the actions of the application run transparent, and the application itself never considers storing its own output stream. You should not attempt to write or manage log files. Each running process outputs directly to standard output (stdout). The output stream of each process is captured by the runtime environment, collated with other output streams, and sent to one or more final processors for viewing or long-term archiving.

In the environment of a physical machine or virtual machine, the log is usually output to a file and managed by the user himself, which makes the centralized management and analysis of the log difficult and inconvenient. While Kubernetes, Docker and other container technologies directly output logs to stdout, which makes centralized management and analysis of logs more convenient and natural.

The general log architecture given by the official website of Kubernetes is shown in the following figure, including log Agent, back-end service and front-end console. Both mature logging solutions like ELK/EFK and cloud native 2018 open source Loki share a similar architecture, with contributions from ELK/EFK, Loki and KubeSphere described below.

![https://kubernetes.io/docs/concepts/cluster-administration/logging/](https://cdn.img.wenhairu.com/images/2019/10/04/8tTIn.png)

<center><strong>Marriage of old and new: ELK to EFK, Fluentd to Fluent Bit</strong></center><hr size="2px" />
ELK, short for Elasticsearch, Logstash and Kibana, is a mainstream open source logging solution. Fluentd, which graduated from CNCF in April 2019 and is written by C and Ruby, is a general log collector. With its high efficiency, flexibility and ease of use, Fluentd has gradually replaced Logstash written by Java as an important member of the new log solution EFK and has been widely recognized and applied in the cloud native field. Google's cloud logging service Stackdriver also USES the modified Fluentd as the Agent. However, the Fluentd development team did not stop there and launched a more lightweight product Fluent Bit written entirely in C, as shown in the figure below:

![https://docs.fluentbit.io/manual/about/fluentd_and_fluentbit](https://cdn.img.wenhairu.com/images/2019/10/04/8tVlA.png)

As you can see, Fluent Bit takes up less resources than Fluentd and is more suitable as a logging collector. Fluentd plug-in is very many, more suitable as a log aggregator.

![](https://cdn.img.wenhairu.com/images/2019/10/04/8zaqR.png)

<center><strong>FluentBit Operator and its applications in KubeSphere</strong></center><hr size="2px"/>
Fluent Bit is lighter and more efficient, but it also has a problem: configuration file changes don't automatically reload new configurations gracefully. See the official Github issue for details:

1. https://github.com/fluent/fluent-bit/pull/842
2. https://github.com/fluent/fluent-bit/issues/365

To address these issues, the KubeSphere team developed FluentBit Operator and applied it to KubeSphere as a logging collector. The architecture and principle of FluentBit Operator are shown in the figure below:

1. Add FluentBit Controller process control in the main Container of FluentBit Pod to start and stop the main process of FluentBit;

2. Join ConfigMap Reload sidecars Container used to monitor FluentBit ConfigMap configuration file is change, and in monitoring the change called when FluentBit Controller Reload interface: http://localhost:24444/api/config.reload;

3. The FluentBit Controller then restarts the FluentBit master process to load the new configuration file.

![the architecture diagram of FluentBit Operator](https://cdn.img.wenhairu.com/images/2019/10/04/8tBgG.png)

From KubeSphere, select Elasticsearch as the log backend service and FluentBit as the log collector. The KubeSphere log console controls the FluentBit configuration in FluentBit CRD through the FluentBit Operator. (users can also change fluentbit configuration in a kubernetes native way by kubectl edit fluentbit fluentbit fluentbit)

![the architecture diagram of KubeSphere log system](https://cdn.img.wenhairu.com/images/2019/10/04/8tb3v.png)

With FluentBit Operator, KubeSphere has the flexibility to add/remove/suspend/configure log receivers via the console.

![the configuration interface of KubeSphere log](https://cdn.img.wenhairu.com/images/2019/10/04/8tiw0.png)

![the search interface of KubeSphere log](https://cdn.img.wenhairu.com/images/2019/10/04/8tsRU.png)

<center><strong>Multi-tenant log management</strong></center><hr size="2px"/>

Multi-tenant features are currently a concern in the Kubernetes community, and there are various implementation schemes, such as soft multi-rent, hard multi-rent and so on. In terms of log management, Loki supports multi-tenancy by Tenant ID. KubeSphere has achieved tenant isolation through its workspace. Let's take a look at some of the multi-tenant practices of KubeSphere log management. (KubeSphere's upcoming v2.1 release has significant enhancements to log management, such as better support for Chinese log retrieval, automatic injection of fallen logs, Sidecar, etc.)

Can see three layers of KubeSphere is based on the RBAC multi-tenant architecture, Cluster/Workspace/Project three levels have different levels of Roles and the matching.

![](https://cdn.img.wenhairu.com/images/2019/10/04/8t2Bj.png)

Prior to access to log data (or other services), authentication and authorization by API Gateway are required:

![](https://cdn.img.wenhairu.com/images/2019/10/04/8trmg.png)

KubeSphere's complete logging solution is as follows:

![](https://cdn.img.wenhairu.com/images/2019/10/04/8ttpK.png)

<center><strong>Cloud's Native biological son Loki：Like Prometheus, but for logs</strong></center><hr size="2px"/>

Although popular as a logging scheme, ELK/EFK has a well-known weakness: it occupies too many resources, either memory or disk storage. That's because Elasticsearch has full-text indexes on the data there, allowing for fast full-text searches. In many cases full-text indexing is not necessary for log data. Additionally, Elasticsearch, which is developed by the Java language, is less efficient and resource efficient than Go, the cloud native development language.

Cloud native has been waiting for a log management software developed by Go, Loki came into being. Within 10 months of Loki's release in December 2018, Github had more than 7,000 stars. Loki was developed by Grafana Labs, which developed Grafana. Developers of Prometheus, the famous cloud native monitoring software, are in Grafana Labs and provide the Cortex of Prometheus monitoring service on the cloud. Loki was inspired by Prometheus. Therefore, it has many features similar to Prometheus, such as tight integration with Kubernetes, sharing Label with Prometheus, query language LogQL with similar query syntax as Prometheus, query functions similar to Prometheus, etc. Even log data of Loki can be directly viewed and retrieved in Grafana. In addition, Loki's share many components with Cortex, making it easy to scale horizontally.

The most important features of Loki are its low storage cost and low resource footprint. Loki do this is at the beginning of the design is intended to solve Elasticsearch occupancy resources more shortcomings, through to metadata indexes such as Label, only to compress log stream data storage, and in the user search to narrow down the view by index of Label when the log text, real-time decompression and mechanism of log like grep stream data filtering. As shown in the figure below, Loki's components include Agent Promtail that collects the data, Distributor that receives the data, Ingester that caches the data for bulk writes, and Querier for querying the data, all of which can scale and be highly available depending on the load level.

![Loki architecture (from Grafana Labs official blog)](https://cdn.img.wenhairu.com/images/2019/10/04/8tzI3.png)

As shown in the following figure, the index and log data blocks of Loki are respectively stored with different storage media, and the index can be stored to Cassandra, BoltDB, etc. Log data blocks can be stored on local disk or in cloud object storage or S3 compliant Minio. So in the realization of mass log data storage at low cost, but also to meet the needs of users quickly query log.

![Loki storage (from Grafana Labs official blog)](https://cdn.img.wenhairu.com/images/2019/10/04/8tjlo.png)

As a log solution emerging in the era of cloud natives, Loki has rapidly gained great attention due to its low cost, scalability and high availability, as well as its close integration with Kubernetes and Prometheus. It is expected to gain the status of Prometheus on Kubernetes and become the DE facto standard for log management in the cloud natives.

<center><strong>Expectation</strong></center><hr size="2px"/>

The Kubernetes architecture provides the possibility of centralized log management. And the outstanding log management scheme that emerges one after another makes the user can mine the value of log data better, obtain better observability. KubeSphere, an open source Kubernetes distribution, will continue to focus on and actively participate in the development of Loki, inspired by Prometheus, in addition to improving existing Kubernetes logging solutions (such as multi-cluster log management, log alert, etc.), in order to integrate the industry's leading log management technologies into KubeSphere for users to use.

<center><strong>Q&A</strong></center><hr size="2px"/>

**Q: how to debug Prometheus?**
A: if you want to participate in the development of Prometheus, you may refer to the development guidelines of Prometheus Github. If you encounter problems in use, you can combine it with the log, or check Prometheus Console UI with some intuitive exception tips, and consult slack or Google group in Prometheus community.

**Q: do you use Loki in production? Are there any best practices?**
A: we are investigating Loki. Grafana Labs already offers Loki as A logging service. The mode of their deployment reference: https://github.com/grafana/loki/tree/master/production/ksonnet. The recommendation is that several components be deployed separately, with multiple copies of each component for high availability. In addition, index and chunk are selected according to the situation to use appropriate storage respectively.

**Q: excuse me, the user wants to customize log parsing, how to achieve? At present, our implementation mode is that fluentd parser is deployed to every node of Kubernetes as an Agent in the manner of DeamonSet, and it is collected and parsed at the same time. The disadvantage is that it occupies too much node resources. How do you implement it?**
A: the Agent collecting logs should be more lightweight, such as Fluent Bit. Fluentd can be taken as the receiver of Fluent Bit, and then sent to the final storage after the centralized analysis with Fluentd, so that Fluentd need not be deployed on every node. An architecture like this:

![](https://cdn.img.wenhairu.com/images/2019/10/04/8thUf.png)

**Q: have Fluent and Filebeat done any pressure tests? Or because Fluentd is CNCF project just choose this? Ruby also has GIL locks, which can only squeeze single-core performance.**
A: I chose Fluent Bit mainly because of its small memory footprint. Filebeat is also very popular. Go writes that it USES a Bit more memory than Fluent Bit, as far as I know. Fluent Bit is written entirely in C, not Ruby. Fluentd core with c, plug-in with Ruby.

**Q: are logs from different services mixed? Not a different index, right? How do I collect logs in the container?**
A: at present, the log of different services is an index every day. If you want different indexes, you should use filter and tag to implement. Logs that are not output to stdout disks in the container can be forwarded to stdout by adding sidecar to the container. Kubesphere 2.1 is about to launch with a sidecar auto-injection feature.

**Q: Standard output, will the data be saved to disk? How?**
A: standard output, the log doesn't land on the disk mounted in the container, but it does land on the disk of the node where the container is, and usually the container log for that node has A rotation setting, which cleans it up periodically.