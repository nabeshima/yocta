# yocta

## Objective

Yocta is an example of conf files and recipes for Yocto Linux.

It includes diffconfig of linux kernel and busybox for a generic x86 board.

## Setup

Ubuntu 16.04.5 LTS is the host.

- Install necessary packages by,

```
sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib \
     build-essential chrpath socat cpio python python3 python3-pip python3-pexpect \
     xz-utils debianutils iputils-ping emacs xterm 
```

- Download and extract the Yocto Linux (sumo) source code.

```
cd <arbitrary directory>
wget http://downloads.yoctoproject.org/releases/yocto/yocto-2.5.1/poky-sumo-19.0.1.tar.bz2
tar xvjf poky-sumo-19.0.1.tar.bz2
```

- Loard env. variables and change directory to `build/`

```
cd poky-sumo-19.0.1
. oe-init-build-env
```

- Clone this project

```
git clone https://github.com/nabeshima/yocta.git
```

- Set local.conf

```
mv conf/local.conf conf/local.conf.orig
ln -s ../yocta/local.conf conf/local.conf
```

- Add layer

```
bitbake-layers add-layer yocta/meta-yocta
```

## Build


- Build the SDK

Comment out the following lines in `meta-yocta/recipes-rt/images/core-image-rt.bbappend`.

```
CORE_IMAGE_EXTRA_INSTALL += " \
			 r8168-driver \
			 linux-firmware-ralink \
			 "
```

This is because these packages require the relevant SDK and the kernel source to build.
This comment out should be reveted after installation of SDK.

Then, do,

```
bitbake core-image-rt -c populate_sdk
```


- Install the SDK

```
sudo ./tmp/deploy/sdk/poky-glibc-i686-core-image-rt-core2-32-toolchain-2.5.1.sh
cd /opt/poky/2.5.1/
. environment-setup-core2-32-poky-linux
cd sysroots/core2-32-poky-linux/usr/src/kernel
sudo make silentoldconfig scripts
```

Then it becomes able to build your own application software on the host such as out-of-tree drivers.

- Build the installer images

```
bitbake core-image-rt
```

The installer image will be placed at `tmp/deploy/images/genericx86/core-image-rt-genericx86.iso` (Live DVD image) and `tmp/deploy/images/genericx86/core-image-rt-genericx86.hddimg` (Bootable mass storage image).



