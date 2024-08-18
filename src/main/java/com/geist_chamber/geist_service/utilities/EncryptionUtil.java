package com.geist_chamber.geist_service.utilities;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    private final AES256TextEncryptor textEncryptor;

    public EncryptionUtil(@Value("${jasypt.encryptor.password}") String encryptionKey) {
        this.textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionKey);
    }

    public String encrypt(String input) {
        return textEncryptor.encrypt(input);
    }

    public String decrypt(String input) {
        return textEncryptor.decrypt(input);
    }
}
