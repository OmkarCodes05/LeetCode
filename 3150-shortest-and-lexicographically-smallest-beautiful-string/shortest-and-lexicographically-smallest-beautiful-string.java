class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int countOnes = 0;
        int left = 0;
        String ans = "";

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '1') {
                countOnes++;
            }

            // Shrink window from left to maintain exactly k ones and remove leading zeros
            while (countOnes == k) {
                // Trim leading zeros in the current valid window
                while (s.charAt(left) == '0') {
                    left++;
                }

                String current = s.substring(left, right + 1);

                // Update best answer if:
                // 1. We haven't found any valid substring yet
                // 2. Current substring is shorter than previous best
                // 3. Current substring length matches best, but is lexicographically smaller
                if (ans.isEmpty() || current.length() < ans.length() || 
                   (current.length() == ans.length() && current.compareTo(ans) < 0)) {
                    ans = current;
                }

                // Shrink window by moving left past the first '1'
                if (s.charAt(left) == '1') {
                    countOnes--;
                }
                left++;
            }
        }

        return ans;
    }
}