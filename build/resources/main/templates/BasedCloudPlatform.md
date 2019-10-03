## Container-Based Cloud Platform for Mobile Computation Ofﬂoading

------------

*by Song Wu, Chao Niu, Jia Rao, Hai Jin and Xiaohai Dai*
*Published on IPDPS 2017*

------------

![Container-BasedCloudPlatformforMobileComputationOfﬂoading](https://cdn.img.wenhairu.com/images/2019/10/02/8rMXK.png "Container-BasedCloudPlatformforMobileComputationOfﬂoading")

------------

With the explosive growth of smartphones and cloud computing, mobile cloud, which leverages cloud resource to boost the performance of mobile applications, becomes attractive. Many efforts have been made to improve the performance and reduce energy consumption of mobile devices by ofﬂoading computational codes to the cloud. However, the ofﬂoading cost caused by the cloud platform has been ignored for many years. In this paper, we propose Rattrap, a lightweight cloud platform which improves the ofﬂoading performance from cloud side. To achieve such goals, we analyze the characteristics of typical ofﬂoading workloads and design our platform solution accordingly. Rattrap develops a new runtime environment, Cloud Android Container, for mobile computation ofﬂoading, replacing heavy weight virtual machines (*VMs*). Our design exploits the idea of running operating systems with differential kernel features inside containers with driver extensions, which partially breaks the limitation of OS-level virtualization. With proposed resource sharing and code cache mechanism, Rattrap fundamentally improves the ofﬂoading performance. Our evaluation shows that Rattrap not only reduces the startup time of runtime environments and shows an average speedup of 16x, but also saves a large amount of system resources such as 75% memory footprint and at least 79% disk capacity. Moreover, Rattrap improves ofﬂoading response by as high as 63% over the cloud platform based on VM, and thus saving the battery life.

------------

