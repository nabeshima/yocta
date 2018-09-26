DESCRIPTION = "Customizing core-image-rt."

#
# To include additional packages
#
CORE_IMAGE_EXTRA_INSTALL += " \
			 attr \
			 dbus \
			 dropbear \
			 ethtool \
			 expat \
			 flex \
			 libcap \
			 libdaemon \
			 ncurses \
			 libpam \
			 curl \
			 libidn \
			 iptables \
			 powertop \
			 e2fsprogs \
			 e2fsprogs-mke2fs \
			 e2fsprogs-e2fsck \
			 e2fsprogs-tune2fs \
			 parted \
			 pseudo \
			 grub \
			 util-linux-fsck \
			 jq \
			 wireless-tools \
			 usbutils \
			 procps \
			 wpa-supplicant \
			 "

#
# To include original packages
#
CORE_IMAGE_EXTRA_INSTALL += " \
			 yocta-files \
			 "

#
# To include external kernel drivers
# r8168 is for Pico821 ethernet chip
#
CORE_IMAGE_EXTRA_INSTALL += " \
			 r8168-driver \
			 linux-firmware-ralink \
			 "

#
# To include kernel drivers in kernel tree
#
IMAGE_INSTALL += " kernel-modules"


#
# To include kernel source into sdk
#
TOOLCHAIN_TARGET_TASK_append = " kernel-devsrc"

LICENSE = "MIT"