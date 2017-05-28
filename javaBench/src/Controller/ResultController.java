package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ResultController {

    /**
     *
     */
    private static final Map<String, Long> cpuResults = new HashMap<>();
    /**
     *
     */
    private static final Map<String, Long> ramResults = new HashMap<>();
    /**
     *
     */
    private static final Map<String, Long> diskResults = new HashMap<>();
    /**
     *
     */
    private static long gpuResults;

    /**
     * @param time
     */
    public static void setIntResult(long time){
        cpuResults.put("intResult", time);
    }

    /**
     * @param time
     */
    public static void setLongResult(long time){
        cpuResults.put("longResult", time);
    }

    /**
     * @param time
     */
    public static void setDoubleResult(long time){
        cpuResults.put("DoubleResult", time);
    }

    /**
     * @param time
     */
    public static void setQuickstartResult(long time){
        cpuResults.put("QuicksortResult", time);
    }

    /**
     * @param time
     */
    public static void setCompressResult(long time){
        cpuResults.put("compressResult", time);
    }

    /**
     * @param time
     */
    public static void setEncryptResult(long time){
        cpuResults.put("encryptResult", time);
    }

    /**
     * @param time
     */
    public static void setPrimeNumberResult(long time){
        cpuResults.put("primeNumberResult", time);
    }

    /**
     * @param time
     */
    public static void setDiskReadResult(long time){
        diskResults.put("readResult", time);
    }

    /**
     * @param time
     */
    public static void setDiskWriteResult(long time){
        diskResults.put("writetResult", time);
    }

    /**
     * @param time
     */
    public static void setRamLinearWalkResult(long time){
        ramResults.put("linearResult", time);
    }

    /**
     * @param time
     */
    public static void setRamRandomPageWalkResult(long time){
        ramResults.put("randomPageResult", time);
    }

    /**
     * @param time
     */
    public static void setRamRandomHeapWalkResult(long time){
        ramResults.put("randomHeapResult", time);
    }

    /**
     * @param fps
     */
    public static void setGnuResult(long fps){
        gpuResults = fps;
    }

    /**
     * @return
     */
    public static long getCpuScore(){
        double sumOfTime = 0.0;
        for(double time : cpuResults.values()) sumOfTime += time;
        long cpuSlope = 100;
        double cpuReferenceTime = 10000;
        long cpuReferenceScore = 20000;
        return (sumOfTime > 0) ? (long) (-cpuSlope /100 * sumOfTime + (cpuReferenceScore - cpuSlope /100* cpuReferenceTime)) : Long.MIN_VALUE;
    }

    /**
     * @return
     */
    public static long getGpuScore(){
        long gpuSlope = 100;
        long gpuReferenceFps = 150;
        long gpuReferenceScore = 20000;
        return (gpuResults > 0) ? (gpuSlope /10 * gpuResults + (gpuReferenceScore - gpuSlope /100* gpuReferenceFps)) : Long.MIN_VALUE;
    }

    /**
     * @return
     */
    public static long getRamScore(){
        double sumOfTime = 0.0;
        for(double time : ramResults.values()) sumOfTime += time;
        long ramSlope = 100;
        double ramReferenceTime = 10000;
        long ramReferenceScore = 2000;
        return (sumOfTime > 0) ? (long) (-ramSlope /100 * sumOfTime + (ramReferenceScore - ramSlope /100* ramReferenceTime)) : Long.MIN_VALUE;
    }


    /**
     * @return
     */
    public static long getDiskScore(){
        double sumOfTime = 0.0;
        for(double time : diskResults.values()) sumOfTime += time;
        long diskSlope = 100;
        double diskReferenceTime = 10000;
        long diskReferenceScore = 20000;
        return (sumOfTime > 0) ? (long) (-diskSlope /100 * sumOfTime + (diskReferenceScore - diskSlope /100* diskReferenceTime)) : Long.MIN_VALUE;
    }

    /**
     * @return
     */
    public static long getTotalScore(){
        List<Long> results = new ArrayList<>();
        long sum = 0;
        results.add(getCpuScore());
        results.add(getDiskScore());
        results.add(getGpuScore());
        results.add(getRamScore());
        for(long result : results){
            if (result != Long.MIN_VALUE)
                sum += result;
        }
        return sum;
    }

    /**
     *
     */
    public static void reset(){
        cpuResults.clear();
        gpuResults = 0;
        ramResults.clear();
        diskResults.clear();
    }

}
