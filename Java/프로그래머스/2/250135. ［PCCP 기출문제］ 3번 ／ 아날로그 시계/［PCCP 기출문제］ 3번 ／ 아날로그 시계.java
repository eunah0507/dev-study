class Solution {
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int start = h1 * 3600 + m1 * 60 + s1;
        int end = h2 * 3600 + m2 * 60 + s2;

        int answer = count(end) - count(start);

        if (m1 == 0 && s1 == 0) {
            answer++;
        }

        return answer;
    }

    private int count(int time) {
        int secondMinute = time * 59 / 3600;
        int secondHour = (int) ((long) time * 719 / 43200);
        int overlap = time / 43200;

        return secondMinute + secondHour - overlap + 1;
    }
}