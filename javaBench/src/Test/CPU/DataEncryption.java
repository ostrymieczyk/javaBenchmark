package Test.CPU;

import Controller.ResultController;
import Helper.Timer;
import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 *
 */
public class DataEncryption {

    /**
     *
     */
    private static int RESULT = 0;
    /**
     *
     */
    private static byte[] encryptedData;

    /**
     * @param key
     * @param initVector
     * @param decryptedData
     * @return
     */
    private static byte[] encrypt(String key, String initVector, byte[] decryptedData) {
        return crypto(key, initVector, decryptedData);
    }

    /**
     * @param key
     * @param initVector
     * @param data
     * @return
     */
    private static byte[] crypto(String key, String initVector, byte[] data){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            return cipher.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param size
     * @return
     */
    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    /**
     * @param key
     * @param initVector
     * @param decryptedData
     * @return
     */
    private static long measureEncryptTime(String key, String initVector, byte[] decryptedData) {
        Timer t = new Timer();
        encryptedData = encrypt(key, initVector, decryptedData);
        return t.check();
    }

    /**
     *
     */
    private static void getIntFromCompressedDataAndAddItToResult(){
        RESULT += encryptedData.hashCode();
    }

    /**
     * @param loop
     * @return
     */
    private static long encryptTest(int loop){
        System.out.println("\nencryptTest\n");
        long time = 0;
        String chars = "ABCDEFGHIJKLMNOPRST1234567890";
        for (int i = 0; i<loop; i++){
            byte[] b = getRandomByteArrayInSize(1024*1024*5);
            String key = RandomStringUtils.random(16, chars);
            String initVector = RandomStringUtils.random(16, chars);
            time += measureEncryptTime(key, initVector, b);
            getIntFromCompressedDataAndAddItToResult();
        }
        System.out.println(time/(1e6*loop)+ " ms\n");
        return time;
    }

    /**
     *
     */
    public static void warmupAndTest(){
        long TOTAL_TIME = 0;
        long a = encryptTest(30);
        TOTAL_TIME += encryptTest(180);
        ResultController.setEncryptResult(TOTAL_TIME);
    }
}
