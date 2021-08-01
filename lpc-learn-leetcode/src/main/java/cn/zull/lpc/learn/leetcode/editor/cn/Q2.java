package cn.zull.lpc.learn.leetcode.editor.cn;

//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。 
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。 
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 
//
// 示例 1： 
//
// 
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
// 
//
// 示例 2： 
//
// 
//输入：l1 = [0], l2 = [0]
//输出：[0]
// 
//
// 示例 3： 
//
// 
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
// 
//
// 
//
// 提示： 
//
// 
// 每个链表中的节点数在范围 [1, 100] 内 
// 0 <= Node.val <= 9 
// 题目数据保证列表表示的数字不含前导零 
// 
// Related Topics 递归 链表 数学 
// 👍 6274 👎 0

public class Q2 {
    public static void main(String[] args) {
        Solution solution = new Q2().new Solution();
//        ListNode listNode1 = new ListNode(2, new ListNode(4, new ListNode(3)));
//        ListNode listNode2 = new ListNode(5, new ListNode(6, new ListNode(4)));
//        System.out.println(solution.addTwoNumbers(listNode1, listNode2));
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode result = null;
            ListNode resultLast = null;

            int leijia = 0;
            while (true) {
                int s = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
                s = s + leijia;
                if (s > 9) {
                    int remainder = s % 10;
                    leijia = (s - remainder) / 10;
                    s = remainder;
                } else {
                    leijia = 0;
                }
                l1 = l1 == null ? null : l1.next;
                l2 = l2 == null ? null : l2.next;
                ListNode node = new ListNode(s);
                if (result == null) {
                    result = node;
                    resultLast = node;
                } else {
                    resultLast.next = node;
                    resultLast = node;
                }

                if (l1 == null && l2 == null) {
                    if (leijia > 0) {
                        resultLast.next = new ListNode(leijia);
                    }
                    break;
                }
            }
            return result;
        }

        int carry = 0;//记录进位

        public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
            //定义终止条件，当l1,l2指针都为null时且进位为0 ->null
            if (l1 == null && l2 == null && carry == 0) return null;

            //当有一条链表为null 且 进位为0时，next指针直接指向另外一条链表返回
            if (l1 != null && l2 == null && carry == 0) return l1;
            else if (l1 == null && l2 != null && carry == 0) return l2;

            //sum = 两链表指针位置上的数字加上进位
            int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            //计算进位
            carry = sum / 10;
            //计算链表的value
            int value = sum % 10;
            ListNode node = new ListNode(value);

            //递归算出这个node的next指向
            node.next = addTwoNumbers2((l1 == null ? null : l1.next), (l2 == null ? null : l2.next));

            return node;
        }

    }

//leetcode submit region end(Prohibit modification and deletion)

}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}