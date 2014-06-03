package gamefiles;
import java.util.HashMap;

public class Player {

	private String _playerName;
	private int _playerMoney;
	private HashMap<String, Integer> _playerStocks;
	
	// Create a new Player object
	public Player (String name)
	{
		_playerName = name;
		_playerMoney = 5000; // Player starts with $5,000
		_playerStocks = new HashMap<String, Integer>();
	
		// Set initial stock amounts to 0
		for (int i = 0; i < Game.stockNames.length; i++)
		{
			_playerStocks.put(Game.stockNames[i], 1000);
		}
	}
	
	public void SetStockAmount(String stock, int amount)
	{
		_playerStocks.put(stock, amount);
	}

	// Supports rudimentary error-checking (TODO: Add more robust error-checking on input values) 
	public void buyStock(Board gameBoard, String stock, int amount)
	{
		if (amount % 500 == 0)
		{
			int stockPrice = gameBoard.getStockValues().get(stock);
			if ((stockPrice * amount / 100) <= _playerMoney)
			{
				_playerStocks.put(stock,  _playerStocks.get(stock) + amount);
				_playerMoney -= amount;
			}
			else
			{
				System.out.println("Player cannot afford this many shares of this stock");
			}
		}
		else
		{
			System.out.println("Player can only purchase stock in 500 share increments.");
		}
	}
	
	// Supports rudimentary error-checking (TODO: Add more robust error-checking on input values)
	public void sellStock(Board gameBoard, String stock, int amount)
	{
		if (amount % 500 == 0)
		{
			int stockPrice = gameBoard.getStockValues().get(stock);
			if (amount <= _playerStocks.get(stock))
			{
				_playerStocks.put(stock,  _playerStocks.get(stock) - amount);
				_playerMoney += amount * stockPrice / 100;
			}
			else
			{
				System.out.println("Player does not have this many shares of this stocks to sell");
			}
		}
		else
		{
			System.out.println("Player can only sell stock in 500 share increments.");
		}
	}
	
	public void payDividend(Board gameBoard, String stock, int payPercentage)
	{
		if (gameBoard.getStockValues().get(stock) >= 100)
		{
			_playerMoney += _playerStocks.get(stock) * payPercentage / 100;
		}
		else
		{
			System.out.println("Dividends not payable - stock below 100");
		}
	}
	
	public void printPlayerMoney()
	{
		System.out.println(_playerName + "'s balance is $" + _playerMoney);
		System.out.println();
	}
	
	public void printPlayerStocks()
	{
		System.out.println(_playerName + "'s stock share balances are:");
		for (String stock: Game.stockNames)
		{
			System.out.println(stock + ": " + _playerStocks.get(stock) + " shares");
		}
		System.out.println();
	}

	public int PlayerWorth(Board gameBoard)
	{
		int finalWorth = _playerMoney;
		System.out.print(_playerName + "'s final player worth: $");
		for (String stock: Game.stockNames)
		{
			finalWorth += _playerStocks.get(stock) * gameBoard.getStockValues().get(stock) / 100; 
		}
		return finalWorth;
	}	
}
