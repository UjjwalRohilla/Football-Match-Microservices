package com.test.encrypted.service.encryptionService;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class EncryptService {
    private SecretKey secretKey = null;

    public void createKeys(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String encryptData(String object) throws Exception{
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] encrypt = cipher.doFinal(object.getBytes());
            return new String(Base64.getEncoder().encodeToString(encrypt));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String decryptMessage(String encryptMessage){
        try {
            System.out.println("THIS IS ENCRYPTED DATA HAVE TO DECRYPT: "+ encryptMessage);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(encryptMessage));
            String decrypted = new String(decrypt);

            return decrypted;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
