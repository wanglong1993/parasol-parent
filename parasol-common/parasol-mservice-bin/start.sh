#!/bin/bash
cd `dirname $0`
MSERVICE=$1
MSERVICE_NAME=parasol-${MSERVICE}-web*.jar

BIN_DIR=`pwd`
cd ..

#查找服务的名字
JAR_COUNT=`find ./ -mindepth 1 -type f -iname ${MSERVICE_NAME} | wc -l`
if [[ "${JAR_COUNT}" != "1" ]]; then
    echo "${MSERVICE_NAME} only have one"
    exit 1
fi


#服务是否已经启动
MSERVICE_NAME=`/bin/ls ${MSERVICE_NAME}`
PIDS=`ps -ef | grep java | grep "${MSERVICE_NAME}" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $MSERVICE_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

#服务的日志
LOGS_DIR="logs/${MSERVICE}"
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log
#日志文件是否已经存在
if [[  -f  ${STDOUT_FILE} ]]; then
    datename=$(date +%Y%m%d-%H%M%S)
    mv ${STDOUT_FILE} ${STDOUT_FILE}.${datename}
fi






JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $MSERVICE_NAME ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -jar ${MSERVICE_NAME} > $STDOUT_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1 
    
    START_INFO=`tail ${STDOUT_FILE} | grep "Jetty started on port"`
    if [ -n "${START_INFO}" ]; then
        echo ${START_INFO}
        COUNT=2
        break
    fi
done

echo "OK!"
PIDS=`ps -ef | grep java | grep "${MSERVICE_NAME}" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"

