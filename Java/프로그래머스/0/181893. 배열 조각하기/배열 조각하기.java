class Solution {
    public int[] solution(int[] arr, int[] query) {
        int start = 0;
        int end = arr.length;

        for (int i = 0; i < query.length; i++) {
            if (i % 2 == 0) {
                end = start + query[i] + 1;
            } else {
                start += query[i];
            }
        }

        int[] answer = new int[end - start];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = arr[start + i];
        }

        return answer;
    }
}