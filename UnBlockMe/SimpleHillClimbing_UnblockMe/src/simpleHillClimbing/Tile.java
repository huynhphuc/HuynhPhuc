package simpleHillClimbing;

/**
 * 
 * @author Huynh Tan Phuc
 *
 */
public class Tile {
	private boolean _horizontal, _vertical, _goal;
	private int _length;
	private int[] _startPosTile = new int[2];
	
	public Tile(){
		this._horizontal = false;
		this._vertical = false;
		this._goal = false;
		this._length = 0;
		this._startPosTile[0] = -1; // x
		this._startPosTile[1] = -1; // y
	}
	
	/**
	 * 
	 * @param aTile Create object tile with content of aTile
	 */
	public Tile(Tile aTile){
		this._horizontal = aTile._horizontal;
		this._vertical = aTile._vertical;
		this._goal = aTile._goal;
		this._length = aTile._length;
		this._startPosTile[0] = aTile._startPosTile[0]; // x
		this._startPosTile[1] = aTile._startPosTile[1]; // y
	}
	
	public void setHorizontal(boolean aHorizontal) {
		_horizontal = aHorizontal;
	}
	
	public boolean getHorizontal() {
		return _horizontal;
	}
	
	public void setVertical(boolean aVertical) {
		_vertical = aVertical;
	}
	
	public boolean getVertical() {
		return _vertical;
	}
	
	public void setGoal(boolean aGoal) {
		_goal = aGoal;
	}
	
	public boolean getGoal() {
		return _goal;
	}
	
	public void setLength(int aLength) {
		_length = aLength;
	}
	
	public int getLength() {
		return _length;
	}
	
	public void setStartX(int aX) {
		_startPosTile[0] = aX;
	}
	
	public void setStartY(int aY) {
		_startPosTile[1] = aY;
	}
	
	public int getStartX() {
		return _startPosTile[0];
	}
	
	public int getStartY() {
		return _startPosTile[1];
	}
	
	public void setStartPosTile(int[] aStartPosTile) {
		_startPosTile = aStartPosTile;
	}
	
	public int[] getStartPosTile() {
		return _startPosTile;
	}
	
}