package cn.zull.lpc.learn.leetcode.interview.nio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 两数之和 变种，类似三数之和做法
 * <p>
 * <p>
 * 输入一个整数数组 A 和一个目标 target ，返回A中所有和为 target 的两个元素的组合，其中不能出现重复。例如A = [1, 3, 1, 2, 2, 3]， target = 4，那么返回：[[1, 3], [2, 2]]。
 * <p>
 * 要求时间复杂度小于O(N^2)，空间复杂度O(1)。
 *
 * @author zurun
 * @date 2022/8/4 01:40:34
 */
public class TwoSumNio {
    public static void main(String[] args) {
        resolve(new int[]{1, 1, 2, 2, 3, 4, 5, 5}, 6);
    }

    // 排序 + 双指针
    public static List<List<Integer>> resolve(int[] arr, int target) {
        Arrays.sort(arr);
        List<List<Integer>> list = new ArrayList<>();
        // 1 1 2 3 4 5
        // 6
        int l = 0, r = arr.length - 1;
        while (l < r) {
            if (l > 0 && arr[l] == arr[l - 1]) {
                l++;
                continue;
            }
            // r 不加也行
            if (r < (arr.length - 1) && arr[r] == arr[r + 1]) {
                r--;
                continue;
            }

            int a = arr[l] + arr[r];
            if (a == target) {
                list.add(Arrays.asList(arr[l], arr[r]));
                System.out.println(arr[l] + " " + arr[r]);
                l++;
                r--; // 可以不加
                continue;
            }
            if (a < target) l++;
            else r--;
        }
        return list;
    }
}
