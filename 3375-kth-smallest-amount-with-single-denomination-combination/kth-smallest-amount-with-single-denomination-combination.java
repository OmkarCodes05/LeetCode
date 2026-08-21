class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        
        // Precompute LCMs for all bitmask combinations of coins
        long[] lcms = new long[1 << n];
        lcms[0] = 1;
        
        for (int mask = 1; mask < (1 << n); mask++) {
            int lastBit = Integer.numberOfTrailingZeros(mask);
            int prevMask = mask ^ (1 << lastBit);
            lcms[mask] = lcm(lcms[prevMask], coins[lastBit]);
        }

        // Binary search for the answer
        long low = 1;
        long high = 1L * coins[0] * k;
        for (int coin : coins) {
            high = Math.min(high, 1L * coin * k);
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countAmounts(mid, coins, lcms) >= k) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private long countAmounts(long target, int[] coins, long[] lcms) {
        int n = coins.length;
        long count = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            int setBits = Integer.bitCount(mask);
            long currentLcm = lcms[mask];
            
            if (setBits % 2 == 1) {
                count += target / currentLcm;
            } else {
                count -= target / currentLcm;
            }
        }

        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        if (a == 0 || b == 0) return 0;
        return (a / gcd(a, b)) * b;
    }
}