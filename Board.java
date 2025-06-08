public class Board {
    private char[][] board;
    private char currentPlayerMark;

    public Board() {
        board = new char[3][3];
        currentPlayerMark = 'X';
        initializeBoard();
    }

    // Inisialisasi papan dengan space kosong (-)
    public void initializeBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';
    }

    // Cetak papan ke layar
    public void printBoard() {
        System.out.println("+---+---+---+");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("+---+---+---+");
        }
    }

    // Menjalankan giliran pemain saat ini
    public boolean makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = currentPlayerMark;
            togglePlayer();
            return true;
        }
        return false;
    }

    // Ganti giliran pemain
    private void togglePlayer() {
        currentPlayerMark = (currentPlayerMark == 'X') ? 'O' : 'X';
    }

    // Cek apakah papan sudah penuh
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-')
                    return false;
        return true;
    }

    // Mengecek pemenang
    public char checkWinner() {
        // Cek baris dan kolom
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2])
                return board[i][0];

            if (board[0][i] != '-' &&
                board[0][i] == board[1][i] &&
                board[1][i] == board[2][i])
                return board[0][i];
        }

        // Cek diagonal
        if (board[0][0] != '-' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2])
            return board[0][0];

        if (board[0][2] != '-' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0])
            return board[0][2];

        return '-'; // Tidak ada pemenang
    }

    // Cek apakah game sudah selesai (ada pemenang atau draw)
    public boolean isGameOver() {
        return checkWinner() != '-' || isBoardFull();
    }

    // Reset papan ke kondisi awal
    public void resetBoard() {
        initializeBoard();
        currentPlayerMark = 'X';
    }

    // Getter untuk papan (jika dibutuhkan)
    public char[][] getBoard() {
        return board;
    }

    // Getter untuk pemain saat ini
    public char getCurrentPlayerMark() {
        return currentPlayerMark;
    }
}
