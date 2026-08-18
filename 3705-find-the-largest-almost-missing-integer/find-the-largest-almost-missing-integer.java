import java.util.HashMap;
import java.util.Map;

class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // Case 1: k = 1
        if (k == 1) {
            Map<Integer, Integer> count = new HashMap<>();
            for (int num : nums) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }
            int maxVal = -1;
            for (int num : count.keySet()) {
                if (count.get(num) == 1) {
                    maxVal = Math.max(maxVal, num);
                }
            }
            return maxVal;
        }

        // Case 2: k = n
        if (k == n) {
            int maxVal = -1;
            for (int num : nums) {
                maxVal = Math.max(maxVal, num);
            }
            return maxVal;
        }

        // Case 3: 1 < k < n
        int first = nums[0];
        int last = nums[n - 1];
        int fcount = 0;
        int lcount = 0;

        for (int num : nums) {
            if (num == first) fcount++;
            if (num == last) lcount++;
        }

        int maxVal = -1;
        if (fcount == 1) maxVal = Math.max(maxVal, first);
        if (lcount == 1) maxVal = Math.max(maxVal, last);

        return maxVal;
    }
}