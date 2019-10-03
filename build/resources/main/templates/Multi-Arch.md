## Building Multi-Arch Images for Arm and x86 with Docker Desktop

------------

*Forward from [https://engineering.docker.com/2019/04/multi-arch-images/)*

------------

On Wednesday April 24th, Docker announced a partnership with Arm to help accelerate adoption of containers into the massive Arm ecosystem. Today at DockerCon San Francisco 2019, Docker is releasing a tech preview of improved multi-architecture builds within Docker Desktop.

Docker Desktop is the de facto standard when it comes to developing containerized applications. This tech preview will open the rich Arm ecosystem to the millions of developers already using and developing in Docker Desktop. Not only will this simplify the development of container images for deployment on Amazon EC2 A1 Arm-based instances in the cloud, but it will help pave the way forward to the one trillion Arm based Edge & IoT devices around the world! Let’s dig into why we are doing this, how it all works, and how you can get started.
![](https://cdn.img.wenhairu.com/images/2019/10/04/8t0BR.png)

### 1. Why is Docker doing this?
In November 2018 Amazon announced EC2 A1 instances powered by AWS Graviton Processors that feature 64-bit Arm Neoverse cores and custom silicon designed by AWS. A1 EC2 instances are cost and performance optimized for scale-out workloads and offer up to 45% cost savings relative to other EC2 instances. With this Docker Desktop tech preview, Docker is making it easier than ever to develop containers on, and for Arm servers and devices. Using the standard Docker tooling and processes you are already familiar with you can start to build, push, pull, and run images seamlessly on different compute architectures. No changes to Dockerfiles or source code are needed to start building for Arm. Simply rebuild your image using the new features being released today. Finally, Docker is quickly expanding into Edge and IoT, and we see this as an important step in that process. Docker has always been about developers, and making things easy. That is at the heart of why we did this.

### 2. How does it work?
![](https://cdn.img.wenhairu.com/images/2019/10/04/8tSED.png)

Docker Desktop is available for macOS and Windows. It bundles and configures many things for users that make developing containers extremely easy. Docker Desktop ships with hypervisors for the host OS. The hypervisor is responsible for running a lightweight Linux kernel (LinuxKit), which is included as part of Docker Desktop. This fast and lightweight container OS comes packaged with the QEMU emulator, and comes pre-configured with binfmt_misc to run binaries of any supported architecture. Arm is committed to supporting Docker’s QEMU fork and will be helping to maintain this project. All patches will be upstreamed, but Docker Desktop will contain the latest emulation support. In the diagram above you can see QEMU emulation for the arm/v6, arm/v7 and arm64 Docker images.

Docker Desktop Edge release comes with a new CLI command called buildx. Buildx allows you to locally (and soon remotely) build multi-arch images, link them together with a manifest file, and push them all to a registry – with a single command.  With the included emulation, you can transparently build more than just native images! Buildx accomplishes this by adding new builder instances based on BuildKit, and leveraging Docker Desktops technology stack to run non-native binaries.

So why buildx? Let’s first start with the name. The x stands for experimental. In the future, when these new build features are stable and made generally available, we will drop the x and integrate these features directly into the existing docker build command. Note that as buildx is experimental, features and flags are subject to change.

### 3. Getting started！
If you don’t already have Docker Desktop, start by downloading it. Install it by following the installation instructions. Once installed, or if you already have Docker Desktop, you should see the Docker icon in your task tray, click preferences, and simply switch to the edge release.
![](https://cdn.img.wenhairu.com/images/2019/10/04/8tcct.png)

Verify you have version 2.0.4.0 (33772)+ by opening the “About Docker Desktop”in the drop down.
![](https://cdn.img.wenhairu.com/images/2019/10/04/8tg3S.png)

### 4. Examples！
By now I am sure you are interested in how to use these great new features. Let’s take a quick look at some examples. We will start by listing our builders.

    ~ ❯❯❯ docker buildx ls
	NAME/NODE DRIVER/ENDPOINT STATUS  PLATFORMS
	default * docker
	default default         running linux/amd64, linux/arm64, linux/arm/v7, linux/arm/v6
	
We are currently using the default builder, which is basically the old builder. Let’s create a new builder, which gives us access to some new multi-arch features.

	~ ❯❯❯ docker buildx create --name mybuilder
	mybuilder
	~ ❯❯❯ docker buildx use mybuilder
	~ ❯❯❯ docker buildx inspect --bootstrap
	[+] Building 2.5s (1/1) FINISHED
	=> [internal] booting buildkit                                                   2.5s
	=> => pulling image moby/buildkit:master                                         1.3s
	=> => creating container buildx_buildkit_mybuilder0                              1.2s
	Name:   mybuilder
	Driver: docker-container

	Nodes:
	Name:      mybuilder0
	Endpoint:  unix:///var/run/docker.sock
	Status:    running

	Platforms: linux/amd64, linux/arm64, linux/arm/v7, linux/arm/v6

Here I created a new builder instance with the name mybuilder, switched to it, and inspected it. Note that `--bootstrap` isn’t needed, it just starts the build container immediately. Next we will test the workflow, making sure we can build, push, and run multi-arch images. Let’s create a simple example Dockerfile, build a couple of image variants, and push them to Hub.

	~ ❯❯❯ mkdir test && cd test && cat <<EOF > Dockerfile
	FROM ubuntu
	RUN apt-get update && apt-get install -y curl
	WORKDIR /src
	COPY . .
	EOF
	~/test ❯❯❯ docker buildx build --platform linux/amd64,linux/arm64,linux/arm/v7 -t adamparco/demo:latest --push .
	[+] Building 6.9s (19/19) FINISHED
	...
	=> => pushing layers                                                             2.7s
	=> => pushing manifest for docker.io/adamparco/demo:latest                       2.2

That worked well! The `--platform` flag told `buildx` to generate Linux images for Intel 64-bit, Arm 32-bit, and Arm 64-bit architectures. The `--push` flag generates a multi-arch manifest and pushes all the images to Docker Hub. Let’s use `imagetools` to `inspect` what we did. 

	~/test ❯❯❯ docker buildx imagetools inspect adamparco/demo:latest
	Name:      docker.io/adamparco/demo:latest
	MediaType: application/vnd.docker.distribution.manifest.list.v2+json
	Digest:    sha256:2a2769e4a50db6ac4fa39cf7fb300fa26680aba6ae30f241bb3b6225858eab76

	Manifests:
	Name:      docker.io/adamparco/demo:latest@sha256:8f77afbf7c1268aab1ee7f6ce169bb0d96b86f585587d259583a10d5cd56edca
	MediaType: application/vnd.docker.distribution.manifest.v2+json
	Platform:  linux/amd64

	Name:      docker.io/adamparco/demo:latest@sha256:2b77acdfea5dc5baa489ffab2a0b4a387666d1d526490e31845eb64e3e73ed20
	MediaType: application/vnd.docker.distribution.manifest.v2+json
	Platform:  linux/arm64

	Name:      docker.io/adamparco/demo:latest@sha256:723c22f366ae44e419d12706453a544ae92711ae52f510e226f6467d8228d191
	MediaType: application/vnd.docker.distribution.manifest.v2+json
	Platform:  linux/arm/v7
	
The image is now available on Docker Hub with the tag adamparco/demo:latest. You can run a container from that image on Intel laptops, Amazon EC2 A1 instances, Raspberry Pis, and more. Docker pulls the correct image for the current architecture, so Raspberry Pis run the 32-bit Arm version and EC2 A1 instances run 64-bit Arm.

The SHA tags identify a fully qualified image variant, and you can run images targeted for a different architecture on Docker Desktop too. We can try running some of them using the SHA tag, and verifying they are in fact the architecture we expect.
	
	~/test ❯❯❯ docker run --rm docker.io/adamparco/demo:latest@sha256:2b77acdfea5dc5baa489ffab2a0b4a387666d1d526490e31845eb64e3e73ed20 uname -m
	aarch64
	~/test ❯❯❯ docker run --rm docker.io/adamparco/demo:latest@sha256:723c22f366ae44e419d12706453a544ae92711ae52f510e226f6467d8228d191 uname -m
	armv7l

In the above you can see that `uname -m` returned aarch64 and armv7l just as we would have expected, all of this building and running on my native macOS developer machine! I described above the technology stack and configurations needed to make all this possible, but let’s take a minute to reflect on how powerful and seamless the experience really is. Without Docker Desktop and buildx, doing this would have been much harder, and definitely more annoying.

I will do one last slightly more complex example, a python flask web application that displays the host architecture.

	~❯❯❯ docker buildx build -t adamparco/helloworld:latest --platform linux/arm64 --push github.com/adamparco/helloworld
	[+] Building 69.1s (11/11) FINISHED
	 => CACHED [internal] load git source github.com/adamparco/helloworld             0.0s
	 => [internal] load metadata for docker.io/library/python:3.7-alpine              0.5s
	 => [base 1/1] FROM docker.io/library/python:3.7-alpine@sha256:b3957604aaf12d969  3.6s
	...
	 => => pushing layers                                                             7.3s
	 => => pushing manifest for docker.io/adamparco/helloworld:latest                 0.3s

I’ve specified a single platform here, so this image is built for 64-bit Arm only. Now I will run it and publish some ports.

	~❯❯❯ docker run -p5000:5000 adamparco/helloworld:latest
	...
	 * Serving Flask app "hello" (lazy loading)
	 * Environment: production
	 * Debug mode: off
	 * Running on http://0.0.0.0:5000/ (Press CTRL+C to quit)
	 
Loading up my browser and pointing it to `localhost:5000` shows:

![](https://cdn.img.wenhairu.com/images/2019/10/04/8tPwC.png)

There you have it, simple to use Docker commands to build and run multi-architecture images. These are just some of the things you can do with buildx. In the near future we will be adding many more new and exciting features.

