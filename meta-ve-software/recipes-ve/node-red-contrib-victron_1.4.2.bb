SUMMARY = "Victron Venus D-Bus plugin for node-red"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d7725b8b5e691085738d564afb312302"

NPM_ORG="victronenergy"

RDEPENDS_${PN} += "\
    node-red \
"

SRC_URI = "\
    https://github.com/victronenergy/${PN}/archive/refs/tags/v${PV}.tar.gz;downloadfilename=${PN}-${PV}.tgz;unpack=0 \
    file://npm-shrinkwrap.json \
"

SRC_URI[md5sum] = "687e04a6071fac61ba536541466cc91b"
SRC_URI[sha256sum] = "a61cb153b0878e72ac6fbb31fee238748fe6538c0003178e885f7355efa4bcee"

inherit npmve
