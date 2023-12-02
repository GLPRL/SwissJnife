# SwissJnife - WIP
Developed by Gal Pearl & Yonatan Volsky

SwissJnife is a multi tool written in Java, currently featuring file encryption / decryption and a network sniffer (scanner is currently in progress).

Requirements & Dependencies:
- Installed Java (https://www.java.com)
- Java annotations library (https://github.com/JetBrains/java-annotations)*
- pcap4j (https://www.pcap4j.org)*
- JNA (https://github.com/java-native-access/jna)*
- SLF4J (https://www.slf4j.org)*

*.jar file used in project is provided in ```/lib```

Our GUI is created using SWING Java Library as front end for user-friendly use of the tools.

## File Encryption Tool
<sub> by Gal Pearl & Yonatan Volsky </sub>
We based our encryption and decryption on AES/CBC with PKS5 padding algorithm, all of those to enhance the security of the algorithm.
After encrypting a selected file, you'll be required to securely save the key and the initialization vectors (IV), as they will be removed post encryption.
During decryption, you'll be required to provide the same key and initialization vector (IV) as AES is a symmetric encryption algorithm.

After encrypting a file, alongside it will appear a new file, with the original filetype suffix + Enc . This is the encrypted file.
For decrypting it, you will need to choose the very same encrypted file, provide the key and initialization vector.
The decrypted file will be with the name suffix Dec.

## Network Sniffer Tool
<sub> by Gal Pearl </sub>

Using pcap4j library & Swing, I build a user friendly network analyzer, that is capable of reading from all the ports or selected ones only.
First, list the available network interfaces and set the target using the IDs provided. Afterwards, select the ports (or leave them as is) that are your scope,
from the buttons panel or a custom port of your choice.
On applying of the filtering (or not) Using enable/disable filtering radio buttons, you can quick select of general sniffing or more scoped one.
<sub> If a filtering option was selected and no ports were targeted then it wont work </sub>
If none of the buttons was selected, then the program will consider it's current parameters and act accordingly.
While listening to traffic, you will not be able to change the scope, unless you stop the sniffing thread and update the scope.

For each TCP/UDP packet captured, the information that will be displayed is:
- Source/Destination IPs, Ports and MAC addresses (if available).
- TTL, Protocol.

Overview:
![image](https://github.com/GLPRL/SwissJnife/assets/116657293/297c3b43-cc04-423e-bbb2-1c9e93929835)


## Compilation & Running instructions:

Clone the project using and go into the directory 
```
git clone https://github.com/GLPRL/SwissJnife/
cd SwissJnife
```
Now, you can choose how to run the project:

### using Java - 

Windows systems:

Using a provided .bat file for windows, you can run the file run_SwissJnife.bat using ```run_SwissJnife```

### Built in batch :


Linux systems:

```javac -cp .:./lib/pcap4j-core-2.0.0-alpha.6.jar:./lib/* -d ./bin src/*.java src/GUI/*.java src/Logics/*.java src/GUI/EncDec/*.java src/GUI/sniffer/*.java src/Logics/EncDec/*.java src/Logics/sniffer/*.java```

```java -cp .:./lib/pcap4j-core-2.0.0-alpha.6.jar:./lib/*:./bin Main```
