DESCRIPTION = "Node-RED"
HOMEPAGE = "http://nodered.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34f8c1142fd6208a8be89399cb521df9"

RDEPENDS_${PN} += "\
    bash \
"

SRC_URI = "\
    https://registry.npmjs.org/signalk-server/-/${PN}-${PV}.tgz;unpack=0 \
    file://start-signalk.sh \
    file://settings.json \
    file://logo.svg \
    file://venus.json \
    file://defaults.json \
"

SRC_URI[md5sum] = "8d31cbddd2a80075a45480d6a6a72fe9"
SRC_URI[sha256sum] = "dce512ccce4f5ddc35aa9799240f180977f1c48592ccd69f7634e296ac91ad54"

inherit npmve
inherit daemontools

DAEMON_PN = "${PN}"
DAEMONTOOLS_SCRIPT = "HOME=/home/root exec ${bindir}/signalk-server"
DAEMONTOOLS_DOWN = "1"
DAEMONTOOLS_LOG_DIR = "${DAEMONTOOLS_LOG_DIR_PREFIX}/signalk-server"

do_install_append() {
    mkdir ${D}${bindir}
    install -m 0755 ${WORKDIR}/start-signalk.sh ${D}${bindir}/signalk-server

    # this folder keeps the default settings. start-signalk.sh copies them
    # to the data partition on first boot.
    install -d ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/settings.json ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/defaults.json ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/venus.json ${D}${NPM_INSTALLDIR}/defaults
    install -m 0644 ${WORKDIR}/logo.svg ${D}${NPM_INSTALLDIR}/defaults

    npm install \
        --prefix "${D}${NPM_INSTALLDIR}" \
        --arch=${NPM_ARCH} \
        --target_arch=${NPM_ARCH} \
        --production \
        --no-bin-links \
        signalk-venus-plugin@1.25
}
