#!/bin/zsh

#todo find *.part files and download separately by extracting ID
#find /mnt/0 ~ -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
#extract the substring of 11 characters between last/penultimate hyphen/dash(-) and the first period(.) after that

#exec  &>> /home/r/utv.sh.log  #flooded with ^M (i.e. carriage return, keyboard equivalent to \r)

typeset -F SECONDS
echo "(start)«$(\date)»"

while getopts "st:b:" opt; do
	case "$opt" in
	s) lockpoweroff_screen="NoLockPoweroffScreen";;
	t) shutdown_timeout="$OPTARG";;
	b) bandwidth_rate="$OPTARG";;
	\?) # unknown flag
		#echo >&2 \
		echo "usage: $0 [-s] [-t positive_integer] [-b positive_integer[kKmM]]"
		exit 1;;
	*) echo "default case of getopts, most un-eco!";;
	esac
done
shift $((OPTIND-1))

if [[ -z $lockpoweroff_screen ]] || [[ -n $lockpoweroff_screen ]];then
#if ! ( [[ $lockpoweroff_screen == off ]] || [[ $lockpoweroff_screen == 0 ]] || (($lockpoweroff_screen == 0)) );then #NOT work!
	dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock
fi

xt_code=-1
err_counter=0
while [[ $xt_code -ne 0 ]];do
	#https://www.gnu.org/software/bash/manual/bash.html#Bash-Conditional-Expressions
	#-v varname: True if the shell variable varname is set (has been assigned a value).
	#-z string: True if the length of string is zero.
	#-n string:
	#   string: True if the length of string is non-zero.

	#http://zsh.sourceforge.net/Doc/Release/Conditional-Expressions.html
	#if [[ ! -v bandwidth_rate ]] || [[ -z $bandwidth_rate ]];then  #unknown condition: -v #zsh 5.2 (x86_64-redhat-linux-gnu)
	#if [[ ! -v $bandwidth_rate ]] || [[ -z $bandwidth_rate ]];then  #unknown condition: -v #zsh 5.2 (x86_64-redhat-linux-gnu)
	if ((err_counter++ > 9));then
		shutdown_timeout=0
		break
	fi

	if [[ -z $bandwidth_rate ]];then
		echo '#hit [[ -z $bandwidth_rate ]]'
		#find /mnt/0 ~ -xdev -type f -iname 'dllst' -o -iname 'dllst.todo' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -a {} \;
		find /mnt/0 ~ -xdev -type f -iname 'dllst' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -a {} \;
	else
		if [[ ! $bandwidth_rate =~ ^[0-9]+[kKmM]$ ]];then
			if [[ $bandwidth_rate =~ ^[0-9]+$ ]];then
				bandwidth_rate+='k'
			else
				bandwidth_rate='75k'
			fi
		fi
		echo "#bandwidth_rate":$bandwidth_rate
		#find /mnt/0 ~ -xdev -type f -iname 'dllst' -o -iname 'dllst.todo' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -r ${bandwidth_rate} -a {} \;
		find /mnt/0 ~ -xdev -type f -iname 'dllst' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -r ${bandwidth_rate} -a {} \;
	fi
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

if [[ $shutdown_timeout =~ ^[nN][oO][wW]$ ]];then
	echo 'true:[[ $shutdown_timeout =~ ^[nN][oO][wW]$ ]], exec:sudo shutdown -P -f +0'
	sudo shutdown -P -f +0
elif [[ $shutdown_timeout =~ ^[0-9]+$ ]];then
	echo 'true:[[ $shutdown_timeout =~ ^[0-9]+$ ]], exec':"sudo shutdown -P -f $shutdown_timeout"
	sudo shutdown -P -f $shutdown_timeout
else
	exit $xt_code
fi
