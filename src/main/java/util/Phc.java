package util;

import com.lambdaworks.codec.Base64;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;

import java.util.Arrays;


public class Phc {
    public String hashAlgorithm;
    public byte[] salt, hash;
    public int r, p, n;




    public Phc(String phc) {
        validatePhcString(phc);
    }


    private void validatePhcString(String phcString) {
        String[] phc = phcString.split("\\$");
        String hashAlgorithm = phc[1];

        String salt = phc[3];
        String hash = phc[4];

        boolean isScrypt = hashAlgorithm.equals("scrypt");
        boolean containsParams = phc[2].contains("n=") && phc[2].contains("r=") && phc[2].contains("p");
        boolean hasSalt = salt != null;
        boolean hasHash = hash != null;

        if (!isScrypt) {
            throw new IllegalArgumentException(String.format("Hash is not scrypt, value is \"%s\"", phc[1]));
        }
        if (!containsParams) {
            throw new IllegalArgumentException("No params found in the phc string");
        }
        if (!hasSalt) {
            throw new IllegalArgumentException("No salt found in the phc string");
        }
        if (!hasHash) {
            throw new IllegalArgumentException("No hash found in the phc string");
        }

        this.salt = Base64.decode(salt.toCharArray());
        this.hash = Base64.decode(hash.toCharArray());
        this.hashAlgorithm = phc[1];

        Hash.RangeValidator.validate("hash.byteLength", this.hash.length, new int[]{64, 128});
        Hash.RangeValidator.validate("salt.byteLength", this.salt.length, new int[]{8, 1024});

        int r = extractParamValue(phc[2], "r");
        int n = extractParamValue(phc[2], "n");
        int p = extractParamValue(phc[2], "p");

        Hash.RangeValidator.validate("r", r, new int[]{1, Integer.MAX_VALUE});
        this.r = r;
        Hash.RangeValidator.validate("n", n, new int[]{1, Integer.MAX_VALUE});
        this.n = n;
        Hash.RangeValidator.validate("p", p, new int[]{1, (int) Math.floor(((Math.pow(2, 32) - 1) * 32) / (128 * r))});
        this.p = p;

    }

    private int extractParamValue(String params, String paramName) {
        String[] keyValuePairs = params.split(",");
        for (String pair : keyValuePairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2 && parts[0].trim().equals(paramName)) {
                try {
                    return Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid value for parameter " + paramName + ": " + parts[1].trim());
                }
            }
        }
        throw new IllegalArgumentException("Parameter " + paramName + " not found");
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }




    public static String PasswordToPhc(String salt, String hash) {
        // FORMAT: $<id>[$v=<version>][$<param>=<value>(,<param>=<value>)*][$<salt>[$<hash>]]
        // EXAMPLE: $scrypt$n=16384,r=8,p=1$idPZqR6sCtk7R20WhdyaWQ$Bvn4Q6AQpd3zDEGlqi+ScVOZ3igKCt44PrHR1c0lS1ICA1TMBFHBbkvJTdT+TYedNJ6Z3EtYhciLvxH0aZpu+w


        //Zou dit hoogst waarschijnlijks best uit HASH halen..
        String baseString = "$scrypt$n=16384,r=8,p=1";

        baseString = baseString.concat("$" + salt + "$" + hash );

        return baseString;

    }





    public String getHashString() {
        return bytesToHex(this.hash);
    }
}
