package BFS;

/**
 * 
 * @author Huynh Tan Phuc
 *
 * Class name: Tile 
 * A Tile represents 1 square 
 * 
 * An value is a number on a square.
 */
public class Tile {
	private int _value;

	public Tile() {
		this._value = 0;
	}
	
	/**
	 * 
	 * @param aTile The tile need to set value
	 */
	public Tile(Tile aTile) {
		this._value = aTile._value;
	}
	
	/**
	 * 
	 * @return Value of tile
	 */
	public int getValue() {
		return this._value;
	}
	
	/**
	 * 
	 * @param aValue The value of tile need to set
	 */
	public void setValue(int aValue) {
		this._value = aValue;
	}
	
	/**
	 * Compare 2 tiles
	 * 
	 * @param aTile The tile need to compare
	 * @return True if equal
	 */
	public boolean equa(Tile aTile) { 
		if (this._value == aTile._value) return true;
		return false;
	}
	
}
