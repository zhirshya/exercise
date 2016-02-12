#!/bin/zsh
find /mnt/0 -type f -i '*dwn*' -execdir youtube-dl -a {} +
#find /mnt/0 -type f -i '*dwn*' -print
wait
sudo shutdown -P -f +5
