# Detecção de Ameaças Silenciosas em Software

This project only runs in maven projects

Requirements:
- maven project
- python3
- pandas
- jqf-fuzzer
- javaparser

You must add `jqf-fuzzer` and `javaparser` as dependencies on your pom.xml file:

```xml
  <dependencies>
    <dependency>
      <groupId>edu.berkeley.cs.jqf</groupId>
      <artifactId>jqf-fuzz</artifactId>
      <version>1.1</version>
    </dependency>
    <dependency>
      <groupId>com.github.javaparser</groupId>
      <artifactId>javaparser-symbol-solver-core</artifactId>
      <version>3.23.1</version>
    </dependency>		              
  </dependencies>
```

For this project to run you must first clone this repository:

```bash
git clone https://github.com/felipedamin/tcc.git
```

`cd` into the cloned repo
Then you have to move `xisnove` into your maven project src folder `src/main/java`

```bash
cd tcc

mv xisnove <your maven project root>/src/main/java
```

Then you have to cd into your project root, where the `pom.xml` is, and run the install script.
This script will install all dependencies such as `python3`, `pandas`, `jqf-fuzzer` and `javaparser`.

```bash
cd <your project root>

sh src/main/java/xisnove/scripts/install.sh
```

If you want to run the project you must first run the homolog script from your project root.

This script takes 5 mandatory args, which are:
 -p <the path to the file you want to parse starting from src/main/java/...>
 -u <the unit test class for the class you are parsing>
 -c <the full qualified name of the fuzz class>
 -m <the method inside the fuzz class you want to run>
 -t <how long you want to run the fuzzing test>

(It takes a lot of arguments, but we are still working on it)
  
```bash
sh src/main/java/xisnove/scripts/homolog.sh -p src/main/java/<project directory>/<file name>.java -u <package>.<class name> -c <package>.<fuzz class name> -m <fuzz test method you want to run> -t <period of time you want it to run>
```
  
Then after the homolog script is done, you have to run the production script (again from your project root) to instrument the final file with the function for logging suspect executions.
  
This script takes 1 mandatory argument, which is:
 -f <the path to the file you want to parse starting from src/main/java/..., the same file you parsed in the last step>
  
```bash
sh src/main/java/xisnove/scripts/production.sh -f src/main/java/<project directory>/<file name>.java
```
  
And that's it!
