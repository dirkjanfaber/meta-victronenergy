#! /bin/sh

ID_FILE=/data/venus/unique-id

mkdir -p $(dirname ${ID_FILE})
get-unique-id >${ID_FILE}

check_compat() {
    tr '\0' '\n' </sys/firmware/devicetree/base/compatible |
        grep -Eqx -e "$1"
}

gen_config() (
    conf=$1

    test -e ${conf}.in || return

    while IFS=% read val compat; do
        if [ -n "$compat" ]; then
            check_compat $compat || continue
        fi

        echo ${val}
    done <${conf}.in >/run/venus/$(basename ${conf})
)

CONFIGS="
    backlight_device
    blank_display_device
    canbus_ports
"

mkdir -p /run/venus
for file in ${CONFIGS}; do
    gen_config /etc/venus/${file}
done

CAN_DEVS=$(cat /etc/venus/canbus_ports)
CAN_TEMPLATE_DIR="/opt/victronenergy/service-templates/can"
SERVICE_DIR="/run/service"

mkdir -p "$SERVICE_DIR"

add_service() {
    svc=$1
    dev=$2

    SERVICE="$SERVICE_DIR/$svc.$dev"

    # check if service already exists
    test -d "$SERVICE" && return 0

    # copy service template
    cp -a "$CAN_TEMPLATE_DIR/$svc" "$SERVICE"

    # patch run files for CAN-bus device
    sed -i "s:DEV:$dev:" "$SERVICE/run"
    sed -i "s:DEV:$dev:" "$SERVICE/log/run"

    # symlink into /service for svscan to find
    ln -s "$SERVICE" "/service/$svc.$dev"
}

find $CAN_TEMPLATE_DIR -mindepth 1 -maxdepth 1 -type d | while read name; do
    for dev in $CAN_DEVS; do
        add_service $(basename $name) $dev
    done
done
