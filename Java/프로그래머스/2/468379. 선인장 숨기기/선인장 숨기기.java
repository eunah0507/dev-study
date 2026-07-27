import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] solution(int m, int n, int h, int w, int[][] drops) {
        int[][] rainTime = new int[m][n];
        int infinity = drops.length + 1;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                rainTime[row][col] = infinity;
            }
        }

        for (int i = 0; i < drops.length; i++) {
            rainTime[drops[i][0]][drops[i][1]] = i + 1;
        }

        int widthCount = n - w + 1;
        int[][] rowMinimum = new int[m][widthCount];

        for (int row = 0; row < m; row++) {
            Deque<Integer> deque = new ArrayDeque<>();

            for (int col = 0; col < n; col++) {
                while (!deque.isEmpty() && deque.peekFirst() <= col - w) {
                    deque.pollFirst();
                }

                while (!deque.isEmpty()
                        && rainTime[row][deque.peekLast()] >= rainTime[row][col]) {
                    deque.pollLast();
                }

                deque.offerLast(col);

                if (col >= w - 1) {
                    rowMinimum[row][col - w + 1] = rainTime[row][deque.peekFirst()];
                }
            }
        }

        int bestTime = -1;
        int bestRow = 0;
        int bestCol = 0;

        for (int col = 0; col < widthCount; col++) {
            Deque<Integer> deque = new ArrayDeque<>();

            for (int row = 0; row < m; row++) {
                while (!deque.isEmpty() && deque.peekFirst() <= row - h) {
                    deque.pollFirst();
                }

                while (!deque.isEmpty()
                        && rowMinimum[deque.peekLast()][col] >= rowMinimum[row][col]) {
                    deque.pollLast();
                }

                deque.offerLast(row);

                if (row >= h - 1) {
                    int topRow = row - h + 1;
                    int time = rowMinimum[deque.peekFirst()][col];

                    if (time > bestTime
                            || (time == bestTime && topRow < bestRow)
                            || (time == bestTime && topRow == bestRow && col < bestCol)) {
                        bestTime = time;
                        bestRow = topRow;
                        bestCol = col;
                    }
                }
            }
        }

        return new int[]{bestRow, bestCol};
    }
}