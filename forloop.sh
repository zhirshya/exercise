#!/bin/zsh
echo $#
#for ((arg=1;arg<$#;++arg)) echo $($arg)
for i in "$@"; do echo $i; done

echo '$@:['$@']'
echo '"$@":['"$@"']'
