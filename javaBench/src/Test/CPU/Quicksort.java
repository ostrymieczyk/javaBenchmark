package Test.CPU;

import Helper.Timer;

import java.util.Random;

/**
 * Created by Robert on 22.11.2016.
 */
public class Quicksort {

    private static int RESULT = 0;

    private static int partition(int arr[], int left, int right)
    {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
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

    private static int quicksortTest(int loop){
        System.out.println("\nquicksortTest\n");
        double time = 0.0;
        for (int i = 0; i<loop; i++){
            int[] toSort = generateArray(2_500_000), sorted;
            Timer t = new Timer();
            sorted = quickSort(toSort, 0, toSort.length-1);
            time += t.check();
            RESULT += sorted.hashCode();
        }
        System.out.println(time/(1e6*loop)+" ms\n");
        return RESULT;
    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = quicksortTest(warmupLoops);
        int b = quicksortTest(testLoops);
        return a + b;
    }
}

