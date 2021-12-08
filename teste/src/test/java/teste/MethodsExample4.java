package teste;

public class MethodsExample4 {

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

    public static void funcaoNestedIfs() {
        if (a < 10 && b == 5) {
            System.out.println("a é menor que 10");
            if (b > 1) {
                System.out.println(b);
                if (b == 1) {
                    System.out.println(b);
                }
            }
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
        if (boolean1 || a == b){
            System.out.println("funcaoIfmultiplasCondicoes");
        }
    }

    public static void funcaoIfTryCatch() {
        try {
            if (a < 10) {
                System.out.println("a é menor que 10");
            }
        } catch (Exception e) {
            System.err.println("exception");
        }
    }
    
    public static void funcaoIfElseTryCatch() {
        if (a < 10 || b==5) {
                System.out.println("a é menor que 10");
        } else {
            System.out.println("else");
            
            try {
                System.out.println("try");
                if (b>a) {
                    System.out.println("b>a");
                }
            } catch (Exception e) {
                System.err.println("exception");
            }
        }
    }

    public static void funcaoIfNestedTryCatch() {
        try {
            if (boolean1 || a == b) {
                try {
                    if (a < 10) {
                        System.out.println("a é menor que 10");
                    }
                } catch (Exception e) {
                    System.err.println("exception");
                }
            }
        } catch (Exception e) {
            System.err.println("exception");
        }
    }

    public static Boolean funcaoSwitchNested(boolean var, int switchInt) {
        if (var) {
            return !var;
        }
        switch(switchInt + 5) {
            case 6: {
                break;
            }
            case 7:
                System.out.println("is seven");
                if (var) {
                    System.out.println("var is true");
                }
                break;
            case 5:
                break;
            default:
                if (a > 10) {
                    System.out.println("a > 10");
                }
                else {
                    System.out.println("a > 10 is not true");
                }
                break;
        }
        return var;
    }
}
