SUMMARY = "Kernel module for Realtek r8111/8168"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r2"

# DEPENDS = "linux-yocto-rt"
# This is instead of DEPENDS = "virtual/kernel"
do_install[depends] += "virtual/kernel:do_compile_kernelmodules"

SDKDIR = "/opt/poky/2.5.1"
ENVFILE = "${SDKDIR}/environment-setup-core2-32-poky-linux"

R8168_BASENAME = "r8168-8.045.08"

SRC_URI = "file://${R8168_BASENAME}.tar.bz2 \
          "


S = "${WORKDIR}"

# KERNELDIR = "${TOPDIR}/tmp/work-shared/${MACHINE}/kernel-source"
KERNELDIR = "${SDKDIR}/sysroots/core2-32-poky-linux/usr/src/kernel"

KERNEL_VERSION = "${@get_kernelversion('${KERNELDIR}/include/generated/utsrelease.h')}"

# This function comes from linux-kernel-base.bbclass
def get_kernelversion(p):
    import re

    try:
        f = open(p, 'r')
    except IOError:
        return None

    l = f.readlines()
    f.close()
    r = re.compile("#define UTS_RELEASE \"(.*)\"")
    for s in l:
        m = r.match(s)
        if m:
            return m.group(1)
    return None


do_compile() {
    :
    # do_unpack unpacks automatically...
    # tar xjf ${R8168_BASENAME}.tar.bz2

    . ${ENVFILE}

    cd ${R8168_BASENAME}/src
    PWD=`pwd`

    KERNELDIR=${KERNELDIR} make clean
    KERNELDIR=${KERNELDIR} make modules
}

do_clean() {
    :
    rm -rf ${R8168_BASENAME}
}

do_cleanall() {
    :
    rm -rf ${R8168_BASENAME}
}

do_install() {
    :
    #  Target modules_install of src/Makefile of r8168 module sorce code does not work correctlly for yocto
    install -m 0755 -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${R8168_BASENAME}/src/r8168.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net
}

FILES_${PN} = "/lib \
	    /lib/modules \
	    /lib/modules/${KERNEL_VERSION} \
	    /lib/modules/${KERNEL_VERSION}/kernel \
	    /lib/modules/${KERNEL_VERSION}/kernel/drivers \
	    /lib/modules/${KERNEL_VERSION}/kernel/drivers/net \
	    /lib/modules/${KERNEL_VERSION}/kernel/drivers/net/r8168.ko \
              "

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
