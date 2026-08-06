class Solution {
    public int solution(int n, String control) {
        for (char command : control.toCharArray()) {
            if (command == 'w') {
                n++;
            } else if (command == 's') {
                n--;
            } else if (command == 'd') {
                n += 10;
            } else {
                n -= 10;
            }
        }

        return n;
    }
}