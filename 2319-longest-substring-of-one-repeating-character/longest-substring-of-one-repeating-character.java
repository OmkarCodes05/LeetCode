class Solution {
    class Node {
        char lc, rc;
        int pref, suff, max;

        Node(char c) {
            this.lc = c;
            this.rc = c;
            this.pref = 1;
            this.suff = 1;
            this.max = 1;
        }

        Node() {}
    }

    private Node[] tree;
    private char[] chars;
    private int n;

    private Node merge(Node left, Node right, int lLen, int rLen) {
        Node res = new Node();
        res.lc = left.lc;
        res.rc = right.rc;

        // Base max length from either child
        res.max = Math.max(left.max, right.max);

        // Prefix length calculation
        res.pref = left.pref;
        if (left.pref == lLen && left.rc == right.lc) {
            res.pref = left.pref + right.pref;
        }

        // Suffix length calculation
        res.suff = right.suff;
        if (right.suff == rLen && right.lc == left.rc) {
            res.suff = right.suff + left.suff;
        }

        // Check cross-boundary segment
        if (left.rc == right.lc) {
            res.max = Math.max(res.max, left.suff + right.pref);
        }

        res.max = Math.max(res.max, Math.max(res.pref, res.suff));
        return res;
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = new Node(chars[start]);
            return;
        }
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;

        build(leftChild, start, mid);
        build(rightChild, mid + 1, end);

        tree[node] = merge(tree[leftChild], tree[rightChild], mid - start + 1, end - mid);
    }

    private void update(int node, int start, int end, int idx, char val) {
        if (start == end) {
            tree[node] = new Node(val);
            chars[idx] = val;
            return;
        }
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;

        if (idx <= mid) {
            update(leftChild, start, mid, idx, val);
        } else {
            update(rightChild, mid + 1, end, idx, val);
        }

        tree[node] = merge(tree[leftChild], tree[rightChild], mid - start + 1, end - mid);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        n = s.length();
        chars = s.toCharArray();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, idx, ch);
            ans[i] = tree[1].max;
        }

        return ans;
    }
}