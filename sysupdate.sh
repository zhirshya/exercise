#!/bin/sh

exec  &>> /home/r/sysupdate.log

echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
sudo dnf upgrade -y
echo '$?':$?
sudo youtube-dl -U
echo '$?':$?
sudo systemctl stop bluetooth.service
sudo systemctl disable bluetooth.service
sudo systemctl status bluetooth.service
echo '$?':$?

echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
