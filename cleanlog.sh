#!/bin/sh

#Clean all logs on a Linux system without deleting the files:
for CLEAN in $(find /var/log/ -type f)
do
    cp /dev/null  $CLEAN
done

#Samba (/var/www/samba) creates log file-names with ip addresses, for those, you may want to delete them:
for CLEAN in $(find /var/log/samba -type f)
do
    rm -rf $CLEAN
done
