#!/bin/sh
#dig +short myip.opendns.com @resolver1.opendns.com
#dig TXT +short o-o.myaddr.l.google.com @ns1.google.com | awk -F'"' '{print $2}'
#host myip.opendns.com resolver1.opendns.com

ip_opendns="$(dig +short myip.opendns.com @resolver1.opendns.com)"
echo "WAN/Public IP address via opendns: ${ip_opendns}"

ip_ggl="$(dig TXT +short o-o.myaddr.l.google.com @ns1.google.com | awk -F'"' '{print $2}')"
echo "WAN/Public IP address via ggl: ${ip_ggl}"
