#LO MacOS
#export CFLAGS="-stdlib=libc++ -std=c++1z"
#export CXXFLAGS="-stdlib=libc++ -std=c++1z"
#export CPPFLAGS="-stdlib=libc++ -std=c++1z"
#export CC="clang"
#export CXX="clang++"
#export CPP=""
#export CCACHE_CPP2=YES
export LODE_HOME=$HOME/lode
#export JAVA_HOME=`/usr/libexec/java_home`
export GOPATH=$HOME/go-workspace
export GOROOT=/usr/local/opt/go/libexec
#export PATH="${LODE_HOME}/opt/bin:/usr/local/opt/ccache/libexec:${PATH}:/usr/local/bin:/usr/local/sbin:/Applications/Visual Studio Code.app/Contents/Resources/app/bin"
export PATH="${LODE_HOME}/opt/bin:${PATH}:/usr/local/bin:/usr/local/sbin:/Applications/Visual Studio Code.app/Contents/Resources/app/bin:$GOROOT/bin:$GOPATH/bin"

# If you come from bash you might have to change your $PATH.
# export PATH=$HOME/bin:/usr/local/bin:$PATH
export PATH="${LODE_HOME}/opt/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:$HOME/.local/bin:/usr/games:/usr/local/games"

# Path to your oh-my-zsh installation.
export ZSH=$HOME/.oh-my-zsh

# Set name of the theme to load. Optionally, if you set this to "random"
# it'll load a random theme each time that oh-my-zsh is loaded.
# See https://github.com/robbyrussell/oh-my-zsh/wiki/Themes
ZSH_THEME="agnoster"

#python interpreter
#http://docs.python-guide.org/en/latest/dev/virtualenvs/#lower-level-virtualenv
export VIRTUALENVWRAPPER_PYTHON=/usr/bin/python3

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

export MANPATH="/usr/local/man:$MANPATH"

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
# export SSH_KEY_PATH="~/.ssh/rsa_id"

export LC_CTYPE=en_US.UTF-8

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.
#
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"

if [ -e /usr/bin/vimx ]; then  #/usr/bin/vimx: symbolic link to gvim
	alias vm='/usr/bin/vimx -v'
#	alias vm='gvim -v' #The -v option will run gvim in terminal mode, and gvim is always built with support for X11 and the clipboard.
else
	alias vm=vim
fi

#alias vm='mvim -v'
alias nvm=nvim
alias ll='ls -alFt' #Pass the -r option to reverse the order of the sort to get reverse lexicographical order or the oldest entries first (or largest files last, if combined with sort by size)
alias lls='ls -alFt | less'
alias 0s0='sudo shutdown -c'
alias rbt='sudo reboot -f'
alias ins='sudo dnf --best --allowerasing install'
#alias ins='sudo apt install'
alias rmv='sudo dnf remove'
alias rpmsrch='sudo dnf search all'
alias upgr='sudo dnf upgrade --best --allowerasing -y;upgrade_oh_my_zsh'
alias ping='ping -c 6 -i 1.5'
alias vrs='whereis'
alias vch='which'
alias dfz='sudo df -h /var/log /mnt /'
alias duz='sudo du -sh /var/log /mnt /'
#alias ut='youtube-dl -v --youtube-skip-dash-manifest --prefer-ffmpeg'
alias ut='youtube-dl -v --youtube-skip-dash-manifest'
alias utf='youtube-dl -v --youtube-skip-dash-manifest -a dllst'
alias utl='youtube-dl -v --youtube-skip-dash-manifest -ci'
alias utcap='youtube-dl -v --youtube-skip-dash-manifest --write-sub --sub-lang ru,en --sub-format best --embed-subs'
alias utlscap='youtube-dl -v --youtube-skip-dash-manifest --list-subs'
alias utlscvr='youtube-dl -v --youtube-skip-dash-manifest --list-thumbnails'
#SS proxy
alias ut='youtube-dl -v --proxy socks5://127.0.0.1:1080 --youtube-skip-dash-manifest'
alias utf='youtube-dl -v --proxy socks5://127.0.0.1:1080 --youtube-skip-dash-manifest -a dnld'
alias utlf='youtube-dl -v --proxy socks5://127.0.0.1:1080 --youtube-skip-dash-manifest --list-formats'

alias or='cd /mnt/0/dnld/tor-browser_en-US && ./start-tor-browser.desktop && -'

alias ..='cd ../'
alias ...='cd ../../'
alias ....='cd ../../../'

alias txz='tar xvJf'
alias klr=clear
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
alias date='date +"%Y%m%d %H%M%S.%N%::z%Z %A"'
alias bc='bc -l' #echo "scale=3; 30000/7" | bc -l

alias gcc='ccache gcc -std=c11 -Wall -Wextra -pedantic -ggdb3 -Og'
alias gc+='ccache g++ -std=c++17 -Wall -Wextra -pedantic -ggdb3 -Og'
alias vgr='valgrind --leak-check=yes'

alias vlc='vlc --no-metadata-network-access --one-instance --playlist-enqueue --qt-start-minimized'
alias gnome-screenshot='gnome-screenshot -w -p -d 7 -f'
alias wget='wget --no-cookies -c' #or utilize .wgetrc
alias wgetf='wget --no-cookies -c -i' #or utilize .wgetrc
alias fdef='declare -f' #if not defined as shell alias check for shell functions
alias tdr='tail -n 15 /mnt/0/gthb/note/.todo'
alias td='vim /mnt/0/gthb/note/.todo'
alias utv0='utv -tnow'
alias utv0r='utv -tnow -b100'
alias utvr='utv -b100'
alias utvs='utv -s'
alias utvsr='utv -s -b100'
alias lynx='lynx -cookies=off'
#alias rtvl='echo "\$\?":$?'
alias rtvl='echo "\$?":$?'
alias grep='grep --exclude-dir={.git,.svn,.hg,.bzr,cvs,CVS} --color=auto -r -n -H -C 3 -i'
alias src='source ~/.zshrc'

#punctuation,symbol,rus word
alias rangli='echo ð '
alias theta='echo θ Θ ϴ ϑ '
alias rubl='echo ₽ '
alias numero='echo № '
alias pilcrow='echo ¶ '
alias section='echo § '
alias xclam='echo ! '
alias question='echo ? '
alias guillemet='echo ‹ › « » '
alias notemrk='echo ※ '
#alias rfrmrk='echo ※ '
alias apprxeq='echo ≈ '
alias cfmrk='echo ↔ ⇔ '
alias laro='echo ← ⇐ '
alias raro='echo → ⇒ '
alias uaro='echo ↑ ⇑ '
alias daro='echo ↓ ⇓ '
alias tik='echo ✓ ✔ '
alias krs='echo ✗ ✘ '
alias ruclean='echo убирать ' #clean up, tidy up
#alias orsuseg='echo А а, Б б, В в, Г г, Д д, Е е, Ё ё, Ж ж, З з, И и, Й й, К к, Л л, М м, Н н, О о, П п, Р р, С с, Т т, У у, Ф ф, Х х, Ц ц, Ч ч, Ш ш, Щ щ, Ъ ъ, Ы ы, Ь ь, Э э, Ю ю, Я я '
alias orsusegt='echo А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я '
alias orsuseg='echo а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я '
alias mglkrlt='echo А Б В Г Д Е Ё Ж З И Й К Л М Н О Ө П Р С Т У Ү Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я '
alias mglkrl='echo а б в г д е ё ж з и й к л м н о ө п р с т у ү ф х ц ч ш щ ъ ы ь э ю я '
alias grkusegt='echo Α Β Γ Δ Ε Ζ Η Θ Ι Κ Λ Μ Ν Ξ Ο Π Ρ Σ Τ Υ Φ Χ Ψ Ω '
alias grkuseg='echo α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ σ/ς τ υ φ χ ψ ω '
#https://en.wikipedia.org/wiki/Greek_language
alias circldNum='echo ⓪ ① ② ③ ④ ⑤ ⑥ ⑦ ⑧ ⑨ ⑩ ⑪ ⑫ ⑬ ⑭ ⑮ ⑯ ⑰ ⑱ ⑲ ⑳ '

unsetopt CASE_GLOB # match case insensitive patterns with ls
#setopt noglob #turn off globbing(wildcard expansion)

#. ~/.zsh-func
source ~/.zsh-func
#. ~/.nvm/nvm.sh #https://github.com/creationix/nvm, Node Version Manager - Simple bash script to manage multiple active node.js versions

