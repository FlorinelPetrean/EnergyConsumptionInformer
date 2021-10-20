package com.ds.EnergyUtilityPlatform.utils;

import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.*;


@Component
public class PasswordEncoder {
    public PasswordEncoder() {
    }


    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
