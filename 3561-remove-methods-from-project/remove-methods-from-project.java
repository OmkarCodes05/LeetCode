import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Step 1: Build the adjacency list for the graph
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] inv : invocations) {
            graph[inv[0]].add(inv[1]);
        }

        // Step 2: Find all suspicious methods using BFS
        boolean[] suspicious = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(k);
        suspicious[k] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : graph[current]) {
                if (!suspicious[neighbor]) {
                    suspicious[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        // Step 3: Check if any non-suspicious method invokes a suspicious method
        boolean canRemove = true;
        for (int[] inv : invocations) {
            int u = inv[0];
            int v = inv[1];
            if (!suspicious[u] && suspicious[v]) {
                canRemove = false;
                break;
            }
        }

        // Step 4: Construct the final answer
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // Add if we can't remove anything, OR if the method is not suspicious
            if (!canRemove || !suspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }
}