# SwissJnife - WIP
Developed by Gal Pearl & Yonatan 

SwissJnife is a multi tool written in Java, currently featuring file encryption / decryption and a network sniffer (scanner is currently in progress).

Requirements & Dependencies:
- Installed Java (https://www.java.com)
- 

Our GUI is created using SWING Java Library as front end for user-friendly use of the tools.

## File Encryption Tool
We based our encryption and decryption on AES/CBC with PKS5 padding algorithm, all of those to enhance the security of the algorithm.
After encrypting a selected file, you'll be required to securely save the key and the initialization vectors (IV), as they will be removed post encryption.
During decryption, you'll be required to provide the same key and initialization vector (IV) as AES is a symmetric encryption algorithm.

After encrypting a file, alongside it will appear a new file, with the original filetype suffix + Enc . This is the encrypted file.
For decrypting it, you will need to choose the very same encrypted file, provide the key and initialization vector.
The decrypted file will be with the name suffix Dec.

## Network Sniffer Tool
WIP
