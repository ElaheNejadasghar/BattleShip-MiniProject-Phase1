import java.util.Random;
import java.util.Scanner;
public class BattleShip {

    static int GRID_SIZE = 26;

    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];

    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    static Scanner scanner = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.print("Enter grid size: ");
        GRID_SIZE = scannerInt.nextInt();
        while(GRID_SIZE>26 || GRID_SIZE<5){
            System.out.print("Invalid grid size.\nEnter grid size: ");
            GRID_SIZE = scannerInt.nextInt();
        }
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        placeShips(player1Grid);
        placeShips(player2Grid);

        System.out.print("1.Double player \n2.Single player \nChoose an option: ");
        int choice = scannerInt.nextInt();
        while(choice!=2 && choice!=1){
            System.out.print("Invalid choice.\nEnter choice: ");
            choice = scannerInt.nextInt();
        }
        boolean player1Turn = true;
        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                System.out.println();
                playerTurn(player2Grid, player1TrackingGrid);
            } else if(choice == 1) {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                System.out.println();
                playerTurn(player1Grid, player2TrackingGrid);
            }else {
                System.out.println("Computer's turn:");
                printGrid(player2TrackingGrid);
                System.out.println();
                computerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
        if(allShipsSunk(player1TrackingGrid)) {
            System.out.println("Player 1 won!");
        }else{
            System.out.println("Player 2 won!");
        }
        System.out.println("playe 1 grid:");
        printGrid(player1Grid);
        if(choice == 1) {
            System.out.println("playe 2 grid:");
        }else{
            System.out.println("computer's grid:");
        }
        printGrid(player2Grid);
        System.out.println();
    }
    static void initializeGrid(char[][] grid) {
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }

    static void placeShips(char[][] grid) {
        int[] shipSizes = {5,4,3,2};
        for(int size : shipSizes) {
            boolean placed = false;
            while(!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                boolean horizontal = random.nextBoolean();
                if(canPlaceShip(grid, row, col,size,horizontal)){
                    for(int i=0; i<size; i++) {
                        if(horizontal){
                            grid[row][col+i] = 's';
                        }else{
                            grid[row+i][col] = 's';
                        }
                    }
                    placed = true;
                }
            }
        }
    }

    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if(horizontal){
            if(col + size > GRID_SIZE) return false;
            for(int i=0; i<size; i++) {
                if(grid[row][col+i] == 's') return false;
            }
        }else {
            if(row + size > GRID_SIZE) return false;
            for(int i=0; i<size; i++) {
                if(grid[row+i][col] == 's') return false;
            }
        }
        return true;
    }

    static boolean isValidInput(String input) {
        if(input.length()!=2){
            return false;
        }
        int a = input.charAt(0) - 'A';
        int b = Character.getNumericValue(input.charAt(1));
        return (a>=0 && a<GRID_SIZE && b>=0 && b<GRID_SIZE);
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        String input = scanner.nextLine();
        if(isValidInput(input)) {
            int a = input.charAt(0) - 'A';
            int b = Character.getNumericValue(input.charAt(1));
            if (trackingGrid[b][a] != 'X' && trackingGrid[b][a] != 'O') {
                if (opponentGrid[b][a] == 's') {
                    trackingGrid[b][a] = 'X';
                    System.out.println("Hit!");
                } else {
                    trackingGrid[b][a] = 'O';
                    System.out.println("Miss!");
                }
            } else {
                System.out.println("you already guessed that!");
            }
        }else {
            System.out.println("is valid!");
        }
    }

    static void computerTurn(char[][] opponentGrid, char[][] trackingGrid){
        int a = random.nextInt(GRID_SIZE);
        int b = random.nextInt(GRID_SIZE);
        System.out.print((char)(a+'A'));
        System.out.println(b);
        if (trackingGrid[b][a] != 'X' && trackingGrid[b][a] != 'O') {
            if (opponentGrid[b][a] == 's') {
                trackingGrid[b][a] = 'X';
                System.out.println("Hit!");
            } else {
                trackingGrid[b][a] = 'O';
                System.out.println("Miss!");
            }
        }
    }
    static boolean isGameOver() {
        return (allShipsSunk(player1TrackingGrid) || allShipsSunk(player2TrackingGrid));
    }
    static boolean allShipsSunk(char[][] grid) {
        int score = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'X') {
                    score++;
                }
            }
        }
        return (score == 14);
    }

    static void printGrid(char[][] grid) {
        char start = 'A';
        System.out.print("   ");
        for(int i=0; i<GRID_SIZE; i++) System.out.print(" "+(char)(start+i)+" ");
        System.out.println();
        for(int i=0; i<GRID_SIZE; i++) {
            System.out.print(" "+i+" ");
            for(int j=0; j<GRID_SIZE; j++) {
                System.out.print(" " + grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
