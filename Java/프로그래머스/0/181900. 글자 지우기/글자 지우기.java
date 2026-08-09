class Solution {
    public String solution(String my_string, int[] indices) {
        StringBuilder sb = new StringBuilder();
        boolean[] removed = new boolean[my_string.length()];

        for (int index : indices) {
            removed[index] = true;
        }

        for (int i = 0; i < my_string.length(); i++) {
            if (!removed[i]) {
                sb.append(my_string.charAt(i));
            }
        }

        return sb.toString();
    }
}