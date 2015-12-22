import java.awt.Color;

/* TODO
 * 1) Update color changes
 * 2) Rewrite all comments
 * 3) Calculate running time (amortized)
 * 4) Check tester for 2048 nodes
 * 5) Write doc file
*/

public class RBTree_or {
	private int size; // size of the tree
	private RBNode root;
	private RBNode min;
	private RBNode max;
	private RBNode[] tree_array; // An array of all the nods by order.
	private boolean array_status; // Is the array above up to date.
	private final RBNode blank = new RBNode(-1, null, Color.BLACK, null, null); // The NIL node
	private static final int INDENT_STEP = 4;
	public String darker = "darken";
	public String switcher = "switch";
	
	/**
	 * public RBTree()
	 * 
	 * a builder of the tree
	 * 
	 * sets all the fields of tree tree to 
	 * be empty at start
	 */
	public RBTree_or() {
		this.root = this.blank;
		this.min = this.blank;
		this.max = this.blank;
		this.tree_array = null;
		this.array_status = false;
	}

	/**
	 * performs a binary search on the tree
	 * in attempt to find the node with k key.
	 * returns the node that has k as key.
	 * 
	 * @param root
	 * @param k
	 * @return
	 */
	private RBNode binSearch(RBNode root, int k) { // an added recursive function
		if (root == this.blank) {
			return this.blank;
		} else if (root.key == k) {
			return root;
		} else if ( (root.key < k)&&(root.rightT != this.blank) ) {
			root = root.rightT;
			return binSearch(root, k);
		} else if ( (root.key > k)&&(root.leftT != this.blank) ) {
			root = root.leftT;
			return binSearch(root, k);
		} else {
			return this.blank;
		}
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
	private RBNode findSccr(RBNode node) {
		if (node.rightT != this.blank) { // if node has a right sub-tree. 
			node = node.getRight();
			while(node.leftT != this.blank) {// has a left Subtree.
				node = node.getLeft();
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
	 * 
	 * @param x
	 * if the node x we deleted was BLACK we overview all cases (learned in lecture)
	 * to maintain tree's invariants
	 * returns the number of color changes needed to fix the tree
	 */
	public int fixDelete(RBNode node) {
		int count = 0;
		while ( (node.parentT != this.blank)&&(node.color == Color.DARK_GRAY) ) {
			if (node == node.parentT.leftT) {
				RBNode brtr = node.parentT.rightT;
				if (brtr == this.blank) { // Node don't have brothers (Only child)
					count += node.changeColor(Color.black);
					node = node.parentT;
					count += node.changeColor("darken");
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.leftT != this.blank)&&(brtr.leftT.isRed()) ) { // Case 3
						rightRotate(brtr); //							//	      ?Y?
						count += brtr.changeColor("darken"); //			//	     /   \
						count += brtr.parentT.changeColor("switch"); //	//	  |X|      W
						brtr = brtr.parentT; //							//	 /   \   /   \
					} //												//	?a? ?b? <c> ?d?
					if ( (brtr.rightT != this.blank)&&(brtr.rightT.isRed()) ) { // Case 4
						leftRotate(node.parentT);
						count += brtr.changeColor(node.parentT); //						//	      ?Y?
						count += node.changeColor(Color.BLACK); //						//	     /   \
						count += node.parentT.changeColor(Color.BLACK); //				//	  |X|      W
						count += node.parentT.parentT.rightT.changeColor("switch"); //	//	 /   \   /   \
						node = node.parentT.parentT; //									//	?a? ?b? ?c? <d>
					} else { // Case 2
						count += node.changeColor(Color.BLACK); //		//	      ?Y?
						count += node.parentT.changeColor("darken"); //	//	     /   \
						count += brtr.changeColor("switch"); //			//	  |X|      W 
						count += 3; //									//	 /   \   /   \
						node = node.parentT; //							//	?a? ?b?  c   d
					}
				} else { // Case 1
					if (node.parentT.parentT == this.blank) { //				//	      Y
						this.root = node.parentT.rightT; //						//      /   \
					} //														//   |X|     <W>
					leftRotate(node.parentT); //								//	/   \   /   \
					count += node.parentT.parentT.changeColor("switch"); //		//	a   b   c   d
					count += node.parentT.changeColor("switch");
				}
			} else {
				RBNode brtr = node.parentT.leftT;
				if (brtr == this.blank) { // Node don't have brothers (Only child)
					count += node.changeColor(Color.BLACK);
					node = node.parentT;
					count += node.changeColor("darken");
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.rightT != this.blank)&&(brtr.rightT.isRed()) ) { // Case 3
						leftRotate(brtr); //							//	      ?Y?
						count += brtr.changeColor("switch"); //			//	     /   \
						count += brtr.parentT.changeColor("switch"); //	//	   X      |W|
						brtr = brtr.parentT;							//	 /   \   /   \
					} //												//	?a? <b> ?c? ?d?
					if ( (brtr.leftT != this.blank)&&(brtr.leftT.isRed()) ) { // Case 4
						rightRotate(node.parentT);
						count += brtr.changeColor(node.parentT.color); //				//	      ?Y?
						count += node.changeColor(Color.BLACK); //						//	     /   \
						count += node.parentT.changeColor(Color.BLACK); //				//	   X      |W|
						count += node.parentT.parentT.leftT.changeColor("switch"); //	//	 /   \   /   \
						node = node.parentT.parentT; //									//	<a> ?b? ?c? ?d?
						
					} else { // Case 2
						count += node.changeColor(Color.BLACK); //			//	      ?Y?
						count += node.parentT.changeColor("darken"); //		//	     /   \
						count += brtr.changeColor("switch"); //				//	   X      |W|
						 //													//	 /   \   /   \
						node = node.parentT; //								//	 a   b  ?c? ?d?
					}
				} else { // Case 1
					if (node.parentT.parentT == this.blank) { //			//	      Y
						this.root = node.parentT.leftT; //					//	    /   \
					} //													//	 <X>     |W|
					rightRotate(node.parentT); //							//	/   \   /   \
					count += node.parentT.parentT.changeColor("switch"); //	//	a   b   c   d
					count += node.parentT.changeColor("switch");
				}
			}
		}
		if (node.parentT == this.blank) { // node is the root
			this.root = node;
			count += this.root.changeColor(Color.BLACK);
		}
		return count;
	}
	/**
	 * 
	 * @param x
	 * Returns the number of color changes needed to keep the tree's invariants
	 * by overviewing all cases (learned in lecture) 
	 */
	public int fixInsert(RBNode node) {
		int count = 0;
		while ( (node.parentT != this.blank)&&(node.parentT.isRed()) ) {
			if (node.parentT == node.parentT.parentT.leftT) {
				RBNode uncle = node.parentT.parentT.rightT;
				if ( (uncle == this.blank)||(!uncle.isRed()) ) { //			//	      Y
					if (node == node.parentT.rightT) { // Case 2 //			//	    /   \
						node = node.parentT; //								//	   <X>  d
						leftRotate(node); //								//	  /   \
					} // Case 3 //											//	 <Z>  c
					count += node.parentT.parentT.changeColor(Color.RED); ////	/   \
					count += node.parentT.changeColor(Color.BLACK); //		//	a   b
					rightRotate(node.parentT.parentT);
				} else { // Case 1											//	      Y
					count += node.parentT.parentT.changeColor(Color.RED); ////	    /   \
					count += node.parentT.changeColor(Color.BLACK); //		//	 <X>     <W>
					count += uncle.changeColor(Color.BLACK); //				//	/   \   /   \
					node = node.parentT.parentT; //							//	a  <Z>  d   e
				} //														//	  /   \
			} else { //														//	  b   c
				RBNode uncle = node.parentT.parentT.leftT;
				if ( (uncle == this.blank)||(!uncle.isRed()) ) { //			//	      Y
					if (node == node.parentT.leftT) { // Case 2 //			//	    /   \
						node = node.parentT; //								//	   a   <X>
						rightRotate(node); //								//	      /   \
					} // Case 3 //											//	     b   <Z>
					count += node.parentT.parentT.changeColor(Color.RED); ////	        /   \
					count +=node.parentT.changeColor(Color.BLACK); //		//	        c   d
					leftRotate(node.parentT.parentT);
				} else { // Case 1 //										//	      Y
					count += node.parentT.parentT.changeColor(Color.RED); ////	    /   \
					count += node.parentT.changeColor(Color.BLACK); //		//	 <W>     <X>
					count += uncle.changeColor(Color.BLACK); //				//	/   \   /   \
					//count += 3; //										//	a   b   c  <Z>
					node = node.parentT.parentT; //							//	          /   \
				} //														//	          d   e
			}
		}
		if (node.parentT == this.blank) {
			this.root = node;
			count += this.root.changeColor(Color.BLACK);
		}
		return count;
	}
	/**
	 * recursive max finder.
	 * @param root2
	 * @return
	 */
	private RBNode getMax(RBNode root) {
		if ( (root != this.blank)&&(root.rightT != this.blank) ) {
			return getMax(root.getRight());
		}
		return root;
	}
	/**
	 * recursive min finder.
	 * @param root
	 * @return
	 */
	private RBNode getMin(RBNode root) {
		if ( (root != this.blank)&&(root.leftT != this.blank) ) {
			return getMin(root.getLeft());
		}
		return root;
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
		if (child != this.blank) {
			child.parentT = parent;
		}
	}
	/**
	 * Performs a left rotation on node x,
	 * according to the left rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void leftRotate(RBNode node){
		RBNode y = node.rightT;
		if (node.parentT != this.blank) {
			transplant(node,y);
		} else { // x was the root
			y.parentT = this.blank;
			this.root = y;
		}
		if (y.leftT != this.blank) {
			rightChild(node,y.leftT);
		} else {
			node.rightT = this.blank;
		}
		leftChild(y,node);
	}
	private static void printHelper(RBTree_or.RBNode n, int indent, RBNode blank) {
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
	/**
	 * private static void rightChild
	 * 
	 * sets child to be the parents right child
	 * 
	 * @param parent
	 * @param Child
	 */
	private void rightChild(RBNode parent, RBNode child) {
		parent.rightT = child;
		if (child != this.blank) {
			child.parentT = parent;
		}
	}
	/**
	 * Performs a right rotation on node x,
	 * according to the right rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void rightRotate(RBNode node){
		RBNode x = node.leftT;
		if (node.parentT != this.blank) {
			transplant(node,x);
		} else {
			x.parentT = this.blank;
			this.root = node;
		}
		if (x.rightT != this.blank) {
			leftChild(node,x.rightT);
		} else {
			node.leftT = this.blank;
		}
		rightChild(x,node);
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
	private RBNode[] updateArray(RBNode[] arr, RBNode root, int cnt) {
		if(root == this.blank) {
			arr = null;
		} else {
			if(root.leftT != this.blank) {
				arr = updateArray(arr,root.leftT,cnt);
				cnt = findLast(arr,cnt+1);
			}
			arr[cnt] = root;
			cnt = findLast(arr,cnt+1);
			if(root.rightT != this.blank) {
				arr = updateArray(arr,root.rightT,cnt);
			}
		}
		return arr;	
	}
	/**
	 *  compares the new node that was inserted/Deleted to 
	 *  the min and max of the tree, and resets it 
	 *  to be the new node if needed.
	 *  
	 *  type=0 : Insert
	 *  type=1 : Delete
	 *  
	 * @param newBaby
	 */
	private void updateMinMax(RBNode node, int type) {
		if (node.key >= this.max.key) {
			if (type == 0) {
				this.max = node;
			} else {
				this.max = getMax(this.root);
			}
		}
		if (node.key <= this.min.key) {
			if (type == 0) {
				this.min = node;
			} else {
				this.min = getMin(this.root);
			}
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
	private RBNode whereToInsert(RBNode root ,RBNode node) {
		RBNode ans = root;
		if(node.key < root.key) {
			if(root.leftT != this.blank) {
				ans = whereToInsert(root.leftT, node);
			}
		} else if(node.key > root.key) {
			if(root.rightT != this.blank) {
				ans = whereToInsert(root.rightT, node);
			}
		}
		return ans;
	}

	/**
	 * public class RBNode
	 */
	public class RBNode {
		Color color;
		int key;
		String value;
		RBNode leftT = blank;
		RBNode rightT = blank;
		RBNode parentT = blank;
		private RBNode(int key, String value, Color color, RBNode leftT, RBNode rightT){
			this.key = key;
			this.value = value;
			this.color = color;
			this.leftT = leftT;
			this.rightT = rightT;
		}
		/**
		 * 
		 * @return color of node
		 */
		public Color getColor() {
			return color;
		}
		/**
		 * 
		 * @return node.left
		 */
		RBNode getLeft() {
			return leftT;
		}
		/**
		 * 
		 * @return node.right
		 */
		RBNode getRight() {
			return rightT;
		}
		/**
		 * 
		 * @return key of node
		 */
		int getKey() {
			return this.key;
		}
		/**
		 * 
		 * @return true if node is RED
		 */
		boolean isRed() {
			return (this.color == Color.RED);
		}
		/**
		 * 
		 * @return true if node is BLACK
		 */
		boolean isBlack(){
			return (this.color == Color.BLACK);
		}
		/**
		 * public int changeColor(Color color)
		 * 
		 * resets the color of the node from red 
		 * to black and vise versa.
		 * 
		 */
		public int changeColor(Color color) {
			if (this.color == color) {
				return 0;
			} else  {
				this.color = color;
				return 1;
			}
		}
		public int changeColor(RBNode node) {
			if (this.color == node.color) {
				return 0;
			} else  {
				this.color = node.color;
				return 1;
			}	
		}
		/**
		 * public int changeColor("darken")
		 * resets the color of the node from red to 
		 *  black and from black to dark gray
		 * (Similar to double black)
		 * 
		 */
		public int changeColor(String string) {
			if(string.equals("darken")){
				if (this.color == Color.RED) {
					this.color = Color.BLACK;
				} else if (this.color == Color.BLACK) {
					this.color = Color.DARK_GRAY;
				} else {
					System.err.println("tried to draken the drakest. witch is impossible. unless?? mohaa mohaa haa haa ");
				}
				return 1;
			} else if(string.equals("switch")) { 
				if (this.color == Color.RED) {
					this.color = Color.BLACK;
				} else if (this.color == Color.BLACK) {
					this.color = Color.RED;
				}  else {
					System.err.println("tried to switch the drakest. witch is impossible. unless?? mohaa mohaa haa haa ");
				}
				return 1;
			}
			else { 
				System.err.println("not a coorect input");
				return 0;
			}
		}
		public boolean recognizeBlank(RBNode node) {
			if ( (node.parentT == node.leftT)&&(node.rightT == node.leftT) ) {
				return true;
			} else {
				return false;
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
			if ( (recognizeBlank(this.rightT))&&(recognizeBlank(this.leftT)) ) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * public RBNode oneChild()
		 * 
		 * returns the only child
		 */
		public RBNode oneChild() {
			if ( (recognizeBlank(this.rightT))&&(!recognizeBlank(this.leftT)) ) {
				return this.leftT;
			} else if ( (!recognizeBlank(this.rightT))&&(recognizeBlank(this.leftT)) ) {
				return this.rightT;
			} else {
				return this;
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
			if ( (!recognizeBlank(this.rightT))&&(!recognizeBlank(this.leftT)) ) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * returns true if node itself is a left 
		 * son of an other node.
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
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) {
		int count = 0;
		RBNode centenarian = binSearch(this.root, k); // "A centenarian is a person who lives to or beyond the age of 100 years" (from Wikipedia)
		if (centenarian == this.blank) { // No such key
			return -1;
		} else { // There is such key at the tree
			this.array_status = false;
			if (this.size == 1) { // The node was the only node at the tree
				this.root = this.blank;
				this.max = this.blank;
				this.min = this.blank;
				this.size = 0;
				return count;
			} else { // The node have family
				RBNode node;
				RBNode sccr;
				if (centenarian.leftT == this.blank || centenarian.rightT == this.blank) {
					sccr = centenarian;
				} else {
					sccr = this.findSccr(centenarian);
				}
				if (sccr.leftT != this.blank) {
					node = sccr.leftT;
				} else {
					node = sccr.rightT;
				}
				node.parentT = sccr.parentT;
				if (sccr.parentT == this.blank) {
					if (node.isRed()) {
						node.color = Color.BLACK;
						count++;
					}
					this.root = node;
				} else if (sccr == sccr.parentT.leftT) {
					sccr.parentT.leftT = node;
				} else {
					sccr.parentT.rightT = node;
				}
				if (sccr != centenarian) {
					centenarian.key = sccr.key;
					centenarian.value = sccr.value;
				}
				if (sccr.isBlack()) {
					count += this.fixDelete(node);
				}
				size--;
				updateMinMax(centenarian,1);
				return count;
			}
		}
	}
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 */
	public boolean empty() {
		return (this.root == this.blank);
	}
	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 */
	public RBNode getRoot() {
		if (this.root == this.blank) {
			return null;
		} else {
			return this.root;
		}
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
		int changes = 0;
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
		if ( (this.root != this.blank)&&(search(k) != null) ) {
			return -1;
		} else {
			this.array_status = false;
			this.size++;
			RBNode newBaby = new RBNode(k, v, Color.RED, this.blank, this.blank);
			if (this.root == this.blank) { //First node at the tree
				this.root = newBaby;
				this.max = newBaby;
				this.min = newBaby;
				changes += this.root.changeColor("switch");
				return changes;
			} else {
				RBNode father = whereToInsert(this.root, newBaby);
				if (newBaby.key < father.key) {
					leftChild(father, newBaby);
				} else {
					rightChild(father, newBaby);
				}
				if (father.isRed()) {
					changes += fixInsert(newBaby);
				}
				updateMinMax(newBaby, 0);
				return changes;
			}
		}
	}
	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		if (!this.array_status) {
			RBNode[] arr = new RBNode[this.size]; // new array of nodes
			this.tree_array = updateArray(arr, this.root, 0);
			this.array_status = true;
		}
		if (this.tree_array != null) {
			int[] retArray = new int[this.tree_array.length];
			for (int i=0;i<this.tree_array.length;i++) {
				retArray[i] = this.tree_array[i].key;
			}
			return retArray;
		} else {
			return new int[0];
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
		if (this.max != this.blank){
			return this.max.value; 
		} else {
			return null;
		}
	}
	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 * 
	 * @return minimum node at the tree
	 */
	public String min(){
		if (this.min != this.blank){
			return this.min.value; 
		} else {
			return null;
		}
	}
	/**
	 * public void print()
	 *
	 * Print the tree.
	 */
	public void print() {
		RBTree_or.printHelper(this.root, 0, this.blank);
	}
	public void printlist() {
		int l;
		int r;
		int p;
		if (!this.array_status) {
			RBNode[] arr = new RBNode[this.size]; // new array of nodes
			this.tree_array = updateArray(arr, this.root, 0);
			this.array_status = true;
		}
		for (RBNode node : this.tree_array) {
			if (node.parentT != this.blank) {p=node.parentT.key;} else {p=-1;}
			if (node.leftT != this.blank) {l=node.leftT.key;} else {l=-1;}
			if (node.rightT != this.blank) {r=node.rightT.key;} else {r=-1;}
			System.out.println("Node " + node.key + "\t parent is " + p + ",\t Left child is: " + l + ",\t Right child is: " + r);
		}
	}
	public String search(int k) { // envelop function
		RBNode node = binSearch(this.root,k);
		if(node != this.blank){
			return node.value;
		} else {
			return null;
		}
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
		return size;
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
				retArray[i] = this.tree_array[i].value;
			}
			return retArray;
		} else {
			return new String[0];
		}
	}
}
