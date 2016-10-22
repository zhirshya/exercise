#!/bin/zsh

#1. find *.part files and download separately by extracting ID
#find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
# extract the substring of 11 characters between last/penultimate hyphen/dash(-) and the first period(.) after that
echo «\date»
xt_code=-1
while [[ $xt_code -ne 0 ]];do
  find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 19 -a {} +
  xt_code=$?
  echo "exit code(find...-execdir youtube-dl...{} +):$xt_code"
done

echo «\date»

#timeout_arg=now
if [[ $# -eq 1 ]] && [[ "$1" =~ [0-9]+ ]];then
	timeout_arg=+$1
#	elif [[ $1 -eq 's' ]]; then #stand-by
#		exit 0
#	fi 
else
#	timeout_arg="+3"
	exit 0
fi

sudo shutdown -P -f $timeout_arg
