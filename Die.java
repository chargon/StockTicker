package gamefiles;

public class Die {

	private int _numSides;
	private String[] _sideValues;
	
	public Die(String[] sideValues)
	{
		_numSides = sideValues.length;
		_sideValues = sideValues;
	}
	
	// Generic die roller. Game object decides what to do with the result
	public String Roll()
	{
		int rollResult = (int)(Math.random() * _numSides);
		String rollValue = _sideValues[rollResult];
		
		return rollValue;
	}	
}
