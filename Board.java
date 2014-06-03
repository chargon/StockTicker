package gamefiles;
import java.util.HashMap;

public class Board {

	private HashMap<String, Integer> _stockValues;
	
	// Build our basic board - kept as a HashMap for space efficiency 
	public Board()
	{
		_stockValues = new HashMap<String, Integer>();
		
		for (int i = 0; i < Game.stockNames.length; i++)
		{
			_stockValues.put(Game.stockNames[i], 100);
		}
	}
	
	public HashMap<String, Integer> getStockValues()
	{
		return _stockValues;
	}	
	
	public void printBoard()
	{
		for (String stock: Game.stockNames)
		{
			System.out.println(stock + ": " + _stockValues.get(stock));
		}
		System.out.println();
	}
}
