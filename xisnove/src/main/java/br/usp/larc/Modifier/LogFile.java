package br.usp.larc.Modifier;

import java.io.IOException;
import java.util.logging.*;

public class LogFile {
    private static final Logger logger = Logger.getLogger(LogFile.class.getName());
    private static final FileHandler fileHandler;

    static {
        try {
            // Remove default console handler
            logger.setUseParentHandlers(false);
            
            fileHandler = new FileHandler("xisnove/src/main/java/br/usp/larc/Modifier/logFile.out", true); // Set append mode to true
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord record) {
                    return record.getMessage() + System.lineSeparator();
                }
            });
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to initialize logger");
        }
    }

    public static void write(String classAndMethodName, String conditionType, String condition, String[] conditionParams, Object[] paramValue, boolean finalValue) {
        // array, se for nested, se nao for, loga mais uma linha
        // ordem de implementaçao:
        // "class#methodName#[a<10,b!=a]#true
        // "class#methodName#a<10 && b!=a#[a.toString(),b.toString()]#true
        // "class#methodName#(if|for|while|switch)#a<10 && b!=a#[a,b]#[a.toString(),b.toString()]#true"
        // "class#methodName#(if|for|while|switch)#a<10 && b!=a#[a,b]#[a.toString(),b.toString()]#true#(numLinha|hash)"
        String formatted = String.format("%s#%s#%s", classAndMethodName);
        logger.info(formatted);
    }

    public static void write(String classAndMethodName, String conditionType, String condition, boolean finalValue, String allTokens, String ...tokenValues) {
        // "class#methodName#a<10 && b!=a#true
        String formatted = String.format("%s#%s#%s#%b#%s#%s", classAndMethodName, conditionType, condition, finalValue, allTokens, String.join(",", tokenValues));
        logger.info(formatted);
    }

    public static void write(String classAndMethodName, String conditionType, String condition, boolean finalValue) {
        // array, se for nested, se nao for, loga mais uma linha
        String formatted = String.format("%s#%s#%s#%b", classAndMethodName, conditionType, condition, finalValue);
        logger.info(formatted);
    }
}
