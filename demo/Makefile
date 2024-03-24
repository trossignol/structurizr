default: build

build:
	mvn clean package

run:
	mvn clean spring-boot:run

parse:
	mvn fr.rossi:structurizr-parser-maven-plugin:0.0.1-SNAPSHOT:structurizr-parser -Dpackage=com.example.demo

model:
	docker run -it --rm -p 9000:8080 -v ./docs:/usr/local/structurizr structurizr/lite