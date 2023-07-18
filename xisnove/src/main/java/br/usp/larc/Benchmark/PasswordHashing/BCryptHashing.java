package br.usp.larc.Benchmark.PasswordHashing;


import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashing {

    private static final int BCRYPT_ROUNDS = 10;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    public static Boolean verifyPassword(String password, String hashedPassword) {
        Boolean isValidPassword = BCrypt.checkpw(password, hashedPassword);

        if (isValidPassword) {
            return true;
        } else {
            return false;
        }
    }
}