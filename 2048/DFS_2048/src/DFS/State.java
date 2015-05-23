package DFS;

/**
 * 
 * @author Huynh Tan Phuc
 *
 * Class name: State
 * 
 * Each of _tiles is marked from 0 1 2 ... 15
 * From left to right and top down to bottom.
 */
public class State {
	private Tile[] _tiles;
	
	/**
	 * Create a state with index of tiles 0 2 3 4 ... 15
	 */
	public State() {
		this._tiles = new Tile[16];
		int k = 0;
		for (int i = 0; i < 4; i++) 
			for (int j = 0; j < 4; j++) {
				this._tiles[k] = new Tile();
				k++;
			}
	}
	
	/**
	 * 
	 * @param aState The state need to set 
	 */
	public State(State aState) {
		this._tiles = new Tile[16];
		for (int i = 0; i < 16; i++) this._tiles[i] = new Tile(aState._tiles[i]);
	}
	
	/**
	 * Set value for a tile 
	 * 
	 * @param anIndex Index of tile 
	 * @param aValue Value of tile
	 */
	public void setTile(int anIndex, int aValue) {
		this._tiles[anIndex].setValue(aValue);
	}
	
	/**
	 * 
	 * @param anIndex Index of tile
	 * @return Tile with index
	 */
	public Tile getTile(int anIndex) {
		return this._tiles[anIndex];
	}
	
	/**
	 * 
	 * Infer a next state
	 * 
	 * @param aTile1 Is a tile 1 
	 * @param aTile2 Is a tile 2
	 * @param aTile3 Is a tile 3 
	 * @param aTile4 Is a tile 4 
	 * @return
	 */
	public int[] inference(Tile aTile1, Tile aTile2, Tile aTile3, Tile aTile4) {
		int[] result = new int[4];
		
		if (aTile1.getValue()==0 && aTile2.getValue()==0 && aTile3.getValue()==0 && aTile4.getValue()!=0) { // Check case 0 0 0 aTile4
			result[0] = aTile4.getValue();
			result[1] = 0;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.equa(aTile2) && aTile3.equa(aTile4) && aTile1.getValue()==0) { // Example 0 0 4 4 
			result[0] = aTile3.getValue()*2;
			result[1] = 0;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0 && aTile2.getValue()==0 && !aTile3.equa(aTile4)) { // Check case 0 0 aTile3 aTile4, aTile3<>aTile4
			result[0] = aTile3.getValue();
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile2.equa(aTile4) && aTile1.getValue()==0 && aTile3.getValue()==0) { // Example 0 2 0 2
			result[0] = aTile2.getValue()*2;
			result[1] = 0;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0 && !aTile2.equa(aTile4) && aTile3.getValue()==0) { // Check case 0 aTile2 0 aTile4, aTile2<>aTile4
			result[0] = aTile2.getValue();
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0 && !aTile1.equa(aTile2) && aTile2.equa(aTile3)) { // Example 0 2 2 4 
			result[0] = aTile2.getValue()*2;
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0 && aTile2.getValue()!=0 && !aTile2.equa(aTile3) && aTile3.equa(aTile4)) { // Example 0 4 2 2 
			result[0] = aTile2.getValue();
			result[1] = aTile3.getValue()*2;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0 && !aTile2.equa(aTile3) && !aTile2.equa(aTile4) && !aTile3.equa(aTile4)) { // Check case 0 aTile2 aTile3 aTile4, aTile2<>aTile3, aTile2<>aTile4, aTile3<>aTile4
			result[0] = aTile2.getValue();
			result[1] = aTile3.getValue();
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else if (aTile1.getValue()!=0 && aTile2.getValue()==0 && aTile3.getValue()==0 && !aTile1.equa(aTile4)) { // Check case aTile1 0 0 aTile4, aTile1<>aTile4
			result[0] = aTile1.getValue();
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()!=0 && aTile2.getValue()==0 && aTile3.getValue()==0 && aTile1.equa(aTile4)) { // Check case aTile1 0 0 aTile4, aTile1=aTile4
			result[0] = aTile1.getValue()*2;
			result[1] = 0;
			result[2] = 0;
			result[3] = 0;
		}
		else if (!aTile1.equa(aTile2) && aTile2.equa(aTile3) && aTile1.getValue()!=0) { // Example 2 4 4 _
			result[0] = aTile1.getValue();
			result[1] = aTile2.getValue()*2;
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else if (aTile1.equa(aTile2) && aTile3.equa(aTile4) && aTile1.getValue()!=0) { // Example 2 2 4 4 or 2 2 2 2
			result[0] = aTile1.getValue()*2;
			result[1] = aTile3.getValue()*2;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.equa(aTile2) && !aTile3.equa(aTile4) && aTile3.getValue()!=0) { // Example 2 2 2 4
			result[0] = aTile1.getValue()*2;
			result[1] = aTile3.getValue();
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else if (aTile1.equa(aTile2) && !aTile3.equa(aTile4) && aTile3.getValue()==0) { // Example 2 2 0 2 or 2 2 0 4
			result[0] = aTile1.getValue()*2;
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile2.equa(aTile4) && aTile3.getValue()==0 && aTile1.getValue()!=0) { // Example 4 2 0 2
			result[0] = aTile1.getValue();
			result[1] = aTile2.getValue()*2;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile3.equa(aTile4) && aTile1.getValue()!=0 && aTile2.getValue()!=0) { // Example 2 2 4 4 aTile1,aTile2<>0
			result[0] = aTile1.getValue();
			result[1] = aTile2.getValue();
			result[2] = aTile3.getValue()*2;
			result[3] = 0;
		}
		else if (aTile1.equa(aTile3) && aTile2.getValue()==0) { // Example 4 0 4 _
			result[0] = aTile1.getValue()*2;
			result[1] = aTile4.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile3.equa(aTile4) && aTile2.getValue()==0 && aTile1.getValue()!=0) { // Example 2 0 4 4
			result[0] = aTile1.getValue();
			result[1] = aTile3.getValue()*2;
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()!=0 && aTile2.getValue()==0 && aTile3.getValue()!=0 && !aTile1.equa(aTile3) && aTile4.getValue()==0) { // Example 2 0 4 0
			result[0] = aTile1.getValue();
			result[1] = aTile3.getValue();
			result[2] = 0;
			result[3] = 0;
		}
		else if (aTile1.getValue()==0) { //0xxx
			result[0] = aTile2.getValue();
			result[1] = aTile3.getValue();
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else if (aTile1.getValue()!=0 && aTile2.getValue()==0) { //x0xx
			result[0] = aTile1.getValue();
			result[1] = aTile3.getValue();
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else if (aTile1.getValue()!=0 && aTile2.getValue()!=0 && aTile3.getValue()==0) { //xx0x
			result[0] = aTile1.getValue();
			result[1] = aTile2.getValue();
			result[2] = aTile4.getValue();
			result[3] = 0;
		}
		else {
			result[0] = aTile1.getValue();
			result[1] = aTile2.getValue();
			result[2] = aTile3.getValue();
			result[3] = aTile4.getValue();
		}
		
		return result;
	}
	
	/**
	 * Do move left action
	 */
	public void moveLeft() {
		for (int i = 0; i < 4; i++) {
			int[] t = inference(this._tiles[0 + 4 * i], this._tiles[1 + 4 * i], this._tiles[2 + 4 * i], this._tiles[3 + 4 * i]);
			this._tiles[0 + 4 * i].setValue(t[0]);
			this._tiles[1 + 4 * i].setValue(t[1]);
			this._tiles[2 + 4 * i].setValue(t[2]);
			this._tiles[3 + 4 * i].setValue(t[3]);
		}
	}
	
	/**
	 * Do move right action
	 */
	public void moveRight() {
		for (int i = 0; i < 4; i++) {
			int[] t = inference(this._tiles[3 + 4 * i], this._tiles[2 + 4 * i], this._tiles[1 + 4 * i], this._tiles[0 + 4 * i]);
			this._tiles[3 + 4 * i].setValue(t[0]);
			this._tiles[2 + 4 * i].setValue(t[1]);
			this._tiles[1 + 4 * i].setValue(t[2]);
			this._tiles[0 + 4 * i].setValue(t[3]);
		}
	}
	
	/**
	 * Do move up action
	 */
	public void moveUp() {
		for (int i = 0; i < 4; i++) {
			int[] t = inference(this._tiles[0 + i], this._tiles[4 + i], this._tiles[8 + i], this._tiles[12 + i]);
			this._tiles[0 + i].setValue(t[0]);
			this._tiles[4 + i].setValue(t[1]);
			this._tiles[8 + i].setValue(t[2]);
			this._tiles[12 + i].setValue(t[3]);
		}
	}
	
	/**
	 * Do move down action
	 */
	public void moveDown() {
		for (int i = 0; i < 4; i++) {
			int[] t = inference(this._tiles[12 + i], this._tiles[8 + i], this._tiles[4 + i], this._tiles[0 + i]);
			this._tiles[12 + i].setValue(t[0]);
			this._tiles[8 + i].setValue(t[1]);
			this._tiles[4 + i].setValue(t[2]);
			this._tiles[0 + i].setValue(t[3]);
		}
	}
	
	/**
	 * 
	 * @param aState The state need to check visited
	 * @return True if the State has just already visited
	 */
	public boolean checkVisited(State aState) {
		for (int i = 0; i < 16; i++) {
			if (this._tiles[i].getValue() != aState._tiles[i].getValue())
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param aGoal Is a power of 2
	 * @return True if the state is goal
	 */
	public boolean checkGoal(int aGoal) {
		for (int i = 0; i < 16; i++)
			if (this._tiles[i].getValue() == aGoal) return true;
		
		return false;
	}
	
	/**
	 * Print the state is execute
	 */
	public void printState() {
		for (int i = 0; i < 16; i++) {
			int val = this._tiles[i].getValue();
			if (val>=0 && val<10) System.out.print(this._tiles[i].getValue() + "       ");
			else if (val < 100) System.out.print(this._tiles[i].getValue() + "      ");
			else if (val < 1000) System.out.print(this._tiles[i].getValue() + "     ");
			else System.out.print(this._tiles[i].getValue() + "    ");

			if (i==3 || i==7 || i==11 || i==15) System.out.println();
		}
	}
}
