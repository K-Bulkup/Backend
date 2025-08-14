# Gradle/jdk 17 이미지를 이용해서 빌드
FROM gradle:8.10.2-jdk17 AS build

# 컨테이너 디렉토리를 /app 으로 지정
WORKDIR /app

# build.gradle과 settings.gradle을 /app 디렉토리에 복사
COPY build.gradle settings.gradle ./

# gradle wrapper 파일들도 복사
COPY gradlew ./
COPY gradle ./gradle

# Gradle wrapper를 사용하여 프로젝트의 정확한 Gradle 버전(9.0.0) 사용
RUN ./gradlew dependencies --no-daemon

# 현재 src 폴더에 있는 코드를 복사
COPY src ./src

# 이전에 빌드한 내용을 제거하고 build를 통해 새로운 war 파일을 생성한다.
# clean이 컴파일된 build 폴더를 지움
# build가 프로젝트를 빌드해서 war 또는 jar 파일을 만들어주는 명령어
RUN ./gradlew clean build -x test --no-daemon

# 톰캣 이미지 이용 (JDK 17 호환)
FROM tomcat:9.0-jdk17

# 이전에 실행된 build라는 이름을 가진 이미지의 build/libs 내부에 빌드된 war 파일을
# /usr/local/tomcat/webapps/ROOT.war 에 복사
# webapps가 톰캣이 자동으로 WAR 파일을 배포하는 기본 위치
COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

# war 파일 추출
RUN mkdir -p /usr/local/tomcat/webapps/ROOT && \
    cd /usr/local/tomcat/webapps/ROOT && \
    jar -xvf /usr/local/tomcat/webapps/ROOT.war

# 포트번호 지정
EXPOSE 8080

# 톰캣 서버를 실행하는 스크립트 작성
CMD ["catalina.sh", "run"]