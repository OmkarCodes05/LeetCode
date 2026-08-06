class Solution {
    public int smallestNumber(int n, int t) {
        int current = n;
        
        // Continuously check each number starting from n
        while (true) {
            if (getDigitProduct(current) % t == 0) {
                return current;
            }
            current++;
        }
    }
    
    // Helper method to calculate the product of the digits of a number
    private int getDigitProduct(int num) {
        int product = 1;
        
        // Handle the case if num is 0 (though n >= 1 based on constraints)
        if (num == 0) return 0;
        
        while (num > 0) {
            product *= num % 10; // Multiply by the last digit
            num /= 10;           // Remove the last digit
        }
        
        return product;
    }
}