package Test.CPU;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Helper.Timer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by Robert on 22.11.2016.
 */
public class DataEncryptior {

    private static int RESULT = 0;
    private static byte[] encryptedData;
    private static ByteBuffer bb;

    public static byte[] encrypt(String key, String initVector, byte[] decryptedData) {
        return crypto(key, initVector, decryptedData, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decrypt(String key, String initVector, byte[] encryptedData) {
        return crypto(key, initVector, encryptedData, Cipher.DECRYPT_MODE);
    }

    private static byte[] crypto (String key, String initVector, byte[] data, int mode){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(mode, skeySpec, iv);

            byte[] original = cipher.doFinal(data);

            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    private static double measureEncryptTime(String key, String initVector, byte[] decryptedData) {
        Timer t = new Timer();
        encryptedData = encrypt(key, initVector, decryptedData);
        return t.check();
    }

    private static void getIntFromCompressedDataAndAddItToResult(){
        RESULT += ByteBuffer.wrap(encryptedData).getInt();
    }

    private static int encryptTest(int loop){
        double time = 0;
        for (int i = 0; i<loop; i++){
            byte[] b = getRandomByteArrayInSize(10_000_000);
            time += measureEncryptTime("Bar12345Bar12345", "RandomInirVector", b);
            getIntFromCompressedDataAndAddItToResult();
        }
        System.out.println(time/(1e6*loop));
        return RESULT;
    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = encryptTest(warmupLoops);
        int b = encryptTest(testLoops);
        return a+b;
    }
}
