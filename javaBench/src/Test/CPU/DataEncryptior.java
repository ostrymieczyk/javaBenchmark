package Test.CPU;

import Helper.Timer;
import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Robert on 22.11.2016.
 */
public class DataEncryptior {

    private static int RESULT = 0;
    private static byte[] encryptedData;

    public static byte[] encrypt(String key, String initVector, byte[] decryptedData) {
        return crypto(key, initVector, decryptedData, Cipher.ENCRYPT_MODE);
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
        RESULT += encryptedData.hashCode();
    }

    private static int encryptTest(int loop){
        System.out.println("\nencryptTest\n");
        double time = 0;
        String chars = "ABCDEFGHIJKLMNOPRST1234567890";
        for (int i = 0; i<loop; i++){
            byte[] b = getRandomByteArrayInSize(1024*1024*5);
            String key = RandomStringUtils.random(16, chars);
            String initVector = RandomStringUtils.random(16, chars);
            time += measureEncryptTime(key, initVector, b);
            getIntFromCompressedDataAndAddItToResult();
        }
        System.out.println(time/(1e6*loop)+ " ms\n");
        return RESULT;
    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = encryptTest(warmupLoops);
        int b = encryptTest(testLoops);
        return a+b;
    }
}
