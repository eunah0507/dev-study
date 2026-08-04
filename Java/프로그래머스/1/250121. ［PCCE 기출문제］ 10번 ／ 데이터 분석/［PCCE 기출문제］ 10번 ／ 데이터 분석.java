import java.util.*;

class Solution {
    public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
        String[] columns = {"code", "date", "maximum", "remain"};
        int extIndex = 0;
        int sortIndex = 0;

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(ext)) {
                extIndex = i;
            }

            if (columns[i].equals(sort_by)) {
                sortIndex = i;
            }
        }

        int finalExtIndex = extIndex;
        int finalSortIndex = sortIndex;

        return Arrays.stream(data)
                .filter(row -> row[finalExtIndex] < val_ext)
                .sorted(Comparator.comparingInt(row -> row[finalSortIndex]))
                .toArray(int[][]::new);
    }
}