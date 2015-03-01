#!/bin/bash
# https://www.youtube.com/watch?v=IzrSb9WuG8o

	if [ $# -eq 1 ]; then
		id="$1"
		if [[ $1 = http*://*youtu*be*/*watch\?v=[-_a-zA-Z0-9]* ]]; then
			id="${id#http*://*youtu*be*/*watch\?v=}"
		elif [[ $1 = http*://*youtu*be*/[-_a-zA-Z0-9]* ]]; then
			id="${id#http*://*youtu*be*/}"

# bash: ${$1#http*://*youtu*be*/*watch\?v[=]}: bad substitution
# bash: ${"$1"#http*://*youtu*be*/*watch\?v[=]}: bad substitution

		fi

		echo "[SCRIPT]>>Search for ID [$id] now..."
		sudo find / -xdev -iname "*$id*"  # -xdev -> find: ‘/run/user/1000/gvfs’: Permission denied #find: paths must precede expression
		echo "[SCRIPT]>>END"
	fi
