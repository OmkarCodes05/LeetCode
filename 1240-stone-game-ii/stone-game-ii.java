class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        if (n == 0) return 0;
        
        // suffixSum[i] stores the total stones from index i to the end of the array
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        // memo[i][m] will store the max stones a player can get starting from index i with M = m
        int[][] memo = new int[n][n + 1];
        
        return dfs(piles, 0, 1, memo, suffixSum);
    }
    
    private int dfs(int[] piles, int i, int m, int[][] memo, int[] suffixSum) {
        int n = piles.length;
        
        // Base case: If the current player can take all remaining piles
        if (i + 2 * m >= n) {
            return suffixSum[i];
        }
        
        // Return precomputed result if it exists
        if (memo[i][m] > 0) {
            return memo[i][m];
        }
        
        int minOpponent = Integer.MAX_VALUE;
        
        // Current player can take X piles, where 1 <= X <= 2M
        for (int x = 1; x <= 2 * m; x++) {
            // We want to minimize the stones the next player will get
            minOpponent = Math.min(minOpponent, dfs(piles, i + x, Math.max(m, x), memo, suffixSum));
        }
        
        // Current player's max score is total remaining stones minus the opponent's best possible score
        memo[i][m] = suffixSum[i] - minOpponent;
        
        return memo[i][m];
    }
}