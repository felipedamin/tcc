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
        else if (b > 10 || b == 10) {
            System.out.println("b é maior que 10");
        }

        if ((boolean1 & true) || (true && a==b)) {
            System.out.println("boolean1 é true");
        }
        else {
            System.out.println("else sem if");
        }
        if (!boolean2 | boolean2) {
            System.out.println("boolean2 é false");
        }
        System.out.println("!boolean2 é: " + !boolean2);
        String ternary = boolean1 ? "ternario" : "falso";
    }

    public static void method1() {
        System.out.println("metodo 1 foi chamado");

        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }

        for (int i = 0, j = 0; i < 3 && j == i; i++, j++) {
            System.out.println(i);
        }
        
        int whileInt = 0;
        while (whileInt <= 2) {
            System.out.println(whileInt);
            whileInt += 1;
        }
    }

    public static Boolean method2(boolean var, int switchInt) {
        if (var) {
            return !var;
        }
        switch (switchInt + 5) {
            case 6:    
                break;
            case 7:    
                break;
            case 5:    
                break;
            
            default:
                break;
        }
        return var;
    }
}
