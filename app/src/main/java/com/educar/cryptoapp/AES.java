package com.educar.cryptoapp;

import android.util.Base64;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Eduardo on 14/05/2016.
 */
public class AES {

    private static final String ALGORITMO = "AES";
    private static final String salt ="elfrionoexiste";


    /**
     * Método que genera una SecretKey a partir de un String pasado por paremtro
     * @param pass es el String a partir del cual se genera la SecretKey
     * @return una SecretKey
     */
    public static SecretKey generarKey(String pass)
    {
        char[] bpassword = pass.toCharArray();
        byte[] bsalt = salt.getBytes();
        SecretKeyFactory factory = null;
        SecretKey secretKey = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(bpassword, bsalt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * Metodo que encripta un String en función de la key pasada por parámetro
     * @param texto_a_encriptar es el texto a encriptar
     * @param key es la key que determina la encriptación
     * @return un String con el texto encriptado
     * @throws Exception
     */
    public static String encriptar (String texto_a_encriptar,Key key) throws Exception
    {

        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, key );

        byte[] encrypted = cipher.doFinal(texto_a_encriptar.getBytes("UTF-8"));
        String texto_encriptado = Base64.encodeToString(encrypted, Base64.DEFAULT);//new String(encrypted, "UTF-8");

        return texto_encriptado;


    }

    public static String desencriptar(String texto_encriptado,Key key) throws Exception
    {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodificar_texto = Base64.decode(texto_encriptado.getBytes("UTF-8"), Base64.DEFAULT);
        byte[] desencriptado = cipher.doFinal(decodificar_texto);

        return new String(desencriptado, "UTF-8");
    }
}
