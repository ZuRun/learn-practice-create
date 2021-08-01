package cn.zull.lpc.learn.basic.algorithm;

import cn.zull.lpc.common.basis.utils.RandomUtils;

import java.util.Arrays;

/**
 * 快排
 *
 * @author Jared.Zu
 * @date 2021/5/29 15:30:07
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[10];
        int[] arr2 = new int[10];
        for (int i = 0; i < arr.length; i++) {
            int v = RandomUtils.randomNumber(100);
            arr[i] = v;
            arr2[i] = v;
        }
        System.out.println("------------------排序前------------------");
        System.out.println(Arrays.toString(arr));
        System.out.println("------------------排序后------------------");
        start(arr);
        System.out.println("------------------标准答案------------------");
        StandardAnswer.start(arr2);

        System.out.println(Arrays.equals(arr, arr2));
    }


    public static void start(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int n = partition(arr, left, right);
            quickSort(arr, left, n - 1);
            quickSort(arr, n + 1, right);
        }
    }

    /**
     * {*39, 28, 55, 87, 66, 3, 17, *39}
     * {*17, 28, 55, 87, 66, 3, *xx, 39}
     * {17, 28, *xx, 87, 66, 3, *55, 39}
     * -
     * {17, 28, *3, 87, 66, *xx, 55, 39}
     * {17, 28, 3, *xx, 66, *87, 55, 39}
     * -
     * {17, 28, 3, 39, 66, 87, 55, 39}
     * <p>
     * -----------
     * {17, 28, 3}   {66, 87, 55, 39}
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */

    public static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }


    public static class StandardAnswer {
        public static void main(String[] args) {
            int[] arr = new int[]{39, 28, 55, 87, 66, 3, 17, 39};
            start(arr);
        }

        public static void start(int[] arr) {
            quickSort(arr, 0, arr.length - 1);
            System.out.println(Arrays.toString(arr));
        }

        public static void quickSort(int[] arr, int left, int right) {
            // 中心元素
            int middle;
            // 因为要递归,所以要判断
            if (left < right) {
                // 小于中心元素的放左侧,大于的放右边
                middle = partition(arr, left, right);
                // 中心元素左侧数组 继续排序
                quickSort(arr, left, middle - 1);
                // 中心元素右侧数组 继续排序
                quickSort(arr, middle + 1, right);
            }
        }

        /**
         * {*39, 28, 55, 87, 66, 3, 17, *39}
         * {*17, 28, 55, 87, 66, 3, *xx, 39}
         * {17, 28, *xx, 87, 66, 3, *55, 39}
         * -
         * {17, 28, *3, 87, 66, *xx, 55, 39}
         * {17, 28, 3, *xx, 66, *87, 55, 39}
         * -
         * {17, 28, 3, 39, 66, 87, 55, 39}
         * <p>
         * -----------第一波排序后
         * {17, 28, 3}   {66, 87, 55, 39}
         */
        public static int partition(int[] arr, int left, int right) {
            int pivot = arr[left];
            while (left < right) {
                while (left < right && arr[right] >= pivot)
                    right--;
                arr[left] = arr[right];
                while (left < right && arr[left] <= pivot)
                    left++;
                arr[right] = arr[left];
            }
            arr[left] = pivot;
            return left;
        }
    }
}
