SUMMARY = "Set init file for initramfs based on initramfs-live-boot_1.0.bb"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
DEPENDS = "virtual/kernel"
RDEPENDS_${PN} = "udev udev-extraconf busybox"
SRC_URI = "file://init"

S = "${WORKDIR}"

do_install() {
        install -m 0755 ${WORKDIR}/init ${D}/init
        install -d ${D}/dev
        mknod -m 622 ${D}/dev/console c 5 1
}

FILES_${PN} += " /init /dev "

# Due to kernel dependency
PACKAGE_ARCH = "${MACHINE_ARCH}"
