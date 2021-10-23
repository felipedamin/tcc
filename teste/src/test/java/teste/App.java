package teste;

public class App 
{
    private static Integer a = 5;
    private static Integer b = 5;
    private static Boolean boolean1 = true;
    private static Boolean boolean2 = false;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        if (a < 10) {
            System.out.println( "a é menor que 10" );
        }
        if (b > 10) {
            System.out.println( "b é maior que 10" );
        }
        if (boolean1) {
            System.out.println( "boolean1 é true" );
        }
        if (!boolean2) {
            System.out.println( "boolean2 é false" );
        }
        System.out.println("!boolean2 é: "+!boolean2);
    }
    public static void method1() {
        System.out.println("metodo 1 foi chamado");
    }

    public static Boolean method2() {
        return true;
    }

    public static void funcaoIfElseTryCatch() {
        if (a < 10) {
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
            if ((boolean1 & true) || (true && a == b)) {
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
}
