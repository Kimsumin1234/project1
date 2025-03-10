FROM openjdk:17-jdk
# 환경 변수 설정
ENV com.example.upload.path="/home/ec2-user/app/upload"
# 영구 저장 디렉토리 설정
VOLUME ["/home/ec2-user/app/upload"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]