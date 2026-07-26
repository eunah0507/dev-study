class Solution {
    public int solution(int n, int w, int num) {
        int targetRow = (num - 1) / w;
        int targetPosition = (num - 1) % w;
        int targetColumn = targetRow % 2 == 0 ? targetPosition : w - 1 - targetPosition;
        int answer = 0;

        for (int box = num; box <= n; box++) {
            int row = (box - 1) / w;
            int position = (box - 1) % w;
            int column = row % 2 == 0 ? position : w - 1 - position;

            if (column == targetColumn) {
                answer++;
            }
        }

        return answer;
    }
}