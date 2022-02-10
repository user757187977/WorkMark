package codetop.b数组;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/9 09:21
 */
public class 数组排序 {

    public static void charu(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            int j    = i;
            while (j > 0 && nums[j - 1] > temp) {
                nums[j - 1] = nums[j];
                j--;
            }
            nums[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 1, 6, 2, 5, 4};
        charu(nums);
        System.out.println(nums);
    }
}
