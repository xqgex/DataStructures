import java.util.Arrays;

public class RBTree {
	private RBNode root; // all of these must be implemented while making, and changed while changing the tree.
	private RBNode min;
	private RBNode max;
	protected int size;

	public static void main(String[] args) {
		int a = 0;
	}

	/**
	* public class RBNode
	*/
	public static class RBNode {
		private RBNode leftT;
		private RBNode parentT;
		private RBNode rightT;
		protected String info;
		private String key;
		private String color;

		public class color{
			private color red;
			private color balck;
			private color yello;
		}
		public RBNode(RBNode leftT, RBNode parentT, RBNode rightT, String info, String key, String color){
			this.leftT = leftT;
			this.parentT = parentT;
			this.rightT = rightT;
			this.info = info;
			this.key = key;
			this.color = color;
	}

			public boolean isRed() {
			 boolean ans = false;
			 if (this.color == "red"){
				 ans = true;
				 }
			 return ans;
			 }
		public RBNode getLeft() {
			return this.leftT;}
		public RBNode getRight() {
			return this.rightT;}
		public int getValue() { // does this means the key or the value?
			return Integer.parseInt(this.info);}
	}
/*
##############################################################
#####	Those function are from the .ppt presentation	######
##############################################################
*/
	private static void leftChild(RBNode x, RBNode y) {
		x.leftT = y;
		y.parentT = x;
	}

	private static void rightChild(RBNode x, RBNode y) {
		x.rightT = y;
		y.parentT = x;
	}

	private static void transplant(RBNode x, RBNode y) {
		if(x.parentT.leftT == x) {
			leftChild(x.parentT,y);
		} else {
			rightChild(x.parentT,y);
		}
	}

	private static void replace(RBNode x, RBNode y) {
		transplant(x,y);
		leftChild(y,x.leftT);
		rightChild(y,x.rightT);
	}

	private static void leftRotate(RBNode x) {
		RBNode y = x.rightT;
		transplant(x,y);
		leftChild(x,y.leftT);
		rightChild(y,x);
	}

 	/**
	* public RBNode getRoot()
	*
	* returns the root of the red black tree
	*
	*/
	public RBNode getRoot() {
			return this.root;
		}

	/**
	* public boolean empty()
	*
	* returns true if and only if the tree is empty
	*
	*/
	public boolean empty() {
		Boolean ans = false;
		if (this.root == null){
			ans = true;
		}
		return ans; 
	}

 	/**
	* public String search(int k)
	*
	* returns the value of an item with key k if it exists in the tree
	* otherwise, returns null
	*/
	public String search(int k) { // envelop function
		String ans = null;
		RBNode root = this.root;
		RBNode ansNode = null;
		RBNode node = binSearch(root,k,ansNode);
		if(node != null){
		ans = node.info;
		}
		return ans;	
	}
	public RBNode binSearch(RBNode root, int k,RBNode ansNode){ // an added recursive function
		if (Integer.parseInt(root.key) == k){
			ansNode = root;
		}
		else if(Integer.parseInt(root.key) < k && root.leftT != null){
			root = root.leftT;
			binSearch(root, k,ansNode);
		}
		else if(Integer.parseInt(root.key) > k && root.rightT != null){
			root = root.rightT;
			binSearch(root, k,ansNode);
		}
		else{
			ansNode = null;
		}
		return ansNode;
	}

	/**
	* public int insert(int k, String v)
	*
	* inserts an item with key k and value v to the red black tree.
	* the tree must remain valid (keep its invariants).
	* returns the number of color switches, or 0 if no color switches were necessary.
	* returns -1 if an item with key k already exists in the tree.
	*/
	public int insert(int k, String v) {
		return 42;	// to be replaced by student code
	}

	/**
	* public int delete(int k)
	*
	* deletes an item with key k from the binary tree, if it is there;
	* the tree must remain valid (keep its invariants).
	* returns the number of color switches, or 0 if no color switches were needed.
	* returns -1 if an item with key k was not found in the tree.
	*/
	public int delete(int k) {
		return 42;	// to be replaced by student code
	}

	/**
	* public String min()
	*
	* Returns the value of the item with the smallest key in the tree,
	* or null if the tree is empty
	*/
	public String min() {
		return (this.min).key; // do i need to return key or info? 
	}

	/**
	* public String max()
	*
	* Returns the value of the item with the largest key in the tree,
	* or null if the tree is empty
	*/
	public String max() {
		return (this.max).key; // do i need to return key or info? 
	}

	/**
	* public int[] keysToArray()
	*
	* Returns a sorted array which contains all keys in the tree,
	* or an empty array if the tree is empty.
	*/
	public int[] keysToArray() { // envelope function
		int[] arr = new int[this.size]; 
		int cnt = 0;
		makeArrkey(arr,this.root, cnt);
		Arrays.sort(arr); // in case were arr is empty will it work?
		return arr;						
	}
	// this can also by made by calling the subfunction of valuesToArray(), and then sorted by key.
	private int[] makeArrkey(int[] arr, RBNode root,int cnt) { // recursive subfunction
		if(root.rightT == null && root.leftT == null){ // 
			return null; // is this a good stopping action?
		}
		else if(root.rightT == null){
			arr[cnt] = Integer.parseInt(root.key); // info or key?
			cnt++;
			root = root.leftT;
			makeArrkey(arr,root,cnt);
		}
		else if(root.leftT == null){
			arr[cnt] = Integer.parseInt(root.key); // info or key?
			cnt++;
			root = root.rightT;
			makeArrkey(arr,root,cnt);
		}
		else{
			arr[cnt] = Integer.parseInt(root.key); // info or key?
			cnt++;
			makeArrkey(arr,root.leftT,cnt);
			makeArrkey(arr,root.rightT,cnt);
		}
		return arr;
	}

	/**
	* public String[] valuesToArray()
	*
	* Returns an array which contains all values in the tree,
	* sorted by their respective keys,
	* or an empty array if the tree is empty.
	*/
	public String[] valuesToArray() {
		RBNode[] arr = new RBNode[this.size]; // new array of nodes
		int cnt = 0;
		makeArr(arr,this.root, cnt);//creates an array of all the nodes
		return null;
//		return arr; // now we need to sort this array of nodes by their keys, and return a string[] of values. 					
	}

	private RBNode[] makeArr(RBNode[] arr, RBNode root,int cnt) {
		if(root.rightT == null && root.leftT == null){ // 
			return null; // is this a good stopping action?
		}
		else if(root.rightT == null){
			arr[cnt] = root; // info or key?
			cnt++;
			root = root.leftT;
			makeArr(arr,root,cnt);
		}
		else if(root.leftT == null){
			arr[cnt] = root; // info or key?
			cnt++;
			root = root.rightT;
			makeArr(arr,root,cnt);
		}
		else{
			arr[cnt] = root; // info or key?
			cnt++;
			makeArr(arr,root.leftT,cnt);
			makeArr(arr,root.rightT,cnt);
		}
		return arr;
	}

	/**
	* public int size()
	*
	* Returns the number of nodes in the tree.
	*
	* precondition: none
	* postcondition: none
	*/
	public int size() {
		return this.size; // to be replaced by student code
	}

 	/**
	* If you wish to implement classes, other than RBTree and RBNode, do it in this file, not in 
	* another file.
	*/
}

