package code.codetop.b数组;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/9 09:40
 * 给你一个原本有序的数组并进行旋转后的数组, 比如: [0,1,2,4,5,6,7] 在下标 3 处旋转, 得到 [4,5,6,7,0,1,2]
 * 从这个旋转后的数组中, 找到目标值, 返回下标
 * 直接遍历数组, 找到目标, 即可
 * 进阶: 要求时间复杂度 O(log n)
 * 其实这题的目的就是让你二分查找
 * 思路: [4,5,6,7,0,1,2] 二分了, 必然得到一个有序的数组和一个无序的数组, 有序数组进行二分查找, 无序数组递归进行二分
 */
public class 搜索旋转排序数组 {

    public static int test(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int leftV = nums[left];
            int rightV = nums[right];
            int midV = nums[mid];
            // 二分之后 先找到递增那边 判断递增的那边是否满足你的需求
            if (midV == target) {
                return mid;
            } else if (midV <= rightV) {
                if (target > midV && target <= rightV) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            } else if (midV >= leftV) {
                if (target >= leftV && target < midV) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(test(nums, 2));
    }

}
