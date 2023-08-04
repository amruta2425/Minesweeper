package Minesweeper;

//Import Functions
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {
    public static final int BOARD_SIZE = 10;
    public static final int NUM_MINES = 10;
    public static final char UNREVEALED_CELL = '\u25A0';    
    public static final char MINE_CELL = '\u25CF';
   

    public char[][] grid;
    public boolean[][] revealed;
    public int remainingCells;

    // Constructor 
    public MinesweeperGame() {
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
        remainingCells = BOARD_SIZE * BOARD_SIZE;
        
        // Initialize the game grid with unrevealed cells
        initializeGrid();
        
     // Randomly place mines on the grid
        placeMines();
    }
    
 // Function to print instructions 
    public void printInstructions() {
        System.out.println("\n              Welcome to Minesweeper!       \n \n" +
                "1. To play the game, enter the row and column numbers of the cell you want to reveal.   \n" +
                "2. Rows and columns are numbered from 0 to 9.\n" +
                "3. For example, to reveal the cell in the first row and second column, enter '0 1'.\n" +
                "4. Avoid revealing a mine (represented by " + MINE_CELL + "), as it will end the game.\n" +
                "5. If you reveal all non-mine cells, you win the game.\n \n" +
                "Let's begin! Good luck!");
    }

    
    // Function to initialize the game grid with unrevealed cells
    public void initializeGrid() {
        //System.out.println("\n      Welcome To the Minesweeper Game ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = UNREVEALED_CELL;
                revealed[i][j] = false; // Initialize revealed array
            }
        }
    }
    
    // Function to randomly place mines on the game grid
    public void placeMines() {
    	
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < NUM_MINES) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            if (grid[row][col] != MINE_CELL) {
                grid[row][col] = MINE_CELL;
                minesPlaced++;
            }
        }
    }

    // Function to count the number of adjacent mines around a given cell
    public int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && grid[i][j] == MINE_CELL) {
                    count++;
                }
            }
        }
        return count;
    }
    // Function to reveal a cell
    public void revealCell(int row, int col) {
    	
    	// If the cell has already been revealed, return and do nothing
        if (revealed[row][col]) {
            return;
        }
        // Mark the cell as revealed and reduce the count of remaining cells
        revealed[row][col] = true;
        remainingCells--;
        
        // If the revealed cell contains a mine, the game ends.
        if (grid[row][col] == MINE_CELL) {
            printGrid();
            System.out.println();
            System.out.println("Boom! Game Over!");
            System.out.println("Better Luck Next Time!");
            System.exit(0);
            
        } else if (remainingCells == NUM_MINES) {
            printGrid();
            System.out.println("Congratulations! You Win!");
            System.exit(0);
            
        } else {
            int adjacentMines = countAdjacentMines(row, col);
            grid[row][col] = (char) (adjacentMines + '0');
            
            //recursively revelaing adjacent cells
            if (adjacentMines == 0) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE) {
                            revealCell(i, j);
                        }
                    }
                }
            }
        }
    }

    public void printGrid() {
    	 // Print the column headers
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
     // Print the rows and cells of the game grid
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!revealed[i][j]) {
                    System.out.print(UNREVEALED_CELL + " ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        printInstructions();

        while (true) {
            System.out.println();
            printGrid();
            System.out.println();
            System.out.print("Enter row (0-9): ");
            int row = readValidCoordinate(scanner);

            System.out.print("Enter column (0-9): ");
            int col = readValidCoordinate(scanner);

            if (isValidCoordinate(row) && isValidCoordinate(col)) {
                revealCell(row, col);
            } else {
                System.out.println("Invalid coordinates. Please enter values between 0 and 9.");
            }
        }
    }

    public int readValidCoordinate(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            try {
                int coordinate = Integer.parseInt(input);
                if (isValidCoordinate(coordinate)) {
                    return coordinate;
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    public boolean isValidCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < BOARD_SIZE;
    }

    
    
   
}

