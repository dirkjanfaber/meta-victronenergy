DESCRIPTION = "Node-RED"
HOMEPAGE = "http://nodered.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6f37569f5013072e9490d2194d10ae6"

RDEPENDS_${PN} += "\
    bash \
"

SRC_URI = "\
    https://registry.npmjs.org/${PN}/-/${PN}-${PV}.tgz;unpack=0 \
    file://npm-shrinkwrap.json \
    file://settings.js \
    file://start-node-red.sh \
    file://user-authentication.js \
"

SRC_URI[md5sum] = "d237b07ff9b5feb6cac72e3f09015201"
SRC_URI[sha256sum] = "941ed26cf9fdd86881db4a1ee0a0c01362c9f00caa500e01c0604012829622e6"

inherit npmve
inherit daemontools

DAEMON_PN = "${PN}"
DAEMONTOOLS_SCRIPT = "HOME=/home/root exec ${bindir}/node-red"
DAEMONTOOLS_DOWN = "1"
DAEMONTOOLS_LOG_DIR = "${DAEMONTOOLS_LOG_DIR_PREFIX}/node-red"

do_install_append() {
    # Remove hardware specific files
    rm ${D}${NPM_INSTALLDIR}/bin/node-red-pi

    # this folder keeps the default settings. start-node-red.sh copies them
    # to the data partition on first boot.
    install -d ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/settings.js ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/user-authentication.js ${D}${NPM_INSTALLDIR}

    # Startup script
    mkdir ${D}${bindir}
    install -m 0755 ${WORKDIR}/start-node-red.sh ${D}${bindir}/${PN}
}

FILES_${PN} += " \
    ${bindir}/node-red \
"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
