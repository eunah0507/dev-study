import java.util.*;

class Solution {
    public int[] solution(int l, int r) {
        List<Integer> list = new ArrayList<>();

        for (int i = l; i <= r; i++) {
            String number = String.valueOf(i);
            boolean valid = true;

            for (int j = 0; j < number.length(); j++) {
                if (number.charAt(j) != '0' && number.charAt(j) != '5') {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                list.add(i);
            }
        }

        if (list.isEmpty()) {
            return new int[]{-1};
        }

        int[] answer = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i);
        }

        return answer;
    }
}