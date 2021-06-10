package cn.zull.lpc.learn.leetcode.editor.cn;

//给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。 
//
// 
// '.' 匹配任意单个字符 
// '*' 匹配零个或多个前面的那一个元素 
// 
//
// 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。 
// 
//
// 示例 1： 
//
// 
//输入：s = "aa" p = "a"
//输出：false
//解释："a" 无法匹配 "aa" 整个字符串。
// 
//
// 示例 2: 
//
// 
//输入：s = "aa" p = "a*"
//输出：true
//解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
// 
//
// 示例 3： 
//
// 
//输入：s = "ab" p = ".*"
//输出：true
//解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
// 
//
// 示例 4： 
//
// 
//输入：s = "aab" p = "c*a*b"
//输出：true
//解释：因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
// 
//
// 示例 5： 
//
// 
//输入：s = "mississippi" p = "mis*is*p*."
//输出：false 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 20 
// 0 <= p.length <= 30 
// s 可能为空，且只包含从 a-z 的小写字母。 
// p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。 
// 保证每次出现字符 * 时，前面都匹配到有效的字符 
// 
// Related Topics 字符串 动态规划 回溯算法 
// 👍 2170 👎 0

public class Q10 {
    public static void main(String[] args) {
        Solution solution = new Q10().new Solution();
//        System.out.println(solution.isMatch("aa", ".*"));
//        System.out.println(solution.isMatch("aa", "a"));
//        System.out.println(solution.isMatch("abbas", "ab*as"));
//        System.out.println(solution.isMatch("abbas", "ab.as"));
//        System.out.println(solution.isMatch("abbas", "ab.*as"));
//        System.out.println(solution.isMatch("abbas", "ab.*aas"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isMatch(String s, String p) {
            while (p.startsWith("*") && p.length() > 1) {
                p = p.substring(1);
            }
            int sOffset = 0;
            int pOffset = 0;
            char asterisk = '\0';
            while (sOffset < s.length()) {
                char c = s.charAt(sOffset);
                while (true) {
                    char pChar;
                    // 如果p长度不够了,除非结尾*,否则返回失败
                    if (p.length() <= pOffset) {
                        if (p.lastIndexOf(0) != '*') {
                            return false;
                        }
                        pChar = '*';
                    } else {
                        pChar = p.charAt(pOffset);
                    }

                    if (pChar != '*') {
                        asterisk = pChar;
                    }
                    if (pChar == '.') {
                        pOffset++;
                        break;
                    }
                    if (pChar == '*') {
                        // 匹配到了或.全匹配
                        if (asterisk == c || asterisk == '.') {
                            break;
                        }
                        pOffset++;

                        continue;
                    }
                    if (pChar == c) {
                        pOffset++;
                        break;
                    }
                    return false;
                }
                sOffset++;
            }
            // 最后校验p 是否有未结束的
            // 结尾必须是*才可
            while (pOffset + 1 < p.length()) {
                char end = p.charAt(pOffset + 1);
                if (end != '*') {
                    return false;
                }
                pOffset++;
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}