#!/bin/sh
sudo apt update && sudo apt upgrade -y && youtube-dl -U
sudo apt install -y vim valgrind g++ git python perl htop evince ack-grep p7zip-full unrar fcitx fcitx-pinyin fcitx-sunpinyin fcitx-googlepinyin fcitx-anthy fcitx-mozc ffmpeg vlc browser-plugin-vlc libreoffice sqlite mosh
sudo apt install -y traceroute ettercap-graphical dsniff nmap wireshark 
sudo apt install -y openprinting-gutenprint cups

#mount -t cifs -o username=<share user>,password=<share password>[,domain=example.com] //WIN_PC_IP/<share name> /mnt
sudo apt install -y winbind cifs-utils smbclient

#fcitx
im-config

#linux unable to locate printer
socket://192.168.1.140:9100
ipp://192.168.1.140:631/ipp

systemctl status cups.service
systemctl start cups.service

#~/.vimrc
set ts=2 sts=2 sw=2 noexpandtab

useradd -m -d /home/x -s /bin/zsh x
groups x

