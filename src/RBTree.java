import java.awt.Color;

public class RBTree {
	private RBNode root; // all of these must be implemented while making, and changed while changing the tree.
	private RBNode min;
	private RBNode max;
	private RBNode[] tree_array; // an array of all the nods by order.
	private boolean array_status; // is the array above up to date.
	protected int size; // size of the tree
	private static final int INDENT_STEP = 4;
	private static final RBNode blank = new RBNode(null,null,null,"-1","-1",Color.BLACK);
	public static void main(String[] args) {
	}
	/**
	 * public RBTree()
	 * 
	 * a builder of the tree
	 * 
	 * sets all the fields of tree tree to 
	 * be empty at start
	 */
	public RBTree() {
		this.root = RBTree.blank;
		this.min = RBTree.blank;
		this.max = RBTree.blank;
		this.tree_array = null;
		this.array_status = false;
		this.size = 0;
	}
	/**
	 * private static void leftChild
	 * 
	 * sets child to be the parents left child
	 * 
	 * @param parent
	 * @param Child
	 */
	private void leftChild(RBNode parent, RBNode child) {
		parent.leftT = child;
		if (child != RBTree.blank) {
			child.parentT = parent;
		}
	}
	/**
	 * private static void leftChild
	 * 
	 * sets child to be the parents right child
	 * 
	 * @param parent
	 * @param Child
	 */
	private void rightChild(RBNode parent, RBNode child) {
		parent.rightT = child;
		if (child != RBTree.blank) {
			child.parentT = parent;
		}
	}
	/**
	 * private static void transplant(RBNode x, RBNode y)
	 * 
	 * Update y as a child of x parent instead of x
	 * @param x
	 * @param y
	 */
	private void transplant(RBNode x, RBNode y) {
		if(x.parentT.leftT == x) { // x is a left child
			leftChild(x.parentT,y); // Update y as a left child instead of x
		} else { // x is a right child
			rightChild(x.parentT,y); // Update y as a right child instead of x
		}
	}
	/**
	 * private static void replace(RBNode x, RBNode y)
	 * 
	 * Replace old node (x) with a new one (y)
	 * @param x
	 * @param y
	 */
	private void replace(RBNode x, RBNode y) {
		boolean father = false;
		RBNode origFather = null;
		if (y.parentT == x) {
			father = true;
		} else {
			origFather = y.parentT;
		}
		this.print();
		System.out.println("cccccc");
		if (x.parentT != RBTree.blank) {
			transplant(x,y);
		} else { // x was the root
			y.parentT = RBTree.blank;
			this.root = y;
		}

		this.print();
		System.out.println("bbbbbb");
		if (y != x.leftT) {
			leftChild(y,x.leftT);
		}
		this.print();
		System.out.println("kkkkkk");
		if ( (y != x.rightT)&&(x.rightT != RBTree.blank) ) {
			rightChild(y,x.rightT);
		}
		if (!father) { // Make x father forget he ever was a son
			if (origFather.leftT == y) {
				origFather.leftT = RBTree.blank;
			} else if (origFather.rightT == y) {
				origFather.rightT = RBTree.blank;
			}
		}
		this.print();
		System.out.println("aaaaaa");
	}
	/**
	 * Performs a left rotation on node x,
	 * according to the left rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void leftRotate(RBNode x) {
		RBNode y = x.rightT;
		if (x.parentT != RBTree.blank) {
			transplant(x,y);
		} else { // x was the root
			y.parentT = RBTree.blank;
			this.root = y;
		}
		if (y.leftT != RBTree.blank) {
			rightChild(x,y.leftT);
		} else {
			x.rightT = RBTree.blank;
		}
		leftChild(y,x);
	}
	/**
	 * Performs a right rotation on node x,
	 * according to the right rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void rightRotate(RBNode y) {
		RBNode x = y.leftT;
		if (y.parentT != RBTree.blank) {
			transplant(y,x);
		} else {
			x.parentT = RBTree.blank;
			this.root = y;
		}
		if (x.rightT != RBTree.blank) {
			leftChild(y,x.rightT);
		} else {
			y.leftT = RBTree.blank;
		}
		rightChild(x,y);
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
		/**
		 * public boolean isRed()
		 * 
		 * returns true if node is red 
		 * 
		 * @return
		 */
		public boolean isRed() { // returns true if is red. 
			 if (this.color == Color.RED) {
				 return true;
			 }
			 return false;
		}
		/**
		 * returns the left son of the node.
		 * 
		 * @return
		 */
		public RBNode getLeft() {
			return this.leftT;
		}
		/**
		 * returns the right son of the node.
		 * 
		 * @return
		 */
		public RBNode getRight() {
			return this.rightT;
		}
		public RBNode getParent() {
			return this.parentT;
		}
		/**
		 * returns the value of the node.
		 * 
		 * @return
		 */
		public int getValue() {
			return Integer.parseInt(this.info);
		}
		/**
		 * returns the key of the node.
		 * 
		 * @return
		 */
		public String getKey() {
			return this.key;
		}
		/**
		 * public void changeColor()
		 * 
		 * resets the color of the node from red 
		 * to black and vise versa.
		 * 
		 */
		public void changeColor() {
			if (this.color == Color.RED) {
				this.color = Color.BLACK;
			} else if (this.color == Color.BLACK) {
				this.color = Color.RED;
			}
		}
		/**
		 * public void darken()
		 * resets the color of the node from red to 
		 *  black and from black to dark gray
		 * (Similar to double black)
		 * 
		 */
		public void darken() {
			if (this.color == Color.RED) {
				this.color = Color.BLACK;
			} else if (this.color == Color.BLACK) {
				this.color = Color.DARK_GRAY;
			}
		}
		/**
		 * public boolean barren()
		 * 
		 * returns true if node has no children.
		 * returns false otherwise.
		 * 
		 * @return
		 */
		public boolean barren() {
			if ( (this.rightT == RBTree.blank)&&(this.leftT == RBTree.blank) ) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * public RBNode oneChild()
		 * 
		 * returns true if node has only one child
		 * 
		 * returns false otherwise
		 */
		public RBNode oneChild() {
			if ( (this.rightT == RBTree.blank)&&(this.leftT != RBTree.blank) ) {
				return this.leftT;
			} else if ( (this.rightT != RBTree.blank)&&(this.leftT == RBTree.blank) ) {
				return this.rightT;
			} else {
				return RBTree.blank;
			}
		}
		/**
		 * public boolean twoChilds()
		 * 
		 * returns true if node has two children
		 * returns false otherwise
		 * 
		 * @return
		 */
		public boolean twoChilds() {
			if ( (this.rightT != RBTree.blank)&&(this.leftT != RBTree.blank) ) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * returns true if node itself is a left 
		 * son of an other node.
		 * 
		 * 
		 * @return
		 */
		public boolean mILeftchild() {
			if (this == this.parentT.leftT) {
				return true;
			} else {
				return false;
			}
		}
	}
	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 */
	public RBNode getRoot() {
			return this.root;
	}
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 */
	public boolean empty() {
		if (this.root == RBTree.blank) {
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
		RBNode root = this.root;
		RBNode ansNode = RBTree.blank;
		RBNode node = binSearch(root,k,ansNode);
		if(node != RBTree.blank){
			return node.info;
		} else {
			return null;
		}
	}
	/**
	 * performs a binary search on the tree
	 * in attempt to find the node with k key.
	 * returns the node that has k as key.
	 * 
	 * @param root
	 * @param k
	 * @param ansNode
	 * @return
	 */
	public RBNode binSearch(RBNode root, int k,RBNode ansNode) { // an added recursive function
		if (root == RBTree.blank) {
			ansNode = RBTree.blank;
		} else if (Integer.parseInt(root.key) == k) {
			ansNode = root;
		} else if ( (Integer.parseInt(root.key) < k)&&(root.rightT != RBTree.blank) ) {
			root = root.rightT;
			ansNode = binSearch(root, k,ansNode);
		} else if ( (Integer.parseInt(root.key) > k)&&(root.leftT != RBTree.blank) ) {
			root = root.leftT;
			ansNode = binSearch(root, k,ansNode);
		} else {
			ansNode = RBTree.blank;
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
	 * 
	 * @param k
	 * @param v
	 * @return color changes count
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
		if ( (this.root != RBTree.blank)&&(search(k) != null) ) {
			return -1;
		} else {
			this.array_status = false;
			this.size++;
			RBNode newBaby = new RBNode(RBTree.blank, RBTree.blank, RBTree.blank, v, String.valueOf(k), Color.RED);
			if (this.root == RBTree.blank) { //First node at the tree
				this.root = newBaby;
				this.max = newBaby;
				this.min = newBaby;
				this.root.changeColor();
				return 0;
			} else {
				int changes = 0;
				RBNode father = whereToInsert(this.root, newBaby);
				if (Integer.parseInt(newBaby.key) < Integer.parseInt(father.key)) {
					leftChild(father, newBaby);
				} else {
					rightChild(father, newBaby);
				}
				//this.print();
				//System.out.println("llllll");
				if (father.isRed()) {
					changes = fixInsert(newBaby);
					//this.print();
					//System.out.println("mmmmmm");
				}
				upDate(newBaby);
				/*if (this.size > 6) {
					this.print();
					System.out.println("dddddd");
				}*/
				return changes;
			}
		}
	}
	/**
	 *  compares the new node that was inserted to 
	 *  the min ans max of the tree, ans resets it 
	 *  to be the new node if needed.
	 *  
	 * @param newBaby
	 */
	private void upDate(RBNode newBaby) {
		if (Integer.parseInt(newBaby.key) >= Integer.parseInt(this.max.key)) {
			this.max = newBaby;
		}
		if (Integer.parseInt(newBaby.key) <= Integer.parseInt(this.min.key)) {
			this.min = newBaby;
		}
	}
	/**
	 * performs a binary search for the correct spot to insert 
	 * the node.
	 * 
	 * @param root
	 * @param node
	 * @return new node father
	 */
	public RBNode whereToInsert(RBNode root ,RBNode node) {
		RBNode ans = root;
		if(Integer.parseInt(node.key) < Integer.parseInt(root.key)) {
			if(root.leftT != RBTree.blank) {
				ans = whereToInsert(root.leftT, node);
			}
		} else if(Integer.parseInt(node.key) > Integer.parseInt(root.key)) {
			if(root.rightT != RBTree.blank) {
				ans = whereToInsert(root.rightT, node);
			}
		}
		return ans;
	}
	/**
	 * Rebalance the tree after the insertion 
	 * of the node.
	 * 
	 * @param node
	 * @return color changes count
	 */
	public int fixInsert(RBNode node) {
		int count = 0;
		while ( (node.parentT != RBTree.blank)&&(node.parentT.isRed()) ) {
			//this.print();
			//System.out.println("wwwwww");
			if (node.parentT == node.parentT.parentT.leftT) {
				RBNode uncle = node.parentT.parentT.rightT;
				if ( (uncle == RBTree.blank)||(!uncle.isRed()) ) {
					if (node == node.parentT.rightT) { // Case 2
						//this.print();
						//System.out.println("nnnnnn");
						node = node.parentT; //			//	      Y
						leftRotate(node); //			//	    /   \
						//this.print();
						//System.out.println("qqqqqq");
					} // Case 3 //						//	   <X>  d
					node.parentT.parentT.color = Color.RED; //	  /   \
					node.parentT.color = Color.BLACK; //		//	 <Z>  c
					count += 2; //						//	/   \
					//node = node.parentT; //				//	a   b
					rightRotate(node.parentT.parentT);
					//this.print();
					//System.out.println("pppppp");
				} else { // Case 1						//	      Y
					//this.print();
					//System.out.println("oooooo");
					node.parentT.parentT.color = Color.RED; //	    /   \
					node.parentT.color = Color.BLACK; //		//	 <X>     <W>
					uncle.color = Color.BLACK; //				//	/   \   /   \
					count += 3; //						//	a  <Z>  d   e
					node = node.parentT.parentT; //		//	  /   \
				} //									//	  b   c
			} else {
				RBNode uncle = node.parentT.parentT.leftT;
				if ( (uncle == RBTree.blank)||(!uncle.isRed()) ) {
					if (node == node.parentT.leftT) { // Case 2
						//this.print();
						//System.out.println("rrrrrr");
						node = node.parentT; //			//	      Y
						rightRotate(node); //			//	    /   \
						//this.print();
						//System.out.println("ssssss");
					} // Case 3 //						//	   a   <X>
					node.parentT.parentT.color = Color.RED; //	      /   \
					node.parentT.color = Color.BLACK; //		//	     b   <Z>
					count += 2; //						//	        /   \
					//node = node.parentT; //				//	        c   d
					leftRotate(node.parentT.parentT);
					//this.print();
					//System.out.println("tttttt");
				} else { // Case 1						//	      Y
					//this.print();
					//System.out.println("uuuuuu");
					node.parentT.parentT.color = Color.RED; //	    /   \
					node.parentT.color = Color.BLACK; //		//	 <W>     <X>
					uncle.color = Color.BLACK; //				//	/   \   /   \
					count += 3; //						//	a   b   c  <Z>
					node = node.parentT.parentT; //		//	          /   \
					//this.print();
					//System.out.println("xxxxxx");
				} //									//	          d   e
			}
		}
		if (node.parentT != RBTree.blank) {
			//node.changeColor();
		} else { // node is the root
			this.root = node;
			this.root.color = Color.BLACK;
		}
		return count+1;
	}
	public int fixDeleteADT(RBNode node) {
		int count = 0;
		RBNode brtr;
		while ( (node!=this.root)&&(node.color == Color.BLACK) ) {
			if (node == node.parentT.leftT) {
				brtr = node.parentT.rightT; // Case 1
				if (brtr.color == Color.RED) {
					brtr.color = Color.BLACK;
					node.parentT.color = Color.RED;
					leftRotate(node.parentT);
					brtr = node.parentT.rightT;
				}
				if ( (brtr.leftT.color == Color.BLACK)&&(brtr.rightT.color == Color.BLACK) ) {
					brtr.color = Color.RED; // Case 2
					node = node.parentT;
				} else if (brtr.rightT.color == Color.BLACK) {
					if ( (brtr.leftT != RBTree.blank)&&(brtr.leftT.isRed()) ) {
						brtr.leftT.color = Color.BLACK; // Case 3
						brtr.color = Color.RED;
						rightRotate(brtr);
						brtr = node.parentT.rightT;
						brtr.color = node.parentT.color;
					}
					node.parentT.color = Color.BLACK; // Case 4
					brtr.rightT.color = Color.BLACK;
					leftRotate(node.parentT);
					node = this.root;
				}
			} else {
				brtr = node.parentT.leftT; // Case 1
				if (brtr.color == Color.RED) {
					brtr.color = Color.BLACK;
					node.parentT.color = Color.RED;
					leftRotate(node.parentT);
					brtr = node.parentT.leftT;
				}
				if ( (brtr.rightT.color == Color.BLACK)&&(brtr.leftT.color == Color.BLACK) ) {
					brtr.color = Color.RED; // Case 2
					node = node.parentT;
				} else if (brtr.leftT.color == Color.BLACK) {
					if ( (brtr.rightT != RBTree.blank)&&(brtr.rightT.isRed()) ) {
						brtr.rightT.color = Color.BLACK; // Case 3
						brtr.color = Color.RED;
						rightRotate(brtr);
						brtr = node.parentT.leftT;
						brtr.color = node.parentT.color;
					}
					node.parentT.color = Color.BLACK; // Case 4
					brtr.leftT.color = Color.BLACK;
					leftRotate(node.parentT);
					node = this.root;
				}
			}
		}
		node.color = Color.BLACK;
		return count;
	}
	/**
	 * Rebalance the tree after the deletion 
	 * of the node.
	 * 
	 * @param node
	 * @return color changes count
	 */
	public int fixDelete(RBNode node) { // TODO Delete me
		int count = 0;
		while ( (node.parentT != RBTree.blank)&&(node.color == Color.DARK_GRAY) ) {
			//this.print();
			//System.out.println("dddddd");
			if (node == node.parentT.leftT) {
				RBNode brtr = node.parentT.rightT;
				if (brtr == RBTree.blank) { // Node don't have brothers (Only child)
					node.color = Color.BLACK;
					node = node.parentT;
					node.darken();
					count += 2;
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.leftT != RBTree.blank)&&(brtr.leftT.isRed()) ) { // Case 3
						rightRotate(brtr); //			//	      ?Y?
						brtr.changeColor(); //			//	     /   \
						brtr.parentT.changeColor(); //	//	  |X|      W
						brtr = brtr.parentT; //			//	 /   \   /   \
						count += 2; //					//	?a? ?b? <c> ?d?
					}
					if ( (brtr.rightT != RBTree.blank)&&(brtr.rightT.isRed()) ) { // Case 4
						leftRotate(node.parentT);
						brtr.color = node.parentT.color; //				//	      ?Y?
						node.color = Color.BLACK; //					//	     /   \
						node.parentT.color = Color.BLACK; //			//	  |X|      W
						node.parentT.parentT.rightT.changeColor(); //	//	 /   \   /   \
						node = node.parentT.parentT; //					//	?a? ?b? ?c? <d>
						count += 4;
					} else { // Case 2
						node.color = Color.BLACK; //		//	      ?Y?
						node.parentT.darken(); //			//	     /   \
						brtr.changeColor(); //				//	  |X|      W 
						count += 3; //						//	 /   \   /   \
						node = node.parentT; //				//	?a? ?b?  c   d
						//this.print();
						//System.out.println("ffffff");
					}
				} else { // Case 1
					if (node.parentT.parentT == RBTree.blank) {
						this.root = node.parentT.rightT;
					} //									//	      Y
					leftRotate(node.parentT); //			//      /   \
					node.parentT.parentT.changeColor(); //	//   |X|     <W>
					node.parentT.changeColor(); //			//	/   \   /   \
					count += 2; //							//	a   b   c   d
				}
			} else {
				RBNode brtr = node.parentT.leftT;
				if (brtr == RBTree.blank) { // Node don't have brothers (Only child)
					node.color = Color.BLACK;
					node = node.parentT;
					node.darken();
					count += 2;
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.rightT != RBTree.blank)&&(brtr.rightT.isRed()) ) { // Case 3
						leftRotate(brtr); //			//	      ?Y?
						brtr.changeColor(); //			//	     /   \
						brtr.parentT.changeColor(); //	//	   X      |W|
						count += 2; //					//	 /   \   /   \
					} //								//	?a? <b> ?c? ?d?
					if ( (brtr.leftT != RBTree.blank)&&(brtr.leftT.isRed()) ) { // Case 4
						rightRotate(node.parentT);
						brtr.color = node.parentT.color; //				//	      ?Y?
						node.color = Color.BLACK; //					//	     /   \
						node.parentT.color = Color.BLACK; //			//	   X      |W|
						node.parentT.parentT.leftT.changeColor(); //	//	 /   \   /   \
						node = node.parentT.parentT; //					//	<a> ?b? ?c? ?d?
						count += 4;
					} else { // Case 2
						//this.print();
						//System.out.println("gggggg");
						node.color = Color.BLACK; //		//	      ?Y?
						node.parentT.darken(); //			//	     /   \
						brtr.changeColor(); //				//	   X      |W|
						count += 3; //						//	 /   \   /   \
						node = node.parentT; //				//	 a   b  ?c? ?d?
					}
				} else { // Case 1
					if (node.parentT.parentT == RBTree.blank) {
						this.root = node.parentT.leftT;
					} //									//	      Y
					rightRotate(node.parentT); //			//	    /   \
					node.parentT.parentT.changeColor(); //	//	 <X>     |W|
					node.parentT.changeColor(); //			//	/   \   /   \
					count += 2; //							//	a   b   c   d
				}
			}
		}
		if (node.parentT == RBTree.blank) { // node is the root
			this.root = node;
			this.root.color = Color.BLACK;
		}
		return count;
	}
	/**
	 * finds the successor of the node,
	 * and return it
	 *  
	 * @param node
	 * @return node successor
	 */
	public RBNode findSccr(RBNode node) {
		if (node.rightT != RBTree.blank) { // if node has a right sub-tree. 
			node = node.getRight();
			while(node.leftT != RBTree.blank) {// has a left Subtree.
				node = node.getLeft(); // TODO originally it was node.getRight() => Is this a bug?
			}
			return node;
		} else { //if node does not have a right sub-tree.
			if (node.parentT.getLeft() == node) {
				return node.parentT;	
			} else {
				while (node != node.parentT.getLeft()) {// node is a left child.
					node = node.parentT;
				}
			}
			return node;
		}
	}
	/**
	* public int delete(int k)
	*
	* deletes an item with key k from the binary tree, if it is there;
	* the tree must remain valid (keep its invariants).
	* returns the number of color switches, or 0 if no color switches were needed.
	* returns -1 if an item with key k was not found in the tree.
	* 
	* @param k
	* @return color changes count
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
		//this.print();
		//System.out.println("kkkkkk");
		RBNode centenarian = binSearch(this.root, k, RBTree.blank); // "A centenarian is a person who lives to or beyond the age of 100 years" (from Wikipedia)
		if(centenarian == RBTree.blank){ // No such key
			return -1;
		} else {
			this.array_status = false;
			this.size--;
			int changes = 0;
			RBNode child;
			if (centenarian.barren()) { // The centenarian don't have child's
				if (!centenarian.isRed()) { // i am leaf and I'm black
					centenarian.darken();
					this.print();
					System.out.println("hhhhhh");
					changes += fixDelete(centenarian);
				} // We can safely delete the centenarian
				if (centenarian.parentT != RBTree.blank) {
					//centenarian.darken();
					//changes += fixDelete(centenarian);
					//this.print();
					//System.out.println("jjjjjj");
					if (centenarian.mILeftchild()) {
						centenarian.parentT.leftT = RBTree.blank;
					} else {
						centenarian.parentT.rightT = RBTree.blank;
					}
				} else { // Delete the root
					this.root = RBTree.blank;
				}
			} else if ((child = centenarian.oneChild()) != RBTree.blank) { // The centenarian have only one child
				if (!centenarian.isRed()) { // We can safely bridge the centenarian
					child.darken();
					this.print();
					System.out.println("aaaaaa");
					changes += fixDelete(child);
					this.print();
					System.out.println("bbbbbb");
				}
				replace(centenarian,child); // This will make the centenarian to disappear because no one is looking at the poor guy
				if (centenarian.parentT == RBTree.blank) {
					this.root = child;
				}
			} else { // The centenarian have two children
				RBNode sccr = findSccr(centenarian);
				sccr.color = centenarian.color;
				//this.print();
				//System.out.println("cccccc");
				//changes += fixDelete(sccr);
				if ( (sccr.rightT != RBTree.blank)&&(centenarian != sccr.parentT) ) {
					sccr.rightT.color = sccr.color;
					sccr.parentT.leftT = sccr.rightT;
				} else if ( (sccr.rightT != RBTree.blank)&&(centenarian.leftT != RBTree.blank) ) {
					sccr.rightT.color = centenarian.leftT.color;
				}
				this.print();
				System.out.println("eeeeee");
				replace(centenarian,sccr); // This will make the centenarian to disappear because no one is looking at the poor guy
				if (centenarian.parentT == RBTree.blank) {
					this.root = sccr;
				}
			}
			upDateDel(centenarian);
			return changes;
		}
	}
	private void upDateDel(RBNode centenarian) {
		if (Integer.parseInt(centenarian.key) >= Integer.parseInt(this.max.key)) {
			this.max = getMax(this.root);
		}
		if (Integer.parseInt(centenarian.key) <= Integer.parseInt(this.min.key)) {
			this.min = getMin(this.root);
		}
		
	}
	/**
	 * recursive max finder.
	 * @param root2
	 * @return
	 */
	private RBNode getMax(RBNode root2) {
		if ( (root2 != RBTree.blank)&&(root2.rightT != RBTree.blank) ) {
			return getMax(root2.getRight());
		}
		return root2;
	}
	/**
	 * recursive min finder.
	 * @param root2
	 * @return
	 */
	private RBNode getMin(RBNode root2) {
		if ( (root2 != RBTree.blank)&&(root2.leftT != RBTree.blank) ) {
			return getMin(root2.getLeft());
		}
		return root2;
	}
	/**
	* public String min()
	*
	* Returns the value of the item with the smallest key in the tree,
	* or null if the tree is empty
	* 
	* @return minimum node at the tree
	*/
	public String min() {
		if (this.min != RBTree.blank){
			return (this.min).key; 
		} else {
			return null;
		}
	}
	/**
	* public String max()
	*
	* Returns the value of the item with the largest key in the tree,
	* or null if the tree is empty
	* 
	* @return maximum node at the tree
	*/
	public String max() {
		if (this.max != RBTree.blank){
			return (this.max).key; 
		} else {
			return null;
		}
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
		if(this.tree_array != null){
			int[] retArray = new int[this.tree_array.length];
			for (int i=0;i<this.tree_array.length;i++) {
				retArray[i] = Integer.parseInt(this.tree_array[i].key);
			}
			return retArray;
		} else {
			return new int[0];
		}
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
		if(this.tree_array != null){
			String[] retArray = new String[this.tree_array.length];
			for (int i=0;i<this.tree_array.length;i++) {
				retArray[i] = this.tree_array[i].info;
			}
			return retArray;
		} else {
			return new String[0];
		}
	}
	private RBNode[] updateArray(RBNode[] arr, RBNode root, int cnt) {
		if(root == RBTree.blank) {
			arr = null;
		} else {
			//arr[cnt] = root;
			if(root.leftT != RBTree.blank) {
				arr = updateArray(arr,root.leftT,cnt);
				cnt = findLast(arr,cnt+1);
			}
			arr[cnt] = root;
			cnt = findLast(arr,cnt+1);
			if(root.rightT != RBTree.blank) {
				arr = updateArray(arr,root.rightT,cnt);
			}
		}
		return arr;	
	}
	/**
	 * The function find the last element in array that isn't null
	 * @return
	 */
	private int findLast(RBNode[] arr, int lastCount) {
		for (int i=lastCount; i<arr.length; i++) {
			if (arr[i] == null) {
				return i;
			}
		}
		return -1;
	}
	/*if(root.rightT == null && root.leftT == null) {
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
	}*/
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
	* public void print()
	*
	* Print the tree.
	*/
	public void print() {
		RBTree.printHelper(this.root, 0, RBTree.blank);
	}
	/**
	* private static void printHelper(RBTree.RBNode n, int indent)
	*
	* Print a tree.
	*
	* @param n
	* @param indent
	*/
	private static void printHelper(RBTree.RBNode n, int indent, RBNode blank) {
		if (n == blank) {
	        System.out.print("<empty tree>");
	        return;
	    }
	    if (n.getRight() != blank) {
	        printHelper(n.getRight(), indent + INDENT_STEP, blank);
	    }
	    for (int i = 0; i < indent; i++)
	        System.out.print(" ");
	    if (n.isRed()) {
	    	System.out.println("<" + n.getKey() + ">");
	    } else if (n.color == Color.DARK_GRAY) {
	    	System.out.println("|" + n.getKey() + "|");
	    } else {
	    	System.out.println(n.getKey());
	    }
	    if (n.getLeft() != blank) {
	        printHelper(n.getLeft(), indent + INDENT_STEP, blank);
	    }
	}
	public void printlist() {
		String l, p, r;
		if (!this.array_status) {
			RBNode[] arr = new RBNode[this.size]; // new array of nodes
			this.tree_array = updateArray(arr, this.root, 0);
			this.array_status = true;
		}
		for (RBNode node : this.tree_array) {
			if (node.parentT != RBTree.blank) {p=node.parentT.key;} else {p="null";}
			if (node.leftT != RBTree.blank) {l=node.leftT.key;} else {l="null";}
			if (node.rightT != RBTree.blank) {r=node.rightT.key;} else {r="null";}
			System.out.println("Node " + node.key + "\t parent is " + p + ",\t Left child is: " + l + ",\t Right child is: " + r);
		}
	}
}

