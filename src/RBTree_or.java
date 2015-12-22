


/*
 * Netanel Yosephian, ID: 304979214, USERNAME: yosephian
 * Ori Licht, ID: 201143179, USERNAME: oril
 */

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree_or {
	RBNode root;
	int size;
	RBNode minNode;
	RBNode maxNode;
	private final RBNode NIL = new RBNode(-1, null, "black", null, null); //NIL node

	/**
	 * Constructs an empty tree with NIL as root, min and max
	 */
	public RBTree_or() {
		root = NIL;
		maxNode = NIL;
		minNode = NIL;
	}

	/**
	 * public class RBNode
	 */
	public class RBNode {
		

		
		private RBNode(){}
		
		/**
		 * 
		 * RBNode constructor that creates a RED node with key and value
		 */
		private RBNode(int key, String value) {
			this.key = key;
			this.value = value;
			this.color = "red";
		}
		
		/**
		 * 
		 * @param key
		 * @param value
		 * @param color
		 * RBNode constructor that creates a node in Color color with key and value
		 */
		private RBNode(int key, String value, String color) {
			this.key = key;
			this.value = value;
			this.color = color;
		}
		
		/**
		 * 
		 * @param k
		 * @param v
		 * @param c
		 * @param l
		 * @param r
		 * RBNode constructor that creates a node with key k, value v, color c,
		 * left child l, right child r
		 */
		private RBNode(int k, String v, String c, RBNode l, RBNode r){
			key = k;
			value = v;
			color = c;
			left = l;
			right = r;
		}
			
		String color;
		int key;
		String value;
		RBNode left = NIL;
		RBNode right = NIL;
		RBNode parent = NIL;
				

		/**
		 * 
		 * @return true if node is internal leaf
		 * 
		 */
		public boolean isLeaf() {
			return left == NIL && right == NIL;
		}

		/**
		 * 
		 * @return color of node
		 */
		public String getColor() {
			return color;
		}

		/**
		 * 
		 * @return true if node is RED
		 */
		boolean isRed() {
			return color.equals("red");
		}
		
		/**
		 * 
		 * @return true if node is BLACK
		 */
		boolean isBlack(){
			return !isRed();
		}

		/**
		 * 
		 * @return node.left
		 */
		RBNode getLeft() {
			return left;
		}

		/**
		 * 
		 * @return node.right
		 */
		RBNode getRight() {
			return right;
		}

		/**
		 * 
		 * @return key of node
		 */
		int getKey() {
			return this.key;
		}
	}

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot() {
		RBNode result = root;
		if (result == NIL)
			result = null;
		return result;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return size == 0;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	
	public String search(int k){
		RBNode found = this.searchNode(k); //searching the node with key k
		if (found == null)
			return null;
		return found.value;
	}
	
	/**
	 * 
	 * @param k
	 * returns the node with key k, or null if not in tree
	 */
	private RBNode searchNode(int k) {
		RBNode tmpNode = root;
		int found = -1; //indicator
		while (found == -1 && tmpNode != NIL) { //binary tree search
			if (tmpNode.key == k)
				found = 1;
			else if (tmpNode.key < k)
				tmpNode = tmpNode.right;
			else
				tmpNode = tmpNode.left;
		}
		if (found == -1) //node NOT FOUND
			return null;
		return tmpNode;
	}


	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		RBNode z = new RBNode(k, v, "red"); //Inserting z
		if (k > maxNode.key)
			maxNode = z;
		if (k < minNode.key)
			minNode = z;
		RBNode x = root;
		int changes = 0; //Color changes
		if (empty()) { //If tree is empty - insert as root
			z.parent = NIL;
			root = z;
			z.color = "black";
			changes = 1;
			minNode = z;
			maxNode = z;
			size++;
		} else { // Binary tree insert
			RBNode y = NIL;
			while (x != NIL) { //Finding the spot to enter
				y = x;
				if (k < x.key) //Should be entered in left subtree
					x = x.left;
				else if (k > x.key) //Should be entered in right subtree
					x = x.right;
				else //If node with same key exists in tree
					return -1;
			}
			z.parent = y;
			if (z.key < y.key) //Linking z to parent y
				y.left = z;
			else
				y.right = z;
			size++;
			changes += fixInsert(z);
		}
		return changes;
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) {
		int changes = 0; //Color changes
		RBNode toDelete = this.searchNode(k); //Finding the node we want to delete
		if (toDelete == null) //Key k not in tree
			return -1;
		if (size == 1){ //Remove the root
			this.root = NIL;
			maxNode = NIL;
			minNode = NIL;
			this.size = 0;
			return changes;
		}
		RBNode x, y;
		if (toDelete.left == NIL || toDelete.right == NIL) //If toDelete has at most
															//one child
			y = toDelete; //We now want to delete y
		else
			y = this.getsuccessor(toDelete); //We want to delete toDelete's successor
		if (y.left != NIL)
			x = y.left;
		else
			x = y.right;
		x.parent = y.parent;
		if (y.parent == NIL){ //Deleting the root
			if (x.isRed()){
				x.color = "black";
				changes++;
			}
			this.root = x; //x is the new root
		}
		else if (y == y.parent.left) //Cutting links
			y.parent.left = x;
		else
			y.parent.right = x;
		if (y != toDelete){ //If we took successor
			toDelete.key = y.key; //Copying successor's key to his new spot
			toDelete.value = y.value; //Copying successor's value to his new spot
		}
		if (y.isBlack()){ //If we deleted a BLACK node
			changes += this.fixDelete(x); //Fixing the tree
		}
		size--; //Updating tree size
		if (minNode == toDelete)
			minNode = minNode();
		if (maxNode == toDelete)
			maxNode = maxNode();
		return changes;
	}
	
	/**
	 * 
	 * @param x
	 * if the node x we deleted was BLACK we overview all cases (learned in lecture)
	 * to maintain tree's invariants
	 * returns the number of color changes needed to fix the tree
	 */
	private int fixDelete(RBNode x) {
		RBNode w; //x's "uncle"
		int changes = 0; //Color changes
		while (x.parent != NIL && x.isBlack()) { //x is not root and RED
			if (x == x.parent.left) { //If x is left child
				w = x.parent.right;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = "black";
					if (x.parent.isBlack())
						changes++;
					x.parent.color = "red";
					this.leftRotate(x.parent);
					w = x.parent.right;
				}
				if (w.left.isBlack() && w.right.isBlack()) { // Case 2
					if (w.isBlack())
						changes++;
					w.color = "red";
					x = x.parent;
				} else {
					if (w.right.isBlack()) { // Case 3
						if (w.left.isRed())
							changes++;
						w.left.color = "black";
						if (w.isBlack())
							changes++;
						w.color = "red";
						this.rightRotate(w);
						w = x.parent.right;
					} // Case 4
					if (!w.color.equals(x.parent.color))
						changes++;
					w.color = x.parent.color;
					if (x.parent.isRed())
						changes++;
					x.parent.color = "black";
					if (w.right.isRed())
						changes++;
					w.right.color = "black";
					this.leftRotate(x.parent);
					x = this.root;
				}
			} else {
				w = x.parent.left;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = "black";
					if (x.parent.isBlack())
						changes++;
					x.parent.color = "red";
					this.rightRotate(x.parent);
					w = x.parent.left;
				}
				if (w.left.isBlack() && w.right.isBlack()) { // Case 2
					if (w.isBlack())
						changes++;
					w.color = "red";
					x = x.parent;
				} else {
					if (w.left.isBlack()) { // Case 3
						if (w.right.isRed())
							changes++;
						w.right.color = "black";
						if (w.isBlack())
							changes++;
						w.color = "red";
						this.leftRotate(w);
						w = x.parent.left;
					} // Case 4
					if (!w.color.equals(x.parent.color))
						changes++;
					w.color = x.parent.color;
					if (x.parent.isRed())
						changes++;
					x.parent.color = "black";
					if (w.left.isRed())
						changes++;
					w.left.color = "black";
					this.rightRotate(x.parent);
					x = this.root;
				}
			}
		}
		if (x.isRed()) //If x is root and RED we want to change
			changes++;
		x.color = "black";
		return changes;
	}
	
	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min(){
		if (minNode == NIL) //Tree is empty
			return null;
		return minNode.value;
	}
	
	/**
	 * 
	 * returns the node with the minimal key by iterating all the way to left
	 */
	private RBNode minNode() {
		if (empty())
			return NIL;
		RBNode tmpNode = root;
		while (tmpNode.left != NIL)
			tmpNode = tmpNode.left;
		return tmpNode; //Min node
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() {
		if (maxNode == NIL) //Tree is empty
			return null;
		return maxNode.value;
	}
	
	/**
	 * 
	 * returns the node with max key by iterating all the way to the right
	 */
	private RBNode maxNode(){
		if (empty())
			return NIL;
		RBNode maxNode = root;
		while (maxNode.right != NIL)
			maxNode = maxNode.right;
		return maxNode;
	}
	
	/**
	 * 
	 * @param node
	 * finding the successor in tree instance of RBNode node
	 */
	public RBNode getsuccessor(RBNode node) {
		RBNode successor = NIL;
		if (node != this.maxNode()){ //Has successor
			successor = node.right;
			if (successor != NIL){ //If node has right child -
				//then go right and all the way to the left
				while (successor.left != NIL)
					successor = successor.left;
			} else { //Go up to the left, and take the first right
				successor = node.parent;
				if (node == node.parent.right){
					while (successor == successor.parent.right)
						successor = successor.parent;
					if (successor != root)
						successor = successor.parent;
				}
			}
		}
		return successor; //Successor node
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		if (size == 0)
			return new int[0];
		RBNode[] arr = nodesToArray(minNode); //arr = array of all nodes in tree
		int[] keysArr = new int[size]; //To be array of keys
		for (int i = 0; i < arr.length; i++) {
				keysArr[i] = arr[i].key;
		}
		return keysArr;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		if (size == 0)
			return new String[0];
		RBNode[] arr = nodesToArray(minNode); //arr = array of all nodes in tree
		String[] valuesArr = new String[size]; //To be array of values
		for (int i = 0; i < arr.length; i++)
			valuesArr[i] = arr[i].value;
		return valuesArr;
	}

	/**
	 * 
	 * @param minNode
	 * @return the array of all nodes in tree in-order
	 */
	private RBNode[] nodesToArray(RBNode minNode){
		RBNode[] arr = new RBNode[size]; //To be array of all nodes
		arr[0] = minNode; //Inserting node with smallest key
		RBNode s = getsuccessor(minNode);
		for (int i=1; i<size; i++){ //(n = size) n calls for successor
			arr[i] = s;
			s = getsuccessor(s);
		}
		return arr;
	}
	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return size;
	}

	/**
	 * 
	 * @param x
	 * Returns the number of color changes needed to keep the tree's invariants
	 * by overviewing all cases (learned in lecture) 
	 */
	private int fixInsert(RBNode x) {
		int cntChanges = 0; //Color changes
		RBNode y = NIL; //To be x's uncle
		while (x.parent != NIL && x.parent.isRed()) { //x is not the root and child of RED node
			if (x.parent == x.parent.parent.left) { //x's father is left child
				y = x.parent.parent.right;
				if (y.isRed()){ //Uncle is RED, case 1
					cntChanges += fixColor(x, y);
					x = x.parent.parent; //Bubbling-up the colors problem
				}
				else { //Case 2
					if (x == x.parent.right) { //x is a right child
						x = x.parent; 
						leftRotate(x); //Fixing the tree with left rotation
					}
					//Case 3
					cntChanges += 2;
					x.parent.color = "black";
					x.parent.parent.color = "red";
											//Changing colors to maintain invariants
					rightRotate(x.parent.parent); //Fix using right rotation
				}
			} else { //x's father is right child
				y = x.parent.parent.left; //Still his uncle
				if (y.isRed()){ //Case 1, same as before
					cntChanges += fixColor(x, y);
					x = x.parent.parent; //Bubbling-up the problem
				}
				else{
					if (x == x.parent.left) { //Case 2
						x = x.parent;
						rightRotate(x);
					}
					//Case 3
					cntChanges += 2;
					x.parent.color = "black";
					x.parent.parent.color = "red";
					leftRotate(x.parent.parent);
				}
			}
		}
		if (root.isRed()) {
			root.color = "black";
			cntChanges++;
		}
		return cntChanges;
	}

	/**
	 * 
	 * @param x - the new node
	 * @param y - x's uncle
	 * Case 2 of fix insert, coloring y and his sibling (x's father) black and their
	 * father black, by that maintaining tree's invariants
	 * @return is the number of color changes in this case
	 */
	private int fixColor(RBNode x, RBNode y) {
		x.parent.color = "black";
		y.color = "black";
		x.parent.parent.color = "red";
		return 3;
	}
	
	/**
	 * 
	 * @param node
	 * The method changes the relations between RBNode node, his right child,
	 * RBNode y (soon be his father) and y's left child
	 */
	private void leftRotate(RBNode node){
		RBNode y = node.right; //node.right not null
		node.right = y.left; //Changing relations
		if (y.left != NIL)
			y.left.parent = node;
		y.parent = node.parent; //Linking y to node's father
		if (node.parent == NIL){ //Changing root
			root = y;
		}
		else if (node == node.parent.left)
			node.parent.left = y;
		else
			node.parent.right = y;
		//Completing links
		y.left = node;
		node.parent = y;
	}
	/**
	 * 
	 * @param node
	 * The method changes the relations between RBNode node, his left child,
	 * RBNode y (soon be his father) and y's right child
	 */
	private void rightRotate(RBNode node){
		//Comments exactly like in leftRotate
		RBNode y = node.left;//node.left not null
		node.left = y.right;
		if (y.right != NIL)
			y.right.parent = node;
		y.parent = node.parent;
		if (node.parent == NIL){
			root = y;
		}
		else if (node == node.parent.right)
			node.parent.right = y;
		else
			node.parent.left = y;
		y.right = node;
		node.parent = y;
	}
	
	

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */

}
