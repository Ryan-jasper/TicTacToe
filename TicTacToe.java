import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Board board = new Board(); // Membuat papan permainan TicTacToe
        char user, komp; // Variabel pemain dan komputer

        // Menampilkan judul dan tujuan permainan
        System.out.println("===== Permainan TicTacToe =====");
        System.out.println("=========== Tujuan ============");
        System.out.println("Kalahkan komputer dalam permaianan TicTacToe ini!");

        // Menanyakan siapa yang main duluan
        System.out.print("Apakah Anda ingin bermain duluan? (Iya/Tidak): ");
        String input = scan.nextLine().trim().toLowerCase();
        boolean userTurn = input.equals("i");

        // Menetapkan simbol berdasarkan giliran pertama
        user = userTurn ? 'X' : 'O';
        komp = (user == 'X') ? 'O' : 'X';

        board.printBoard(); // Menampilkan papan kosong

        // Mulai permainan utama
        while (true) {
            if (userTurn) {
                // Giliran pemain
                System.out.println("Giliran Anda (" + user + "):");
                int row, col;
                while (true) {
                    try {
                        System.out.print("Baris (1-3): ");
                        row = scan.nextInt();
                        row -= 1;
                        System.out.print("Kolom (1-3): ");
                        col = scan.nextInt();
                        col -= 1; 

                        // Validasi input
                        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                            if (board.getBoard()[row][col] == '-') {
                                board.getBoard()[row][col] = user;
                                break;
                            } else {
                                System.out.println("Kotak ini sudah terisi, coba lagi di kotak lain!.");
                            }
                        } else {
                            System.out.println("Baris dan kolom harus antara 1 sampai 3!");
                        }
                    } catch (Exception e) {
                        System.out.println("Input tidak valid. Harap masukkan angka 1-3!");
                        scan.nextLine();
                    }
                }
            } else {
                // Giliran komputer
                System.out.println("Giliran komputer (" + komp + "):");
                Move bestMove = findBestMove(board, komp, user);
                board.getBoard()[bestMove.row][bestMove.col] = komp;
                System.out.println("Komputer memilih: baris " + bestMove.row + ", kolom " + bestMove.col);
            }

            board.printBoard(); // Cetak papan setelah giliran

            // Cek apakah ada pemenang
            char winner = board.checkWinner();
            if (winner != '-') {
                System.out.println((winner == user ? "Selamat, Anda menang!" : "Komputer menang!"));
                break;
            } else if (board.isBoardFull()) {
                System.out.println("Permainan berakhir seri!");
                break;
            }

            userTurn = !userTurn;
        }

        scan.close(); 
    }

    static class Move {
        int row, col;
    }

    // Fungsi untuk mencari langkah terbaik 
    private static Move findBestMove(Board board, char ai, char user) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = new Move();

        // Mencari semua posisi kosong
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == '-') {
                    board.getBoard()[i][j] = ai;
                    int score = minimax(board, 0, false, ai, user);
                    board.getBoard()[i][j] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove.row = i;
                        bestMove.col = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(Board board, int depth, boolean isMax, char ai, char user) {
        char winner = board.checkWinner(); // Cek pemenang
        if (winner == ai) return 10 - depth; // Komputer menang
        if (winner == user) return depth - 10; // User menang
        if (board.isBoardFull()) return 0; // Seri

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE; 

        // Mencari langkah yang memungkin
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == '-') {
                    board.getBoard()[i][j] = isMax ? ai : user; 
                    int score = minimax(board, depth + 1, !isMax, ai, user); 
                    board.getBoard()[i][j] = '-'; 
                    best = isMax ? Math.max(best, score) : Math.min(best, score); 
                }
            }
        }

        return best; 
    }
}
