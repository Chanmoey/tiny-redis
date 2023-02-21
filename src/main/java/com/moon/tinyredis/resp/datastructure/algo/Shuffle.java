package com.moon.tinyredis.resp.datastructure.algo;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class Shuffle {

    private static final Random RANDOM = new Random();

    private Shuffle() {
    }

    public static void shuffle(Object[] arr) {
        int length = arr.length;
        for (int i = length - 1; i >= 0; i--) {
            int randomIdx = RANDOM.nextInt(length);
            Object temp = arr[i];
            arr[i] = arr[randomIdx];
            arr[randomIdx] = temp;
            length--;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Shuffle.shuffle(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
