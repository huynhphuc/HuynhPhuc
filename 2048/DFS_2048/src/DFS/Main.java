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
	
	/**
	 * Create a blank tile with number is 4 or 2
	 * 
	 * @param aNextState Is a next state
	 * @param aState Is a current state
	 * @param aHashMap Is a hash map
	 * @param aStack Is a stack
	 */
	public static void createNextState(State aNextState, State aState, CHashMap aHashMap, Stack aStack) {
		for (int i = 0; i < 16; i++) { // create a blank with number is 2
			if (aNextState.getTile(i).getValue() == 0) {
				State theTmpState = new State(aNextState);
				theTmpState.setTile(i, 2);
				aHashMap.put(theTmpState, aState);
				aStack.push(theTmpState);
			}
		}
		for (int i = 0; i < 16; i++) { // create a blank with number is 4
			if (aNextState.getTile(i).getValue() == 0) {
				State theTmpState = new State(aNextState);
				theTmpState.setTile(i, 4);
				aHashMap.put(theTmpState, aState);
				aStack.push(theTmpState);
			}
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Program is runing ... ");
		long startTime = System.currentTimeMillis();
		
		State startState = new State();
		int GOAL = 2048;
		////////////////////////////////////////////////////////////////////////////
		/** Input 1 **/
		/*
		startState.setTile(0, 4);
		startState.setTile(2, 4);
		
		GOAL = 8;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 2 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(2, 4);
		
		GOAL = 8;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 3 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(2, 2);
		
		GOAL = 8;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 4 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(2, 2);
		
		GOAL = 32;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 5 **/
		/*
		startState.setTile(0, 4);
		startState.setTile(5, 2);
		
		GOAL = 64;
		*/
		////////////////////////////////////////////////////////////////////////////	
		/** Input 6 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(9, 2);
		
		GOAL = 128;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 7 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(9, 2);
		
		GOAL = 256;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 8 **/
		
		startState.setTile(0, 2);
		startState.setTile(9, 2);
		
		GOAL = 512;
		
		////////////////////////////////////////////////////////////////////////////
		/** Input 9 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(9, 2);
		
		GOAL = 2048;
		*/
		////////////////////////////////////////////////////////////////////////////
		/** Input 10 **/
		/*
		startState.setTile(0, 2);
		startState.setTile(2, 2);
		
		GOAL = 4096;
		*/
		////////////////////////////////////////////////////////////////////////////
		
		Stack stack = new Stack();
		stack.push(startState);
		
		LinkedList listResult = new LinkedList();
		CHashMap hm = new CHashMap();
		int numberMove = 0;
		while (!stack.empty()) {
			State currentState = new State((State)stack.pop());
			if (currentState.checkGoal(GOAL)) {
				System.out.println("This is solution:");
				listResult.addFirst(currentState);
				State stateGoal = (State)hm.get(currentState);
				while (stateGoal != null) {
					listResult.addFirst(stateGoal);
					stateGoal = hm.get(stateGoal);
				}
				for (int i = 0; i < listResult.size(); i++) {
					numberMove++;
					System.out.println("---------step:" + numberMove + "----------");
					((State)listResult.get(i)).printState();
				}
				System.out.println("Solution found!");
				break;
			}
			State stateUp = new State(currentState);
			stateUp.moveUp();
			// Check the state and the father state are same? if no same then the move can be execute 
			if (!stateUp.checkVisited(currentState)) createNextState(stateUp, currentState, hm, stack);
			
			
			State stateDown = new State(currentState);
			stateDown.moveDown();
			// Check the state and the father state are same? if no same then the move can be execute 
			if (!stateDown.checkVisited(currentState)) createNextState(stateDown, currentState, hm, stack);
			
			
			State stateLeft = new State(currentState);
			stateLeft.moveLeft();
			// Check the state and the father state are same? if no same then the move can be execute 
			if (!stateLeft.checkVisited(currentState)) createNextState(stateLeft, currentState, hm, stack);
			
			
			State stateRight = new State(currentState);
			stateRight.moveRight();
			// Check the state and the father state are same? if no same then the move can be execute 
			if (!stateRight.checkVisited(currentState)) createNextState(stateRight, currentState, hm, stack);
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
