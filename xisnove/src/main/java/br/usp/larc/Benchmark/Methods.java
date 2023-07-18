package br.usp.larc.Benchmark;

public class Methods {

    private static Integer a = 5;

    private static Integer b = 5;

    private static Boolean boolean1 = true;

    private static Boolean boolean2 = false;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (a < 10 || b < 5) {
            System.out.println("a é menor que 10");
        }
        if (a < 10) {
            System.out.println("a é menor que 10");
        }
        if (a < 10 || b < 5) {
            System.out.println("a é menor que 10");
        }
        if (a < 5) {
            System.out.println("a é menor que 5");
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

    public static Integer getA() {
        return a;
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
        String ternary = boolean1 ? "ternario" : "falso";
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

    public static void funcaoComVariavelAntesDoIf() {
        Integer c = 2;
        if (c>b) {
            System.out.println("c>b");
        } else if (c==b) {
            System.out.println("c==b");
        } else {
            System.out.println("c<b");
        }
    }
}
