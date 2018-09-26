SUMMARY = "JSON Parser"
DESCRIPTION = "jq"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "flex-native bison-native" 
SRC_URI = "http://stedolan.github.io/${BPN}/download/source/${BP}.tar.gz"

SRC_URI[md5sum] = "0933532b086bd8b6a41c1b162b1731f9" 
SRC_URI[sha256sum] = "c4d2bfec6436341113419debf479d833692cc5cdab7eb0326b5a4d4fbe9f493c" 

inherit autotools 
EXTRA_OECONF = "--disable-docs"

