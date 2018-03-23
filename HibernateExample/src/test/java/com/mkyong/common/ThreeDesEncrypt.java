package com.mkyong.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 ** Illustrates 3DES encryption and decryption that is interoperable between Java
 * JCE and OpenSSL C/C++ programs.
 */
public class ThreeDesEncrypt {

    static String keyStr = "abcdefg"; // test cipher key

    public static String encrypt(String password) throws IllegalBlockSizeException, BadPaddingException, Exception {

        ThreeDesEncrypter encrypter = new ThreeDesEncrypter();

        byte[] bytesToEncrypt = password.getBytes("UTF-8");
        byte[] result = encrypter.getCipher(Cipher.ENCRYPT_MODE, keyStr, ThreeDesEncrypter.iv).doFinal(bytesToEncrypt);

        String strEncodedPwd = encrypter.bytesToHexString(result);

        return strEncodedPwd;
    }

    public static String decrypt(String strEncodedPwd) throws IllegalBlockSizeException, BadPaddingException, Exception {

        ThreeDesEncrypter encrypter = new ThreeDesEncrypter();

        byte[] target = encrypter.getCipher(Cipher.DECRYPT_MODE, keyStr, ThreeDesEncrypter.iv).doFinal(
                encrypter.hexStringToBytes(strEncodedPwd));
        String strDecodedPwd = new String(target, "UTF-8");
        
        if(strDecodedPwd!=null && strDecodedPwd.length()>8){
            strDecodedPwd = strDecodedPwd.substring(8);
        }

        return strDecodedPwd;

    }

}
