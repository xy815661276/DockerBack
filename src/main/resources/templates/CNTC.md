## CNTC: A Container Aware Network Traﬃc Control Framework

------------

*by Lin Gu, Junjian Guan, Song Wu, Hai Jin, Jia Rao, Kun Suo, and Deze Zeng*
*Published on GPC 2019*

------------

![CNTC](https://cdn.img.wenhairu.com/images/2019/10/02/8rgyg.png "CNTC")

------------

As a lightweight virtualization technology, containers are attracting much attention and widely deployed in the cloud data centers. To provide consistent and reliable performance, cloud providers should ensure resource isolation since each host consists of multiple containers sharing the host kernel. As a mainstream container system, Docker uses CGroup to provide CPU, memory, and disk resource isolation. Unfortunately, all present solutions ignore the network resource, leading to the resource competition and violating the performance of networked hosts. Although several researches discuss the possibility of leveraging Linux Traﬃc Control (*TC*) module to guarantee network bandwidth, they fail to capture the diversity and dynamics of container network resource demands and therefore cannot be applied to container-level network traﬃc control. In this paper, we propose a Container Network Traﬃc Control (*CNTC*) framework which can provide strong isolation and container-level management for network resource with joint consideration of container characteristics and quality of service. To simplify the traﬃc control, we also design a series of APIs which allow inexpert programers to perform complicated traﬃc control on each container. Through experiment results, we show that CNTC works well in all network modes of containers.

------------

