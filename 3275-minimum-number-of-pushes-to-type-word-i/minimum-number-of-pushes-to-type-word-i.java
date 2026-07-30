class Solution {
    public int minimumPushes(String word) {
        int totalPushes = 0;
        int n = word.length();
        
        for (int i = 0; i < n; i++) {
            // (i / 8) + 1 gives the number of pushes for the character at index i
            totalPushes += (i / 8) + 1;
        }
        
        return totalPushes;
    }
}