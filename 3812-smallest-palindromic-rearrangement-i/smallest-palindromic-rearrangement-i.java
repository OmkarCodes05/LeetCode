class Solution {
    public String smallestPalindrome(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        StringBuilder firstHalf = new StringBuilder();
        String middleChar = "";
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                middleChar = String.valueOf((char) (i + 'a'));
            }
            int halfCount = count[i] / 2;
            for (int j = 0; j < halfCount; j++) {
                firstHalf.append((char) (i + 'a'));
            }
        }
        StringBuilder secondHalf = new StringBuilder(firstHalf).reverse();
        return firstHalf.toString() + middleChar + secondHalf.toString();
    }
}