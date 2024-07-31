import java.util.*;

public class Othello {

    public final int N = 8;
    // Their will be only 3 values on the board
    // 0 => Black Disc
    // 1 => White Disc
    // -1 => Empty Square
    public int[][] BOARD;
    public final int[][] DIR = { { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 },
            { -1, -1 } };
    public int CURRENT_PLAYER;
    public int MOVES;

    public final int BLACK = 0;
    public final int WHITE = 1;
    public final int EMPTY = -1;

    public int BLACK_DISCS_COUNT;
    public int WHITE_DISCS_COUNT;

    public Othello() {
        BOARD = new int[N][N];

        BOARD[3][3] = WHITE;
        BOARD[3][4] = BLACK;
        BOARD[4][3] = BLACK;
        BOARD[4][4] = WHITE;

        CURRENT_PLAYER = 0;
        MOVES = 4;

        BLACK_DISCS_COUNT = 2;
        WHITE_DISCS_COUNT = 2;
    }

    public boolean isGameOver() {
        return (MOVES == (N * N)) || (validPositions(CURRENT_PLAYER).size() == 0)
                || (validPositions(1 - CURRENT_PLAYER).size() == 0);
    }

    public void workAfterEveryMove() {

    }

    // Valid Positions for Opponents
    public List<int[]> validPositions(int color) {

        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                for (int d = 0; d < DIR.length; d++) {
                    int x = i + DIR[d][0];
                    int y = j + DIR[d][1];

                    boolean isValid = false;
                    while (x >= 0 && y >= 0 && x < N && y < N) {
                        if (BOARD[x][y] == color || BOARD[x][y] == EMPTY)
                            break;
                        if (BOARD[x][y] == 1 - color)
                            isValid = true;

                        x += DIR[d][0];
                        y += DIR[d][1];
                    }

                    if (isValid && x >= 0 && y >= 0 && x < N && y < N)
                        res.add(new int[] { x, y });
                }

            }
        }

        return res;
    }

    // Find all the discs endpoints in all the 8 lines up to which discs are to be
    // reverted
    public List<int[]> destinationsFinder(int r, int c) {
        int color = BOARD[r][c];

        List<int[]> res = new ArrayList<>();
        for (int d = 0; d < DIR.length; d++) {
            int x = r + DIR[d][0];
            int y = c + DIR[d][1];

            while (x >= 0 && y >= 0 && x < N && y < N) {
                if (BOARD[x][y] == color || BOARD[x][y] == EMPTY)
                    break;
                if (BOARD[x][y] == 1 - color) {
                    res.add(new int[] { d, r, c });
                    break;
                }
            }
        }

        return res;
    }

    public void revertColor(int r, int c, List<int[]> endPoints) {
        int color = BOARD[r][c];

        for (int[] point : endPoints) {
            int d = point[0];
            int n = point[1];
            int m = point[2];

            int i = r + BOARD[d][0];
            int j = c + BOARD[d][1];

            while (i <= n && j <= m) {
                BOARD[i][j] = 1 - color;
                i += BOARD[d][0];
                j += BOARD[d][1];
            }
        }

        updateDiscsCount();
    }

    public void updateDiscsCount() {
        BLACK_DISCS_COUNT = 0;
        WHITE_DISCS_COUNT = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                BLACK_DISCS_COUNT += (BOARD[i][j] == 0 ? 1 : 0);
                WHITE_DISCS_COUNT += (BOARD[i][j] == 1 ? 1 : 0);
            }
        }
    }
}