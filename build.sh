#!/bin/bash
rm -r out/*
javac -d out src/main/java/*.java src/main/java/io/*.java src/main/java/handlers/*.java src/main/java/util/*.java
cd out
jar cfe FileIO.jar Main *.class handlers/*.class io/*.class util/*.class
