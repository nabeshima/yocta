SUMMARY = "Octa files"
DESCRIPTION = "Basic init system drived from tiny-init"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r1"

DEPENDS = "core-image-initramfs"

# This dependency won't be resolved... bitbake core-image-initramfs should be done before..
# RDEPENDS_${PN} = "core-image-initramfs"

do_install[depends] += "core-image-initramfs:do_image_complete"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-u 500 -g 500 -d /home/octa -r -s /bin/sh octa"
GROUPADD_PARAM_${PN} = "-g 500 octa"

CONFDIR = "${TOPDIR}/conf"

# To be used as a part of filename. It is determined in do_install(), and then also used in FILES to go into rootfs.

SRC_URI = "file://startup-scripts/inittab \ 
	   file://startup-scripts/rcS \  
	   file://startup-scripts/rcK \   
	   file://startup-scripts/sysinit \  
	   \   
	   \
	   file://install-scripts/installer.sh \
	   file://install-scripts/profile \
	  "

S = "${WORKDIR}"

do_local_fetch() {
    :
}

addtask local_fetch before do_fetch

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
	# Install files necessary for the basic usage with dns and sshd even if booted from live DVD

	install -m 0755 -d ${D}/etc

	ln -sf ../run/resolv.conf ${D}/etc/resolv.conf

	install -o octa -g octa -m 0755 -d ${D}/home/octa

	install -m 0755 -d ${D}/usr/Octa/startup-scripts
	install -m 0755 ${S}/startup-scripts/* ${D}/usr/Octa/startup-scripts/
	install -m 0644 ${TMPDIR}/work/genericx86-poky-linux/core-image-initramfs/1.0-r0/deploy-core-image-initramfs-image-complete/core-image-initramfs-genericx86.cpio.gz ${D}/usr/Octa/startup-scripts/initramfs
    
	#
	# Copy scripts to install the system to the installer image.
	#
	install -m 0755 -d ${D}/usr/Octa/install-scripts
	install -m 0755 ${S}/install-scripts/installer.sh ${D}/usr/Octa/install-scripts/

	# Overwrite profile file to start installer.sh just after login.
	#
	install -m 0755 -d ${D}/home/root
	install -m 0755 ${S}/install-scripts/profile ${D}/home/root/.profile

}

FILES_${PN} = "/etc \
            \
            /etc/resolv.conf \
	    \
	    /home/octa \
	    \
	    /usr \
	    /usr/Octa \
	    \
	    /usr/Octa/startup-scripts \
	    /usr/Octa/startup-scripts/* \
	    /usr/Octa/install-scripts \
	    /usr/Octa/install-scripts/* \
            \
            /home/root \
            /hoem/root/.profile \
	   "

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
