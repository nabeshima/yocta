DESCRIPTION = "Customizing wpa_supplicant."

WPASUP_BASENAME = "wpa_supplicant-2.6"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


####
# "src_drivers_driver_nl80211_scan.patch" overwrites "hidden" SSID of APs with the requested SSID when it is reporeted 
# by a device driver via cfg80211 interface.
# When the reported SSID's length is 0 or it is the same-length string of NULL ('\0') as the requested SSID, this patch 
# deals it as "hidden" SSID and overwrites it with the requested SSID.
# It also hold the requrested SSID inside to support when APs are found by both of probe and beacon.
#
# If changing the requested SSID, wpa_supplicant should be killed and restart after modifying wpa.conf/wpa_supplicant.conf
#
# If applying this patch, wpa_supplicant can assume and deal it as possible AP having the requested SSID. Then wpa_supplicant can 
# try authentication and association.
# 
# Previously, this function was implemented in a kernel space driver, i.e. cfg80211. "net_wireless_scan.patch" was for it.
# The patch is not used now but it is left in the recipes-kernel directory for linux-yocto-rt package.
#

SRC_URI += " file://src_drivers_driver_nl80211_scan.patch;patchdir=${WORKDIR}/${WPASUP_BASENAME} \
	"

do_configure_append() {
    sed -i "s/#CONFIG_HT_OVERRIDES/CONFIG_HT_OVERRIDES/g" wpa_supplicant/.config
}
