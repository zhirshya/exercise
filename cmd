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

#&&
03f && ? ".f1*." ".f2*." ".f3*." ".f4*." ".f5*."

#get video dimension from ffmpeg -i
ffprobe -v error -show_entries stream=width,height -of default=noprint_wrappers=1 headscarf\ old\ women.jpg
width=960
height=640

#trim/clip, strip off audio/video
#https://trac.ffmpeg.org/wiki/Encode/HighQualityAudio
ffmpeg -i InputFile -c copy -ss 00:00:00 -t <duration> OutPutFile
ffmpeg -i InputFile  -vn -acodec copy -ss 00:00:00 -t <duration> OutPutFile
ffmpeg -i MGTOW\ Reason\ \#13\ -\ Aftershock\ From\ The\ Wedding-A17CBtwEmcE.webm -an -vcodec copy aftershock\ marriage-A17CBtwEmcE.webm
ffmpeg -i How\ to\ lace\ dress\ shoes\ ·\ Carmina\ shoemaker-L3p_jjYKImc.mkv -an -vcodec copy -ss 0:0:7 -t 0:0:39 lace_dress_shoe-L3p_jjYKImc.mkv
ffmpeg -i 21\ дарь\ эхийн\ ачлал.mkv -vn -c:a libmp3lame -aq 0 21\ дарь\ эхийн\ ачлал_aq_0.mp3

#speed up av
ffmpeg -i Russian\ numbers\ 2\ 10\ 20\ 30,\ 100,\ 1000-vFlzWw62XQo.mp4 -filter:v "setpts=PTS/1.5" -filter:a "atempo=1.5" rus_num_1000-vFlzWw62XQo_1.5spd.mp4

#clip+speed
ffmpeg -i Russian\ Cursive\ Writing\(connecting\ letters\)-ZP8SDSHIk9g.mp4 -c copy -ss 0:0:40 -t 0:5:0 rus_cursive_connected-ZP8SDSHIk9g.mp4 && avspd rus_cursive_connected-ZP8SDSHIk9g.mp4 && echo '$?':$?

#concatenate
FFMeg Concat: "unsafe file name" error
The demuxer accepts the "-safe" option (obviously an input option, to
be placed before the corresponding input file):
    safe
        If set to 1, reject unsafe file paths.
        [...]
        If set to 0, any file name is accepted.
ffmpeg -f concat -safe 0 -i filelist -c copy "swanlake-part1__No.4_Gmajor_K41_II.Andante.mp3"

https://trac.ffmpeg.org/wiki/Concatenate
ffmpeg -f concat -i filelist -c copy "Подмосковные Вечера(en+rss).mp3"
#content of filelist
file '/tmp/Moscow Night  Helmut Lotti-VbZHzF-Av1I.mp3'
file '/tmp/Russian Red Army Choir - Moscow Nights-aw5L0IdKjps.mp3'

#crop, scale
ffmpeg -i crop_1.jpg -vf crop="250:ih-15:0:15" crop_2.jpg
ffmpeg -i headscarf\ old\ women.jpg -vf scale=320:-1 crop.jpg

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
find /mnt/0 ~ -type f \( -iname "*07n*" -o -iname "*0n7*" -o -iname "*0o7*" -o -iname "*07o*" \) -exec ls -alF {} \;
find /mnt/0 ~ -xdev -type f -iname 'dwn' -execdir rm {} \; && echo '$?':$?
#find output redirection works
find /mnt/0 ~ -xdev -type f -iname '*.py' -exec ls -alF {} \; > outfile

#http://stackoverflow.com/questions/6844785/how-to-use-regex-with-find-command
find /mnt/0 ~ -xdev -type f -regextype posix-extended -regex '.*node.*(\.(pdf|epub|(ppt|doc|xls)x?)?)?' -print
find /mnt/0 ~ -xdev -type f -regextype posix-egrep -regex '.*node.*(\.(pdf|epub|(ppt|doc|xls)x?)?)?' -print

#http://askubuntu.com/questions/419115/make-bluetooth-disabled-by-default
rfkill unblock bluetooth

#Create Symbolic Links in Linux
sudo ln -s /mnt/0/gthb/xrcs/utv.sh /usr/local/bin/utv

grep -Ril "2>&1" .

#sed, vim
sed -i.bak "s/\"\\\\n\"/'\\\\n'/g" *.cpp /* cout << "\n" --> cout << '\n' (see Effective Modern C++ by Scott Meyer) */
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

find /mnt/0 ~ ! -path "/mnt/0/linux/*" ! -path "/mnt/0/linux-insides/*" -type f -iname "*debug*" -exec ls -alF {} \;

#shell single quote file name with single quote and space
infile_quoted=$(printf \'%s\' "$(printf %s "$infile" | sed "s/'/'\\\\''/g")")
outfile_quoted=$(printf \'%s\' "$(printf %s "$outfile" | sed "s/'/'\\\\''/g")")
echo '"${infile_quoted}"':"${infile_quoted}"
echo '"${outfile_quoted}"':"${outfile_quoted}"
echo '"$infile_quoted"':"$infile_quoted"
echo '"$outfile_quoted"':"$outfile_quoted"
echo '$infile_quoted':$infile_quoted
echo '$outfile_quoted':$outfile_quoted

#read into var
read start _ < <(du -bcm kali-linux-2016-W41-amd64.iso| tail -1); echo $start

scrub -p dod /dev/sdb
echo '$?':$?
$?:0
#or
scrub -p dod /dev/sdb1...

#Find Out What Version of Linux You Are Running
#http://www.tecmint.com/find-linux-kernel-version-distribution-name-version-number/
cat /proc/version
cat /etc/os-release
lsb_release -a

lscpu

#disown, nohup
#Detach a Linux Processes From Controlling Terminal
#to completely detach a process from a controlling terminal, use the command format below, this is more effective for graphical user interface (GUI) applications such as firefox:
firefox </dev/null &>/dev/null &
#http://www.tecmint.com/run-linux-command-process-in-background-detach-process/

#list all open ports or currently running ports including TCP and UDP
cat /etc/services | less
netstat -lntu

#youtube
framestep through a paused video using , (comma) and . (dot) to go backwards and forwards 1 frame.

#shell debug
1. #!/bin/bash -xv
2. bash/zsh/ksh -xv shellscript

http://docstore.mik.ua/orelly/unix3/upt/ch37_01.htm
#save the debugging output in file and see it on screen? Use tee to copy the scrfile stdout and stderr; add tee to the pipeline before the pager.
# $ scrfile | tee outfile 2>&1 | less
shellscript | tee output 2>&1 | less

tail -f shellscript.log

#linux lock screen and power off display from command line
https://geek1011.github.io/linux-tip-lock-screen-from-command-line/
https://github.com/geek1011/Scripts.sh/blob/master/LockScreen/LockScreen.sh
xset dpms force off
dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock

dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock;xset dpms force off;utv0

#Linux Unified Key Setup (LUKS)
#The security issue relies due to a vulnerability (CVE-2016-4484) in the implementation of the Cryptsetup utility used for encrypting hard drives via Linux Unified Key Setup (LUKS), which is the standard implementation of disk encryption on a Linux-based operating system.
#modify the cryptroot file to stop the boot sequence when the number of password attempts has been exhausted.
#http://thehackernews.com/2016/11/hacking-linux-system.html
sed -i 's/GRUB_CMDLINE_LINUX_DEFAULT="/GRUB_CMDLINE_LINUX_DEFAULT="panic=5 /' /etc/default/grub grub-install

#UEFI
dd;BIOS UEFI mode;secure boot OFF #Kali 2016.2+
dd;BIOS UEFI mode #Fedora

#change case of entire file
dd if=tst.tst of=tst.ucase conv=ucase
diff tst.tst tst.ucase
dd if=tst.tst of=tst.lcase conv=lcase
diff tst.tst tst.lcase

#rpm
rpm -qa | grep rpmfusion
rpmfusion-free-appstream-data-25-3.fc25.noarch
rpmfusion-free-release-25-2.noarch

rpm -qa | grep vlc

rpm -qa | egrep '(uname)|(vim)|(gdb)|(gcc)|(g++)|(git)|(openssl)|(ssh)|(libreoffice)|(ffmpeg)|(gimp)|(vlc)|(firefox)|(xmllint)|(node)|(youtube-dl)' #grep lot more other app

#?
tar -xvzf wkhtmltopdf-0.10.0_rc2-static-amd64.tar.bz2 -C /opt

#.tar.lz
tar --lzip -xvf gmp-6.1.2.tar.lz

#clang
clang++ -std=c++14 your_source.cpp -o your_binary

#sort & uniq
#After running sort on an input stream, you can remove repeated lines with uniq as in the example below.
#To indicate the number of occurrences of a line, use the -c option and ignore differences in case while comparing by including the -i option:
sort txt_file | uniq -i -c

#sjis -> utf8
for i in $(find . -xdev -type f -iname '*.*sh' -o -iname '*.sql' -o -iname '*.csv');do
#iconv -f shift-JIS -t utf-8 < ${i} > ${i%.*}_utf-8.${i##*.}  #${var}? vs "$var"
iconv -f shift-JIS -t utf8 ${i} > ${i%.*}_utf8.${i##*.}  #${var}? vs "$var" #man iconv: utf8,utf-8
echo '$?':$?
done

#useradd
useradd amlkcfr && usermod -aG appGrp amlkcfr

#Find Number of Files in a Directory and Subdirectories
FILES=$(sudo find . -type f -print | wc -l)
echo "There are $FILES in the current working directory."

#firefox simplified/text-only print preview
about:config >> print.use_simplify_page
Alt-f-v

#shutdown after wget exit
wgetf url && s0

#g++
g++ -std=c++11 -Wall -Wextra -O0 -ggdb3 -S numberpermute.cpp /*output: numberpermute.s*/

#make
linux makefile put rules for different programming languages in one makefile (possible with java and c++ on RHEL7.1, tested on Mar.17,2017)

#systemctl
udisks2.service changed on disk. Run 'systemctl daemon-reload' to reload units.

#seq
seq -s, 2 7 100
seq -s, 100

#tee
pstree | tee -a pstree_tee.out

#df
df -aTh

