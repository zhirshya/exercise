#!/bin/bash

function \?() {
for id in "$@"; do
	if [[ $id = http*://*youtu*be*/*watch\?v=[-_a-zA-Z0-9]* ]]; then
		id="${id#http*://*youtu*be*/*watch\?v=}"
	elif [[ $id = http*://*youtu*be*/[-_a-zA-Z0-9]* ]]; then
		id="${id#http*://*youtu*be*/}"
	fi
# bash: ${$1#http*://*youtu*be*/*watch\?v[=]}: bad substitution
# bash: ${"$1"#http*://*youtu*be*/*watch\?v[=]}: bad substitution
	echo "#search local(non /) pattern:$id"
	find /mnt/0 ~ -iname "*$id*" -execdir ls -alF {} \;
done
}

function srvstr() {
for srv in "$@"; do
	sudo service "$srv" start
done
}

function srvstp() {
for srv in "$@"; do
	sudo service "$srv" stop
done
}

function lpr() {
if [ $# -eq 0 ]; then
	ll *part
else
	if [[ "$1" = */ ]]; then
		ll "$1"*part
	else
		ll "$1"/*part
	fi
fi
}

function utmp3() {
	youtube-dl --youtube-skip-dash-manifest -R 21 --prefer-ffmpeg -x --audio-format mp3 --audio-quality 0 "$1" #to keep video: -k
}

function ut18() {
	youtube-dl --youtube-skip-dash-manifest -R 21 --prefer-ffmpeg -f 18 "$1"
}

function dprx() {
unset http_proxy
unset https_proxy
unset HTTP_PROXY
unset HTTPS_PROXY

echo "http_proxy: [$http_proxy]"
echo "https_proxy:[$https_proxy]"
echo "HTTP_PROXY: [$HTTP_PROXY]"
echo "HTTPS_PROXY:[$HTTPS_PROXY]"
}

function prprx() { 
echo "http_proxy: [$http_proxy]"
echo "https_proxy:[$https_proxy]"
echo "HTTP_PROXY: [$HTTP_PROXY]"
echo "HTTPS_PROXY:[$HTTPS_PROXY]"
}

function --() { #clean
rm -rf ~/.cache ~/.Trash ~/.thumbnails ~/.Thumbnails ~/.local/share/Trash ~/.local/share/trash 
sudo apt autoremove -y
sudo apt autoclean
#E: encountered a section with no package: header
#E: problem with Mergelist/var/lib/apt/lists
#sudo rm -Rf /var/lib/apt/lists/* -vf
}

function +() { #append to todo file
if [[ $# > 0 ]]; then
	echo "$@" >> /mnt/0/gthb/note/.todo
fi
}

function ++() { #append to youtube download file
if [[ $# > 0 ]]; then
	echo "$@" >> dwn
fi
}

function upgrlog() { #view cron job upgrade cmd log
if [[ -f /var/log/sys_upgrade.log ]]; then
	less /var/log/sys_upgrade.log
elif [[ -f /var/log/youtube-dl_upgrade.log ]]; then
	less /var/log/youtube-dl_upgrade.log
fi
}

function vd() {
vlc "$@" &> /dev/null &
}

function utup() {
shopt -s nocasematch

xt_code=-1
out_msg="error"
while [[ $xt_code -ne 0 || "$out_msg" =~ error* ]]; do # ERROR: can't find the current version. Please try again later.
	out_msg=$(sudo youtube-dl -U)
	xt_code=$?	
	echo "exit code:$xt_code"
	echo "output message:$out_msg"
done
}

function fx() {
firefox --ssl-version-min=tls1.2 -private "$@" &> /dev/null &
}

function krm() {
google-chrome-stable --ssl-version-min=tls1.2 --incognito weblio.jp merriam-webster.com "$@" &> /dev/null &
}

function 0p() {
#echo '$#':$#
if [[ $# -eq 1 && "$1" =~ [0-9]+ ]]; then
	sudo shutdown -P -f "$1"
else
	sudo shutdown -P -f 0
fi
}

function dwn() {
shopt -s nocasematch

for file in $(find /mnt/0 ~ -type f -iname 'dwn*'); do
	if [[ "$file" != "find*permission denied*" ]]; then
		echo $file:
		more $file
	fi
done
}

#apache2
#alias apstt='sudo systemctl -l status apache2'
#alias apstp='sudo systemctl stop apache2'
#alias apstr='sudo systemctl start apache2'
#alias aprst='sudo systemctl restart apache2'
#alias aprld='sudo systemctl reload apache2'

alias apstt='sudo /etc/init.d/apache2 status'
alias apstp='sudo /etc/init.d/apache2 graceful-stop'
alias apstr='sudo /etc/init.d/apache2 start'
alias aprst='sudo /etc/init.d/apache2 restart'
alias aprld='sudo /etc/init.d/apache2 reload'
#mysql, mariadb
alias mystt='sudo systemctl -a status mysql'
alias mystp='sudo systemctl stop mysql'
alias mystr='sudo systemctl start mysql'
alias myrst='sudo systemctl restart mysql'

alias ufwst='sudo ufw status verbose'
alias src='source ~/.zshrc'

if [ -e /usr/bin/vimx ]; then 
	alias vm='/usr/bin/vimx'
else
	alias vm=vim
fi

alias ll='ls -alF'
alias lls='ls -alF | less'
#alias s0='sudo shutdown -P -f --no-wall'	#Fedora
#alias rbt='sudo reboot -f --no-wall'	#Fedora
#alias ins='sudo dnf install'	#Fedora
alias ins='sudo apt install'
alias 00p='sudo shutdown -c'
alias rbt='sudo reboot -f'	#Ubuntu
alias wrs='whereis'
alias dfz='sudo df -h /var/log /mnt /'
alias duz='sudo du -sh /var/log /mnt /'
#alias ss='sudo dnf upgrade -y && sudo youtube-dl -U'	#Fedora
alias ss='sudo apt update && sudo apt upgrade -y && sudo youtube-dl -U'
alias ut='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21'
alias utf='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 -a dwn'
alias utl='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 -ci'
alias utcap='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 --write-sub --sub-lang ru,en --sub-format best --embed-subs '
alias utlscap='youtube-dl --youtube-skip-dash-manifest -R 21 --list-subs'

alias or='cd ~/Downloads/tor-browser_en-US && ./start-tor-browser.desktop && -'

alias ..='cd ..'
alias ...='cd ../..'
alias ....='cd ../../..'

alias txz='tar xvJf'
alias clr=clear
alias tl=tail
alias hd=head
alias lss=less
alias lsn='less -N'
alias dff='diff -Bb'
alias mr='more -s'
alias rmrf='rm -rf'
alias kty=whoami #Кто я?
alias 0='echo -n > '
alias 03f='disable -p "?"'
alias 13f='enable -p ?'
alias 021='disable -p "!"' #incorrect
alias 121='enable -p !'
alias 023='disable -p "#"'
alias 123='enable -p #'
#alias ?='sudo find / -iname '
#alias upgrlog='less /var/log/upgrd.log' #extended to zsh function
alias mv='mv -i'
alias cp='cp -i'

alias gcc='ccache gcc -std=c11 -Wall -Wextra -g '
alias gxx='ccache g++ -std=c++14 -Wall -Wextra -g '
alias vgr='valgrind --leak-check=yes'

alias scrot='scrot -q 100 -d 7 -c -s -e eog '
alias wget='wget --no-cookies --retry-connrefused -t 40 ' #or utilize .wgetrc
alias fdef='declare -f ' #if not defined as shell alias check for shell functions
alias tdr='tail -n 15 /mnt/0/gthb/note/.todo'
alias td='vim /mnt/0/gthb/note/.todo'
alias utv0='utv 0'
#rss word
alias rs1='echo убирать' #clean up, tidy up

unsetopt CASE_GLOB # match case insensitive patterns with ls
#setopt noglob #turn off globbing(wildcard expansion)
