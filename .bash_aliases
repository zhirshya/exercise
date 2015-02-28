alias ugr='sudo apt-get update && sudo apt-get upgrade -y && sudo pip install setuptools dnslib pycrypto cryptography pyopenssl greenlet gevent youtube-dl --upgrade'

alias ut=youtube-dl
alias utf='youtube-dl -a'
alias utl='youtube-dl -citk --max-quality FORMAT'

alias cd.='cd ~'
alias cd-='cd -'
alias cd1='cd ..'
alias cd2='cd ../..'
alias cd3='cd ../../..'

#alias less='less -N'
alias txz='tar xvJf'
#alias ll='ls -alF'
alias clr=clear
alias rmrf='rm -rf'

function dtx()
{
	rm -rf ~/.cache/thumbnail* ~/.thumbnail*
}

function inst()
{
	sudo apt-get install "$*"
}

function fnd() 
{
# https://www.youtube.com/watch?v=IzrSb9WuG8o

	if [ $# -eq 1 ]; then
		id="$1"
		if [[ $1 = http*://*youtu*be*/*watch\?v=[-_a-zA-Z0-9]* ]]; then
			id="${id#http*://*youtu*be*/*watch\?v=}"
		elif [[ $1 = http*://*youtu*be*/[-_a-zA-Z0-9]* ]]; then
			id="${id#http*://*youtu*be*/}"

# bash: ${$1#http*://*youtu*be*/*watch\?v[=]}: bad substitution
# bash: ${"$1"#http*://*youtu*be*/*watch\?v[=]}: bad substitution

		fi

		echo "Search for ID [$id] now"
		sudo find / -iname "*$id*"

	fi
}

function srvstr()
{
	if [ $# -eq 1 ]; then
		sudo service "$1" start
	fi
}

function srvstp()
{
	if [ $# -eq 1 ]; then
		sudo service "$1" stop
	fi
}

function lpr()
{
	if [ $# -eq 0 ]; then
		ll *part
	else
		if [[ $1 = */ ]]; then
			ll "$1"*part
		else
			ll "$1"/*part
		fi
	fi
}

function utmp3()
{
        youtube-dl -x --audio-format mp3 --audio-quality 0 "$1"
}

function ut18()
{
        youtube-dl -f 18 "$1"
}

export http_proxy=http://127.0.0.1:8087
export HTTP_PROXY=$http_proxy

export https_proxy=http://127.0.0.1:8087
export HTTPS_PROXY=$https_proxy
