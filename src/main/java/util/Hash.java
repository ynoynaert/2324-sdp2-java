package util;

import com.lambdaworks.codec.Base64;
import org.bouncycastle.crypto.generators.SCrypt;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;


public class Hash {

    private static final int N = 16384;
    private static final int R = 8;
    private static final int P = 1;
    private static final int dkLen = 64;
    Phc phc;

    public Hash(String phcFormat) {
        this.phc = new Phc(phcFormat);

    }


    public static String make(String password) {

        try {
            byte[] salt = LocalDate.now().toString().getBytes();

            byte[] hash = SCrypt.generate(password.getBytes(), salt, N, R, P, dkLen);
            char[] hashBase64 = Base64.encode(hash);
            char[] saltBase64 = Base64.encode(salt);
            String hashGood = new String(hashBase64);
            String saltGood = new String(saltBase64);


            return Phc.PasswordToPhc(saltGood, hashGood);
        } catch (Exception e) {

            throw new IllegalArgumentException(String.format("Something went wrong! %s", e.getMessage()));
        }

    }

    public boolean verify(String password) {

        String generatedHash = Phc.bytesToHex(SCrypt.generate(password.getBytes(StandardCharsets.UTF_8), phc.salt, phc.n, phc.r, phc.p, 64));
        boolean b = generatedHash.equals(phc.getHashString());

        if (!b) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return b;

    }


    public static class RangeValidator {
        public static void validate(String label, int value, int[] range) {
            int minValue = range[0];
            int maxValue = range[1];
            if (value < minValue || value > maxValue) {
                throw new IllegalArgumentException("The \"" + label + "\" option must be in the range (" + minValue + " <= " + label + " <= " + maxValue + ")");
            }
        }
    }


}
