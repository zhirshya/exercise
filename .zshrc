# If you come from bash you might have to change your $PATH.
# export PATH=$HOME/bin:/usr/local/bin:$PATH
export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"
# Path to your oh-my-zsh installation.
export ZSH=/home/r/.oh-my-zsh

# Set name of the theme to load. Optionally, if you set this to "random"
# it'll load a random theme each time that oh-my-zsh is loaded.
# See https://github.com/robbyrussell/oh-my-zsh/wiki/Themes
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

source $ZSH/oh-my-zsh.sh

# User configuration

# export MANPATH="/usr/local/man:$MANPATH"

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

export LC_CTYPE=en_US.UTF-8

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.
#
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"
alias src='source ~/.zshrc'

if [ -e /usr/bin/vimx ]; then  #/usr/bin/vimx: symbolic link to gvim
	alias vm='/usr/bin/vimx -v'
#	alias vm='gvim -v' #The -v option will run gvim in terminal mode, and gvim is always built with support for X11 and the clipboard.
else
	alias vm=vim
fi

alias ll='ls -alFt' #Pass the -r option to reverse the order of the sort to get reverse lexicographical order or the oldest entries first (or largest files last, if combined with sort by size)
alias lls='ls -alFt | less'
#alias s0='sudo shutdown -P -f --no-wall'	#Fedora
alias rbt='sudo reboot -f --no-wall'	#Fedora
alias ins='sudo dnf install'
#alias ins='sudo apt install'
alias rmv='sudo dnf remove'
alias rpmsrch='sudo dnf search all'
alias 00p='sudo shutdown -c'
#alias rbt='sudo reboot -f'	#Ubuntu
alias wrs='whereis'
alias dfz='sudo df -h /var/log /mnt /'
alias duz='sudo du -sh /var/log /mnt /'
alias ut='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg'
alias utf='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -a dwn'
alias utl='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg -ci'
alias utcap='youtube-dl --youtube-skip-dash-manifest --prefer-ffmpeg --write-sub --sub-lang ru,en --sub-format best --embed-subs'
alias utlscap='youtube-dl --youtube-skip-dash-manifest --list-subs'
alias utlscvr='youtube-dl --youtube-skip-dash-manifest --list-thumbnails'

alias or='cd /mnt/0/skachat/tor-browser_en-US && ./start-tor-browser.desktop && -'

alias ..='cd ..'
alias ...='cd ../..'
alias ....='cd ../../..'

alias txz='tar xvJf'
alias clr=clear
alias tl=tail
alias hd=head
alias lss=less
alias lsn='less -N'
alias dff=diff
alias dffBb='diff -Bb'
alias mr='more -s'
alias rmrf='rm -rf'
alias kty=whoami #Кто я?
alias 03f='disable -p "?"'
alias 13f='enable -p ?'
#alias 026='disable -p "&"' #disable: invalid pattern: &
alias 026='disable -p \&' #disable: invalid pattern: &
alias 126='enable -p \&'
alias 021='disable -p "!"' #incorrect
alias 121='enable -p !'
alias 023='disable -p "#"'
alias 123='enable -p #'
#alias upgrlog='less /var/log/upgrd.log' #extended to zsh function
alias mv='mv -i'
alias cp='cp -i'
alias date='date +"%Y-%m-%d %H:%M:%S"'

alias gcc='ccache gcc -std=c11 -Wall -Wextra -g'
alias gxx='ccache g++ -std=c++14 -Wall -Wextra -g'
alias vgr='valgrind --leak-check=yes'

alias scrot='scrot -q 100 -d 7 -c -e eog $f'
alias wget='wget --no-cookies -c' #or utilize .wgetrc
alias wgetf='wget --no-cookies -c -i' #or utilize .wgetrc
alias fdef='declare -f' #if not defined as shell alias check for shell functions
alias tdr='tail -n 15 /mnt/0/gthb/note/.todo'
alias td='vim /mnt/0/gthb/note/.todo'
alias utv0='utv 0'
alias lynx='lynx -cookies=off'

#punctuation,symbol,rus word
alias rangli='echo ð'
alias theta='echo θ Θ ϴ ϑ'
alias rubl='echo ₽'
alias numero='echo №'
alias pilcrow='echo ¶'
alias section='echo §'
alias xclam='echo !'
alias question='echo ?'
alias guillemet='echo ‹ › « »'
alias notesin='echo ※'
alias cfmrk='echo ↔ ⇔'
alias tikcrs='echo ✓ ✔ ✗ ✘'
alias ruclean='echo убирать' #clean up, tidy up

unsetopt CASE_GLOB # match case insensitive patterns with ls
#setopt noglob #turn off globbing(wildcard expansion)

. ~/.zsh-func
#. ~/.nvm/nvm.sh #https://github.com/creationix/nvm, Node Version Manager - Simple bash script to manage multiple active node.js versions
