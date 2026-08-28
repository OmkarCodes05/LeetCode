import java.util.*;

class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int oddCount = 0;
        char midChar = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                oddCount++;
                midChar = (char) ('a' + i);
            }
        }

        // A palindrome cannot be formed if more than 1 character has an odd count
        if (oddCount > 1) {
            return "";
        }

        int[] halfCount = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        int m = n / 2;

        // Try to match prefix of length L (from m down to 0)
        for (int L = m; L >= 0; L--) {
            int[] currentHalfCount = halfCount.clone();
            boolean possible = true;
            StringBuilder prefix = new StringBuilder();

            // Check if target[0 ... L-1] can be formed using available half counts
            for (int i = 0; i < L; i++) {
                char c = target.charAt(i);
                if (currentHalfCount[c - 'a'] > 0) {
                    currentHalfCount[c - 'a']--;
                    prefix.append(c);
                } else {
                    possible = false;
                    break;
                }
            }

            if (!possible) {
                continue;
            }

            // Case 1: L == m
            // The first half matches target's first half exactly.
            if (L == m) {
                String fullPalindrome = buildPalindrome(prefix.toString(), midChar);
                if (fullPalindrome.compareTo(target) > 0) {
                    return fullPalindrome;
                }
                continue;
            }

            // Case 2: L < m
            // At index L, pick the smallest character strictly greater than target.charAt(L)
            char targetChar = target.charAt(L);
            for (int c = targetChar - 'a' + 1; c < 26; c++) {
                if (currentHalfCount[c] > 0) {
                    currentHalfCount[c]--;
                    prefix.append((char) ('a' + c));

                    // Fill the remaining positions of the first half with smallest available characters
                    for (int j = 0; j < 26; j++) {
                        while (currentHalfCount[j] > 0) {
                            prefix.append((char) ('a' + j));
                            currentHalfCount[j]--;
                        }
                    }

                    return buildPalindrome(prefix.toString(), midChar);
                }
            }
        }

        return "";
    }

    private String buildPalindrome(String firstHalf, char midChar) {
        StringBuilder sb = new StringBuilder(firstHalf);
        if (midChar != 0) {
            sb.append(midChar);
        }
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            sb.append(firstHalf.charAt(i));
        }
        return sb.toString();
    }
}