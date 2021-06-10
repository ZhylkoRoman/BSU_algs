import java.util.ArrayList;

public class KMP {
    private String pat;
    private String txt;
    private int[][] dfa;
    private ArrayList<Integer> offsets = new ArrayList<>();

    public KMP(String txt, int spaces) {
        StringBuilder pat = new StringBuilder();
        pat.append(" ".repeat(spaces));
        this.pat = pat.toString();
        this.txt = txt;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pat.charAt(j)][j] = j + 1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    public int search() {
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M)
            return i - M;
        else
            return N;
    }

    public int searchRepeats() {
        return searchRepeats(txt, 0);
    }

    private int searchRepeats(String txt, int abs) {
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) {
            offsets.add(i - M + abs);
            if (i + j < txt.length())
                return searchRepeats(txt.substring(i + j - M), i + abs);
            else
                return findMaxRepeats(offsets, M);
        } else {
            return N;
        }
    }

    private int findMaxRepeats(ArrayList<Integer> offsets, int M) {
        int max = 0;
        int temp = 0;
        int tempId = -1;
        int maxId = -1;

        for (int i = 0; i < offsets.size() - 1; i++) {
            if (offsets.get(i) + M == offsets.get(i + 1)) {
                if (temp == 0)
                    tempId = offsets.get(i);
                temp++;
            } else {
                if (temp > max) {
                    max = temp;
                    maxId = tempId;
                }
                temp = 0;
            }
        }
        if (Math.max(tempId, maxId) < 0)
            return txt.length();
        return Math.max(tempId, maxId);
    }
}
