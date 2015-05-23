package DFS;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 
 * @author Huynh Tan Phuc - 51202787
 *
 */
public class Main {
	private static final double MEGABYTE = 1024.0 * 1024.0;
	
	public static double bytesToMegabytes(double bytes) {
		return bytes / MEGABYTE;
	}
	
	public static void main(String[] args) {
		System.out.println("Program is runing ... ");
		long startTime = System.currentTimeMillis();
		////////////////////////////////////////////////////////////////////////
		/** Input 1 **/
		/*
		Tile[] tiles = new Tile[1];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(2);
		tiles[0].setStartX(2);
		tiles[0].setStartY(0);
		tiles[0].setGoal(true);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 2 **/
		/*
		Tile[] tiles = new Tile[3];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(2);
		tiles[0].setStartX(1);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setHorizontal(true);
		tiles[1].setLength(2);
		tiles[1].setStartX(2);
		tiles[1].setStartY(0);
		tiles[1].setGoal(true);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(2);
		tiles[2].setStartX(2);
		tiles[2].setStartY(2);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 3 **/
		/*
		Tile[] tiles = new Tile[7];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(3);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setHorizontal(true);
		tiles[1].setLength(3);
		tiles[1].setStartX(0);
		tiles[1].setStartY(3);
		tiles[2] = new Tile();
		tiles[2].setHorizontal(true);
		tiles[2].setLength(3);
		tiles[2].setStartX(1);
		tiles[2].setStartY(0);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(3);
		tiles[3].setStartX(1);
		tiles[3].setStartY(3);
		tiles[4] = new Tile();
		tiles[4].setHorizontal(true);
		tiles[4].setLength(2);
		tiles[4].setGoal(true);
		tiles[4].setStartX(2);
		tiles[4].setStartY(1);
		tiles[5] = new Tile();
		tiles[5].setVertical(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(3);
		tiles[5].setStartY(3);
		tiles[6] = new Tile();
		tiles[6].setHorizontal(true);
		tiles[6].setLength(3);
		tiles[6].setStartX(4);
		tiles[6].setStartY(0);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 4 **/
		/*
		Tile[] tiles = new Tile[13];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(3);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setVertical(true);
		tiles[1].setLength(2);
		tiles[1].setStartX(0);
		tiles[1].setStartY(4);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(3);
		tiles[2].setStartX(0);
		tiles[2].setStartY(5);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(2);
		tiles[3].setStartX(1);
		tiles[3].setStartY(0);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(1);
		tiles[4].setStartY(3);
		tiles[5] = new Tile();
		tiles[5].setHorizontal(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(2);
		tiles[5].setStartY(0);
		tiles[5].setGoal(true);
		tiles[6] = new Tile();
		tiles[6].setVertical(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(2);
		tiles[6].setStartY(4);
		tiles[7] = new Tile();
		tiles[7].setVertical(true);
		tiles[7].setLength(2);
		tiles[7].setStartX(3);
		tiles[7].setStartY(0);
		tiles[8] = new Tile();
		tiles[8].setHorizontal(true);
		tiles[8].setLength(2);
		tiles[8].setStartX(3);
		tiles[8].setStartY(1);
		tiles[9] = new Tile();
		tiles[9].setVertical(true);
		tiles[9].setLength(2);
		tiles[9].setStartX(3);
		tiles[9].setStartY(3);
		tiles[10] = new Tile();
		tiles[10].setVertical(true);
		tiles[10].setLength(2);
		tiles[10].setStartX(4);
		tiles[10].setStartY(2);
		tiles[11] = new Tile();
		tiles[11].setHorizontal(true);
		tiles[11].setLength(2);
		tiles[11].setStartX(5);
		tiles[11].setStartY(4);
		tiles[12] = new Tile();
		tiles[12].setHorizontal(true);
		tiles[12].setLength(2);
		tiles[12].setStartX(5);
		tiles[12].setStartY(0);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 5 **/
		/*
		Tile[] tiles = new Tile[9];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(3);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setVertical(true);
		tiles[1].setLength(3);
		tiles[1].setStartX(0);
		tiles[1].setStartY(5);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(2);
		tiles[2].setStartX(1);
		tiles[2].setStartY(3);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(2);
		tiles[3].setStartX(2);
		tiles[3].setStartY(0);
		tiles[3].setGoal(true);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(2);
		tiles[4].setStartY(4);
		tiles[5] = new Tile();
		tiles[5].setVertical(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(3);
		tiles[5].setStartY(0);
		tiles[6] = new Tile();
		tiles[6].setHorizontal(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(3);
		tiles[6].setStartY(1);
		tiles[7] = new Tile();
		tiles[7].setVertical(true);
		tiles[7].setLength(2);
		tiles[7].setStartX(3);
		tiles[7].setStartY(3);
		tiles[8] = new Tile();
		tiles[8].setHorizontal(true);
		tiles[8].setLength(2);
		tiles[8].setStartX(5);
		tiles[8].setStartY(0);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 6 **/
		/*
		Tile[] tiles = new Tile[3];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(2);
		tiles[0].setStartX(2);
		tiles[0].setStartY(1);
		tiles[0].setGoal(true);
		tiles[1] = new Tile();
		tiles[1].setVertical(true);
		tiles[1].setLength(3);
		tiles[1].setStartX(2);
		tiles[1].setStartY(5);
		tiles[2] = new Tile();
		tiles[2].setHorizontal(true);
		tiles[2].setLength(3);
		tiles[2].setStartX(5);
		tiles[2].setStartY(3);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 7 **/
		/*
		Tile[] tiles = new Tile[7];
		tiles[0] = new Tile();
		tiles[0].setVertical(true);
		tiles[0].setLength(2);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setHorizontal(true);
		tiles[1].setLength(3);
		tiles[1].setStartX(0);
		tiles[1].setStartY(1);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(2);
		tiles[2].setStartX(0);
		tiles[2].setStartY(5);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(2);
		tiles[3].setStartX(2);
		tiles[3].setStartY(0);
		tiles[3].setGoal(true);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(2);
		tiles[4].setStartY(2);
		tiles[5] = new Tile();
		tiles[5].setVertical(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(2);
		tiles[5].setStartY(3);
		tiles[6] = new Tile();
		tiles[6].setVertical(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(2);
		tiles[6].setStartY(5);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 8 **/
		// Puzzle 701 in game
		/*
		Tile[] tiles = new Tile[13];
		tiles[0] = new Tile();
		tiles[0].setVertical(true);
		tiles[0].setLength(3);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setHorizontal(true);
		tiles[1].setLength(2);
		tiles[1].setStartX(0);
		tiles[1].setStartY(1);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(2);
		tiles[2].setStartX(0);
		tiles[2].setStartY(3);
		tiles[3] = new Tile();
		tiles[3].setVertical(true);
		tiles[3].setLength(3);
		tiles[3].setStartX(0);
		tiles[3].setStartY(5);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(1);
		tiles[4].setStartY(1);
		tiles[5] = new Tile();
		tiles[5].setVertical(true);
		tiles[5].setLength(3);
		tiles[5].setStartX(1);
		tiles[5].setStartY(4);
		tiles[6] = new Tile();
		tiles[6].setHorizontal(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(2);
		tiles[6].setStartY(2);
		tiles[6].setGoal(true);
		tiles[7] = new Tile();
		tiles[7].setHorizontal(true);
		tiles[7].setLength(2);
		tiles[7].setStartX(3);
		tiles[7].setStartY(0);
		tiles[8] = new Tile();
		tiles[8].setVertical(true);
		tiles[8].setLength(2);
		tiles[8].setStartX(3);
		tiles[8].setStartY(2);
		tiles[9] = new Tile();
		tiles[9].setVertical(true);
		tiles[9].setLength(2);
		tiles[9].setStartX(3);
		tiles[9].setStartY(3);
		tiles[10] = new Tile();
		tiles[10].setHorizontal(true);
		tiles[10].setLength(2);
		tiles[10].setStartX(4);
		tiles[10].setStartY(4);
		tiles[11] = new Tile();
		tiles[11].setHorizontal(true);
		tiles[11].setLength(2);
		tiles[11].setStartX(5);
		tiles[11].setStartY(1);
		tiles[12] = new Tile();
		tiles[12].setHorizontal(true);
		tiles[12].setLength(2);
		tiles[12].setStartX(5);
		tiles[12].setStartY(3);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 9 **/
		// Puzzle 702 in game
		/*
		Tile[] tiles = new Tile[13];
		tiles[0] = new Tile();
		tiles[0].setHorizontal(true);
		tiles[0].setLength(3);
		tiles[0].setStartX(0);
		tiles[0].setStartY(1);
		tiles[1] = new Tile();
		tiles[1].setVertical(true);
		tiles[1].setLength(2);
		tiles[1].setStartX(0);
		tiles[1].setStartY(4);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(3);
		tiles[2].setStartX(0);
		tiles[2].setStartY(5);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(2);
		tiles[3].setStartX(1);
		tiles[3].setStartY(0);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(1);
		tiles[4].setStartY(3);
		tiles[5] = new Tile();
		tiles[5].setHorizontal(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(2);
		tiles[5].setStartY(0);
		tiles[5].setGoal(true);
		tiles[6] = new Tile();
		tiles[6].setVertical(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(2);
		tiles[6].setStartY(4);
		tiles[7] = new Tile();
		tiles[7].setVertical(true);
		tiles[7].setLength(2);
		tiles[7].setStartX(3);
		tiles[7].setStartY(0);
		tiles[8] = new Tile();
		tiles[8].setHorizontal(true);
		tiles[8].setLength(2);
		tiles[8].setStartX(3);
		tiles[8].setStartY(1);
		tiles[9] = new Tile();
		tiles[9].setVertical(true);
		tiles[9].setLength(2);
		tiles[9].setStartX(3);
		tiles[9].setStartY(3);
		tiles[10] = new Tile();
		tiles[10].setVertical(true);
		tiles[10].setLength(2);
		tiles[10].setStartX(4);
		tiles[10].setStartY(2);
		tiles[11] = new Tile();
		tiles[11].setHorizontal(true);
		tiles[11].setLength(2);
		tiles[11].setStartX(4);
		tiles[11].setStartY(4);
		tiles[12] = new Tile();
		tiles[12].setHorizontal(true);
		tiles[12].setLength(2);
		tiles[12].setStartX(5);
		tiles[12].setStartY(0);
		*/
		////////////////////////////////////////////////////////////////////////
		/** Input 10 **/
		// Puzzle 669 in game
		
		Tile[] tiles = new Tile[11];
		tiles[0] = new Tile();
		tiles[0].setVertical(true);
		tiles[0].setLength(2);
		tiles[0].setStartX(0);
		tiles[0].setStartY(0);
		tiles[1] = new Tile();
		tiles[1].setHorizontal(true);
		tiles[1].setLength(3);
		tiles[1].setStartX(0);
		tiles[1].setStartY(1);
		tiles[2] = new Tile();
		tiles[2].setVertical(true);
		tiles[2].setLength(2);
		tiles[2].setStartX(0);
		tiles[2].setStartY(5);
		tiles[3] = new Tile();
		tiles[3].setHorizontal(true);
		tiles[3].setLength(2);
		tiles[3].setStartX(2);
		tiles[3].setStartY(0);
		tiles[3].setGoal(true);
		tiles[4] = new Tile();
		tiles[4].setVertical(true);
		tiles[4].setLength(2);
		tiles[4].setStartX(2);
		tiles[4].setStartY(2);
		tiles[5] = new Tile();
		tiles[5].setVertical(true);
		tiles[5].setLength(2);
		tiles[5].setStartX(2);
		tiles[5].setStartY(3);
		tiles[6] = new Tile();
		tiles[6].setVertical(true);
		tiles[6].setLength(2);
		tiles[6].setStartX(2);
		tiles[6].setStartY(5);
		tiles[7] = new Tile();
		tiles[7].setHorizontal(true);
		tiles[7].setLength(2);
		tiles[7].setStartX(3);
		tiles[7].setStartY(0);
		tiles[8] = new Tile();
		tiles[8].setVertical(true);
		tiles[8].setLength(2);
		tiles[8].setStartX(4);
		tiles[8].setStartY(3);
		tiles[9] = new Tile();
		tiles[9].setHorizontal(true);
		tiles[9].setLength(2);
		tiles[9].setStartX(4);
		tiles[9].setStartY(4);
		tiles[10] = new Tile();
		tiles[10].setHorizontal(true);
		tiles[10].setLength(2);
		tiles[10].setStartX(5);
		tiles[10].setStartY(1);
		
		////////////////////////////////////////////////////////////////////////
		State startState = new State(tiles.length);
		startState.createStartState(tiles);
		
		Stack stack = new Stack();
		stack.push(startState);
		
		LinkedList listStateVisited = new LinkedList(); // This list saves visited states
		LinkedList listResult = new LinkedList();
		CHashMap hm = new CHashMap();
		int numberMove = 0;
		
		while (!stack.empty()) {
			State currentState = new State((State)stack.pop());		
			listStateVisited.addFirst(currentState);
	
			if (currentState.checkGoal()) {
				System.out.println("This is solution:");
				listResult.addFirst(currentState);
				State stateGold = (State)hm.get(currentState);
			
				while (stateGold != null) {
					listResult.addFirst(stateGold);
					stateGold = hm.get(stateGold);
				}
				for (int i = 0; i < listResult.size(); i++) {
					numberMove++;
					System.out.println("---------step:" + numberMove + "----------");
					((State)listResult.get(i)).printFactState();
				}
				System.out.println("Solution found!");
				break;
			}
			for (int i = 0; i < currentState.getNumTiles(); i++) {
				// Move left tile i
				State stateLeft = new State(currentState);
				if (stateLeft.moveLeft(i) && !stateLeft.checkVisitedState(listStateVisited)) {
					hm.put(stateLeft, currentState);
					stack.push(stateLeft);
				}
			
				// Move right tile i
				State stateRight = new State(currentState);
				if (stateRight.moveRight(i) && !stateRight.checkVisitedState(listStateVisited)) {
					hm.put(stateRight, currentState);
					stack.push(stateRight);
				}
			
				// Move up tile i
				State stateUp = new State(currentState);
				if (stateUp.moveUp(i) && !stateUp.checkVisitedState(listStateVisited)) {
					hm.put(stateUp, currentState);
					stack.push(stateUp);
				}
				
				// Move down tile i
				State stateDown = new State(currentState);
				if (stateDown.moveDown(i) && !stateDown.checkVisitedState(listStateVisited)) {
					hm.put(stateDown, currentState);
					stack.push(stateDown);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		System.out.println("It takes " + totalTime + " s");
		
		// Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		double memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory is bytes: " + memory + " bytes");
		System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory) + " MB");
	}
}
