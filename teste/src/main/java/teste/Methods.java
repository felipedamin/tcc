package teste;

public class Methods {

    private static Integer a = 5;

    private static Integer b = 5;

    private static Boolean boolean1 = true;

    private static Boolean boolean2 = false;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (a < 10 && true) {
            System.out.println("a é menor que 10");
        }
        if (b > 10 || b == 10) {
            System.out.println("b é maior que 10");
        }
        if (boolean1 & true) {
            System.out.println("boolean1 é true");
        }
        if (!boolean2 | boolean2) {
            System.out.println("boolean2 é false");
        }
        System.out.println("!boolean2 é: " + !boolean2);
        String ternary = boolean1 ? "ternario" : "falso";
    }

    public static void method1() {
        System.out.println("metodo 1 foi chamado");
    }

    public static Boolean method2(boolean var) {
        if (var) {
            return !var;
        }
        return var;
    }
}
