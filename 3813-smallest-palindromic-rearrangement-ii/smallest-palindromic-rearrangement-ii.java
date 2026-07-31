class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        
        int[] halfFreq = new int[26];
        String center = "";
        int totalLen = 0;
        
        // Count frequencies for the first half
        for (int i = 0; i < 26; i++) {
            if (counts[i] % 2 != 0) {
                center = String.valueOf((char)(i + 'a'));
            }
            halfFreq[i] = counts[i] / 2;
            totalLen += halfFreq[i];
        }
        
        // If the total distinct palindromes are fewer than k, return ""
        long totalPerms = countPermutations(halfFreq, totalLen, k);
        if (totalPerms < k) {
            return "";
        }
        
        StringBuilder firstHalf = new StringBuilder();
        long remainingK = k;
        
        // Build the first half character by character
        for (int i = 0; i < totalLen; i++) {
            for (int c = 0; c < 26; c++) {
                if (halfFreq[c] > 0) {
                    halfFreq[c]--; // Try picking this character
                    
                    // Count how many permutations we can form with the rest
                    long ways = countPermutations(halfFreq, totalLen - 1 - i, remainingK);
                    
                    if (ways >= remainingK) {
                        // The k-th permutation is within this branch
                        firstHalf.append((char)(c + 'a'));
                        break; 
                    } else {
                        // Skip this character and decrease our target k
                        remainingK -= ways;
                        halfFreq[c]++; // Backtrack and try the next character
                    }
                }
            }
        }
        
        String halfStr = firstHalf.toString();
        String reversedHalfStr = firstHalf.reverse().toString();
        
        return halfStr + center + reversedHalfStr;
    }
    
    // Computes the number of unique permutations for a given frequency array.
    // Caps the calculation at the target `cap` to prevent integer overflow.
    private long countPermutations(int[] freq, int N, long cap) {
        long ways = 1;
        int n = N;
        
        // Find the maximum frequency to skip during division to optimize 
        int maxFreq = 0;
        int maxIdx = -1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxIdx = i;
            }
        }
        
        for (int i = 0; i < 26; i++) {
            if (i == maxIdx) continue;
            int f = freq[i];
            for (int j = 1; j <= f; j++) {
                // Compute Combinations: multiply by n, divide by j
                // Guaranteed to be divisible evenly
                ways = (ways * n) / j;
                n--;
                
                // Early exit if we exceed the cap
                if (ways > cap) {
                    return cap + 1;
                }
            }
        }
        
        return ways;
    }
}