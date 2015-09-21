#how to check if port is open and unused
netstat -tln | tail -n +3 | awk '{ print $4 }'

