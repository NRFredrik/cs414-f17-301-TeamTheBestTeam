package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;
/**
 * Thanks to a Hashing tutorial the hashing function allows us to store secure passwords
 * https://www.examsmyantra.com/article/101/java/using-md5-or-sha-hashing-in-java
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author kb
 *@return the hashed value as a string
 *@param the string to be hashed
 */
public class HashPassword {
    public static String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte b1 : b){
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString();
    }
}

