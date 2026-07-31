import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        // Step 1: Count the frequency of each character in the word
        int[] freq = new int[26];
        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Step 2: Sort the frequencies in ascending order
        Arrays.sort(freq);
        
        int totalPushes = 0;
        
        // Step 3: Iterate from the most frequent character (end of the array) to the least
        for (int i = 0; i < 26; i++) {
            // If the frequency is 0, we've processed all present characters
            if (freq[25 - i] == 0) {
                break;
            }
            
            // Calculate the multiplier based on the character's rank (i)
            // i / 8 + 1 gives the push cost: 
            // i=0..7 -> 1 push, i=8..15 -> 2 pushes, etc.
            int pushesForChar = (i / 8) + 1;
            totalPushes += freq[25 - i] * pushesForChar;
        }
        
        return totalPushes;
    }
}