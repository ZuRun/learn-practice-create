package cn.zull.lpc.learn.leetcode.editor.cn;

//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 
// 👍 3715 👎 0

public class Q5 {
    public static void main(String[] args) {
        Solution solution = new Q5().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public  String longestPalindrome(String s) {
            int length = s.length();
            int maxLength = 0;

            int answer = 0;
            for (int i = 0; i < length; i++) {
                int left = i, right = i;
                // abba这种情况
                while (right + 1 < length && s.charAt(left) == s.charAt(right + 1)) {
                    right++;
                }
                if (right + 1 - left > 0) {
                    i += right - left;
                }

                while (left >= 0
                        && right < length
                        && s.charAt(left) == s.charAt(right)
                ) {
                    if ((right - left) > maxLength) {
                        maxLength = right - left;
                        answer = left;
                    }
                    left--;
                    right++;
                }
            }
            return s.substring(answer, answer + maxLength + 1);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}