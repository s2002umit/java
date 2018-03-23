/**
 * 
 */
package com.mkyong.common;

/******************************************************************************
 *
 *  Copyright (c) 2010 by Cisco Systems, Inc. All rights reserved.
 *
 *  This software contains proprietary information which shall not be
 *  reproduced or transferred to other documents and shall not be disclosed
 *  to others or used for manufacturing or any other purpose without prior
 *  permission of Cisco Systems.
 *
 *=============================================================================
 */

//http://forums.sun.com/thread.jspa?threadID=5153188
//http://forums.sun.com/thread.jspa?threadID=5262948

import java.io.ByteArrayOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 ** Illustrates 3DES encryption and decryption that is interoperable between Java
 * JCE and OpenSSL C/C++ programs.
 */
public class ThreeDesEncrypter {
    /*
     * * Hardcode the IV to an arbitrary value for now, though this is settable.
     * * The IV here has to agree with the IV in the C++/OpenSSL version of
     * this.
     */
    public static byte[] iv = { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37 };

    /*
     * static public void setIV( byte[] iv ) { ThreeDesEncrypter.iv = iv; }
     */

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toString(bytes[i] & 0xff, 16);

            if (hex.length() == 1)
                sb.append("0");

            sb.append(hex);
        }

        return sb.toString();
    }

    public byte[] hexStringToBytes(String s) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for (int i = 0; i < s.length(); i += 2) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c))
                continue;

            if (i == s.length() - 1) {
                throw new IllegalArgumentException("invalid hex string=" + s);
            }

            try {
                os.write(Integer.parseInt(s.substring(i, i + 2), 16));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("invalid hex string=" + s + ": " + ex);
            }
        }

        return os.toByteArray();
    }

    public Cipher getCipher(int mode, String keyStr, byte[] ivBytes) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        /*
         * * I believe for three-key triple-DES that only 24 bytes of this are
         * used.
         */
        byte[] keyBytes = new byte[32];
        byte[] keyStrBytes = keyStr.getBytes("UTF-8");
        for (int i = 0; i < keyBytes.length; i++)
            keyBytes[i] = (i < keyStrBytes.length) ? keyStrBytes[i] : 0;

        DESedeKeySpec spec = new DESedeKeySpec(keyBytes);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

        SecretKey secret = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("DESede/CFB/NoPadding");

        cipher.init(mode, secret, ivSpec);

        return cipher;
    }
}