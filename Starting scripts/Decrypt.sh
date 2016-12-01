echo -n "Enter the name of the file to decrypt WITHOUT the extension > "
read inputfile
echo -n "Enter the name of the decrypted file WITHOUT the extension > "
read outputfile
java -Dfile.encoding=UTF-8 -jar Defi_crypto.jar -dec $inputfile $outputfile