class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Try matching target[0...i-1] exactly, then branching at position i
        for (int i = n - 1; i >= 0; i--) {
            // Count frequencies needed to match target[0...i-1]
            int[] tempCount = count.clone();
            boolean possible = true;
            for (int j = 0; j < i; j++) {
                int idx = target.charAt(j) - 'a';
                if (tempCount[idx] <= 0) {
                    possible = false;
                    break;
                }
                tempCount[idx]--;
            }

            if (!possible) continue;

            // Find the smallest character strictly greater than target[i]
            int targetCharIdx = target.charAt(i) - 'a';
            int greaterCharIdx = -1;
            for (int c = targetCharIdx + 1; c < 26; c++) {
                if (tempCount[c] > 0) {
                    greaterCharIdx = c;
                    break;
                }
            }

            // Construct the solution if a valid character exists
            if (greaterCharIdx != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.substring(0, i));
                sb.append((char) ('a' + greaterCharIdx));
                tempCount[greaterCharIdx]--;

                // Fill remaining positions with remaining characters in ascending order
                for (int c = 0; c < 26; c++) {
                    while (tempCount[c] > 0) {
                        sb.append((char) ('a' + c));
                        tempCount[c]--;
                    }
                }
                return sb.toString();
            }
        }

        return "";
    }
}