package util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhcTest {

    @Test
    void constructor_valid_phc() {

        String validPhcString = "$scrypt$n=16384,r=8,p=1$idPZqR6sCtk7R20WhdyaWQ$Bvn4Q6AQpd3zDEGlqi+ScVOZ3igKCt44PrHR1c0lS1ICA1TMBFHBbkvJTdT+TYedNJ6Z3EtYhciLvxH0aZpu+w";


        Phc phc = new Phc(validPhcString);

        assertNotNull(phc);
        assertEquals("scrypt", phc.hashAlgorithm);
        // and doing things
        assertNotNull(phc.salt);
        assertNotNull(phc.hash);
        assertEquals(16384, phc.n);
        assertEquals(8, phc.r);
        assertEquals(1, phc.p);
    }

    @Test
    void constructor_invalid_phc() {

        String invalidPhcString = "$invalid$phc$string$e";

        assertThrows(IllegalArgumentException.class, () -> new Phc(invalidPhcString));
    }


    @Test
    void valid_bytes_to_hex() {

        byte[] bytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F};
        String hexString = Phc.bytesToHex(bytes);
        assertEquals("48656c6c6f", hexString);
    }

    @Test
    void invalid_bytes_to_hex() {

        byte[] bytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F};
        String hexString = Phc.bytesToHex(bytes);
        assertNotEquals("47646d6e4a", hexString);
    }


    @Test
    void password_to_phc() {
        String salt = "salt";
        String hash = "hash";
        String phcString = Phc.PasswordToPhc(salt, hash);


        assertNotNull(phcString);
        assertTrue(phcString.contains(salt));
        assertTrue(phcString.contains(hash));
    }
}
