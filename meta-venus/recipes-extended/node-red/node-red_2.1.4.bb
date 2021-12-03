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

SRC_URI[md5sum] = "5df0d272465831aaf0e22457e58cac52"
SRC_URI[sha256sum] = "e840fa1c7d7b25b0565551ad3582e24214cefb772a9af0238a9f7dac94f4dabb"

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
