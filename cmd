#zsh
declare -f FuncName
#How to show zsh function definition (like bash “type myfunc”)?
The zsh idiom is whence, the -f flag prints function definitions:
zsh$ whence -f foo
foo () {
    echo hello
    }
zsh$
In zsh, type is defined as equivalent to whence -v, so you can continue to use type, but you'll need to use the -f argument:

zsh$ type -f foo
foo () {
        echo hello
}
zsh$
And, finally, in zsh which is defined as equivalent to whence -c - print results in csh-like format, so which foo will yield the same results.

man zshbuiltins for all of this.

Works, but only if no alias of the same name happens to be defined, in which case that is reported (whence -f, type -f, which report the highest-precedence form of the command).
declare -f foo is better because even if the function is hidden by an alias, it will still be shown, whereas 'whence' is liable to show just the alias, OR both the alias and the function.

setopt nocasematch
unsetopt nocasematch
setopt #list set options

#tar -C /opt -xzvf node-v4.2.0-linux-x64.tar.gz

#Concatenating media files
https://trac.ffmpeg.org/wiki/Concatenate
ffmpeg -f concat -i filelist -c copy "Подмосковные Вечера(en+rss).mp3"
#content of filelist
file '/tmp/Moscow Night  Helmut Lotti-VbZHzF-Av1I.mp3'
file '/tmp/Russian Red Army Choir - Moscow Nights-aw5L0IdKjps.mp3'

#apache
httpd -V
Server version: Apache/2.4.17 (Fedora)
...
Server MPM:     prefork

#how to check if port is open and unused
netstat -tln | tail -n +3 | awk '{ print $4 }'

#crop audio/video
ffmpeg -i InputFile -vcodec copy -acodec copy -ss 00:00:00 -t <duration> OutPutFile
ffmpeg -i InputFile  -vn -acodec copy -ss 00:00:00 -t <duration> OutPutFile

#List Cron Jobs
crontab –l –u username

#Edit Cron Jobs
To use cron for tasks meant to run only for your user profile, add entries to your own user's crontab file. Start the crontab editor from a terminal window:
crontab -e
add line:
07,37 * * * * /usr/bin/tunlrupdate.sh

Commands that normally run with administrative privileges (i.e. they are generally run using sudo) should be added to the root user's crontab (instead of the user's crontab):
sudo crontab -e
add same line:
07,37 * * * * /usr/bin/tunlrupdate.sh

Depending on the commands being run, you may need to expand the root users PATH variable by putting the following line at the top of their crontab file:
PATH=/usr/sbin:/usr/bin:/sbin:/bin

#Remove Cron Jobs
crontab -ir

#rename files in respective directories
#find /media/r/0 -type f -iname 'dwn.wait' -execdir mv {} dwn.todo +
find /media/r/0 -type f -iname 'dwn.wait' -execdir rename -v 's/\.wait/\.todo/' {} +

#http://askubuntu.com/questions/419115/make-bluetooth-disabled-by-default
rfkill unblock bluetooth

#Create Symbolic Links in Linux
sudo ln -s /media/r/0/gthb/xrcs/utv.sh /usr/local/bin/utv

grep -Ril "2>&1" .

#sed, vim
%s/\(>\s*[/\w.+~-]\+\)\s\+2>&1/\&\1/gc #pattern not found
g/\(>\s*[/\w.-]\+\)\s\+2>&1/s//\&\1/gc #pattern not found
g/\(>.\+\)\s\+2>&1/s//\&\1/gc	#work

7z x -o/media/r/0/dbC\(gtdвrus\) dbC.7z
7z a -p'P@s5w0rd' gthb.7z gthb

less /proc/sys/dev/cdrom/info
cdrecord -minfo -v

grub2-mkconfig -o /boot/grub2/grub.cfg

utl $(cat list)

➜  DEBS sudo dpkg -R -i .

How to modify invalid /etc/sudoers file?
pkexec visudo
Better create custom sudoers in /etc/sudoers.d instead of modifying /etc/sudoers
pkexec visudo -f /etc/sudoers.d/myOverrides

dd if=kali-linux-2016.1-amd64.iso of=/dev/sdb bs=512k

pdftk polymersummit-es6.pdf cat 15-69 72-end output use-es6-with-polymer,2015.pdf
pdftk Eloquent\ JavaScript,\ 2nd\ Edition.pdf cat 1 8 13-end output eloquent_JavaScript_2nd_ed.NoStarchPress.pdf

cd /media/r/0
fdupes -Sr js nodejs_screencast java cpp aws c c++graphic eecs-ict php embnix nixcpp nixc3d nixpythn socket 01 fpga wbst swift ms tekvid android game hak how pentst

#access and mount iPhone 6 in Linux
sudo apt install ideviceinstaller python-imobiledevice libimobiledevice-utils libimobiledevice4 libplist2 python-plist ifuse 
sudo vim /etc/fuse.conf
idevicepair pair
ifuse /opt/iPhone/
fusermount -u /opt/iPhone

eog - GNOME image viewer

#turn off globbing(wildcard expansion) temporaly/for current session
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
zsh: no matches found: https://www.youtube.com/watch?v=kGosatWQ5ag
➜  0 disable -p ?                                 
disable: invalid pattern: c
➜  0 disable -p '?'                               
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
Search for ID [kGosatWQ5ag] now...
find: ‘/home/r/.dbus’: Permission denied
-rw-rw-r-- 1 r r 43429548 сен 25  2014 /media/r/0/oros(Русский)/Weekly Russian Words with Katya - Computer Words-kGosatWQ5ag.mkv
➜  0 enable ?
enable: no such hash table element: ?
➜  0 enable -p ?
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
zsh: no matches found: https://www.youtube.com/watch?v=kGosatWQ5ag
➜  0 disable -p "?"                               
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
Search for ID [kGosatWQ5ag] now...
find: ‘/home/r/.dbus’: Permission denied
-rw-rw-r-- 1 r r 43429548 сен 25  2014 /media/r/0/oros(Русский)/Weekly Russian Words with Katya - Computer Words-kGosatWQ5ag.mkv
#persistent effect
setopt noglob
#bash
set -o noglob

diff ~/.zshrc /media/r/0/gthb/xrcs/.zshrc 
diff ~/.zsh-func /media/r/0/gthb/xrcs/.zsh-func 
diff /media/r/0/gthb/xrcs/Makefile /media/r/0/xrc/Makefile

#Delete lines in text file that containing specific string
ex +g/match/d -cwq file

top -b -o +%MEM | head
ps -eo %mem,%cpu,pid,ppid,cmd --sort=-%mem | head

#chntpw
cd %systemroot%/System32/config
sudo chntpw [-u <username>] SAM

#ftp
lcd(local cd) get put del/rm?


