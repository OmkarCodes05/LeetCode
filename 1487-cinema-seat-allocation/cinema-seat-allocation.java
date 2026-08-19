import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        // Map row number to bitmask of reserved seats (for seats 2 to 9)
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];
            // We only care about seats 2 through 9
            if (col >= 2 && col <= 9) {
                // Set the corresponding bit for seat 'col'
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }
        
        // Start assuming all non-reserved rows can take 2 groups
        int maxGroups = (n - map.size()) * 2;
        
        // Bitmasks for seat blocks
        // Seats 2, 3, 4, 5 -> bits 2, 3, 4, 5 set -> (1<<2)|(1<<3)|(1<<4)|(1<<5) = 0b00111100 (60)
        int left = 0b00111100;
        // Seats 6, 7, 8, 9 -> bits 6, 7, 8, 9 set -> (1<<6)|(1<<7)|(1<<8)|(1<<9) = 0b1111000000 (960)
        int right = 0b1111000000;
        // Seats 4, 5, 6, 7 -> bits 4, 5, 6, 7 set -> (1<<4)|(1<<5)|(1<<6)|(1<<7) = 0b0011110000 (240)
        int middle = 0b0011110000;
        
        for (int mask : map.values()) {
            boolean canLeft = (mask & left) == 0;
            boolean canRight = (mask & right) == 0;
            
            if (canLeft && canRight) {
                maxGroups += 2;
            } else if (canLeft || canRight || (mask & middle) == 0) {
                maxGroups += 1;
            }
        }
        
        return maxGroups;
    }
}