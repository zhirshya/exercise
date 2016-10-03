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

#apache
httpd -V
Server version: Apache/2.4.17 (Fedora)
...
Server MPM:     prefork

#how to check if port is open and unused
netstat -tln | tail -n +3 | awk '{ print $4 }'

#get video dimension from ffmpeg -i
ffprobe -v error -show_entries stream=width,height -of default=noprint_wrappers=1 headscarf\ old\ women.jpg
width=960
height=640

#trim, strip off audio/video
ffmpeg -i InputFile -c copy -ss 00:00:00 -t <duration> OutPutFile
ffmpeg -i InputFile  -vn -acodec copy -ss 00:00:00 -t <duration> OutPutFile
ffmpeg -i MGTOW\ Reason\ \#13\ -\ Aftershock\ From\ The\ Wedding-A17CBtwEmcE.webm -an -vcodec copy aftershock\ marriage-A17CBtwEmcE.webm
ffmpeg -i How\ to\ lace\ dress\ shoes\ ·\ Carmina\ shoemaker-L3p_jjYKImc.mkv -an -vcodec copy -ss 0:0:7 -t 0:0:39 lace_dress_shoe-L3p_jjYKImc.mkv

#Concatenating media files
https://trac.ffmpeg.org/wiki/Concatenate
ffmpeg -f concat -i filelist -c copy "Подмосковные Вечера(en+rss).mp3"
#content of filelist
file '/tmp/Moscow Night  Helmut Lotti-VbZHzF-Av1I.mp3'
file '/tmp/Russian Red Army Choir - Moscow Nights-aw5L0IdKjps.mp3'
#crop, scale
ffmpeg -i crop_1.jpg -vf crop="250:ih-15:0:15" crop_2.jpg
ffmpeg -i headscarf\ old\ women.jpg -vf scale=320:-1 crop.jpg

#speed up av
ffmpeg -i Russian\ numbers\ 2\ 10\ 20\ 30,\ 100,\ 1000-vFlzWw62XQo.mp4 -filter:v "setpts=PTS/1.5" -filter:a "atempo=1.5" rus_num_1000-vFlzWw62XQo_1.5spd.mp4

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
#find /mnt/0 -type f -iname 'dwn.wait' -execdir mv {} dwn.todo +
find /mnt/0 -type f -iname 'dwn.wait' -execdir rename -v 's/\.wait/\.todo/' {} \;

find . -type f -exec chmod 644 {} \;
find -type f \( -name "*" -or -name ".*" \) -execdir wc -l {} ";"
find -type f \( -name "*" -or -name ".*" \) -execdir wc -l {} \;

#http://askubuntu.com/questions/419115/make-bluetooth-disabled-by-default
rfkill unblock bluetooth

#Create Symbolic Links in Linux
sudo ln -s /mnt/0/gthb/xrcs/utv.sh /usr/local/bin/utv

grep -Ril "2>&1" .

#sed, vim
%s/\(>\s*[/\w.+~-]\+\)\s\+2>&1/\&\1/gc #pattern not found
g/\(>\s*[/\w.-]\+\)\s\+2>&1/s//\&\1/gc #pattern not found
g/\(>.\+\)\s\+2>&1/s//\&\1/gc	#work

7z x -o/mnt/0/vrk -p".*\!#" vrk.7z
7z a -p".*\!#" curr.7z curr

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

cd /mnt/0
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
-rw-rw-r-- 1 r r 43429548 сен 25  2014 /mnt/0/oros(Русский)/Weekly Russian Words with Katya - Computer Words-kGosatWQ5ag.mkv
➜  0 enable ?
enable: no such hash table element: ?
➜  0 enable -p ?
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
zsh: no matches found: https://www.youtube.com/watch?v=kGosatWQ5ag
➜  0 disable -p "?"                               
➜  0 ? https://www.youtube.com/watch?v=kGosatWQ5ag
Search for ID [kGosatWQ5ag] now...
find: ‘/home/r/.dbus’: Permission denied
-rw-rw-r-- 1 r r 43429548 сен 25  2014 /mnt/0/oros(Русский)/Weekly Russian Words with Katya - Computer Words-kGosatWQ5ag.mkv
#persistent effect
setopt noglob
#bash
set -o noglob

diff ~/.zshrc /mnt/0/gthb/xrcs/.zshrc 
diff ~/.zsh-func /mnt/0/gthb/xrcs/.zsh-func 
diff /mnt/0/gthb/xrcs/Makefile /mnt/0/xrc/Makefile

#Delete lines in text file that containing specific string
ex +g/match/d -cwq file

top -b -o +%MEM | head
ps -eo %mem,%cpu,pid,ppid,cmd --sort=-%mem | head

#chntpw
cd %systemroot%/System32/config
sudo chntpw [-u <username>] SAM

#ftp
lcd(local cd) get put del/rm?

#date
\date +"now is week: %V of %Y"
\date +"now is week: %U of %Y"
gsettings set com.canonical.indicator.datetime time-format 'custom'
gsettings set com.canonical.indicator.datetime custom-time-format '%A %B %d %Y %H:%M:%S %:::z'

#linux commnad line weather
curl wttr.in/hailar
curl wttr.in/st.petersburg

#https://fedoraproject.org/wiki/How_to_create_and_use_Live_USB#quickstarts
sudo dd if=/path/to/image.iso of=/dev/sdX bs=8M status=progress oflag=direct

#ACL
drwxr-x---+  3 root root 4096 сен 24 17:56 r/
➜ getfacl r
# file: r
# owner: root
# group: root
user::rwx
user:r:r-x
group::---
mask::r-x
other::---

#set ACL
chown -R root:root /mnt/0/*
chown -R root:root /mnt/0/.*
#find /mnt/0 -type f -execdir chmod 640 {} \;
#find /mnt/0 -type d -execdir chmod 750 {} \;
find /mnt/0 -type d -execdir chmod 750 {} \; -execdir setfacl -m u:r:rwx {} \;
find /mnt/0 -type d -name '.*' -execdir chmod 750 {} \; -execdir setfacl -m u:r:rwx {} \;
find /mnt/0 -type f -name '.*' -execdir chmod 640 {} \; -execdir setfacl -m u:r:rw {} \;
find /mnt/0 -type f -execdir chmod 640 {} \; -execdir setfacl -m u:r:rw {} \;
#effective permissions are formed by ANDing permissions with umask.
➜  xrcs git:(master) ✗ getfacl .
# file: .
# # owner: root
# # group: root
# user::rwx
# user:r:rwx			#effective:r-x
# group::r-x
# mask::r-x
# other::---
#
# ➜  xrcs git:(master) ✗ umask 
# 022
#

#linux recursive search and replace string in directory
find . -type f -execdir sed -i 's/\/media\/r\/0/\/mnt\/0/g' {} \;

#UNIX / Linux Command To Check Existing Groups and Users
➜  xrcs git:(master) ✗ groups r
r : r
➜  xrcs git:(master) ✗ id r
uid=1000(r) gid=1000(r) groups=1000(r)
➜  xrcs git:(master) ✗ id adm
uid=3(adm) gid=4(adm) groups=4(adm)
➜  xrcs git:(master) ✗ id admin
id: ‘admin’: no such user
➜  xrcs git:(master) ✗ getent group r
r:x:1000:
➜  xrcs git:(master) ✗ getent group adm
adm:x:4:
➜  xrcs git:(master) ✗ getent group admin
➜  xrcs git:(master) ✗ 

#shell cmd
print - $((524288000/1024/1024))

find /mnt/0 ~ ! -path "/mnt/0/linux/*" ! -path "/mnt/0/linux-insides/*" -iname "*debug*" -type f -exec ls -alF {} \;

#shell single quote file name with single quote and space
infile_quoted=$(printf \'%s\' "$(printf %s "$infile" | sed "s/'/'\\\\''/g")")
outfile_quoted=$(printf \'%s\' "$(printf %s "$outfile" | sed "s/'/'\\\\''/g")")
echo '"${infile_quoted}"':"${infile_quoted}"
echo '"${outfile_quoted}"':"${outfile_quoted}"
echo '"$infile_quoted"':"$infile_quoted"
echo '"$outfile_quoted"':"$outfile_quoted"
echo '$infile_quoted':$infile_quoted
echo '$outfile_quoted':$outfile_quoted

