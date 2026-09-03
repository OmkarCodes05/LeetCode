class Solution {
    public boolean uniformArray(int[] nums1) {
        int minVal = Integer.MAX_VALUE;
        boolean hasOdd = false;

        for (int num : nums1) {
            if (num < minVal) {
                minVal = num;
            }
            if (num % 2 != 0) {
                hasOdd = true;
            }
        }

        // If the smallest element is odd, we can make all elements odd.
        if (minVal % 2 != 0) {
            return true;
        }

        // If the smallest element is even, we can only make all elements even 
        // if there are no odd elements in nums1.
        return !hasOdd;
    }
}