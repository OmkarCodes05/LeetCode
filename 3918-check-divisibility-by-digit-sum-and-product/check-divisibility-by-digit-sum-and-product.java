class Solution {
    public boolean checkDivisibility(int n) {
        int ds=0;
        int dp=1;
        int num=n;
        int ld=0;
        while(num>0){
            ld=num%10;
            dp*=ld;
            ds+=ld;
            num=(int)(num/10);
        }
        if (n%(ds+dp)==0){
            return true;
        }
        return false;
    }
}