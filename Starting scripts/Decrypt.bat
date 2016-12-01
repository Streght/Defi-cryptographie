@echo off
set /p inputFile=Enter the name of the file to decrypt WITHOUT the extension:
set /p outputFile=Enter the name of the decrypted file WITHOUT the extension:
java -Dfile.encoding=UTF-8 -jar Defi_crypto.jar -dec %inputFile% %outputFile%