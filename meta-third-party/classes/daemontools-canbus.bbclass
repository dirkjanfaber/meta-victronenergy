# note: a profile select the bitrate, hence serves are only started after
# the bitrate is set and hence down by default.
DAEMONTOOLS_SERVICE_SYMLINK = "0"
DAEMONTOOLS_DOWN = "1"
DAEMONTOOLS_LOG_DIR ?= "${DAEMONTOOLS_LOG_DIR_PREFIX}/${PN}.DEV"
DAEMONTOOLS_SERVICE_DIR = "${DAEMONTOOLS_TEMPLATE_DIR}"

inherit daemontools

do_per_canbus_service() {
    # make sure the service dir exists and is empty
    if [ -e "${D}${DAEMONTOOLS_SERVICES_DIR}" ]; then
        rm -rf "${D}${DAEMONTOOLS_SERVICES_DIR}"
    fi
    install -d ${D}${DAEMONTOOLS_SERVICES_DIR}
    install -d ${D}${DEAMONTOOLS_COMMON_SERVICES_DIR}

    # make a service per CAN bus interface
    for dev in ${VE_CAN_PORTS}
    do
        SERVICE="${D}${DEAMONTOOLS_COMMON_SERVICES_DIR}/${PN}.$dev"
        cp -r ${D}${DAEMONTOOLS_SERVICE_DIR} "$SERVICE"

        # patch run files for CAN-bus device
        sed -i "s:DEV:$dev:" "$SERVICE/run"
        sed -i "s:DEV:$dev:" "$SERVICE/log/run"

        if [ ${DAEMONTOOLS_OVERLAYFS} = "0" ]; then
            ln -s "${DEAMONTOOLS_COMMON_SERVICES_DIR}/${PN}.$dev" "${D}${DAEMONTOOLS_SERVICES_DIR}/${PN}.$dev"
        fi
    done
}

addtask per_canbus_service after do_install before do_package
do_per_canbus_service[fakeroot] = "1"

FILES_${PN} += "${DEAMONTOOLS_COMMON_SERVICES_DIR}"
