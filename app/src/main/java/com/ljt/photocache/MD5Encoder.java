/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ljt.photocache;


import java.security.MessageDigest;

/**
 * Encode an MD5 digest into a String.
 * <p>
 * The 128 bit MD5 hash is converted into a 32 character long String.
 * Each character of the String is the hexadecimal representation of 4 bits
 * of the digest.
 *
 * @author Remy Maucherat
 * @version $Revision: 467222 $ $Date: 2006-10-24 05:17:11 +0200 (mar., 24 oct. 2006) $
 */

public final class MD5Encoder {


    // ----------------------------------------------------- Instance Variables


    private static final char[] hexadecimal =
    {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
     'a', 'b', 'c', 'd', 'e', 'f'};


    // --------------------------------------------------------- Public Methods


    /**
     * Encodes the 128 bit (16 bytes) MD5 into a 32 character String.
     *
     * @param binaryData Array containing the digest
     * @return Encoded MD5, or null if encoding failed
     */
    public String encode( byte[] binaryData ) {

        if (binaryData.length != 16)
            return null;

        char[] buffer = new char[32];

        for (int i=0; i<16; i++) {
            int low = (int) (binaryData[i] & 0x0f);
            int high = (int) ((binaryData[i] & 0xf0) >> 4);
            buffer[i*2] = hexadecimal[high];
            buffer[i*2 + 1] = hexadecimal[low];
        }

        return new String(buffer);

    }
    public static String encode(String message) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++)
            {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return md5StrBuff.toString().toUpperCase();//字母大写
    }


}