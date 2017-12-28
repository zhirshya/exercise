#!/bin/zsh

#todo find *.part files and download separately by extracting ID
#find /mnt/0 ~ -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
#extract the substring of 11 characters between last/penultimate hyphen/dash(-) and the first period(.) after that

#http://unix.stackexchange.com/questions/52313/how-to-get-execution-time-of-a-script-effectively
trap time EXIT

#setup logging; todo: littered with tons of ^M(i.e. CTRL-M:carriage return, keyboard equivalent to \r), better log only errors with '2>>'
#exec  &>> /home/r/utv.sh.log

echo "(start)«$(\date)»"

#https://stackoverflow.com/questions/43158140/way-to-create-multiline-comments-in-bash
: <<'COMMENT'
format code: 43,18,22,36
22 best but too big
COMMENT

#http://www.bahmanm.com/blogs/command-line-options-how-to-parse-in-bash-using-getopt

while getopts "st:f:b:" opt; do
	case "$opt" in
	s) local lockpoweroff_screen="NoLockPoweroffScreen";;
	t) local shutdown_timeout="$OPTARG";;
	f) local format_num="$OPTARG";;
	b) local bandwidth_rate="$OPTARG";;
	\?) # unknown flag
		#echo >&2 \
		echo "usage: $0 [-s] [-t positive_integer] [-b positive_integer[kKmM]]"
		exit 1;;
	*) echo "default case of getopts, most un-eco!";;
	esac
done
shift $((OPTIND-1))

#todo: Fedora poweroff and lock screen
if [[ -z $lockpoweroff_screen ]] || [[ -n $lockpoweroff_screen ]];then
#if ! ( [[ $lockpoweroff_screen == off ]] || [[ $lockpoweroff_screen == 0 ]] || (($lockpoweroff_screen == 0)) );then #NOT work!
	dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock
fi

local xt_code=-1
local err_counter=0
local cmd_fnd_exc="find /mnt/0 ~ -xdev -type f -iname 'dllst' -execdir youtube-dl --youtube-skip-dash-manifest "
local cmd_exc_fileopt=" -a {} \;"

#https://www.gnu.org/software/bash/manual/bash.html#Bash-Conditional-Expressions
#-v varname: True if the shell variable varname is set (has been assigned a value).
#-z string: True if the length of string is zero.
#-n string:
#   string: True if the length of string is non-zero.

#http://zsh.sourceforge.net/Doc/Release/Conditional-Expressions.html
#if [[ ! -v bandwidth_rate ]] || [[ -z $bandwidth_rate ]];then  #unknown condition: -v #zsh 5.2 (x86_64-redhat-linux-gnu)
#if [[ ! -v $bandwidth_rate ]] || [[ -z $bandwidth_rate ]];then  #unknown condition: -v #zsh 5.2 (x86_64-redhat-linux-gnu)

if [[ ! -z $bandwidth_rate ]];then
	echo "bandwidth_rate":$bandwidth_rate
	if [[ ! $bandwidth_rate =~ ^[0-9]+[kKmM]$ ]];then
		if [[ $bandwidth_rate =~ ^[0-9]+$ ]];then
			bandwidth_rate+='k'
		else
			bandwidth_rate='75k'
		fi
	fi
	#find /mnt/0 ~ -xdev -type f -iname 'dllst' -o -iname 'dllst.todo' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -r $bandwidth_rate -a {} \;
	#find /mnt/0 ~ -xdev -type f -iname 'dllst' -execdir youtube-dl --youtube-skip-dash-manifest -r $bandwidth_rate -a {} \;
	cmd_fnd_exc+=" -r $bandwidth_rate "
fi

if [[ ! -z $format_num ]];then
	echo "format_num":$format_num
	cmd_fnd_exc+=" -f $format_num "
fi

cmd_fnd_exc+="$cmd_exc_fileopt"

#https://stackoverflow.com/questions/16489809/emulating-a-do-while-loop-in-bash
while
	echo 'cmd to exc:['$cmd_fnd_exc']'
	eval " $cmd_fnd_exc"
	xt_code=$?
	echo "exit code(find...-execdir youtube-dl...{} +):$xt_code"
	(( $xt_code != 0 ))
do
	#;
	#continue
	
	if ((err_counter++ > 9));then
	#	shutdown_timeout=0
		break
	fi
done

echo "(end)«$(\date)»"
#bash, ksh and zsh have a $SECONDS special variable that counts the number of seconds since the shell was started. In both zsh and ksh93, that variable can also be made floating point (with typeset -F SECONDS) to get more precision. This is only wall clock time, not CPU time.
typeset -F SECONDS
echo '$SECONDS(⃝  typeset -F SECONDS)':$SECONDS

if [[ $shutdown_timeout =~ ^[nN][oO][wW]$ ]];then
	echo 'true:[[ $shutdown_timeout =~ ^[nN][oO][wW]$ ]], exec:sudo shutdown -P -f +0'
	sudo shutdown -P -f +0
elif [[ $shutdown_timeout =~ ^[0-9]+$ ]];then
	echo 'true:[[ $shutdown_timeout =~ ^[0-9]+$ ]], exec':"sudo shutdown -P -f $shutdown_timeout"
	sudo shutdown -P -f $shutdown_timeout
else
	exit $xt_code
fi
