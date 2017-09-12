#!/bin/sh

exec  &>> /mnt/0/workspace/sysupdate.log

echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
sudo dnf upgrade -y
echo '$?':$?
sudo youtube-dl -U
echo '$?':$?
blutu0

echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
