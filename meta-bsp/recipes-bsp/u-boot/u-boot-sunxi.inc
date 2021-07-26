LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

COMPATIBLE_MACHINE = "sunxi"

PV = "2018.05-sunxi-v12"

SRC_URI = "git://github.com/victronenergy/u-boot.git;protocol=https;branch=mans/sunxi"
SRCREV = "${AUTOREV}"
PV_append = "+git${SRCPV}"
S = "${WORKDIR}/git"
