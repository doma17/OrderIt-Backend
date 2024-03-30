#!/bin/bash
 echo "> now ing app pid find!"
 CURRENT_PID=$(pgrep -f order_it)
 echo "$CURRENT_PID"
 if [ -z $CURRENT_PID ]; then
         echo "> no ing app."
 else
         echo "> sudo kill -9 $CURRENT_PID"
         sudo kill -9 $CURRENT_PID
         sleep 3
 fi
 echo "> new app deploy"

 cd /home/ubuntu/deploy/build/libs
 # nohup 디렉토리가 없으면 생성
 mkdir -p /home/ubuntu/deploy/nohup
 JAR_NAME=$(ls | grep 'order_it' | tail -n 1)
 echo "> JAR Name: $JAR_NAME"

 # nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME &
 sudo nohup java -jar $JAR_NAME 1>/home/ubuntu/deploy/nohup/stdout.txt 2>/home/ubuntu/deploy/nohup/stderr.txt
 sleep 2