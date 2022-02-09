package codetop.b数组;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/9 09:40
 * 给你一个原本有序的数组并进行旋转后的数组, 比如: [0,1,2,4,5,6,7] 在下标 3 处旋转, 得到 [4,5,6,7,0,1,2]
 * 从这个旋转后的数组中, 找到目标值, 返回下标
 * 直接遍历数组, 找到目标, 即可
 * 进阶: 要求时间复杂度 O(log n)
 * 其实这题的目的就是让你二分查找
 */
public class 搜索旋转排序数组 {

    public static void test(int[] nums, int target) {

    }

    public static void main(String[] args) {
        test(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
    }
}
