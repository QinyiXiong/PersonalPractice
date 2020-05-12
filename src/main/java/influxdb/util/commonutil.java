package influxdb.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author: 覃义雄
 * @dateTime: 2020/5/11 1:49 下午
 * @project_Name: personalPractice
 * @Name: commonutil
 * @Describe：
 */
public class commonutil {

    private static final String SALTED_STR = "Salted__";
    private static final byte[] SALTED_MAGIC = new byte[0];

    private static byte[] array_concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static String encrypt(String text, String password) throws Exception {
        byte[] pass = password.getBytes(StandardCharsets.US_ASCII);
        byte[] salt = (new SecureRandom()).generateSeed(8);
        byte[] inBytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] passAndSalt = array_concat(pass, salt);
        byte[] hash = new byte[0];
        byte[] keyAndIv = new byte[0];

        byte[] iv;
        for(int i = 0; i < 3 && keyAndIv.length < 48; ++i) {
            iv = array_concat(hash, passAndSalt);
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(iv);
            keyAndIv = array_concat(keyAndIv, hash);
        }

        byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
        iv = Arrays.copyOfRange(keyAndIv, 32, 48);
        SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, key, new IvParameterSpec(iv));
        byte[] data = cipher.doFinal(inBytes);
        data = array_concat(array_concat(SALTED_MAGIC, salt), data);
        return Base64.getEncoder().encodeToString(data);
    }

    public static String decrypt(String encrypted, String password) throws Exception {
        byte[] pass = password.getBytes(StandardCharsets.US_ASCII);
        byte[] inBytes = Base64.getDecoder().decode(encrypted);
        byte[] shouldBeMagic = Arrays.copyOfRange(inBytes, 0, SALTED_MAGIC.length);
        if (!Arrays.equals(shouldBeMagic, SALTED_MAGIC)) {
            throw new IllegalArgumentException("Initial bytes from input do not match OpenSSL SALTED_MAGIC salt value.");
        } else {
            byte[] salt = Arrays.copyOfRange(inBytes, SALTED_MAGIC.length, SALTED_MAGIC.length + 8);
            byte[] passAndSalt = array_concat(pass, salt);
            byte[] hash = new byte[0];
            byte[] keyAndIv = new byte[0];

            for(int i = 0; i < 3 && keyAndIv.length < 48; ++i) {
                byte[] hashData = array_concat(hash, passAndSalt);
                MessageDigest md = MessageDigest.getInstance("MD5");
                hash = md.digest(hashData);
                keyAndIv = array_concat(keyAndIv, hash);
            }

            byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
            SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
            byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, key, new IvParameterSpec(iv));
            byte[] clear = cipher.doFinal(inBytes, 16, inBytes.length - 16);
            return new String(clear, StandardCharsets.UTF_8);
        }
    }
}
