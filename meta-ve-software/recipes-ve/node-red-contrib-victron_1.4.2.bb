SUMMARY = "Victron Venus D-Bus plugin for node-red"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d7725b8b5e691085738d564afb312302"

NPM_ORG="victronenergy"

RDEPENDS_${PN} += "\
    node-red \
"

SRC_URI = "\
    https://registry.npmjs.org/@${NPM_ORG}/${PN}/-/${PN}-${PV}.tgz;unpack=0 \
    file://npm-shrinkwrap.json \
"

SRC_URI[md5sum] = "89f6345cd84b283f4e1fcb091fc3c656"
SRC_URI[sha256sum] = "94f61186cc2043c01ccce77f98bcead2989316045b341c9ec18bbade74d47f78"

inherit npmve
