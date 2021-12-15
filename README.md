# Detecção de Ameaças Silenciosas em Software

## Sobre

O projeto em questão foi desenvolvido pelos alunos Eduardo Fernandes Correia Neto, Felipe Augusto Schaedler Damin e Victor Min Sub Kim sob orientação do Prof. Dr Marcos Antonio Simplicio Junior para a obtenção do título de Engenheiro de Computação pela Escola Politécnica da Universidade de São Paulo (POLI-USP).

## Contexto e Motivação

A demanda por segurança cibernética em ambientes corporativos cresceu durante a pandemia da COVID-19 devido ao fato dos ataques cibernéticos contra empresas terem aumentado ao menos 300% no período. Apenas no Brasil, foram mais de 5 milhões ataques do tipo no período. Atualmente, com o uso difundido de bases de código aberto, atacantes buscam formas de inserir “backdoors” nessas bases, de forma a ter a possibilidade de corromper sistemas que utilizam dessas bases e também de escalar seus ataques. Esse tipo de ameaça não se restringe apenas às grandes cadeias de suprimento de software, mas também à própria equipe de desenvolvedores da corporação, que podem utilizar de seus acessos privilegiados para inserir backdoors no sistema e explorar tais trechos de código malicioso futuramente.

## Objetivo

O projeto tem como objetivo a concepção de uma ferramenta e técnicas que possibilitem a detecção da presença de código malicioso inserido de forma sorrateira em aplicações de software. O objetivo ainda é garantir que os administradores dos sistemas recebam avisos sobre a existência de tais trechos de código malicioso, de forma a saberem caso alguma exploração ocorra.

## Método

O método encontrado para alcançar tal objetivo foi baseado na instrumentação do código em diferentes fases do processo de desenvolvimento e implementação de software. Uma instrumentação consiste no processo de adicionar trechos de códigos que gerem logs de sistema ao serem executados. A instrumentação é possível por meio do processo de parsing, a transformação de trechos de códigos em estruturas de dados que possibilitem sua navegação e inserção de novas instruções no código.

## Arquitetura

O projeto se insere nos ambientes de Homologação e Produção. Inicialmente, o trecho de código a ser testado passa por um Parser que instrumenta todos os condicionais e, a partir disso, são realizados testes unitários e Fuzzing na aplicação para se entender quais trechos de código são executados mais frequentemente e quais não são. Os trechos que forem menos executados nos testes são considerados suspeitos, por isso, de modo a não prejudicar o desempenho, apenas estes são instrumentados no código a ser colocado em produção. Se executado, o trecho suspeito gera um log.

## Resultados

Os testes para averiguar o funcionamento da ferramenta foram realizados em uma plataforma de Internet Banking na qual foram inseridos backdoors criados a partir de condicionais, como backdoors de senha mestra. Ao se executar tais backdoors, a ferramenta foi capaz de identificar que um trecho de código suspeito foi executado. O resultado foi de acordo com o esperado.

## Execução

This project only runs in maven projects!

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
