package simpleHillClimbing;

import java.util.LinkedList;

/**
 * 
 * @author Huynh Tan Phuc
 *
 */
public class State {
	private int _numTiles;
	private Tile[] _tiles;
	
	public State(int aNumTiles) {
		this._numTiles = aNumTiles;
		this._tiles = new Tile[_numTiles];
		for (int i = 0; i < _numTiles; i++) this._tiles[i] = new Tile();
	} 
	
	public State(State aState) {
		this._numTiles = aState._numTiles;
		this._tiles = new Tile[this._numTiles];
		for (int i = 0; i < _numTiles; i++) this._tiles[i] = new Tile(aState._tiles[i]);
	}
	
	public void setNumTiles(int aNumTiles) {
		_numTiles = aNumTiles;
	}
	
	public int getNumTiles() {
		return _numTiles;
	}
	
	/**
	 * Create a start state
	 * 
	 * @param aTiles One tile of start state
	 */
	public void createStartState(Tile[] aTiles) {
		for (int i = 0; i < _numTiles; i++) this._tiles[i] = aTiles[i];
	}
	
	/**
	 * Update 1 tile of a state
	 * 
	 * @param anIndex Index of tile
	 * @param aTile	Content use update tile[anIndex]
	 */
	public void UpdateState(int anIndex, Tile aTile) {
		this._tiles[anIndex] = aTile;
	}
	
	/**
	 * 
	 * @param aState State need to check
	 * @return True if the State has already visited
	 */
	public boolean checkVisited(State aState) {
		for (int i = 0; i < this._numTiles; i++) {
			if (this._tiles[i].getStartX() != aState._tiles[i].getStartX() || this._tiles[i].getStartY() != aState._tiles[i].getStartY())
				return false;
		}
		return true;
	}
	
	/**
	 * Check this state with states in list state 
	 * 
	 * @param aListState List state visited
	 * @return True if visited
	 */
	public boolean checkVisitedState(LinkedList aListState) {
		for (int i = 0; i < aListState.size(); i++) {
			if (this.checkVisited((State)aListState.get(i))) return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param aTile Tile is moved
	 * @param aDirectionMove Direction of movement
	 * @return True if can't move
	 */
	public boolean checkCollision(Tile aTile, char aDirectionMove) {
		switch(aDirectionMove) {
			case 'L':
				if (!aTile.getHorizontal()) return true; // Check horizontal tile?
				if (aTile.getStartY() == 0) return true; // Check to meet boundary?
				for (int i = 0; i < _numTiles; i++) { // Check the other tiles 
					if (aTile.getStartPosTile()==_tiles[i].getStartPosTile()) continue; // Not check a tile is a parameter
					else {
						if (_tiles[i].getHorizontal() && _tiles[i].getStartX() == aTile.getStartX()) // Horizontal tiles and same line?
							//if (_tiles[i].getStartY() + _tiles[i].getLength() == aTile.getStartY()) return true;
							if (_tiles[i].getLength() == 2) {
								if (_tiles[i].getStartY() + 2 == aTile.getStartY()) return true;
							}
							else { // Length = 3 
								if (_tiles[i].getStartY() + 3 == aTile.getStartY()) return true;
							}
						else if (_tiles[i].getVertical()) {
							if (_tiles[i].getLength() == 2) {
								if (_tiles[i].getStartX() == aTile.getStartX() && _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 1 == aTile.getStartX() && _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
							}
							else { // Length = 3
								if (_tiles[i].getStartX() == aTile.getStartX() && _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 1 == aTile.getStartX() && _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 2 == aTile.getStartX() && _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
							}
						}
					}
				}
				return false; // No collision
			case 'R':
				if (!aTile.getHorizontal()) return true; // Check horizontal tile?
				if (aTile.getLength() == 2 && aTile.getStartY() == 4) return true; // Check to meet boundary with a tile has a length = 2?
				if (aTile.getLength() == 3 && aTile.getStartY() == 3) return true; // Check to meet boundary with a tile has a length = 3?
				for (int i = 0; i < _numTiles; i++) { // Check the other tiles 
					if (aTile.getStartPosTile() == _tiles[i].getStartPosTile()) continue; // Not check a tile is a parameter
					else {
						if (_tiles[i].getHorizontal() && _tiles[i].getStartX() == aTile.getStartX()) { // Horizontal tiles and same line?
							if (aTile.getLength() == 2) {
								if (aTile.getStartY() + 2 == _tiles[i].getStartY()) return true;
							}
							else { // Length = 3 
								if (aTile.getStartY() + 3 == _tiles[i].getStartY()) return true;
							}
						}
						else if (_tiles[i].getVertical()) { // If tile is a vertical tile
							int lt = aTile.getLength(); // Check tile has length 2 or 3
							if (_tiles[i].getLength() == 2) {
								if (_tiles[i].getStartX() == aTile.getStartX() && _tiles[i].getStartY() - lt == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 1 == aTile.getStartX() && _tiles[i].getStartY() - lt == aTile.getStartY()) return true;
							}
							else { // Length = 3
								if (_tiles[i].getStartX() == aTile.getStartX() && _tiles[i].getStartY() - lt == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 1 == aTile.getStartX() && _tiles[i].getStartY() - lt == aTile.getStartY()) return true;
								else if (_tiles[i].getStartX() + 2 == aTile.getStartX() && _tiles[i].getStartY() - lt == aTile.getStartY()) return true;
							}
						}
					}
					
				}
				return false; // No collision
			case 'U': // move up
				if (!aTile.getVertical()) return true; // Check vertical tile?
				if (aTile.getStartX() == 0) return true; // Check to meet boundary?
				for (int i = 0; i < _numTiles; i++) { // Check the other tiles 
					if (aTile.getStartPosTile() == _tiles[i].getStartPosTile()) continue; // Not check a tile is a parameter
					else {
						if (_tiles[i].getHorizontal() && _tiles[i].getStartX() + 1 == aTile.getStartX()) {
							if (_tiles[i].getLength() == 2) {
								if((_tiles[i].getStartY() == aTile.getStartY()) || (_tiles[i].getStartY() + 1 == aTile.getStartY())) return true;
							}
							else { // Length = 3
								if(_tiles[i].getStartY() == aTile.getStartY() || _tiles[i].getStartY() + 1 == aTile.getStartY() || _tiles[i].getStartY() + 2== aTile.getStartY()) return true;
							}	
						}
						else if (_tiles[i].getVertical() && _tiles[i].getStartY() == aTile.getStartY()) {
							if (_tiles[i].getStartX() + _tiles[i].getLength() == aTile.getStartX()) return true;
						}
					}
				}
				return false; // No collision
			case 'D':
				if (!aTile.getVertical()) return true; // Check vertical tile?
				if (aTile.getLength() == 2 && aTile.getStartX() + 1 == 5) return true; // Check to meet boundary with a tile has a length = 2?
				if (aTile.getLength() == 3 && aTile.getStartX() + 2 == 5) return true; // Check to meet boundary with a tile has a length = 3?
				for (int i = 0; i < _numTiles; i++) { // Check the other tiles 
					if (aTile.getStartPosTile() == _tiles[i].getStartPosTile()) continue; // Not check a tile is a parameter
					else {
						if (aTile.getLength() == 2) {
							if (_tiles[i].getHorizontal() && _tiles[i].getStartX() - 2 == aTile.getStartX()) {
								if (_tiles[i].getLength() == 2){
									if(_tiles[i].getStartY() == aTile.getStartY() || _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
								}
								else { // Length = 3
									if(_tiles[i].getStartY() == aTile.getStartY() || _tiles[i].getStartY() + 1 == aTile.getStartY() || _tiles[i].getStartY() + 2 == aTile.getStartY()) return true;
								}
							}
							else if (_tiles[i].getVertical() && _tiles[i].getStartY() == aTile.getStartY()) // Same vertical titles and the same y
								if (aTile.getStartX() + aTile.getLength() == _tiles[i].getStartX()) return true;
						}
						else { // Length = 3
							if (_tiles[i].getHorizontal() && _tiles[i].getStartX() - 3 == aTile.getStartX()) {
								if (_tiles[i].getLength() == 2){
									if(_tiles[i].getStartY() == aTile.getStartY() || _tiles[i].getStartY() + 1 == aTile.getStartY()) return true;
								}
								else { // Length = 3
									if(_tiles[i].getStartY() == aTile.getStartY() || _tiles[i].getStartY() + 1 == aTile.getStartY() || _tiles[i].getStartY() + 2 == aTile.getStartY()) return true;
								}
							}
							else if (_tiles[i].getVertical() && _tiles[i].getStartY() == aTile.getStartY()) // Same vertical titles and the same y
								if (aTile.getStartX() + aTile.getLength() == _tiles[i].getStartX()) return true;
						}
					}
				}
				return false; // No collision
			default: return true;
		}
		
	}
	
	/**
	 * Move left tile[i]
	 * 
	 * @param anIndex Index of tile is moved
	 * @return True if movement success
	 */
	public boolean moveLeft(int anIndex) {
		if (!checkCollision(this._tiles[anIndex], 'L')) {
			this._tiles[anIndex].setStartY(this._tiles[anIndex].getStartY() - 1);
			return true;
		}
		return false;
	}
	
	/**
	 * Move right tile[i]
	 * 
	 * @param anIndex Index of tile is moved
	 * @return True if movement success
	 */
	public boolean moveRight(int anIndex) { 
		if (!checkCollision(this._tiles[anIndex], 'R')) {
			this._tiles[anIndex].setStartY(this._tiles[anIndex].getStartY() + 1);
			return true;
		}
		return false;
	}
	
	/**
	 * Move up tile[i]
	 * 
	 * @param anIndex Index of tile is moved
	 * @return True if movement success
	 */
	public boolean moveUp(int anIndex) { 
		if (!checkCollision(this._tiles[anIndex], 'U')) {
			this._tiles[anIndex].setStartX(this._tiles[anIndex].getStartX() - 1);
			return true;
		}
		return false;
	}
	
	/**
	 * Move down tile[i]
	 * 
	 * @param anIndex Index of tile is moved
	 * @return if movement success
	 */
	public boolean moveDown(int anIndex) {
		if (!checkCollision(this._tiles[anIndex], 'D')) {
			this._tiles[anIndex].setStartX(this._tiles[anIndex].getStartX() + 1);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return True if a state is goal
	 */
	public boolean checkGoal() {
		for (int i = 0; i < _numTiles; i++)
			if (_tiles[i].getGoal() && _tiles[i].getStartX() == 2 && _tiles[i].getStartY() == 4) return true;
		
		return false;
	}
	
	public void printState() {
		for (int i = 0; i < _numTiles; i++)
			System.out.print("(" + _tiles[i].getStartX() + "," + _tiles[i].getStartY() + ")   ");
		
		System.out.println();
	}
	
	/**
	 * 
	 * @return True if having blank front of goal
	 */
	public boolean checkBlankFrontOfGoal() {
		int[][] arrResult = new int[6][6];
		
		for (int i = 0; i < 6; i++) 
			for (int j = 0; j < 6; j++)  arrResult[i][j] = 0;
		
		for (int i = 0; i < _numTiles; i++) {
			if (_tiles[i].getHorizontal()) {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = 1;
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 1] = 1;
				if (_tiles[i].getLength() == 3) arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 2] = 1;
			}
			else {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = 1;
				arrResult[_tiles[i].getStartX() + 1][_tiles[i].getStartY()] = 1;
				if (_tiles[i].getLength() == 3) arrResult[_tiles[i].getStartX() + 2][_tiles[i].getStartY()] = 1;
			}
			if (_tiles[i].getGoal()) {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = 2;
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 1] = 2;
			}
		}
		
		for (int i = 0; i < 5; i++) {
			if (i + 2 != 6 && arrResult[2][i] == 2 && arrResult[2][i + 2] != 0) return false;
		}	

		return true;
	}
	
	/**
	 * Print a state
	 */
	public void printFactState() {
		char[][] arrResult = new char[6][6];
		
		for (int i = 0; i < 6; i++) 
			for (int j = 0; j < 6; j++)  arrResult[i][j] = '.';
		
		for (int i = 0; i < _numTiles; i++) {
			if (_tiles[i].getHorizontal()) {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = '-';
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 1] = '-';
				if (_tiles[i].getLength() == 3) arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 2] = '-';
			}
			else {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = '|';
				arrResult[_tiles[i].getStartX() + 1][_tiles[i].getStartY()] = '|';
				if (_tiles[i].getLength() == 3) arrResult[_tiles[i].getStartX() + 2][_tiles[i].getStartY()] = '|';
			}
			if (_tiles[i].getGoal()) {
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY()] = '$';
				arrResult[_tiles[i].getStartX()][_tiles[i].getStartY() + 1] = '$';
			}
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(" " + arrResult[i][j] + " ");
			}
			System.out.println();
		}	
	}
}
