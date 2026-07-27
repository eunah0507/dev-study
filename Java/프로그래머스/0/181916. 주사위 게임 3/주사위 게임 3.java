class Solution {
    public int solution(int a, int b, int c, int d) {
        int[] count = new int[7];

        count[a]++;
        count[b]++;
        count[c]++;
        count[d]++;

        int p = 0;
        int q = 0;
        int r = 0;
        int maxCount = 0;
        int distinctCount = 0;

        for (int i = 1; i <= 6; i++) {
            if (count[i] > 0) {
                distinctCount++;
            }

            if (count[i] > maxCount) {
                maxCount = count[i];
                p = i;
            }
        }

        if (maxCount == 4) {
            return 1111 * p;
        }

        if (maxCount == 3) {
            for (int i = 1; i <= 6; i++) {
                if (count[i] == 1) {
                    q = i;
                }
            }

            int score = 10 * p + q;
            return score * score;
        }

        if (distinctCount == 2) {
            for (int i = 1; i <= 6; i++) {
                if (count[i] == 2) {
                    if (q == 0) {
                        q = i;
                    } else {
                        r = i;
                    }
                }
            }

            return (q + r) * Math.abs(q - r);
        }

        if (maxCount == 2) {
            for (int i = 1; i <= 6; i++) {
                if (count[i] == 1) {
                    if (q == 0) {
                        q = i;
                    } else {
                        r = i;
                    }
                }
            }

            return q * r;
        }

        for (int i = 1; i <= 6; i++) {
            if (count[i] == 1) {
                return i;
            }
        }

        return 0;
    }
}