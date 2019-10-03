## A Performance Study of Containers in Cloud Environment

------------

*by Bowen Ruan, Hang Huang, Song Wu, and Hai Jin*
*Published on APSCC 2016*

------------

![APerformanceStudyofContainersinCloudEnvironment](https://cdn.img.wenhairu.com/images/2019/10/02/8r40f.png "APerformanceStudyofContainersinCloudEnvironment")

------------

Container technology has gained great popularity since containers could provide near-native performance in cloud environment. According to diﬀerent design purposes and underlying implementations, containers could be classiﬁed into application containers (e.g., *Docker*) and system containers (e.g., *LXC*). The diversity of containers may lead to a confusing choice about which kind of container is suitable for different usage scenarios. Meanwhile, the architectures of public container services are quite controversial because cloud platforms tend to run containers in virtual machines. From the perspective of performance, an extra virtual machine layer between the bare metal and containers probably brings in unnecessary performance overhead. In this paper, we carry out a performance study to explore the appropriate way to use containers from diﬀerent perspectives. We ﬁrst conduct a series of experiments to measure performance diﬀerences between application containers and system containers, then evaluate the overhead of extra virtual machine layer between the bare metal and containers, and ﬁnally inspect the service quality of ECS (*Amazon EC2 Container Service*) and GKE (*Google Container Engine*). Our results show that system containers are more suitable to sustain I/O-bound workload than application containers, because application containers will suﬀer high I/O latency due to layered ﬁle system. Running containers in virtual machine would result in severe disk I/O performance degradation up to 42.7% and network latency up to 233%. We also ﬁnd out ECS oﬀers better performance than GKE, and cloud platforms could acquire better performance by running containers directly on the bare metal.

------------

