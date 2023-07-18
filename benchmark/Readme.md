Run `mvn clean package` on the xisnove project
Copy the .jar from xisnove/target to benchmark/ `cp ../xisnove/target/xisnove-1.1.jar ./`.


install the .jar with

```mvn install:install-file -Dfile=xisnove-1.1.jar -DgroupId=br.usp.larc -DartifactId=xisnove -Dversion=1.1 -Dpackaging=jar```


verfy if everything is correct with `mvn clean verify` and then run `mvn clean package`. Now you are ready to run the benchmark using `java -jar target/benchmarks.jar`