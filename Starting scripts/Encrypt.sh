echo -n "Enter the name of the file to encrypt WITHOUT the extension > "
read inputfile
echo -n "Enter the name of the encrypted file WITHOUT the extension > "
read outputfile
java -Dfile.encoding=UTF-8 -jar Defi_crypto.jar -enc $inputfile $outputfile