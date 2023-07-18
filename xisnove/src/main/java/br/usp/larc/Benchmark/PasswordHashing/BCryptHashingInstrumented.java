package br.usp.larc.Benchmark.PasswordHashing;

import org.mindrot.jbcrypt.BCrypt;
import br.usp.larc.Modifier.LogFile;

public class BCryptHashingInstrumented {

    private static final int BCRYPT_ROUNDS = 10;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    public static Boolean verifyPassword(String password, String hashedPassword) {
        Boolean isValidPassword = BCrypt.checkpw(password, hashedPassword);
        LogFile.write("BCryptHashing#verifyPassword", "ifStmt", "isValidPassword", isValidPassword);
        if (isValidPassword) {
            return true;
        } else {
            return false;
        }
    }
}
