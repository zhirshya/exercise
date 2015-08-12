#!/bin/zsh

if [[ $# -ne 1 ]]; then
	echo "Usage: $0 URL"
	exit
fi

#youtube, vimeo, facebook
#https://www.youtube.com/watch?v=7nh-O7gKUCg
#https://www.facebook.com/playmagnus/videos/647926035335583/?video_source=pages_finch_main_video
#https://www.facebook.com/ehbichig/videos/711024195622581/?video_source=pages_finch_main_video
#https://vimeo.com/132329259
#if [[ $1 == "https://www.youtube.com/watch?v=*" ||
#	$1 == "https://www.youtu.be/watch?v=*" ||
#	$1 == "www.youtube.com/watch?v=*" ||
#	$1 == "www.youtu.be/watch?v=*" ||
#	$1 == "https://www.facebook.com/*/videos/*" ||
#	$1 == "https://vimeo.com/*" 

#if [[ $1 =~ "[https://]?www.youtube.com/watch[\]?\?v[\]?=*" ||
#	$1 =~ "[https://]?www.youtu.be/watch[\]?\?v[\]?=*" ||
#	$1 =~ "https://www.facebook.com/*/videos/*" ||
#	$1 =~ "https://vimeo.com/*" 

#if [[ $1 -regex-match "(https://)?www\.youtube\.com/watch[\]?[?]v[\]?=*" ||
#	$1 -regex-match "(https://)?www\.youtu\.be/watch[\]?[?]v[\]?=*" ||
#	$1 -regex-match "https://www.facebook.com/*/videos/*" ||
#	$1 -regex-match "https://vimeo.com/*" 

if [[ $1 =~ "(https://)?www\.youtube\.com/watch[\]?[?]v[\]?=*" ||
	$1 =~ "(https://)?www\.youtu\.be/watch[\]?[?]v[\]?=*" ||
	$1 =~ "https://www.facebook.com/*/videos/*" ||
	$1 =~ "https://vimeo.com/*" 
	]]; then
	echo "found match for youtube-dl\n"
	youtube-dl $1
else 
	wget $1
fi

wait

datetime=$(date +"%T %F %Z,%A")
title="[$1] download finished at $datetime, shutdown now"
echo $title

mail -s $title r -c uhaaral200510@gmail.com < "$0"

wait

shutdown -k now
