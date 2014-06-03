package gamefiles;
import java.util.Scanner;
import java.util.HashMap;


// Author: Murray Bratland
// Last Modified: June 3, 2014
// Version: 0.8
// Notes: Basic text-based Stock Ticker game (based on board game version by Copp Clark Games)
// Notes: Basic functionality completed
// Notes: Supports one player but will be (easily) scaled to multiple players 
// Notes: More robust error checking (i.e., on user input values) needs to occur
// Notes: GUI interface in the works as well
public class Game {

	public static final String[] stockNames = {"GOLD", "SILVER", "OIL", "BONDS", "INDUSTRIAL", "GRAIN"};
	public static final String[] directions = {"UP", "DOWN", "DIV"};
	public static final String[] values = {"5", "10", "20"};

	public static void main(String[] args) {		

		// initialize input scanner
		Scanner scanner = new Scanner(System.in);

		// Initialize game board
		Board gameBoard = new Board();

		// Initialize dice
		Die stocksDie = new Die(stockNames);
		Die directionDie = new Die(directions);
		Die valuesDie = new Die(values);

		// Initialized player
		System.out.println("Please enter user name: ");
		String userName = scanner.nextLine();		
		Player playerOne = new Player(userName);

		gameBoard.printBoard();
		playerOne.printPlayerMoney();
		playerOne.printPlayerStocks();

		char actionSelected = ' ';
		System.out.println("Type an action: (R)oll / (B)uy / (S)ell / (Q)uit:");
		actionSelected = scanner.nextLine().toLowerCase().charAt(0);

		while (true)
		{
			while ((actionSelected != 'r') && (actionSelected != 'b') && (actionSelected != 's') && (actionSelected != 'q'))
			{
				System.out.println("Invalid selection.");
				System.out.println("Type an action: (R)oll / (B)uy / (S)ell / (Q)uit:");
				actionSelected = scanner.nextLine().charAt(0);
			}

			if (actionSelected == 'r')
			{
				String stockRollResult = stocksDie.Roll();
				String directionRollResult = directionDie.Roll();
				String valueRollResult = valuesDie.Roll();

				System.out.println();
				System.out.println(stockRollResult + " " + directionRollResult + " " + valueRollResult);
				System.out.println();

				if (directionRollResult.equals(directions[0]))
				{
					int newStockValue = (int)gameBoard.getStockValues().get(stockRollResult) + Integer.parseInt(valueRollResult);
					if (newStockValue >= 200){
						playerOne.SetStockAmount(stockRollResult, (int)gameBoard.getStockValues().get(stockRollResult) * 2);
						newStockValue = 100;
					}
					gameBoard.getStockValues().put(stockRollResult, newStockValue);
				}
				else if (directionRollResult.equals(directions[1]))
				{
					int newStockValue = (int)gameBoard.getStockValues().get(stockRollResult) - Integer.parseInt(valueRollResult);
					if (newStockValue <= 0){
						playerOne.SetStockAmount(stockRollResult, 0);
						newStockValue = 100;
					}
					gameBoard.getStockValues().put(stockRollResult, newStockValue);
				}			
				else if (directionRollResult.equals(directions[2]))
				{
					playerOne.payDividend(gameBoard, stockRollResult, Integer.parseInt(valueRollResult));
				}
				
				gameBoard.printBoard();
				playerOne.printPlayerMoney();
				playerOne.printPlayerStocks();
			}

			// Buy action supports rudimentary error-checking (TODO: Add more robust error-checking on input values, including Integer parsing)
			else if (actionSelected == 'b')
			{
				int sharesToBuy = 0;

				System.out.println("Please enter a stock to buy from: \"GOLD\", \"SILVER\", \"OIL\", \"BONDS\", \"INDUSTRIAL\", or \"GRAIN\":");
				String stockToBuy = scanner.nextLine();
				System.out.println("Please enter amount to shares to buy (multiple of 500):");
				sharesToBuy = Integer.parseInt(scanner.nextLine());
				playerOne.buyStock(gameBoard, stockToBuy, sharesToBuy);

				gameBoard.printBoard();
				playerOne.printPlayerMoney();
				playerOne.printPlayerStocks();
			}

			// Sell action supports rudimentary error-checking (TODO: Add more robust error-checking on input values, including Integer parsing)
			else if (actionSelected == 's')
			{
				int sharesToSell = 0;

				System.out.println("Please enter a stock to sell: \"GOLD\", \"SILVER\", \"OIL\", \"BONDS\", \"INDUSTRIAL\", or \"GRAIN\":");
				String stockToBuy = scanner.nextLine();
				System.out.println("Please enter amount to shares to sell (multiple of 500):");
				sharesToSell = Integer.parseInt(scanner.nextLine());
				playerOne.sellStock(gameBoard, stockToBuy, sharesToSell);

				gameBoard.printBoard();
				playerOne.printPlayerMoney();
				playerOne.printPlayerStocks();
			}
			
			else if (actionSelected == 'q')
			{
				System.out.println(playerOne.PlayerWorth(gameBoard));
				System.out.println("Thanks for playing! Goodbye!");
				System.exit(0);
			}
			
			actionSelected = ' ';
		}		
	}	
}