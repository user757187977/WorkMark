package codetop.字符串.aagZ字形变换;

import java.util.ArrayList;
import java.util.List;

/**
 * 给一个字符串 s 和行数 n，从上往下，从左往右，进行 Z 字形排列，比如
 * s = "PAYPALISHIRING"      n = 3
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * ================================================
 * s = "PAYPALISHIRING"      n = 4
 * P     I     N
 * A   L S   I G
 * Y A   H R
 * P     I
 * ================================================
 * 其实是镜像的 N 字形，非要说成 Z 字形
 * 恶心🤮
 */
public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.convert("PAYPALISHIRING", 5));
    }

    /**
     * 他这个思路有点类似往哪一行里放什么字符，而不是把字符放到哪一行里；遍历的是字符串 s，但同时是在控制 n 的下标，先正再反...1 2 3 2 1...
     * 也就是说 n += 1 也有可能是 n += -1；而到底是 += 1 还是 += -1 要看下标是否为 0 或 n
     * s="PAYPALISHIRING";n=5
     * 遍历 s 开始：
     * n[0] 填进去 s[0]；n[1] 填进去 s[1]；n[2] 填进去 s[2]；n[3] 填进去 s[3]；n[4] 填进去 s[4]
     * 诶，这时候，下标：4 已经是最后一个了，那么，开始反着走！
     * n[3] 填进去 s[5]；n[2] 填进去 s[6]；n[1] 填进去 s[7]；n[0] 填进去 s[8]
     * 诶，这时候，下标：0 已经是最后一个了，那么，开始正着走！
     * n[1] 填进去 s[9]...(未完待续)
     */
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rows = new ArrayList<>();
        //如果 s="ab" 也就是长度为 2，n = 3，那最后我的 n 其实只会装两个字符，所以有这么一下 Math.min(numRows, s.length())
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        int curRow = 0;
        boolean goingDown = false;//控制正、反走
        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
                System.out.printf("开始 %s 走！\n", goingDown ? "正" : "反");
            }
            curRow += goingDown ? 1 : -1;
        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) {
            ret.append(row);
        }
        return ret.toString();
    }

}
