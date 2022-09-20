package hot100.数组;

/**
 * @Description https://leetcode.cn/problems/search-in-rotated-sorted-array/
 * @Author lishoupeng
 * @Date 2022/9/19 09:11
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * 问题的关键是, 要求时间复杂度是 O(log n)!!!
 * 不然直接遍历数组, 返回目标下标即可
 * 解法: 数组一分为二, 其中一个必定是完全有序, 另外一个是部分有序, 有序部分二分查找, 无序部分继续一分为二
 */
public class 搜索旋转排序数组 {

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            int leftV = nums[left], rightV = nums[right], middleV = nums[middle];
            if (middleV == target) return middle;
            if (leftV == target) return left;
            if (rightV == target) return right;
            if (middleV < rightV) {
                // 二分法之后, 如果右边这个区间是严格递增的, 那就看目标值是否在右边这个区间
                if (middleV < target && target <= rightV) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            } else {
                // 二分法之后, 如果右边这个区间不是严格递增的, 那我们看目标值是否在左边这个区间
                if (leftV <= target && target < middleV) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }

}
