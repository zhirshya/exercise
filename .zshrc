# Path to your oh-my-zsh installation.
export ZSH=/home/r/.oh-my-zsh

# Set name of the theme to load.
# Look in ~/.oh-my-zsh/themes/
# Optionally, if you set this to "random", it'll load a random theme each
# time that oh-my-zsh is loaded.
ZSH_THEME="robbyrussell"

# Uncomment the following line to use case-sensitive completion.
# CASE_SENSITIVE="true"

# Uncomment the following line to use hyphen-insensitive completion. Case
# sensitive completion must be off. _ and - will be interchangeable.
HYPHEN_INSENSITIVE="true"

# Uncomment the following line to disable bi-weekly auto-update checks.
# DISABLE_AUTO_UPDATE="true"

# Uncomment the following line to change how often to auto-update (in days).
# export UPDATE_ZSH_DAYS=13

# Uncomment the following line to disable colors in ls.
# DISABLE_LS_COLORS="true"

# Uncomment the following line to disable auto-setting terminal title.
# DISABLE_AUTO_TITLE="true"

# Uncomment the following line to enable command auto-correction.
# ENABLE_CORRECTION="true"

# Uncomment the following line to display red dots whilst waiting for completion.
# COMPLETION_WAITING_DOTS="true"

# Uncomment the following line if you want to disable marking untracked files
# under VCS as dirty. This makes repository status check for large repositories
# much, much faster.
# DISABLE_UNTRACKED_FILES_DIRTY="true"

# Uncomment the following line if you want to change the command execution time
# stamp shown in the history command output.
# The optional three formats: "mm/dd/yyyy"|"dd.mm.yyyy"|"yyyy-mm-dd"
# HIST_STAMPS="mm/dd/yyyy"

# Would you like to use another custom folder than $ZSH/custom?
# ZSH_CUSTOM=/path/to/new-custom-folder

# Which plugins would you like to load? (plugins can be found in ~/.oh-my-zsh/plugins/*)
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
plugins=(git)

# User configuration

export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"
# export MANPATH="/usr/local/man:$MANPATH"

source $ZSH/oh-my-zsh.sh

# You may need to manually set your language environment
# export LANG=en_US.UTF-8

# Preferred editor for local and remote sessions
# if [[ -n $SSH_CONNECTION ]]; then
#   export EDITOR='vim'
# else
#   export EDITOR='mvim'
# fi

# Compilation flags
# export ARCHFLAGS="-arch x86_64"

# ssh
# export SSH_KEY_PATH="~/.ssh/dsa_id"

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.
#
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"

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
alias src=source

if [ -e /usr/bin/vimx ]; then 
	alias vm='/usr/bin/vimx'
else
	alias vm=vim
fi

alias ll='ls -alF'
alias lls='ls -alF | less'
#alias sht='sudo shutdown -P -f --no-wall'	#Fedora
#alias rbt='sudo reboot -f --no-wall'	#Fedora
alias sht='sudo shutdown -P -f'	#Ubuntu
alias sht0='sudo shutdown -P -f 0'	#Ubuntu
alias rbt='sudo reboot -f'	#Ubuntu
alias wrs='whereis'
alias dfz='sudo df -h /var/log /mnt /'
alias duz='sudo du -sh /var/log /mnt /'
#alias s+='sudo dnf upgrade -y && sudo youtube-dl -U'
alias s+='sudo apt-get update && sudo apt-get upgrade -y && sudo youtube-dl -U'
alias ut='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21'
alias utf='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 -a dwn'
alias utl='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 -ci'
alias utcap='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -R 21 --write-sub --sub-lang ru,en --sub-format best --embed-subs '
alias utlscap='youtube-dl --youtube-skip-dash-manifest -R 21 --list-subs'
alias utupg='sudo youtube-dl -U'

alias frf='firefox --ssl-version-min=tls1.2 -private > /dev/null 2>&1 &'
alias krm='google-chrome-stable --ssl-version-min=tls1.1 --incognito http://www.weblio.jp  > /dev/null 2>&1 &'

alias cd.='cd ~'
alias cd-='cd -'
alias cd1='cd ..'
alias cd2='cd ../..'
alias cd3='cd ../../..'

alias txz='tar xvJf'
alias clr=clear
alias tl=tail
alias hd=head
alias lss=less
#alias less='less -N'
alias mr='more -s'
alias rmrf='rm -rf'
alias kty=whoami #Кто я?
alias 0='echo -n > '
#alias ?='sudo find / -iname '
#alias upgrlog='less /var/log/upgrd.log' #extended to zsh function
alias ins='sudo dnf install'

alias gcc='ccache gcc -g -Wall '
alias gxx='ccache g++ -g -Wall -std=c++14 '

alias scrot='scrot -q 100 -d 7 -c -s -e eog '
alias vlc='vlc > /dev/null 2>&1 &'
alias wget='wget --no-cookies --retry-connrefused -t 40 ' #or utilize .wgetrc
alias dff='declare -f ' #if not defined as shell alias check for shell functions
alias tdr='less /mnt/0/gthb/неkod/.todo'
alias tdw='vim /mnt/0/gthb/неkod/.todo'
#rss word
alias rs1='echo убирать' #clean up, tidy up

unsetopt CASE_GLOB # match case insensitive patterns with ls

. ~/.zsh-func
#. ~/.nvm/nvm.sh #https://github.com/creationix/nvm, Node Version Manager - Simple bash script to manage multiple active node.js versions
