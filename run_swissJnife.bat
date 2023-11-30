@echo off
javac -cp .;./lib/pcap4j-core-2.0.0-alpha.6.jar;./lib/* -d ./bin src/*.java src/GUI/*.java src/Logics/*.java src/GUI/EncDec/*.java src/GUI/sniffer/*.java src/Logics/EncDec/*.java src/Logics/sniffer/*.java

java -cp .;lib\pcap4j-core-2.0.0-alpha.6.jar;lib\*;bin Main
pause