import java.awt.Color;

public class RBTree {
	// What happen when there is a duplicated key?
	private RBNode root; // all of these must be implemented while making, and changed while changing the tree.
	private RBNode min;
	private RBNode max;
	private RBNode[] tree_array;
	private boolean array_status;
	protected int size;
	private static final int INDENT_STEP = 4;

	public static void main(String[] args) {
	}
	
	/**
	* public class RBNode
	*/
	public static class RBNode { // is it alive?
		private RBNode leftT;
		private RBNode parentT;
		private RBNode rightT;
		protected String info;
		private String key;
		private Color color;

		public RBNode(RBNode leftT, RBNode parentT, RBNode rightT, String info, String key, Color color){
			this.leftT = leftT;
			this.parentT = parentT;
			this.rightT = rightT;
			this.info = info;
			this.key = key;
			this.color = color;
		}

		public boolean isRed() {
			 if (this.color == Color.RED) {
				 return true;
			 }
			 return false;
		}

		public RBNode getLeft() {
			return this.leftT;
		}

		public RBNode getRight() {
			return this.rightT;
		}

		public int getValue() {
			return Integer.parseInt(this.info);
		}

		public String getKey() {
			return this.key;
		}

		public void setParent(RBNode parentT) {
			this.parentT = parentT;
		}

		public void setLeft(RBNode node) {
			this.leftT = node;
		}

		public void setRight(RBNode node) {
			this.rightT = node;
		}
	}
	public RBTree() {
		this.root = null;
		this.min = null;
		this.max = null;
		this.tree_array = null;
		this.array_status = false;
		this.size = 0;
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
		if (this.root == null) {
			return true;
		}
		return false;
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

	public RBNode binSearch(RBNode root, int k,RBNode ansNode) { // an added recursive function
		if (Integer.parseInt(root.key) == k) {
			ansNode = root;
		} else if (Integer.parseInt(root.key) < k && root.leftT != null) {
			root = root.leftT;
			binSearch(root, k,ansNode);
		} else if (Integer.parseInt(root.key) > k && root.rightT != null) {
			root = root.rightT;
			binSearch(root, k,ansNode);
		} else {
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
		// Insert: Case 1a: z’s uncle w is red, z is a right child
		// Insert: Case 1b: z’s uncle w is red, z is a left child
		// Insert: Case 2:  z’s uncle w is black, z is a right child
		// Insert: Case 3:  z’s uncle w is black, z is a left child
		/* Abstract data type code:
		 * 	Insert as RED (always leaf)
		 * 	Too much RED is extra weight – must be reduced
		 * 		If imbalanced with brother – solve by re-balancing
		 * 		Else – push problem upwards
		 */
		RBNode newBaby = new RBNode(null, null, null, v, String.valueOf(k), Color.RED);
		if (root == null) {
			this.root = newBaby;
			return 0;
		} else {
			RBNode father = whereToInsert(this.root, newBaby);
			newBaby.setParent(father);
			if (Integer.parseInt(newBaby.key) < Integer.parseInt(father.key)) {
				father.setLeft(newBaby);
			} else {
				father.setRight(newBaby);
			}
			return 0;	// to be replaced by student code
		}
	}

	public RBNode whereToInsert(RBNode root ,RBNode node) {
		RBNode ans = root;
		if(Integer.parseInt(node.key) < Integer.parseInt(root.key)) {
			if(root.leftT != null) {
				ans = whereToInsert(root.leftT, node);
			}
		} else {
			if(root.rightT != null) {
				ans = whereToInsert(root.rightT, node);
			}
		}
		return ans;
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
		// Delete: Case 1: x’s sibling w is red
		// Delete: Case 2: x’s sibling w is black, and both children of w are black
		// Delete: Case 3: x’s sibling w is black, w’s left child is red, and w’s right child is black
		// Delete: Case 4: x’s sibling w is black, and w’s right child is red
		/*
		 *	 If the node to be deleted has two children, we delete its successor from the tree and use it to replace the node to be deleted
		 *		Deleted node has at most one child!!!
		 */
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
		if (!this.array_status) {
			RBNode[] arr = new RBNode[this.size]; // new array of nodes
			this.tree_array = updateArray(arr, this.root, 0);
			this.array_status = true;
		}
		int[] retArray = new int[this.tree_array.length];
		for (int i=0;i<this.tree_array.length;i++) {
			retArray[i] = Integer.parseInt(this.tree_array[i].key);
		}
		return retArray;
	}

	/**
	* public String[] valuesToArray()
	*
	* Returns an array which contains all values in the tree,
	* sorted by their respective keys,
	* or an empty array if the tree is empty.
	*/
	public String[] valuesToArray() {
		if (!this.array_status) {
			RBNode[] arr = new RBNode[this.size]; // new array of nodes
			this.tree_array = updateArray(arr, this.root, 0);
			this.array_status = true;
		}
		String[] retArray = new String[this.tree_array.length];
		for (int i=0;i<this.tree_array.length;i++) {
			retArray[i] = this.tree_array[i].info;
		}
		return retArray;				
	}

	private RBNode[] updateArray(RBNode[] arr, RBNode root,int cnt) {
		if(root.rightT == null && root.leftT == null) {
			arr[cnt] = root; // info or key?
			cnt++;
			return null; // is this a good stopping action?
		} else if(root.rightT == null) {
			arr[cnt] = root; // info or key?
			cnt++;
			root = root.leftT;
			updateArray(arr,root,cnt);
		} else if(root.leftT == null) {
			arr[cnt] = root; // info or key?
			cnt++;
			root = root.rightT;
			updateArray(arr,root,cnt);
		} else {
			arr[cnt] = root; // info or key?
			cnt++;
			updateArray(arr,root.leftT,cnt);
			updateArray(arr,root.rightT,cnt);
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

	public void print() {
		RBTree.printHelper(this.root, 0);
	}

	// Print RedBlackTree
	private static void printHelper(RBTree.RBNode n, int indent) {
		if (n == null) {
	        System.out.print("<empty tree>");
	        return;
	    }
	    if (n.getRight() != null) {
	        printHelper(n.getRight(), indent + INDENT_STEP);
	    }
	    for (int i = 0; i < indent; i++)
	        System.out.print(" ");
	    if (n.isRed()) {
	    	System.out.println("<" + n.getKey() + ">");
	    } else {
	    	System.out.println(n.getKey());
	    }
	    if (n.getLeft() != null) {
	        printHelper(n.getLeft(), indent + INDENT_STEP);
	    }
	}
}

