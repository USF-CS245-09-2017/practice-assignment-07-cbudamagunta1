public class Hashtable {

///////////////////////////////////////////////////////////////////////
	
	//HASHNODE
	public class HashNode {

		private String key;
		private String value;
		private HashNode next;
		
		//CONSTRUCTOR
		public HashNode(String inputKey, String inputValue){
			this.key = inputKey;
			this.value = inputValue;
			this.next = null;
		}
	}
	
///////////////////////////////////////////////////////////////////////	
	
	private HashNode[] arr;
	private int capacity;
	private int size;
	
///////////////////////////////////////////////////////////////////////
	
	//CONSTRUCTOR
	public Hashtable() {
		arr = new HashNode[314527];
		capacity = 314527;
		size = 0;
	}
	
///////////////////////////////////////////////////////////////////////

	//CHECK IF KEY IS IN THE HASHTABLE
	public boolean containsKey(String key) {
		
		int pos = ((Math.abs(key.hashCode()))%(capacity));
		HashNode current = arr[pos];	
		
		if(current == null) {
			return false;
		}else {	
			while(current!=null) {
				if(current.key.equals(key)) {
					return true;
				}
				current = current.next;
			}
		}
		return false;
	}

///////////////////////////////////////////////////////////////////////

	//GET THE VALUE OF A GIVEN KEY
	public String get(String key) {
		
		//If key is not in the hashtable
		if(!containsKey(key)) {
			return null;
		}
		
		int pos = ((Math.abs(key.hashCode()))%(capacity));
		HashNode current = arr[pos];
		
		while(current!=null) {
			if(current.key.equals(key)) {
				return current.value;
			}
			current = current.next;
		}
		return null;
	}

///////////////////////////////////////////////////////////////////////

	//ADD A KEY/VALUE PAIR
	public void put(String key, String value) {
		
		int pos = ((Math.abs(key.hashCode()))%(capacity));	
		HashNode head = arr[pos];
		
		//If key is not in the hashtable, add it
		if(!containsKey(key)) {
			HashNode current = new HashNode(key, value);
			current.next = head;
			arr[pos] = current;
			size++;
		//If the key already exists, update the value
		}else {
			while(head!=null) {
				if(head.key.equals(key)) {
					head.value = value;
					break;
				}
				head = head.next;
			}	
		}
		
		//GROW THE ARRAY IF LOAD FACTOR EXCEEDS BOUNDS
		if(size/capacity >= 0.7){
			growArray();
		}
	}

///////////////////////////////////////////////////////////////////////

	//REMOVE A KEY/VALUE PAIR FROM THE HASHTABLE
	public String remove(String key) throws Exception{

		//If key is not in the hashtable 
		if(!containsKey(key)) {
			throw new Exception();
		}
		
		int pos = ((Math.abs(key.hashCode()))%(capacity));
		HashNode current = arr[pos];
		
		if(current.key.equals(key)) {
			arr[pos] = current.next;
			size--;
			return current.value;
		}
		
		HashNode prev = current;
		current = current.next;
		
		while(current!= null) {
			if(current.key.equals(key)) {
				prev.next = current.next;
				size--;
				return current.value;
			}
			prev = current;
			current = current.next;
		}
		return null;
	}

///////////////////////////////////////////////////////////////////////

	//GROW THE ARRAY
	private void growArray() {
		
		capacity = (arr.length) * 2;
		HashNode[] newArr = new HashNode[capacity];		
		
		//NEED TO REHASH EVERYTHING FROM OLD ARRAY TO NEW ARRAY 
		for(int i=0; i<capacity; i++) {
			if(arr[i]!=null) {
				
				HashNode current = arr[i];
				
				while(current!=null) {
					int pos = (Math.abs((current.key.hashCode()))%(capacity));
					current.next = newArr[i];
					newArr[i] = current;
					current = current.next;
				}
			}
		}
		arr = newArr;
	}

///////////////////////////////////////////////////////////////////////

}
