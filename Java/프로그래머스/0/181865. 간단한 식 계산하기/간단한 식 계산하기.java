class Solution {
    public int solution(String binomial) {
        String[] parts = binomial.split(" ");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[2]);

        if (parts[1].equals("+")) {
            return a + b;
        }

        if (parts[1].equals("-")) {
            return a - b;
        }

        return a * b;
    }
}