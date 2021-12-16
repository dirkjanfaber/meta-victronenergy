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

# Careful! When updating the version, also npm-shrinkwrap.json must be updated

SRC_URI[sha256sum] = "88219a92cb8c0ec6b96ef3d83780b51c4bc858fc4efac91ee3fce2b8e79bdc30"

inherit npmve
