# note: a profile select the bitrate, hence serves are only started after
# the bitrate is set and hence down by default.
DAEMONTOOLS_SERVICE_SYMLINK = "0"
DAEMONTOOLS_DOWN = "1"
DAEMONTOOLS_LOG_DIR ?= "${DAEMONTOOLS_LOG_DIR_PREFIX}/${PN}.DEV"
DAEMONTOOLS_SERVICE_DIR = "${DEAMONTOOLS_COMMON_TEMPLATES_DIR}/can/${PN}"

inherit daemontools

FILES_${PN} += "${DEAMONTOOLS_COMMON_SERVICES_DIR}"
