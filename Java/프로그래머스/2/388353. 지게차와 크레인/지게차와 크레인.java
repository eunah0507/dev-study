import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int solution(String[] storage, String[] requests) {
        int n = storage.length;
        int m = storage[0].length();
        char[][] warehouse = new char[n + 2][m + 2];
        int answer = n * m;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                warehouse[i + 1][j + 1] = storage[i].charAt(j);
            }
        }

        for (String request : requests) {
            char target = request.charAt(0);

            if (request.length() == 2) {
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= m; j++) {
                        if (warehouse[i][j] == target) {
                            warehouse[i][j] = 0;
                            answer--;
                        }
                    }
                }
            } else {
                answer -= removeAccessible(warehouse, target);
            }
        }

        return answer;
    }

    private int removeAccessible(char[][] warehouse, char target) {
        int n = warehouse.length;
        int m = warehouse[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][] visited = new boolean[n][m];
        boolean[][] removed = new boolean[n][m];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nr = current[0] + dr[d];
                int nc = current[1] + dc[d];

                if (nr < 0 || nr >= n || nc < 0 || nc >= m || visited[nr][nc]) {
                    continue;
                }

                if (warehouse[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                } else if (warehouse[nr][nc] == target) {
                    removed[nr][nc] = true;
                }
            }
        }

        int count = 0;

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (removed[i][j]) {
                    warehouse[i][j] = 0;
                    count++;
                }
            }
        }

        return count;
    }
}