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
#find /mnt/0 -type f -iname 'dwn.wait' -execdir mv {} dwn.todo +
find /mnt/0 -type f -iname 'dwn.wait' -execdir rename -v 's/\.wait/\.todo/' {} +

#http://askubuntu.com/questions/419115/make-bluetooth-disabled-by-default
rfkill unblock bluetooth

#Create Symbolic Links in Linux
sudo ln -s /mnt/0/gthb/xrcs/utv.sh /usr/local/bin/utv

