package cn.zull.lpc.learn.leetcode.interview.liulishuo;

/**
 * 英语流利说
 *
 * 这题思路挺简单，细节是魔鬼
 *
 * @author zurun
 * @date 2022/8/3 22:57:37
 */
public class LongCount {
    /**
     * All strings in this problem are strings of 0 to L lowercase English characters. We will call them valid strings.
     * All string comparisons are standard comparisons according to their lexicographic order: the string with a smaller character at the leftmost index on which they differ is smaller (e.g., "car" < "cat" because 'r' < 't') and if X is a prefix of Y, X is smaller than Y (e.g., "cat" < "cats").
     * You are given two valid strings A and B such that A < B.
     * Return the number of valid strings C such that A < C < B.
     * <p>
     * Method signature: long count(int L, String A, String B)
     * <p>
     * Constraints
     * - L will be between 0 and 10, inclusive.
     * - A and B will be valid strings.
     * - A will be less than B.
     * <p>
     * Examples
     * 0)
     * 1
     * ""
     * "d"
     * Returns: 3
     * The valid strings between "" and "d" are "a", "b", and "c".
     * <p>
     * 1)
     * 2
     * "ay"
     * "c"
     * Returns: 28
     * The valid strings are "az", "b", "ba", "bb", ..., "bx", "by", "bz".
     * <p>
     * 2)
     * 2
     * "ay"
     * "cb"
     * Returns: 30
     * Compared to the previous example we now also have valid strings "c" and "ca" within the specified range.
     * <p>
     * 3)
     * 10
     * "bulldog"
     * "bulldozers"
     * Returns: 350592
     * <p>
     * 4)
     * 4
     * "bx"
     * "fad"
     * Returns: 57028
     * <p>
     * 5)
     * 4
     * "pier"
     * "pies"
     * Returns: 0
     * <p>
     * 6)
     * 10
     * ""
     * "zzzzzzzzzz"
     * Returns: 146813779479509
     * The largest possible output.
     */

    public static void main(String[] args) {
        System.out.println(new LongCount().longCount(2, "ay", "c"));//28
        System.out.println(new LongCount().longCount(2, "ay", "cb"));//30
        System.out.println(new LongCount().longCount(10, "bulldog", "bulldozers")); //350592
        System.out.println(new LongCount().longCount(4, "pier", "pies")); //0
        System.out.println(new LongCount().longCount(10, "", "zzzzzzzzzz")); //146813779479509
        System.out.println(new LongCount().longCount(2, "", "zz")); //701

    }

    String mm = "````````````"; // `是a前一位
    String nn = "{{{{{{{{{{{{"; // {是z后一位

    public long longCount(Integer len, String str1, String str2) {
        if (len == 0) return 0;
        return longCount(len, str1, str2, 0);
    }

    public long longCount(Integer len, String str1, String str2, int i) {

        if (len == 0) return 0;
        if (i >= len) return 0;

        long count = 0;
        // ab
        // cde
        if (str1.length() <= i && str2.length() <= i) return 0;// 两个str长度都不够，返回0

        char start = str1.length() > i ? str1.charAt(i) : 'a' - 1;
        char end = str2.length() > i ? str2.charAt(i) : 'a' - 1;
        if (start == end) return longCount(len, str1, str2, i + 1); // 两个char相同，进入下一位
        if (start > end) return 0;
        int dif = end - start - 1;
        if (dif > 0) { // 中间间隔部分
            /**
             * 3
             * a
             * aa
             * aaa
             * aab
             * ab
             * aba
             *
             */
            int j = len - i - 1;
            long a = 1;
            while (j-- > 0) a = 26 * a + 1;
//            a = get(j + 1);
            count += dif * (a);
        }
        if (i < str2.length() - 1 && i < len - 1 && end != '{') {
            count += 1;
        }
        //
        if (start > '`') count += longCount(len, str1, nn, i + 1);
        if (end < '{') count += longCount(len, mm, str2, i + 1);
//        System.out.println(str1 + " " + str2 + "  " + i + "   " + count);
        return count;
    }

    public long get(int idx) {
        if (idx > 0) {
            return 26 * get(idx - 1) + 1;
        }
        return 0;
    }
}
