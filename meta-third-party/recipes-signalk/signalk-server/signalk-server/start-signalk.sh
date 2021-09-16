#!/bin/bash
exec 2>&1

VENUS_LARGE=$(get-large-data -t)/venus-large || { svc -d .; exit 1; }
CONF="$VENUS_LARGE/signalk"
PLUGINCONF="$CONF/plugin-config-data"
DEFAULTCONF="/usr/lib/node_modules/signalk-server/defaults"

# Install the default settings, in case there are no settings yet
mkdir -p $PLUGINCONF

if [ ! -f "$CONF/settings.json" ]; then
    cp "$DEFAULTCONF/settings.json" "$CONF"
fi

if [ ! -f "$PLUGINCONF/venus.json" ]; then
    cp "$DEFAULTCONF/venus.json" "$PLUGINCONF"
fi

if [ ! -f "$CONF/defaults.json" ]; then
    cp "$DEFAULTCONF/defaults.json" "$CONF"
fi

if [ ! -f "$CONF/logo.svg" ]; then
    cp "$DEFAULTCONF/logo.svg" "$CONF"
fi

export DISABLED_PLUGIN_UPDATES="signalk-venus-plugin"
export SIGNALK_DISABLE_SERVER_UPDATES=true
export PRESERIALCOMMAND="/opt/victronenergy/serial-starter/stop-tty.sh"

exec /usr/lib/node_modules/signalk-server/bin/signalk-server -c $CONF
