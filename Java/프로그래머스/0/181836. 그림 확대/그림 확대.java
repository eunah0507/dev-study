class Solution {
    public String[] solution(String[] picture, int k) {
        String[] answer = new String[picture.length * k];
        int index = 0;

        for (String row : picture) {
            StringBuilder sb = new StringBuilder();

            for (char c : row.toCharArray()) {
                for (int i = 0; i < k; i++) {
                    sb.append(c);
                }
            }

            for (int i = 0; i < k; i++) {
                answer[index++] = sb.toString();
            }
        }

        return answer;
    }
}