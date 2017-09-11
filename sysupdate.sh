#!/bin/sh

echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
sudo dnf upgrade -y
echo '$?':$?
sudo youtube-dl -U
echo '$?':$?
echo «$(\date +"%Y%m%d %H%M%S.%N%::z%Z %A")»
