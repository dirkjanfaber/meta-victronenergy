#!/bin/bash
exec 2>&1

NODE_RED="/data/home/root/.node-red"
DATA_MODULES="$NODE_RED/node_modules"
VICTRON="$DATA_MODULES/@victronenergy"
DEFAULTCONF="/usr/lib/node_modules/node-red/defaults"

get_setting() {
    dbus-send --print-reply=literal --system --type=method_call \
              --dest=com.victronenergy.settings "$1" \
              com.victronenergy.BusItem.GetValue |
        awk '{ print $3 }'
}

echo "*** Waiting for localsettings..."

while true; do
    mode=$(get_setting /Settings/Services/NodeRed) && break
    sleep 1
done

case $mode in
    0)
	echo "*** Node-RED disabled in settings, starting anyway"
	safe=
	;;
    1)
	echo "*** Starting in normal mode"
	safe=
	;;
    2)
	echo "*** Starting in safe mode"
	safe=--safe
	;;
    *)
	echo "*** Invalid setting $mode, disabling"
	svc -d .
	exit 1
	;;
esac

if [ ! -d $NODE_RED ]; then
    mkdir -p "$NODE_RED"
    mkdir -p "$DATA_MODULES"
    cp "$DEFAULTCONF/settings.js" "$NODE_RED"
    sed -i "s/a-secret-key/$(openssl rand -hex 32)/g" "$NODE_RED/settings.js"
fi

if [ -d $VICTRON ]; then
    rm -rf $VICTRON
fi

if [ ! -f $VICTRON ]; then
    touch $VICTRON
fi

export TZ=$(get_setting /Settings/System/TimeZone)

exec /usr/lib/node_modules/node-red/red.js $safe --userDir "${NODE_RED}" "$@"
