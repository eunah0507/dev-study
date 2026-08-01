import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private int[][] dice;
    private int n;
    private int half;
    private long maxWins;
    private int[] answer;

    public int[] solution(int[][] dice) {
        this.dice = dice;
        n = dice.length;
        half = n / 2;
        answer = new int[half];

        selectDice(0, 0, 0);

        return answer;
    }

    private void selectDice(int index, int count, int mask) {
        if (count == half) {
            calculate(mask);
            return;
        }

        if (index == n) {
            return;
        }

        if (n - index < half - count) {
            return;
        }

        selectDice(index + 1, count + 1, mask | (1 << index));
        selectDice(index + 1, count, mask);
    }

    private void calculate(int mask) {
        List<Integer> aDice = new ArrayList<>();
        List<Integer> bDice = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                aDice.add(i);
            } else {
                bDice.add(i);
            }
        }

        int size = (int) Math.pow(6, half);
        int[] aSums = new int[size];
        int[] bSums = new int[size];

        makeSums(aDice, 0, 0, aSums, new int[]{0});
        makeSums(bDice, 0, 0, bSums, new int[]{0});

        Arrays.sort(bSums);

        long wins = 0;

        for (int sum : aSums) {
            wins += lowerBound(bSums, sum);
        }

        if (wins > maxWins) {
            maxWins = wins;
            int answerIndex = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    answer[answerIndex++] = i + 1;
                }
            }
        }
    }

    private void makeSums(List<Integer> selectedDice, int depth, int sum, int[] sums, int[] index) {
        if (depth == half) {
            sums[index[0]++] = sum;
            return;
        }

        int diceIndex = selectedDice.get(depth);

        for (int value : dice[diceIndex]) {
            makeSums(selectedDice, depth + 1, sum + value, sums, index);
        }
    }

    private int lowerBound(int[] array, int target) {
        int left = 0;
        int right = array.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}