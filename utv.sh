#!/bin/zsh

#1. find *.part files and download separately by extracting ID
#find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
# extract the substring of 11 characters between last(or the one next to last) hyphen(-) and the first period(.) after that

#2. URL list file
find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 19 -a {} +

#wait

timeout_arg=now
if [[ $# -eq 1 && $1 =~ "^[0-9]+$" ]]; then
	timeout_arg=+$1
else
	timeout_arg="+3"
fi

sudo shutdown -P -f $timeout_arg
