#!/bin/zsh

#1. find *.part files and download separately by extracting ID
#find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
# extract the substring of 11 characters between last/penultimate hyphen/dash(-) and the first period(.) after that

typeset -F SECONDS
echo «$(\date)»

xt_code=-1
while [[ $xt_code -ne 0 ]];do
  find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -a {} +
  xt_code=$?
  echo "exit code(find...-execdir youtube-dl...{} +):$xt_code"
done

echo «$(\date)»
echo '$(time)':$(time)
echo '$(times)':$(times)
#or add a trap on EXIT, that way, times will be called whenever the shell exits and the exit status will be preserved.
#trap times EXIT
#bash, ksh and zsh have a $SECONDS special variable that counts the number of seconds since the shell was started. In both zsh and ksh93, that variable can also be made floating point (with typeset -F SECONDS) to get more precision. This is only wall clock time, not CPU time.
#typeset -F SECONDS
echo '$SECONDS':$SECONDS
#http://unix.stackexchange.com/questions/52313/how-to-get-execution-time-of-a-script-effectively

#timeout_arg=now
if (($# == 1)) && [[ "$1" =~ [0-9]+ ]];then
	timeout_arg=+$1
#	elif [[ $1 -eq 's' ]]; then #stand-by
#		exit $xt_code
#	fi 
else
	exit $xt_code
fi

sudo shutdown -P -f $timeout_arg
