FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit daemontools

SRC_URI += "\
    file://0001-pppd-add-support-for-defaultroute-metric-option.patch \
    file://0002-pppd-clear-resolv.conf-on-link-down.patch \
"

DAEMONTOOLS_RUN = "${sbindir}/pppd call provider nodetach"
DAEMONTOOLS_DOWN = "1"

do_install_append() {
    # remove useless files
    rm -fr ${D}${sysconfdir}/chatscripts
    rm -f ${D}${sysconfdir}/ppp/chap-secrets
    rm -f ${D}${sysconfdir}/ppp/pap-secrets
    rm -f ${D}${sysconfdir}/ppp/ip-up.d/*setupdns
    rm -f ${D}${sysconfdir}/ppp/ip-down.d/*removedns
}
