#!/bin/zsh

if [ $# -ne 1 ]; then
	echo "Usage: $0 URL"
	exit
fi

wget "$1"
datetime = $(date +"%T %F %Z,%A")
title = "download finished at $datetime, shutdown now"
mail -s $title r -c uhaaral200510@gmail.com < "$0"
shutdown now
