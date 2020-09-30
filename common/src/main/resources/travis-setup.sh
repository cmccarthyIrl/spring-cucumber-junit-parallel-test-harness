#!/bin/sh
export $(dbus-launch)
export NSS_USE_SHARED_DB=ENABLED
export service dbus start
firefox &