## Adaptive Resource Views for Containers

------------

*by Hang Huang ,Jia Rao ,Song Wu ,Hai Jin ,Kun Suo ,and Xiaofeng Wu*
*Published on HPDC 2019*

------------

![AdaptiveResourceViewsforContainers](https://cdn.img.wenhairu.com/images/2019/10/02/8rcNj.png "AdaptiveResourceViewsforContainers")

------------

As OS-level virtualization advances,container shave become a viable alternative to virtual machines in deploying applications in the cloud.Unlike virtual machines, which allow guest OSes to run atop virtual hardware, containers have direct access to physical hardware and share one OS kernel. While the absence of virtual hardware abstractions eliminates most virtualization over head,it presents unique challenges for containerized applications to efficiently utilize the underlying hardware. The lack of hardware abstraction exposes the total amount of resources that are shared among all containers to each individual container.Parallel run times(e.g.,*OpenMP*) and managed programming languages(e.g.,*Java*) that rely on OS-exported information for resource management could suffer from suboptimal performance.

In this paper,we develop a per-container view of resources to export information on the actual resource allocation to containerized applications.The central design of the resource view is a per-container `sys_namespace` that calculates the effective capacity of CPU and memory in the presence of resource sharing among containers.We further create a virtual `sysfs` to seamlessly interface user space applications with `sys_namespace`. We use two case studies to demonstrate how to leverage the continuously updated resource view to enable elasticity in the HotSpot JVM and OpenMP. Experimental results show that an accurate view of resource allocation leads to more appropriate configurations and improved performance in a variety of containerized applications.

------------
