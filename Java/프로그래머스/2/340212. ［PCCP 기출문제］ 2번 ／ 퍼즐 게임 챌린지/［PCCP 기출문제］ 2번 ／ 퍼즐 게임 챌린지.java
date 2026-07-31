class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int left = 1;
        int right = 100000;

        while (left < right) {
            int level = (left + right) / 2;
            long totalTime = times[0];

            for (int i = 1; i < diffs.length; i++) {
                if (diffs[i] > level) {
                    totalTime += (long) (diffs[i] - level) * (times[i] + times[i - 1]);
                }
                totalTime += times[i];

                if (totalTime > limit) {
                    break;
                }
            }

            if (totalTime <= limit) {
                right = level;
            } else {
                left = level + 1;
            }
        }

        return left;
    }
}