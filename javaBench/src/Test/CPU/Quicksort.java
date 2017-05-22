package Test.CPU;

import Helper.ResultController;
import Helper.Timer;

import java.util.Random;

/**
 * Created by Robert on 22.11.2016.
 */
public class Quicksort {

    private static int RESULT = 0;
    private static long TOTAL_TIME = 0;

    private static int partition(int array[], int left, int right)
    {
        int indexLeft = left, indexRight = right;
        int tmp;
        int pivot = array[(left + right) / 2];

        while (indexLeft <= indexRight) {
            while (array[indexLeft] < pivot)
                indexLeft++;
            while (array[indexRight] > pivot)
                indexRight--;
            if (indexLeft <= indexRight) {
                tmp = array[indexLeft];
                array[indexLeft] = array[indexRight];
                array[indexRight] = tmp;
                indexLeft++;
                indexRight--;
            }
        }
        return indexLeft;
    }

    public static int[] quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
        return arr;
    }

    private static int[] generateArray(int size){
        Random random = new Random();
        int [] array = new int[size];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt();
        }
        return array;
    }

    private static long quicksortTest(int loop){
        System.out.println("\nquicksortTest\n");
        long time = 0;
        for (int i = 0; i<loop; i++){
            int[] toSort = generateArray(2_500_000), sorted;
            Timer t = new Timer();
            sorted = quickSort(toSort, 0, toSort.length-1);
            time += t.check();
            RESULT += sorted.hashCode();
        }
        System.out.println(time/(1e6*loop)+" ms\n");
        return time;
    }

    public static double warmupAndTest(int warmupLoops, int testLoops){
        TOTAL_TIME = 0;
        double a = quicksortTest(warmupLoops);
        TOTAL_TIME += quicksortTest(testLoops);
        ResultController.setQuicksortReslut(TOTAL_TIME);
        return a;
    }
}

