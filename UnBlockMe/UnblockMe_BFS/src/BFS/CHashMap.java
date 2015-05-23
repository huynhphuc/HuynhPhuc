package BFS;

/**
 * 
 * @author Huynh Tan Phuc
 *
 */
class HashEntry{
	State[] _value = new State[2];
	HashEntry _next;
}

/**
 * 
 * @author Huynh Tan Phuc
 *
 */
public class CHashMap {
	private int _count;
	private HashEntry _head;
	
	public CHashMap() {
		this._head=null;
		_count=0;
	}
	
	/**
	 * 
	 * @param aKey 		is a key 
	 * @param aValue	is a value of key
	 */
	public void put(State aKey, State aValue) {
		HashEntry he = new HashEntry();
		he._value[0] = aKey;
		he._value[1] = aValue;
		he._next = _head;
		_head = he;
		_count++;
	}
	
	/**
	 * 
	 * @param aKey 		is a key
	 * @return			the state with a key
	 */
	public State get(State aKey) {
		HashEntry he = this._head;
		for (int i = 0; i < this._count; i++) {
			for (int j = 0; j < 16; j++)
				if(he._value[0].checkVisited(aKey)) return he._value[1];
			
			he = he._next;
		}
		return null;
	}
}
