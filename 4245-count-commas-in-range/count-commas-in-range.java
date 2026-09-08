class Solution {
    public int countCommas(int n) {
        int c=0;
        int num=n;

        if(num>=1000){
            return num-999;
        }
        return 0;
    }
}