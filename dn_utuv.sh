#!/bin/zsh

#1. find *.part files and download separately by extracting ID
#find /mnt/0 -type f -iname '*.part' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +
# extract the substring of 11 characters between last(or the one next to last) hyphen(-) and the first period(.) after that

#2. URL list file
find /mnt/0 -type f -iname 'dwn' -execdir youtube-dl --youtube-skip-dash-manifest -a {} +

#wait

sudo shutdown -P -f +3
#sudo shutdown -P -f now
