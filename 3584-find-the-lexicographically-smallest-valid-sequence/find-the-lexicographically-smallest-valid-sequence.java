class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        
        // dp[i] stores the length of the longest suffix of word2 
        // that exists as a subsequence in word1[i...n-1]
        int[] dp = new int[n + 1];
        
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i] = dp[i + 1] + 1;
                j--;
            } else {
                dp[i] = dp[i + 1];
            }
        }
        
        int[] seq = new int[m];
        int seqIdx = 0;
        boolean changed = false;
        
        j = 0;
        for (int i = 0; i < n && j < m; i++) {
            // Case 1: Exact match, take it greedily
            if (word1.charAt(i) == word2.charAt(j)) {
                seq[seqIdx++] = i;
                j++;
            } 
            // Case 2: No match, but we can use our 1 allowed change here
            // We check if the remaining needed characters (m - 1 - j) 
            // can be fulfilled by the remaining suffix in word1
            else if (!changed && dp[i + 1] >= m - 1 - j) {
                seq[seqIdx++] = i;
                j++;
                changed = true; // Use the change
            }
        }
        
        // If we matched all m characters, return the valid sequence
        if (seqIdx == m) {
            return seq;
        }
        
        // No such sequence exists
        return new int[0];
    }
}