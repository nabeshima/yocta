FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"


SRC_URI += "file://drivers_usb_core_hcd.patch \
	    file://drivers_tty_tty_buffer.patch \
	    file://fragment.cfg \
	   "
