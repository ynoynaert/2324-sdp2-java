package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTest {

    @Test
    void make_hash() {

        String password = "testPassword";
        String hashedPassword = Hash.make(password);

        assertNotNull(hashedPassword);
        assertNotEquals("", hashedPassword);
    }

    @Test
    void verify_works_with_correct_password() {

        String password = "testPassword";
        String hashedPassword = Hash.make(password);

        Hash hash = new Hash(hashedPassword);

        assertTrue(hash.verify(password));
    }

    @Test
    void verify_fails_with_correct_password() {

        String password = "testPassword";
        String hashedPassword = Hash.make("wrongPassword");

        Hash hash = new Hash(hashedPassword);

        assertThrows(IllegalArgumentException.class, () -> hash.verify(password));
    }


}