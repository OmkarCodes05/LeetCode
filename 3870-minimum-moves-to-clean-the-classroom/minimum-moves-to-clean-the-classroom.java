import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startX = -1, startY = -1;
        List<int[]> litterList = new ArrayList<>();
        
        // Identify starting position and all litter locations
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    startX = i;
                    startY = j;
                } else if (c == 'L') {
                    litterList.add(new int[]{i, j});
                }
            }
        }
        
        int totalLitter = litterList.size();
        int targetMask = (1 << totalLitter) - 1;
        
        // Map litter coordinates to bit positions for quick lookup
        int[][] litterIdx = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterIdx[i], -1);
        }
        for (int i = 0; i < totalLitter; i++) {
            int[] pos = litterList.get(i);
            litterIdx[pos[0]][pos[1]] = i;
        }
        
        // bestEnergy[r][c][mask] stores the max remaining energy seen for state (r, c, mask)
        int[][][] bestEnergy = new int[m][n][1 << totalLitter];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }
        
        // Queue state: [row, col, mask, currentEnergy]
        Queue<int[]> queue = new LinkedList<>();
        
        // Initial state
        int initialMask = 0;
        if (litterIdx[startX][startY] != -1) {
            initialMask |= (1 << litterIdx[startX][startY]);
        }
        
        if (initialMask == targetMask) {
            return 0; // Already collected all litter if all litter is at start
        }
        
        queue.offer(new int[]{startX, startY, initialMask, energy});
        bestEnergy[startX][startY][initialMask] = energy;
        
        int moves = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            moves++;
            
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int e = curr[3];
                
                for (int[] dir : dirs) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    
                    // Check boundaries and obstacles
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n || classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }
                    
                    int nextEnergy = e - 1;
                    if (nextEnergy < 0) {
                        continue; // Cannot make this move without energy
                    }
                    
                    char cell = classroom[nr].charAt(nc);
                    
                    // Restore energy if on Reset cell
                    if (cell == 'R') {
                        nextEnergy = energy;
                    }
                    
                    // Update bitmask if stepping on a Litter cell
                    int nextMask = mask;
                    if (litterIdx[nr][nc] != -1) {
                        nextMask |= (1 << litterIdx[nr][nc]);
                    }
                    
                    // Check if all litter collected
                    if (nextMask == targetMask) {
                        return moves;
                    }
                    
                    // Pruning: skip state if we reached it before with higher or equal energy
                    if (nextEnergy <= bestEnergy[nr][nc][nextMask]) {
                        continue;
                    }
                    
                    bestEnergy[nr][nc][nextMask] = nextEnergy;
                    queue.offer(new int[]{nr, nc, nextMask, nextEnergy});
                }
            }
        }
        
        return -1;
    }
}