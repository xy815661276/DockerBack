## Past and present of the open source monitoring system Prometheus

------------

*Forward from [https://mp.weixin.qq.com/s/zDbv3e2PQ6SYqN7voE1jdQ](https://mp.weixin.qq.com/s/zDbv3e2PQ6SYqN7voE1jdQ)*

------------

![Prometheus](https://cdn.img.wenhairu.com/images/2019/10/03/8tNDG.png "Prometheus")
Prometheus is SoundCloud's open source monitoring system and the second to join CNCF after Kubernetes. Prometheus is an excellent monitoring system. WoQu has developed several components around Prometheus, including basic alarm components, service discovery components, and various collected Exporters. These components, together with Prometheus, support most of WoQu's monitoring business. This article focuses on Prometheus, from his source, architecture, and a concrete example, and what Wows has done around Prometheus.

###  1. origin
SoundCloud's previous application architecture was a boulder architecture, where all the features were placed in one large module with no obvious boundaries between functions. There are two main problems in the application of the megalithic architecture. On the one hand, it is difficult to scale horizontally and only expand vertically, but the capability of a single machine is limited after all; on the other hand, the functions are coupled together, adding A feature needs to be developed on an existing technology stack and it is guaranteed that it does not affect existing functionality. So they turned to the microservices architecture, splitting the original functionality into hundreds of independent services, and running thousands of instances across the system. Migrating to the microservices architecture poses certain challenges for monitoring. Now you need to know not only the operation of a component, but also the overall operation of the service. Their monitoring plan at that time was: StatsD + Graphite + Nagios, StatsD combined with Graphite to build monitoring charts, each service pushed the sample data to StatsD, StatsD aggregated the sample data pushed, and regularly pushed to Graphite, and Graphite will sample the data. Stored in the time series database, the user builds a monitoring chart according to the API provided by Graphite, combined with the needs of its own monitoring, and analyzes the indicators of the service through the chart (for example, delay, number of requests per second, number of errors per second, etc.).

![](https://cdn.img.wenhairu.com/images/2019/10/03/8tYSv.png)
So can such a solution meet the monitoring requirements of the microservice architecture? What is the requirement: not only can know the overall operation of the service, but also maintain sufficient granularity to know the operation of a certain component. The answer is hard, why? For example, we need to count the number of POST/tracks request errors in the api-server service. The name of the indicator is api-server.tracks.post.500. This indicator can be measured by the http status code. The status code of the service response is 500. wrong. The structure of the Graphite indicator name is a hierarchical structure, the api-server specifies the name of the service, the tracks specifies the handler of the service, the post specifies the method of the request, the 500 specifies the status code of the request response, and the api-server service instance pushes the indicator to StatsD. StatsD aggregates the metrics pushed by each instance and then pushes them to Graphite periodically. Query the api-server.tracks.post.500 indicator, we can get the response number of the service error, but if our api-server service runs multiple instances and wants to know the response number of an instance error, how can we query it? ? The problem is that using such an architecture often aggregates the metrics sent by each service instance into one. After the aggregation, the information of the instance dimension is lost, and the metric information of a specific instance cannot be counted.

![](https://cdn.img.wenhairu.com/images/2019/10/03/8tf20.png)
The combination of StatsD and Graphite is used to build the monitoring chart. The alarm is made by another system, Nagios-. This system runs the detection script to determine whether the host or service is running normally. If it is not normal, it sends an alarm. The biggest problem with Nagios is that the alarm is host-oriented. The check items of each alarm are all around the host. In the environment of the distributed system, the host is down. This is a normal scenario. The design of the service itself can tolerate the node down. However, Nagios will still trigger an alarm in this scenario.

If you have seen this article(https://landing.google.com/sre/sre-book/chapters/practical-alerting/#id-WakuESGIBIysp-marker) on Google Borgmon, compare Prometheus and you will find these two The system is very similar. In fact, Prometheus was heavily influenced by the Borgmon system, and employees who were involved in building the Google monitoring system joined SoundCloud. In short, the combination of various factors prompted the birth of the Prometheus system.

### 2. the solution of Prometheus
So, how does Prometheus solve these problems? In the previous scenario, the construction of alarms and charts relied on two different systems. Prometheus adopted a new model that collects time series data as the core of the entire system, whether it is an alarm or a monitoring chart. Data to achieve. Prometheus identifies time-series data by the combination of the name of the indicator and the combination of label(key/value). Each label represents a dimension that can be added or subtracted to control the selected time series data. As mentioned earlier, the microservice architecture is under monitoring. Requirements: You can know the overall operation of the service, but also maintain sufficient granularity to know the operation of a component. This goal can be easily achieved with this multi-dimensional data model, or with the previous example of a statistical http error response. We assume here that the api_server service has three running instances, and Prometheus collects sample data in the following format ( Where the intance label is automatically added by Prometheus):

    api_server_http_requests_total{method="POST",handler="/tracks",status="500",instance="sample1"} -> 34
        api_server_http_requests_total{method="POST",handler="/tracks",status="500",instance="sample2"} -> 28
        api_server_http_requests_total{method="POST",handler="/tracks",status="500",instance="sample3"} -> 31

If we only care about the number of errors for a particular instance, just add the instance label. For example, if we want to see the number of incorrect requests with the instance name sample1, then I can use`api_server_http_requests_total{method="POST",handler="/tracks",status="500",instance="sample1"}`to select timing data, the selected data is as follows:

    api_server_http_requests_total{method="POST",handler="/tracks",status="500",instance="sample1"} -> 34

The alarm is implemented by manipulating the time series data instead of running a custom script. Therefore, as long as the indicator data exposed by the service or the host can be collected, the alarm can be triggered.

### 3. Architecture
Let's take a brief look at the architecture of Prometheus, look at the capabilities of each component, and how these components interact.

Prometheus Server is the core of the whole system. It periodically pulls the indicators from the API exposed by the exporters and saves the data to the time series database. If the monitoring target is dynamic, it can be dynamically driven by the mechanism of service discovery. These monitoring targets are added, and it also exposes APIs that execute PromQL, the language used to manipulate time-series data. Other components, such as Prometheus Web, can be used by Grafana to query the corresponding time series data. The Prometheus Server periodically executes the alarm rule. The alarm rule is a PromQL expression. The value of the expression is true or false. If true, the generated alarm data is sent to the alertmanger. The aggregation, grouping, sending, disabling, and restoring of alarm notifications is not done by Prometheus Server, but by Alertmanager. Prometheus Server simply pushes the triggered alarm data to Alertmanager, and then Alertmanger aggregates the alarms according to the configuration. One piece, sent to the corresponding recipient.

If we want to monitor a scheduled task, want the execution time of the instrument task, and the task execution succeeds or fails, how do you expose these metrics to Prometheus Server? For example, every other day to do a database backup, we want to know how long each backup is executed, whether the backup is successful, our backup task will only be executed for a while, if the backup task is over, how should Prometheus Server pull the backup indicator? What about the data? To solve this problem, you can do this through Prometheus' pushgateway component. Each backup task pushes the metrics to the pushgateway component. Pushgateway caches the pushed metrics. Prometheus Server pulls the metrics from the Pushgateway.
![](https://cdn.img.wenhairu.com/images/2019/10/03/8tAGn.png)

### 4. Examples
The above is a introduction to Prometheus from a relatively large level - background, architecture - now, let us take a concrete example to see how to use Prometheus to build monitoring charts, analyze system performance and alarms.

We have a service that exposes four APIs. Each API only returns some simple text data. Now we need to monitor this service. We hope to monitor and analyze the request rate of the service, the average delay of the request, and the request. Delay distribution, and can trigger an alarm when the application's delay is too high or inaccessible, the code example is as follows:

    package main
    
    import (
    	"math/rand"
    	"net/http"
    	"time"
    
    	"github.com/prometheus/client_golang/prometheus"
    	"github.com/prometheus/client_golang/prometheus/promauto"
    	"github.com/prometheus/client_golang/prometheus/promhttp"
    )
    
    var (
    	Latency = promauto.NewHistogramVec(prometheus.HistogramOpts{
    		Help: "latency of sample app",
    		Name: "sample_app_latency_milliseconds",
    		Buckets: prometheus.ExponentialBuckets(10, 2, 9),
    	}, []string{"handler", "method"})
    )
    
    func instrumentationFilter(f http.HandlerFunc) http.HandlerFunc {
    	return func(writer http.ResponseWriter, request *http.Request) {
    		now := time.now()
    		f(writer, request)
    		duration := time.Now().Sub(now)
    		Latency.With(prometheus.Labels{"handler": request.URL.Path, "method": request.Method}).
    			Observe(float64(duration.Nanoseconds()) / 1e6)
    	}
    }
    
    //jitterLatencyFilter make request latency between d and d*maxFactor
    func jitterLatencyFilter(d time.Duration, maxFactor float64, f http.HandlerFunc) http.HandlerFunc {
    	return func(writer http.ResponseWriter, request *http.Request) {
    		time.Sleep(d + time.Duration(rand.Float64()*maxFactor*float64(d)))
    		f(writer, request)
    	}
    }
    
    func main() {
    	rand.Seed(time.Now().UnixNano())
    	http.Handle("/metrics", promhttp.Handler())
    	http.Handle("/a", instrumentationFilter(jitterLatencyFilter(10*time.Millisecond, 256, func(w http.ResponseWriter, r *http.Request) {
    		w.Write([]byte("success"))
    	})))
    	http.Handle("/b", instrumentationFilter(jitterLatencyFilter(10*time.Millisecond, 128, func(w http.ResponseWriter, r *http.Request) {
    		w.Write([]byte("success"))
    	})))
    	http.Handle("/c", instrumentationFilter(jitterLatencyFilter(10*time.Millisecond, 64, func(w http.ResponseWriter, r *http.Request) {
    		w.Write([]byte("success"))
    	})))
    	http.Handle("/d", instrumentationFilter(jitterLatencyFilter(10*time.Millisecond, 32, func(w http.ResponseWriter, r *http.Request) {
    		w.Write([]byte("success"))
    	})))
    	http.ListenAndServe(":5001", nil)
    }

We build a monitoring system according to the process of instrumentation, exposition, collection, query. Instrumentation focuses on how to measure the application indicators, which indicators need to be measured; exposition focuses on how to expose the indicators through the http protocol; how the collection focuses on how Collection of indicators; query focuses on how to construct PromQL expressions for querying time series data. We first from the instrumentation here, there are four indicators that we care about:
- Request rate
- Average latency of requests
- Requested delay distribution
- Access status

First register the indicator, then track and record the value of the indicator. The Golang client library provided by Prometheus can easily track and record the value of the indicator. We put the instrumentation code into the application code. The value of the corresponding indicator state will be recorded for each request.

    var (
    	Latency = promauto.NewHistogramVec(prometheus.HistogramOpts{
    		Help: "latency of sample app",
    		Name: "sample_app_latency_milliseconds",
    		Buckets: prometheus.ExponentialBuckets(10, 2, 9),
    	}, []string{"handler", "method"})
    )

Client golang provides four types of indicators, Counter, Gauge, Histogram, Summary, Counter type indicators to measure only the added value, such as the number of requests for the service; Gauge type indicators are used to measure the status value, that is, Larger, can also be smaller values, such as the delay time of the request; Histogram is similar to the Summary indicator, the value of the two indicators sampled, the distribution of recorded values, the number of statistical observations, the cumulative observed value, can be used It is used to count the distribution of sample data. In order to collect the request rate, the average delay and the delay distribution index, it is convenient to track and record each request with the Histogram type index. The Histogram type indicator is different from the normal type (Counter, Gauge) in that multiple sample data are generated. One is to observe the total number of samples, one is to observe the accumulated value of the sample value, and the other is a series of sample data of the recorded sample percentile. The access status can be expressed using the up indicator, and each time the acquisition is made, Prometheus will record the collected health status to the up indicator.

    http.Handle("/metrics", promhttp.Handler())

After instrumentation is complete, the next step is to perform the exposition. Just add the Prometheus http handler and the indicator will be exposed. The sample data returned by accessing this Handler is as follows (some extraneous sample data is omitted):

    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="10"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="20"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="40"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="80"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="160"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="320"} 0
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="640"} 1
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="1280"} 1
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="2560"} 1
    sample_app_latency_milliseconds_bucket{handler="/d",method="GET",le="+Inf"} 1
    sample_app_latency_milliseconds_sum{handler="/d",method="GET"} 326.308075
    sample_app_latency_milliseconds_count{handler="/d",method="GET"} 1

Just exposing the indicator does not allow the prometheus server to collect the indicator. We need to perform a third step of the collection, configure the prometheus server to discover our service, and collect the sample data exposed by the service. We simply look at the configuration of the prometheus server, where global specifies the global configuration for collection, scrape_interval specifies the interval for collection, evaluation_interval specifies the aerting rule (alerting rule is a PromQL expression, the value is boolean, if true, the associated alarm will be The notification is sent to Alertmanager), which is the evaluation interval of the alarm rule. The scrape_timeout specifies the timeout time for the collection; the altting specifies the address of the Alertmanager service; the scrape_configs specifies how to find the monitored object, where jobname specifies which class the discovered service belongs to, staticconfigs specifies Serving static addresses, as we mentioned earlier, Prometheus supports dynamic service discovery, such as files, kubernetes service discovery mechanisms, where we use the simplest static service discovery mechanism.

    # my global config
    global:
    	scrape_interval:     2s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
    	evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
    	# scrape_timeout is set to the global default (10s).
    rule_files:
    - rule.yaml
    
    # Alertmanager configuration
    alerting:
    	alertmanagers:
    	- static_configs:
    		- targets:
    		- localhost:9093
    
    scrape_configs:
    - job_name: sample-app
    	scrape_interval: 3s
    	static_configs:
    	- targets:
    		- sample:5001

After collecting the indicators, you can use the PromQL language provided by Prometheus to manipulate the acquired time series data.

With the time series data, you can use Grafana to build the monitoring chart. How to configure the Grafana chart is not extended here. The core point is to use PromQL expression to select and calculate the time series data.

![](https://cdn.img.wenhairu.com/images/2019/10/03/8tqLT.png)
The Prometheus alarm is implemented by evaluating the Alerting Rule, which is a series of PromQL expressions, and the aerting rule is stored in the configuration file. We want to alert the application's delay and available status. When the application is too high or inaccessible, the alarm is triggered. The rules can be defined as follows:

### 5. Relation work
Whether it is monitoring the related business of the chart or the business related to the alarm, it is inseparable from the collection of relevant indicators. Wooke is a company that makes database products. We spend a lot of energy to collect database-related indicators, from Oracle to MySQL, and then to SQL Server, the indicators of the mainstream relational database are collected. For some common indicators, such as operating system related indicators, we mainly use open source Exporters to collect. Wooke's products are delivered in both soft and hard. There are a large number of hardware-related indicators that need to be collected. Therefore, we also have Expoters that collect hardware indicators.

In most of WoQu's scenarios, the services to be monitored are dynamic. For example, if a user applies for a database from the platform, the related monitoring service needs to be added, the user deletes the database resource, and the related monitoring service needs to be removed, and the database service to be monitored is in dynamic change. WoQu's infrastructure for each product line is different. The database service runs on Oracle RAC, runs on ZStack, and runs on Kubernetes. For applications running on Kubernetes, you need to worry about how Prometheus discovers the services to be monitored. You only need to configure the relevant service discovery mechanism. For other types, we mainly use Prometheus' file_sd service discovery mechanism. The file-based service discovery mechanism is the most common mechanism. We write the object to be monitored to a file. Prometheus listens for changes in this file. To maintain the object to be monitored, we build a special component on file_sd to be responsible for the dynamic update of the service. Other applications call the API exposed by this component to maintain the object that we want to monitor.

The mechanism of Prometheus itself does not meet the requirements of the alarms in our business. On the one hand, we need to count the alarm notifications, but the Alertmanager itself does not persist the alarm notifications. After the service is restarted, the alarm notifications are lost. The user configures related alarms through the web page. The alarm rules and the route of the alarm notification need to be dynamically generated according to the configuration of the user. In order to solve these two problems, we will make relevant business functions into basic alarm components for use in various product lines. For the problem that Alertmanager can not persist the alarm notification, the basic alarm component uses the Alertmanager webhook mechanism to receive the alarm notification and then save the notification to the database. In addition, the user's alarm configuration needs to be dynamically generated. We define a new model. Describe the alert model on our business.

### 6. Summary
Promtheus collects time series data as the core of the entire system, whether it is to build monitoring charts or alarms, by manipulating time series data. Prometheus meets the monitoring requirements of the microservices architecture with a multi-dimensional data model and a powerful query language: it can know the overall operation of the service, and maintain sufficient granularity to know the operation of a component. Wool stood on the shoulders of giants and built his own monitoring system around Prometheus, from Exporters to different collection requirements to service discovery, and finally to the basic alarm components. These components, combined with Prometheus, form the core of the Worcester surveillance system.