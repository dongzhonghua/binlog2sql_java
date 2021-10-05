FROM java:openjdk-8-jdk-alpine
VOLUME /tmp
ADD target/open-replicator-jar-with-dependencies.jar binlog-sync.jar

#设置alpine系统时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone
ENV JAVA_OPTS=""
CMD java ${JAVA_OPTS} -jar /binlog-sync.jar