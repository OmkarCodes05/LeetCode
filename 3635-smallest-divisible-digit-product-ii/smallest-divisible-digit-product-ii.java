import java.util.Arrays;

class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into 2, 3, 5, 7
        long tempT = t;
        int need2 = 0, need3 = 0, need5 = 0, need7 = 0;
        
        while (tempT % 2 == 0) { need2++; tempT /= 2; }
        while (tempT % 3 == 0) { need3++; tempT /= 3; }
        while (tempT % 5 == 0) { need5++; tempT /= 5; }
        while (tempT % 7 == 0) { need7++; tempT /= 7; }
        
        // If t has other prime factors, it's impossible
        if (tempT > 1) return "-1";

        int n = num.length();
        
        // Count total prime factors needed in remaining length
        // First check if we need more digits than n
        int minLenNeeded = getMinDigitsNeeded(need2, need3, need5, need7);
        int len = Math.max(n, minLenNeeded);

        // Precompute prefix prime factor counts for num
        int[] c2 = new int[n + 1];
        int[] c3 = new int[n + 1];
        int[] c5 = new int[n + 1];
        int[] c7 = new int[n + 1];
        int firstZero = n;

        for (int i = 0; i < n; i++) {
            c2[i + 1] = c2[i];
            c3[i + 1] = c3[i];
            c5[i + 1] = c5[i];
            c7[i + 1] = c7[i];

            int d = num.charAt(i) - '0';
            if (d == 0) {
                if (firstZero == n) firstZero = i;
            } else {
                addFactors(d, c2, c3, c5, c7, i + 1);
            }
        }

        // Try to match prefix of length `i` (from n down to 0)
        for (int i = Math.min(n, firstZero); i >= 0; i--) {
            int rem2 = Math.max(0, need2 - c2[i]);
            int rem3 = Math.max(0, need3 - c3[i]);
            int rem5 = Math.max(0, need5 - c5[i]);
            int rem7 = Math.max(0, need7 - c7[i]);

            // If prefix matches num up to length i
            if (i == n) {
                if (rem2 == 0 && rem3 == 0 && rem5 == 0 && rem7 == 0) {
                    return num; // num itself is valid
                }
                continue;
            }

            int startDigit = (i < n) ? (num.charAt(i) - '0' + 1) : 1;
            
            for (int d = startDigit; d <= 9; d++) {
                int r2 = rem2, r3 = rem3, r5 = rem5, r7 = rem7;
                // Subtract factors of digit d
                if (d == 2) r2 = Math.max(0, r2 - 1);
                else if (d == 3) r3 = Math.max(0, r3 - 1);
                else if (d == 4) r2 = Math.max(0, r2 - 2);
                else if (d == 5) r5 = Math.max(0, r5 - 1);
                else if (d == 6) { r2 = Math.max(0, r2 - 1); r3 = Math.max(0, r3 - 1); }
                else if (d == 7) r7 = Math.max(0, r7 - 1);
                else if (d == 8) r2 = Math.max(0, r2 - 3);
                else if (d == 9) r3 = Math.max(0, r3 - 2);

                int remLen = n - 1 - i;
                if (getMinDigitsNeeded(r2, r3, r5, r7) <= remLen) {
                    // Valid choice! Construct the answer string
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(d);
                    
                    fillSmallestSuffix(sb, remLen, r2, r3, r5, r7);
                    return sb.toString();
                }
            }
        }

        // If no number of length n works, we must increase length to len (or n + 1)
        int newLen = Math.max(n + 1, minLenNeeded);
        StringBuilder sb = new StringBuilder();
        fillSmallestSuffix(sb, newLen, need2, need3, need5, need7);
        return sb.toString();
    }

    private void addFactors(int d, int[] c2, int[] c3, int[] c5, int[] c7, int idx) {
        if (d == 2) c2[idx]++;
        else if (d == 3) c3[idx]++;
        else if (d == 4) c2[idx] += 2;
        else if (d == 5) c5[idx]++;
        else if (d == 6) { c2[idx]++; c3[idx]++; }
        else if (d == 7) c7[idx]++;
        else if (d == 8) c2[idx] += 3;
        else if (d == 9) c3[idx] += 2;
    }

private int getMinDigitsNeeded(int r2, int r3, int r5, int r7) {
    // 5 and 7 cannot be combined with other digits
    int count7 = r7;
    int count5 = r5;

    // Greedily combine 2s into 8s and 3s into 9s
    int count8 = r2 / 3;
    r2 %= 3;
    int count9 = r3 / 2;
    r3 %= 2;

    int count4 = r2 / 2;
    r2 %= 2;
    int count2 = r2;

    int count3 = r3;

    // Combine remaining single 2 and 3 into a 6 if possible
    int count6 = 0;
    if (count2 == 1 && count3 == 1) {
        count6 = 1;
        count2 = 0;
        count3 = 0;
    }

    return count8 + count9 + count7 + count5 + count6 + count4 + count3 + count2;
}

    private void fillSmallestSuffix(StringBuilder sb, int targetLen, int r2, int r3, int r5, int r7) {
        int startPos = sb.length();
        int availableSlots = targetLen;

        // Try filling digits from left to right greedily
        for (int pos = 0; pos < availableSlots; pos++) {
            int slotsLeft = availableSlots - 1 - pos;
            
            for (int d = 1; d <= 9; d++) {
                int nextR2 = r2, nextR3 = r3, nextR5 = r5, nextR7 = r7;
                
                if (d == 2) nextR2 = Math.max(0, nextR2 - 1);
                else if (d == 3) nextR3 = Math.max(0, nextR3 - 1);
                else if (d == 4) nextR2 = Math.max(0, nextR2 - 2);
                else if (d == 5) nextR5 = Math.max(0, nextR5 - 1);
                else if (d == 6) { nextR2 = Math.max(0, nextR2 - 1); nextR3 = Math.max(0, nextR3 - 1); }
                else if (d == 7) nextR7 = Math.max(0, nextR7 - 1);
                else if (d == 8) nextR2 = Math.max(0, nextR2 - 3);
                else if (d == 9) nextR3 = Math.max(0, nextR3 - 2);

                if (getMinDigitsNeeded(nextR2, nextR3, nextR5, nextR7) <= slotsLeft) {
                    sb.append(d);
                    r2 = nextR2;
                    r3 = nextR3;
                    r5 = nextR5;
                    r7 = nextR7;
                    break;
                }
            }
        }
    }
}

