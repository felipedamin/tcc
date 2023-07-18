package br.usp.larc.Benchmark;

public class Methods2 {

    private static Integer a = 5;

    private static Integer b = 5;

    private static Boolean boolean1 = true;

    private static Boolean boolean2 = false;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (a < 10 || b < 5) {
            System.out.println("a é menor que 10");
        }
    }

    public static void funcaoIfElseNested() {
        System.out.println("Hello World!");
        System.out.println("Hello World!");
        if (a < 1) {
            System.out.println(a);
        } else {
            System.out.println("Hello World!");
            System.out.println("Hello World!");
            System.out.println("Hello World!");

            if (b > 1) {
                System.out.println(b);
            }
            if (b == 1) {
                System.out.println(b);
            }
        }
    }

    public static void funcaoIfElseIf() {
        System.out.println("Hello World!");
        System.out.println("Hello World!");
        System.out.println("Hello World!");
        System.out.println("Hello World!");
        if (a < 10) {
            System.out.println("a é menor que 10");
        } else if (b > 10 || b == 10) {
            System.out.println("boolean1 é true");
        } else if (b != a) {
            System.out.println("boolean1 é true");
        } else {
            System.out.println("else sem if");
        }
    }

    public static void funcaoComVariavelAntesDoIf() {
        Integer c = 2;
        if (c > b) {
            System.out.println("c>b");
        } else if (c == b) {
            System.out.println("c==b");
        } else {
            System.out.println("c<b");
        }
    }
}
