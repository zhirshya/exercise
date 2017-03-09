#!/bin/zsh

#todo find *.part files and download separately by extracting ID
#find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
#extract the substring of 11 characters between last/penultimate hyphen/dash(-) and the first period(.) after that

lockpoweroff_screen="on"
shutdown_timeout="NOW"
#shutdown_timeout=0

while getopts l:s: opt;do
	case "$opt" in
	l) lockpoweroff_screen="$OPTARG";; #0(zero) or "off"
	s) shutdown_timeout="$OPTARG";;
	\?) # unknown flag
		echo >&2 \
		"usage: $0 [-l [off|0]] [-s positive_integer]"
		exit 1;;
	*) echo "default case of getopts, nothing to do";;
	esac
done
shift $((OPTIND-1))

if [[ $lockpoweroff_screen != off ]] && [[ $lockpoweroff_screen != 0 ]];then
#if ! ( [[ $lockpoweroff_screen == off ]] || [[ $lockpoweroff_screen == 0 ]] || (($lockpoweroff_screen == 0)) );then #NOT work,DANGER! NO get back!
	dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock
	xset dpms force off
fi

typeset -F SECONDS
echo "(start)«$(\date)»"

xt_code=-1
while [[ $xt_code -ne 0 ]];do
  find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -a {} +
  xt_code=$?
  echo "exit code(find...-execdir youtube-dl...{} +):$xt_code"
done

echo "(end)«$(\date)»"
echo '$(time)':$(time)
echo '$(times)':$(times)
#or add a trap on EXIT, that way, times will be called whenever the shell exits and the exit status will be preserved.
#trap times EXIT
#bash, ksh and zsh have a $SECONDS special variable that counts the number of seconds since the shell was started. In both zsh and ksh93, that variable can also be made floating point (with typeset -F SECONDS) to get more precision. This is only wall clock time, not CPU time.
#typeset -F SECONDS
echo '$SECONDS':$SECONDS
#http://unix.stackexchange.com/questions/52313/how-to-get-execution-time-of-a-script-effectively

#Failed to parse time specification: NOW
if [[ $shutdown_timeout =~ [nN][oO][wW] ]] && shutdown_timeout="now" || [[ $shutdown_timeout =~ [0-9]+ ]];then
	sudo shutdown -P -f $shutdown_timeout
else
	exit $xt_code
fi
