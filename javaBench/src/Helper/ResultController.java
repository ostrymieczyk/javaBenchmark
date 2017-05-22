package Helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by robert.ostaszewski on 22.05.2017.
 */
public class ResultController {

    private static long cpuReferenceScore = 20000;
    private static double cpuReferenceTime = 10000;
    private static long cpuSlope = 100;

    private static long gpuReferenceScore = 20000;
    private static long gpuReferenceFps = 150;
    private static long gpuSlope = 100;

    private static long diskReferenceScore = 20000;
    private static double diskReferenceTime = 10000;
    private static long diskSlope = 100;

    private static long ramReferenceScore = 2000;
    private static double ramReferenceTime = 10000;
    private static long ramSlope = 100;

    private static Map<String, Long> cpuResults = new HashMap<>();
    private static Map<String, Long> ramResults = new HashMap<>();
    private static Map<String, Long> diskResults = new HashMap<>();
    private static long gpuResults;

    public static void setIntReslut(long time){
        cpuResults.put("intResult", time);
    }

    public static void setLongReslut(long time){
        cpuResults.put("longResult", time);
    }

    public static void setDoubleReslut(long time){
        cpuResults.put("DoubleResult", time);
    }

    public static void setQuicksortReslut(long time){
        cpuResults.put("QuicksortResult", time);
    }

    public static void setCompressReslut(long time){
        cpuResults.put("compressResult", time);
    }

    public static void setEncryptReslut(long time){
        cpuResults.put("encryptResult", time);
    }

    public static void setPrimeNumberReslut(long time){
        cpuResults.put("primeNumberResult", time);
    }

    public static void setDiskReadReslut(long time){
        diskResults.put("readResult", time);
    }

    public static void setDiskWriteReslut(long time){
        diskResults.put("writetResult", time);
    }

    public static void setRamLinearWalkReslut(long time){
        ramResults.put("linearResult", time);
    }

    public static void setRamRandomPageWalkReslut(long time){
        ramResults.put("randomPageResult", time);
    }

    public static void setRamRandomHeapWalkReslut(long time){
        ramResults.put("randomHeapResult", time);
    }

    public static void setGpuReslut(long fps){
        gpuResults = fps;
    }

    public static long getCpuScore(){
        double sumOfTime = 0.0;
        for(double time : cpuResults.values()) sumOfTime += time;
        return (sumOfTime > 0) ? (long) (-cpuSlope/100 * sumOfTime + (cpuReferenceScore - cpuSlope/100*cpuReferenceTime)) : Long.MIN_VALUE;
    }

    public static long getGpuScore(){
        return (gpuResults > 0) ? (gpuSlope/10 * gpuResults + (gpuReferenceScore - gpuSlope/100*gpuReferenceFps)) : Long.MIN_VALUE;
    }

    public static long getRamScore(){
        double sumOfTime = 0.0;
        for(double time : ramResults.values()) sumOfTime += time;
        return (sumOfTime > 0) ? (long) (-ramSlope/100 * sumOfTime + (ramReferenceScore - ramSlope/100*ramReferenceTime)) : Long.MIN_VALUE;
    }


    public static long getDiskScore(){
        double sumOfTime = 0.0;
        for(double time : diskResults.values()) sumOfTime += time;
        return (sumOfTime > 0) ? (long) (-diskSlope/100 * sumOfTime + (diskReferenceScore - diskSlope/100*diskReferenceTime)) : Long.MIN_VALUE;
    }

}
