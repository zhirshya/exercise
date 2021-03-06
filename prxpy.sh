#!/bin/bash
### BEGIN INIT INFO
# Provides:          prxpy
# Required-Start:    $remote_fs $syslog $network
# Required-Stop:     $remote_fs $syslog
# X-Stop-After:
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: 
# Description:       
### END INIT INFO

user=nobody
group=nogroup
chuid=nobody
pid=/var/run/prxpy.pid
name=prxpy
python=/usr/bin/python
logfile=/tmp/prxpy.log
prog=/mnt/1/vmshr/prxpy/local/proxy.py

export PYTHON_EGG_CACHE=/tmp/.python-eggs

# Get lsb functions
. /lib/lsb/init-functions

case $1 in
    start)
	log_begin_msg "Starting prxpy..."
	start-stop-daemon --start --quiet --oknodo \
		--user "$user" --name "$name" --pidfile \
		"$pid" --make-pidfile --chuid "$chuid"  \
		--startas "$python" -- "$prog" >/dev/null 2>$logfile &
	log_end_msg $?
	;;
    stop)
	log_begin_msg "Stopping prxpy..."
	start-stop-daemon --stop --pidfile $pid &>/dev/null || true
	log_end_msg $?
	rm -rf $pid $logfile
        ;;
    restart)
	$0 stop
	sleep 1
	$0 start
        ;;
   status)
	status_of_proc -p $pid "$name" "$name"
	;;
   *)
	log_success_msg "Usage: service prxpy {start|stop|restart}"
    ;;
esac
