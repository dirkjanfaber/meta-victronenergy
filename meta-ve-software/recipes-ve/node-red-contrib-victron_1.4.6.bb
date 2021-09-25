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

# Careful! When updating the version, the npm-shrinkwrap.json must be updated as well; it defines
# the version of all dependencies, and they are many.

SRC_URI[sha256sum] = "490fe014624ba9bb1f87fd37ac708561caa0ddbf686680bdc5b9ab064cb0554f"

inherit npmve
