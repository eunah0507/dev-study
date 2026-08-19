import java.util.Arrays;

class Solution {
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int offset = 10;
        int minTemp = 0;
        int maxTemp = 50;
        int outside = temperature + offset;
        int comfortMin = t1 + offset;
        int comfortMax = t2 + offset;
        int inf = Integer.MAX_VALUE / 2;

        int[][] dp = new int[onboard.length][51];

        for (int i = 0; i < onboard.length; i++) {
            Arrays.fill(dp[i], inf);
        }

        dp[0][outside] = 0;

        for (int time = 0; time < onboard.length - 1; time++) {
            for (int temp = minTemp; temp <= maxTemp; temp++) {
                if (dp[time][temp] == inf) {
                    continue;
                }

                int nextTemp = temp;

                if (temp < outside) {
                    nextTemp++;
                } else if (temp > outside) {
                    nextTemp--;
                }

                if (isValid(nextTemp, onboard[time + 1], comfortMin, comfortMax)) {
                    dp[time + 1][nextTemp] = Math.min(
                        dp[time + 1][nextTemp],
                        dp[time][temp]
                    );
                }

                if (isValid(temp, onboard[time + 1], comfortMin, comfortMax)) {
                    dp[time + 1][temp] = Math.min(
                        dp[time + 1][temp],
                        dp[time][temp] + b
                    );
                }

                if (temp > minTemp && isValid(temp - 1, onboard[time + 1], comfortMin, comfortMax)) {
                    dp[time + 1][temp - 1] = Math.min(
                        dp[time + 1][temp - 1],
                        dp[time][temp] + a
                    );
                }

                if (temp < maxTemp && isValid(temp + 1, onboard[time + 1], comfortMin, comfortMax)) {
                    dp[time + 1][temp + 1] = Math.min(
                        dp[time + 1][temp + 1],
                        dp[time][temp] + a
                    );
                }
            }
        }

        int answer = inf;

        for (int temp = minTemp; temp <= maxTemp; temp++) {
            answer = Math.min(answer, dp[onboard.length - 1][temp]);
        }

        return answer;
    }

    private boolean isValid(int temp, int onboard, int t1, int t2) {
        if (onboard == 0) {
            return true;
        }

        return t1 <= temp && temp <= t2;
    }
}