# class-to-open-api
A converter class or folder with classes to yaml open api config

# build
mvn clean install -DskipTests

# build native
mvn clean install -DskipTests -Pnative

# usage
java -jar class-to-api-converter.jar -file {Your class file or folder} -output {path where will be saved result of converting}

OR

class-to-api-converter.exe -file {Your class file or folder} -output {path where will be saved result of converting}

Can put .java file near jar and run with file name (run java -jar code-api-converter.jar Test.java)

