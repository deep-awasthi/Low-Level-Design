import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player deep = new Player("Deep", "x");
        Player shivam = new Player("Shivam", "o");
        Board board = new Board(3);
        Game game = new Game(board, deep, shivam);
        game.playGame();
    }
}

class Player{
    String name;
    String marker;

    Player(String _name, String _marker){
        this.name = _name;
        this.marker = _marker;
    }
}

class Board{
    int size;
    String[][] board;
    HashMap<Integer, HashMap<String, Integer>> rowCounts;
    HashMap<Integer, HashMap<String, Integer>> coloumnCounts;
    HashMap<String, HashMap<String, Integer>> diagonalCounts;

    Board(int _size){
        this.size = _size;
        rowCounts = new HashMap<>();
        coloumnCounts = new HashMap<>();
        diagonalCounts = new HashMap<>();

        for(int i = 0; i<size; i++){
            rowCounts.put(i, new HashMap<>());
            coloumnCounts.put(i, new HashMap<>());
        }
        diagonalCounts.put("forward", new HashMap<>());
        diagonalCounts.put("backward", new HashMap<>());


        board = new String[size][size];
    }

    boolean place(Player player, int x, int y){

        String marker = player.marker;
        if(board[x][y]!=null){
            return false;
        }
        else{
            board[x][y] = marker;
            rowCounts.get(y).put(marker, rowCounts.get(y).getOrDefault(marker, 0)+1);
            if(rowCounts.get(y).get(marker) == size){
                return true;
            }

            coloumnCounts.get(x).put(marker, coloumnCounts.get(x).getOrDefault(marker, 0)+1);
            if(coloumnCounts.get(x).get(marker) == size){
                return true;
            }

            if(x==y){
                diagonalCounts.get("forward").put(marker, diagonalCounts.get("forward").getOrDefault(marker, 0)+1);
                if(diagonalCounts.get("forward").get(marker) == size){
                    return true;
                }
            }

            if(x+y == size - 1){
                diagonalCounts.get("backward").put(marker, diagonalCounts.get("backward").getOrDefault(marker, 0)+1);
                if(diagonalCounts.get("backward").get(marker) == size){
                    return true;
                }
            }
            return false;
        }
    }
}

class Game{
    Board board;
    Player player1;
    Player player2;
    int currTurn;
    boolean gameDone;

    Game(Board board, Player player1, Player player2){
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    void playGame(){
        Scanner sc = new Scanner(System.in);
        currTurn = 1;
        gameDone = false;
        while (!gameDone) {
            Player currPlayer;
            if(currTurn%2==1){
                currPlayer = player1;
            }
            else{
                currPlayer = player2;
            }System.out.println("Current Player Playing : " + currPlayer.name);
            System.out.println("Insert X position");
            int x = sc.nextInt();
            System.out.println("Insert Y position");
            int y = sc.nextInt();

            if(board.place(currPlayer, x, y)){
                gameDone = true;
                System.out.println("Winner is player : " + currPlayer.name);
            }
            else{
                currTurn = currTurn + 1;
            }
        }

        sc.close();
    }
}
