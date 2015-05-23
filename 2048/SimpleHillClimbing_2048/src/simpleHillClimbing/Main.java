package simpleHillClimbing;
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
	 * 
	 * @param aNextState Is a next state
	 * @param aState Is a current state
	 * @param aHashMap Is a hash map
	 * @param aStack Is a stack
	 */
	public static void createNextState(State aNextState, State aState, Stack aStack) {
		for (int i = 0; i < 16; i++) { // create a blank with number is 4
			if (aNextState.getTile(i).getValue() == 0) {
				State theTmpState = new State(aNextState);
				theTmpState.setTile(i, 4);
				aStack.push(theTmpState);
				
				return;
			}
		}
		for (int i = 0; i < 16; i++) { // create a blank with number is 2
			if (aNextState.getTile(i).getValue() == 0) {
				State theTmpState = new State(aNextState);
				theTmpState.setTile(i, 2);
				aStack.push(theTmpState);
				
				return;
			}
		}
	}

	/**
	 * Compute core of new state
	 * 
	 * @param aCore Current core
	 * @param aDirectionMove Direction of movement
	 * @return Core of new state
	 */
	public static int coreOfNewState(int aCore, char aDirectionMove) {
		int aNewCore = aCore;
		if (aDirectionMove == 'R') aNewCore = aCore + 5;
		else if (aDirectionMove == 'U') aNewCore = aCore + 4;
		else if (aDirectionMove == 'D') aNewCore = aCore + 3;
		else aNewCore = aCore - 6;

		return aNewCore;
	}
	
	/**
	 * 
	 * @param aCore Core of old state
	 * @param aNewCore Core of new state
	 * @return true if core of new state lager than core of old state
	 */
	public static boolean functionHillClimbing(int aCore, int aNewCore) {
		float funcHillClimbing = aNewCore - aCore;
		if (funcHillClimbing > 0) return true;
		
		return false;
	}

	public static void main(String[] args) {
		System.out.println("Program is runing ... ");
		long startTime = System.currentTimeMillis();
		int core = 10, newCore;
		
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

		int numberMove = 0;
		while (!stack.empty()) {
			numberMove++;
			State currentState = new State((State)stack.pop());
			
			System.out.println("Value of this state: " + core);
			System.out.println("----------step:" + numberMove + "----------");
			currentState.printState();
			
			if (currentState.checkGoal(GOAL)) {
				System.out.println("Solution found!");
				break;
			}
			State stateUp = new State(currentState);
			stateUp.moveUp();
			newCore = coreOfNewState(core, 'U');
			// Check the state and the old state are same? if no same then the move can be execute
			if (!stateUp.checkVisited(currentState) && functionHillClimbing(core, newCore)) { 
				createNextState(stateUp, currentState, stack);
				core = newCore;
			}
			else {
				State stateRight = new State(currentState);
				stateRight.moveRight();
				newCore = coreOfNewState(core, 'R');
				// Check the state and the old state are same? if no same then the move can be execute
				if (!stateRight.checkVisited(currentState) && functionHillClimbing(core, newCore)) { 
					createNextState(stateRight, currentState, stack);
					core = newCore;
				}
				else {
					State stateDown = new State(currentState);
					stateDown.moveDown();
					newCore = coreOfNewState(core, 'D');
					// Check the state and the old state are same? if no same then the move can be execute
					if (!stateDown.checkVisited(currentState) && functionHillClimbing(core, newCore)) { 
						createNextState(stateDown, currentState, stack);
						core = newCore;
					}
					else {
						State stateLeft = new State(currentState);
						stateLeft.moveLeft();
						newCore = coreOfNewState(core, 'L');
						// Check the state and the old state are same? if no same then the move can be execute
						if (!stateLeft.checkVisited(currentState) && functionHillClimbing(core, newCore)) { 
							createNextState(stateLeft, currentState, stack);
							core = newCore;
						}
					}
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
