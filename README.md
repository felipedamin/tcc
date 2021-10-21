# tcc

Requisitos:
- instalar java
- instalar maven
- adicionar maven no PATH (windows: winkey+s -> digita path -> adiciona o \bin da pasta do maven)
- use o maven pra instalar os packages

comandos:
mvn compile exec:java -Dexec.mainClass="teste.AddLogForAllConditions2" -Dexec.args="src/main/java/teste"
mvn test -Dtest=br.usp.larc.nanoib.handlers.BalanceFunctionalTest