FROM node:25-alpine3.23 AS frontend_builder
WORKDIR /music_downloader/frontend
ADD ./frontend /music_downloader/frontend
RUN npm install && npm install typescript
RUN npm run build

FROM bellsoft/liberica-runtime-container:jdk-25-stream-musl AS builder
ADD --exclude=./frontend . /home/app/music_downloader
COPY --from=frontend_builder /music_downloader/src/main/resources/static /home/app/music_downloader/src/main/resources/static
WORKDIR /home/app/music_downloader
RUN ./gradlew bootJar

FROM bellsoft/liberica-runtime-container:jdk-25-stream-musl AS optimizer

WORKDIR /home/app
COPY --from=builder /home/app/music_downloader/build/libs/*.jar music_downloader.jar
RUN java -Djarmode=tools -jar music_downloader.jar extract --layers --launcher --destination ./music_downloader

FROM bellsoft/liberica-openjdk-alpine:25
EXPOSE 8080
COPY --from=optimizer /home/app/music_downloader/dependencies ./
RUN apk add python3 deno ffmpeg
ADD --chmod=777 https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp /bin/yt-dlp
COPY --from=optimizer /home/app/music_downloader/spring-boot-loader ./
COPY --from=optimizer /home/app/music_downloader/snapshot-dependencies ./
COPY --from=optimizer /home/app/music_downloader/application ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]