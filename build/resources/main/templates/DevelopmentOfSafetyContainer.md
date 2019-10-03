<center><strong>Current status of container technology applications and security issues</strong></center>

As is known to all, containerization, microservitization and cloud biochemistry are the trends of current IT system evolution. According to the Portworks and Aqua Security survey, most of the businesses surveyed are either using or considering containers.

Morning came and the speech of Chris chat just now, he also mentioned that KubeCon in San Diego at the end of this year are expected to have twelve thousand people to attend, he also noted that **cloud the original biochemical is not only a change for the architecture of the application, but also promoted the richer service development, the IT system service scale of ascension is speeding up.**

But despite the sizzle of container technology, there are challenges. 

As you can see, about half of the enterprises, especially those running more than 100 containers, believe that their containers have security holes. Of course, a large proportion of the enterprises do not confirm whether their containers have security holes. As we all know, safety is not only a technical issue, but also a confidence issue, which is what the pink word means, because 42 percent of the respondents were unable to fully embrace the container ecology because of container security concerns.

Of course, I would say that being concerned about safety is a good sign, because you only really look at a technology from a security perspective when you're ready to actually use it in a production environment. Similar to other areas, container security is an end-to-end technology, from the security and integrity of the container image itself, to the security of the hardware and software platform infrastructure that runs the container, to the security of the container runtime engine, which is likely to be the shortest board.

<center><strong>Aliyun  inside and outside the safety container technology development process</strong></center>

When it comes to the security of the container, we can come back to the container of the intense competition period and the warring states period, don't talk about distant FreeBSD Jail and Solaris zones, we from the final into this group of Namespaces and cGroups Linux Kernel, the container technology is, in fact the same from the view of process scheduling, extension of Kernel function, advantage, operating interface is Linux, very convenient, cost is low, can be used to no burden to set on the outside of the existing application to build the isolation environment, And it is a pure software solution that does not conflict with other layers of physical and virtual machines.

However, the problem with it is that it is still a part of the Linux Kernel, and existing Linux isolation problems cannot be eliminated and may even be amplified by new features. <u>Therefore, when Linus was interviewed on Keynote at LinuxCon in Seattle in 2015, he directly said that there was no way to solve the Bug and there must be an isolation layer for security.</u> (The origin: *The only real **solution to security** is to admit that bugs happen, and then mitigate them by having multiple layers*)

![](https://mmbiz.qpic.cn/mmbiz_png/yvBJb5Iiafvm0c5jNDu01p0sGuMTaTibfPPphMdu80jibgtFmhjI1BibV8cV0VcMVZXMMS1jlU57xRpqFP3pRsteJw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

The isolation layer, which lets the application container run on top of its own kernel and not share with other containers. The simplest solution is to run the container in the virtual machine (left 1), which does not require any changes to the software stack, but allows the same user's container to run in its own virtual machine. The problem with this approach is that, in addition to the additional overhead, there are two layers of maintenance complexity.

Another kind of long standing and well established independent kernel is unikernel (right 1), make application to bring their own kernel, the benefits of this approach is the most simplified LibOS can not only have smaller cost, also can have smaller attack surface, but it is more widely used of the problem is that it often need to modify the application compatibility is always the biggest obstacle to a platform technology is adopted.

So, according to the safety of unmodified container plan falls in the middle two options - MicroVM and processes on the virtualization, the former is the use of the lightweight and tailoring of the Linux kernel virtual machine, on the premise of guarantee the compatibility try to reduce the overhead of running and maintenance, while the latter is to use a specific kernel to provide Linux ABI, virtualization process running environment directly, as far as possible for maximum compatibility for Linux applications.

Kata Containers is one such MicroVM secure container solution. First, for applications, it is a runc-compliant container runtime engine that can be called by Kubernetes via containerd/ cri-o and can run any Docker Image or OCI Image directly. However, unlike runC, which USES hardware virtualization capabilities, what is directly facing the user is no longer the kernel of the host, but a separate kernel installed in the virtual machine. Even if there is an unknown security vulnerability that causes the kernel to be attacked, the attacker still cannot easily break through the sandbox built by virtualization technology.


The Kata Containers project is open source by us and Intel in 2017, and last April became the OpenStack foundation's first top open infrastructure project in seven years. As a community-based project, this project also has many developers other than aliyun and ant. Currently, the project is discussing the roadmap of version 2.0, and everyone is welcome to contribute code and requirements to the project.

Technically, in the Kubernetes ecosystem, Kata Containers can dock with CRI daemons like runC like containerd and cri-o. Currently, the interface we recommend is Shim V2 API first introduced in containerd community last summer vacation. This API is also supported by cri-o. There is only one additional Kata helper process per Pod, regardless of how many containers are in the Pod, which is also friendly to the host scheduler.

Shim will pass the vsock MicroVM inside control agent to manage the inside of the Pod OCI container, here, the community version of the Kata support the VMM includes Qemu and AWS open-source FireCracker, the former more feature-rich, better compatibility, the latter is more light, according to our alibaba's ", and should be, and in "spirit, which one you don't need to give up, use Kubernetes RuntimeClass, You can specify the VMM to be used for each Pod. For details on the How to class, you can check out our documentation on GitHub or discuss it on the Slack channel. Don't forget to open an issue if you have a problem. This is a great support to the community.

![](https://mmbiz.qpic.cn/mmbiz_png/yvBJb5Iiafvm0c5jNDu01p0sGuMTaTibfPOuZzD0ib8aePmalSY5nA2Do6UHEOLpXT5nl8LXsYl3kY024OBahuEibg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

Similar Container scheme based on MicroVM techniques are actually there are many, the Hyper - V Container and familiar with Microsoft recently integrated WSL2 in Windows, says from the number, that is the mainstream of the current the most important reason is that for the average Docker Image of the perfect compatibility, and this scheme in the open source solution, of course, is with the Kata for us. Of course, there are many bright spots in the process-based virtualization scheme, among which the biggest bright spot is of course Google's open source gVisor, because the technology Leader of Google's project when it was open source is now my boss, ZhengYu He.

<center><strong>History, current situation and future of aliyun security sandbox</strong></center>

During the six years from 2013 to 2019, container technology and ecology made great strides forward every year, and experienced the rapid development cycle from putting forward technical ideas, constructing cooperative ecology to commercial application, reaching the stage of "turning point" mentioned in the opening speech of BBS.

Let's briefly review the development history of aliyun safety container and safety sandbox technology.

+ More than a year after the Docker concept was proposed, the industry began to jointly promote container technology and ecology in 2015. Aware of runC's limitations, aliyun, hyper-sh, and Intel have started to build a secure operating environment for the container based on virtual machine technology.

+ After one year of research and development, safety container technology began to enter the production environment in 2016. VLinux technology developed by aliyun is implemented in the MaxCompute scenario, and hyper-sh also provides container services based on runV. One of the most important things that happened this year was that K8s defined the CRI interface specification for docking multiple forms of container operating environment, thus providing a solid foundation for the integration of safe container and K8s ecology.

+ In 2017, Microsoft opened ACI container service, and Aliyun also developed virtual machine based container service.

+ In December 2017, hyper-sh runV project and Intel Clear Container project announced the merger of the Kata Container project to jointly promote the safe Container operating environment based on hardware virtualization to achieve "virtual machine security and Container speed".

+ In 2018, Aliyun opened container services based on virtual machines and started to develop secure sandbox technology based on MicroVM route. This year Google open source gVisor and AWS open source firecracker.

+ In 2019, Aliyun security sandbox technology will be launched commercially, supporting ECI, ACK, SAE edge computing and other services. Intel created the Cloud Hypervisor project to create dedicated hypervisors for Cloud native scenarios.


From the end of 2017, Kata, gVisor, Firecracker, Nabla, Cloud Hypervisor and other open source security container technologies keep emerging, and the technology enters the blowout period. We are very pleased that the Hyper team joined alibaba digital economy this year to build a secure and reliable container environment for our customers.

There are different container runtime technologies mentioned in the introduction, and there may be a question in your mind. What are the relationships and differences between these technologies?


I take the common rent in life as an example, so that we can understand the difference of container runtime.

+ First of all, talking about the Fircracker, Firecracker is not a container runtime, but a lightweight VMM

+ RunC is similar to group rentals, with simple decoration and high utilization rate, but less sound insulation, security and occupancy experience

+ Kata 1.x/gVisor with containerd/ cri-o access to Kubernetes is similar to a Shared room, where each tenant has its own bedroom, but the living room, kitchen, and bathroom are Shared

+ **Aliyun security sandbox is similar to hotel-style apartments, providing a full-featured, standardized check-in experience with room service**

+ **VM + containerd is similar to renting an ordinary house with large Shared area and may need to decorate it by yourself**

**Ali cloud security sandbox is committed to building hotel-style apartments, providing customers with move-in container services.**

<center><strong>The technological efforts that we are making and the community as a whole</strong></center>

As we all know, alibaba has both businesses oriented towards personal consumption such as taobao, Tmall and autonavi, as well as services oriented towards enterprises such as aliyun and studs. As the underlying underlying technology, secure sandbox technology needs to support a variety of application scenarios. By integrating various business scenarios, three typical application modes can be roughly concluded:

+ The first is a container service for public cloud services. In this application scenario, we need to ensure not only the security of the infrastructure, but also the strict security of customer data and the isolation between tenants. We need to offer serviced apartments instead of group or Shared apartments.

+ The second scenario is where you need to execute both trusted and untrusted code on a single server. For example, if you need to execute user-uploaded code, an executable without source code, or unaudited open source code. This is the most typical use of a secure sandbox, where you build an execution environment with security boundaries and restrict untrusted code to this restricted execution environment to protect other components of the system.

+ The third scenario is a mixed deployment of multiple services. In order to improve the utilization rate of resources, one of the main ideas is to mix online business and offline business on the same server and use the remaining resources of online service to serve offline tasks. This is a challenging task, and the traditional approach is to ensure the online service experience by reserving enough resources for the online task. Then, how to ensure the online task service experience under the mixed ministry? The idea is also simple: give priority to your online business. However, this is not enough. Due to the Shared kernel, memory allocation and IO scheduling, offline tasks often interfere with online tasks. Therefore, offline tasks are put into an independent safe sandbox to realize resource isolation, fault isolation and environment isolation.

![](https://mmbiz.qpic.cn/mmbiz_png/yvBJb5Iiafvm0c5jNDu01p0sGuMTaTibfP6sKZbq0EvGuXO4WYsGRjI8CYNEapmgEkqlnEM8oQqNHGXicibP7BicmWQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

Based on the above business requirements, we developed aliyun safe sandbox technology, based on the mature and stable infrastructure of aliyun and MicroVM technology route, to build safe, reliable, lightweight and ecologically compatible container runtime for business.

Aliyun security sandbox is a secure container runtime built based on MicroVM. First, it is a MicroVM based on hardware virtualization technology, which adopts a highly customized optimized hypervisor, a minimalist virtual machine device model, and VMM basically has no access to guest memory. Secondly, ali cloud security sandbox is also a container runtime, providing image distribution, image management, container network, container storage, fully compatible with OCI and CRI specifications.

The security of aliyun security Sandbox comes from the new security system language, the extremely small and controllable source code, the extremely simple device model, the highly customized Hypervisor and the secured aliyun Linux for Sandbox system.

So, what value can aliyun security sandbox bring to customers? In addition to safety and reliability, aliyun security sandbox will also bring fast start, extreme flexibility and low resource cost to customers. The actual test data shows that, when removing the time to download container images, aliyun safe sandbox starts container instances in less than 500 milliseconds, and starts more than 200 instances per second on a 96CPU system. The average memory resource of each microvm is less than 2.5m.

So what's the next challenge for security containers? What is the user's ideal container for running runtime?

+ Beyond virtual machine security

+ Native-like performance

+ Compatibility and ease of use like runC


Ant financial and aliyun have been active contributors to security containers in the past, and we will continue to work closely with the open source community in the years ahead.

We will open and community to make joint Kata Containers 2.0 roadmap, we in the container and the best practices in the field of cloud service feedback to the community, to general technical contribution to the Kata Contaienrs and Rust - the VMM community, guarantee the consistency of alibaba CloudSandbox and community, for the majority of users with the industry to create a safe, reliable, efficient and compatible with the ecological container runtime.

![](https://mmbiz.qpic.cn/mmbiz_png/yvBJb5Iiafvm0c5jNDu01p0sGuMTaTibfPTcrx2OuwiaKfatRRQHzK7I7zgLQ3rS716ias8qT3QMkRhAmjh70ps0MQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

