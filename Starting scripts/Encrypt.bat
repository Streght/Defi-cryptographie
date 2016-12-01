@echo off
set /p inputFile=Enter the name of the file to encrypt WITHOUT the extension:
set /p outputFile=Enter the name of the encrypted file WITHOUT the extension:
java -Dfile.encoding=UTF-8 -jar Defi_crypto.jar -enc %inputFile% %outputFile%