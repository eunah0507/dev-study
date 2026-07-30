class Solution {
    public int solution(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int cellCount = n * m;
        int vertexCount = cellCount * 2;

        int[] first = new int[vertexCount];
        int[] second = new int[vertexCount];
        int[] degree = new int[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            first[i] = -1;
            second[i] = -1;
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int cell = row * m + col;

                if (row + 1 < n) {
                    int down = (row + 1) * m + col;
                    addEdge(cell * 2 + 1, down * 2, first, second, degree);
                }

                if (col + 1 < m) {
                    int right = cell + 1;
                    int currentState = grid[row][col] == -1 ? 0 : 1;
                    int rightState = grid[row][col + 1] == -1 ? 1 : 0;

                    addEdge(
                        cell * 2 + currentState,
                        right * 2 + rightState,
                        first,
                        second,
                        degree
                    );
                }
            }
        }

        boolean[] visited = new boolean[vertexCount];
        int[] sequence = new int[vertexCount];
        int[] last = new int[cellCount];
        int[] stamp = new int[cellCount];

        int answer = 1;
        int token = 0;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex]) {
                continue;
            }

            int start = vertex;
            boolean cycle = false;

            if (degree[start] == 2) {
                int previous = -1;
                int current = start;

                while (true) {
                    if (degree[current] < 2) {
                        start = current;
                        break;
                    }

                    int next;

                    if (first[current] != previous) {
                        next = first[current];
                    } else {
                        next = second[current];
                    }

                    previous = current;
                    current = next;

                    if (current == vertex) {
                        cycle = true;
                        break;
                    }
                }
            }

            int length = 0;
            int previous = -1;
            int current = start;

            while (current != -1 && !visited[current]) {
                visited[current] = true;
                sequence[length++] = current;

                int next = -1;

                if (first[current] != -1 && first[current] != previous) {
                    next = first[current];
                } else if (second[current] != -1 && second[current] != previous) {
                    next = second[current];
                }

                previous = current;
                current = next;
            }

            token++;

            if (cycle) {
                int left = 0;

                for (int i = 0; i < length * 2; i++) {
                    int cell = sequence[i % length] / 2;

                    if (stamp[cell] == token) {
                        left = Math.max(left, last[cell] + 1);
                    }

                    left = Math.max(left, i - length + 1);
                    stamp[cell] = token;
                    last[cell] = i;

                    answer = Math.max(answer, i - left + 1);
                }
            } else {
                int left = 0;

                for (int i = 0; i < length; i++) {
                    int cell = sequence[i] / 2;

                    if (stamp[cell] == token) {
                        left = Math.max(left, last[cell] + 1);
                    }

                    stamp[cell] = token;
                    last[cell] = i;

                    answer = Math.max(answer, i - left + 1);
                }
            }
        }

        return answer;
    }

    private void addEdge(
        int from,
        int to,
        int[] first,
        int[] second,
        int[] degree
    ) {
        if (first[from] == -1) {
            first[from] = to;
        } else {
            second[from] = to;
        }

        if (first[to] == -1) {
            first[to] = from;
        } else {
            second[to] = from;
        }

        degree[from]++;
        degree[to]++;
    }
}