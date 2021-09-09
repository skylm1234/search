FROM registry.cn-zhangjiakou.aliyuncs.com/gejian_zhouqiang/java:1.8-full

COPY ./gejian-search-web/target/gejian-search-web*.jar /app.jar

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx256m -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -Djava.security.egd=file:/dev/./urandom"

EXPOSE 9634

CMD java -jar  ${JAVA_OPTS} /app.jar