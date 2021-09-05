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
    }

    public static void funcaoIfElseNested() {
        if (a < 1) {
            System.out.println(a);
        } else {
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
        if (a < 10) {
            System.out.println("a é menor que 10");
        } else if (b > 10 || b == 10) {
            System.out.println("boolean1 é true");
        } else if (b != a){
            System.out.println("boolean1 é true");
        } else {
            System.out.println("else sem if");
        }
    }

    public static void funcaoIfmultiplasCondicoes() {
        if ((boolean1 & true) || (true && a == b)){
            System.out.println("funcaoIfmultiplasCondicoes");
        }
    }
    public static void funcaoTernarioNested() {
        if (!boolean2 | boolean2) {
            if (boolean1) {
                System.out.println(boolean1);
            }
            System.out.println("!boolean2 é: " + !boolean2);
            String ternary = boolean1 ? "ternario" : "falso";
        }
    }

    public static void funcaoForWhile() {
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

    public static Boolean funcaoSwitch(boolean var, int switchInt) {
        if (var) {
            return !var;
        }
        switch(switchInt + 5) {
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
