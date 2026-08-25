import java.util.HashSet;
import java.util.Set;

class Solution {
    public int missingMultiple(int[] nums, int k) {
        // Step 1: Insert all array elements into a HashSet for O(1) lookups
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        
        // Step 2: Test positive multiples of k sequentially (k, 2k, 3k, ...)
        int currentMultiple = k;
        while (set.contains(currentMultiple)) {
            currentMultiple += k;
        }
        
        // Step 3: Return the first multiple not found in the set
        return currentMultiple;
    }
}
