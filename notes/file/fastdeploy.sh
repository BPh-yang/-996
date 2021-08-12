#!/bin/bash
dir_app_name=`pwd`/$2
dir_app_old=`pwd`/$1
dir_log=`pwd`/msg.log
echo "killing............"
pid=`ps -ef|grep ${dir_app_old}|grep -v grep|grep -v restart|awk '{print$2}'`
if [ -n "${pid}" ] ;then
 kill -9 ${pid}
 sleep 1
fi
echo "your pid is killed"
echo "starting......"
nohup ${JAVA_HOME}/bin/java -jar ${dir_app_name} >${dir_log} 2>&1 &
echo "Congratulations!"
