## FastBuild: Accelerating Docker Image Building for Efﬁcient Development and Deployment of Container

------------

*by Zhuo Huang, Song Wu, Song Jiang and Hai Jin*
*Published on MSST 2019*

------------

![FastBuild](https://cdn.img.wenhairu.com/images/2019/10/02/8r7sU.png "FastBuild")

------------

Docker containers have been increasingly adopted on various computing platforms to provide a lightweight virtualized execution environment. Compared to virtual machines, this technology can often reduce the launch time from a few minutes to less than 10 seconds, assuming the Docker image has been locally available. However, Docker images are highly customizable, and are mostly built at run time from a remote base image by running instructions in a script (the Dockerﬁle). During the instruction execution, a large number of input ﬁles may have to be retrieved via the Internet.The image building may be an iterative process as one may need to repeatedly modify the Dockerﬁle until a desired image composition is received. In the process, each ﬁle required by an instruction has to be remotely retrieved, even if it has been recently downloaded. This can make the process of building, and launching a container unexpectedly slow.

To address the issue,we propose a technique,named Fast Build, that maintains a local ﬁle cache to minimize the expensive ﬁle downloading.Bynon-intrusively intercepting remote ﬁle requests, and supplying ﬁles locally, FastBuild enables ﬁle caching in a manner transparent to image building. To further accelerate the image building, FastBuild overlaps operations of instructions’ execution, and writing intermediate image layers to the disk. We have implemented FastBuild. Experiments with images and Dockerﬁles obtained from Docker Hub show that our system can improve building speed by up to 10 times, reduce downloaded data by 72%.

------------
