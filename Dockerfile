FROM openjdk:21
WORKDIR /app
COPY ./src/ /app/
RUN javac -cp "lib/" .\src\server\java*.java .\src\main\java\ChatGPT.java .\src\main\java\DallE.java  .\src\main\java\Whisper.java