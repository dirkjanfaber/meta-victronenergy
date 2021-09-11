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

SRC_URI[sha256sum] = "893913597ee5b751133815525ed62d04e164c73f7e69e44d9ac4889005216354"

inherit npmve
