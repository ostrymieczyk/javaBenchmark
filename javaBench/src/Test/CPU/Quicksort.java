package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.Random;

/**
 *
 */
public class Quicksort {

    /**
     *
     */
    private static int RESULT = 0;

    /**
     * @param array
     * @param left
     * @param right
     * @return
     */
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

    /**
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static int[] quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
        return arr;
    }

    /**
     * @return
     */
    private static int[] generateArray(){
        Random random = new Random();
        int [] array = new int[2_500_000];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt();
        }
        return array;
    }

    /**
     * @param loop
     * @return
     */
    private static long quicksortTest(int loop){
        System.out.println("\nquicksortTest\n");
        long time = 0;
        for (int i = 0; i<loop; i++){
            int[] toSort = generateArray(), sorted;
            Timer t = new Timer();
            sorted = quickSort(toSort, 0, toSort.length-1);
            time += t.check();
            RESULT += sorted.hashCode();
        }
        System.out.println(time/(1e6*loop)+" ms\n");
        return time;
    }

    /**
     *
     */
    public static void warmAndTest(){
        long TOTAL_TIME = 0;
        double a = quicksortTest(10);
        TOTAL_TIME += quicksortTest(60);
        ResultController.setQuickstartResult(TOTAL_TIME);
    }
}

