import java.awt.Color;


public class RBTree_or {
	private int size; // size of the tree
	private RBNode root;
	private RBNode min;
	private RBNode max;
	private RBNode[] tree_array; // An array of all the nods by order.
	private boolean array_status; // Is the array above up to date.
	private final RBNode blank = new RBNode(-1, null, Color.BLACK, null, null); // The NIL node
	private static final int INDENT_STEP = 4;
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
	 * public class RBNode
	 */
	public class RBNode {
		Color color;
		int key;
		String value;
		RBNode leftT = blank;
		RBNode rightT = blank;
		RBNode parentT = blank;
		/**
		 * RBNode constructor that creates a node with key k, value v, color c,
		 * left child l, right child r
		 */
		private RBNode(int k, String v, Color c, RBNode l, RBNode r){
			key = k;
			value = v;
			color = c;
			leftT = l;
			rightT = r;
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
		 * @return true if node is RED
		 */
		boolean isRed() {
			return this.color == Color.RED;
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
		 * public void changeColor()
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
		 * public void darken()
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
		if (this.root == this.blank) {
			return null;
		} else {
			return this.root;
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
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		this.array_status = false;
		RBNode z = new RBNode(k, v, Color.RED, this.blank, this.blank); //Inserting z
		if (k > max.key)
			max = z;
		if (k < min.key)
			min = z;
		RBNode x = root;
		int changes = 0; //Color changes
		if (empty()) { //If tree is empty - insert as root
			z.parentT = blank;
			root = z;
			z.color = Color.BLACK;
			changes = 1;
			min = z;
			max = z;
			size++;
		} else { // Binary tree insert
			RBNode y = blank;
			while (x != blank) { //Finding the spot to enter
				y = x;
				if (k < x.key) //Should be entered in left subtree
					x = x.leftT;
				else if (k > x.key) //Should be entered in right subtree
					x = x.rightT;
				else //If node with same key exists in tree
					return -1;
			}
			z.parentT = y;
			if (z.key < y.key) //Linking z to parent y
				y.leftT = z;
			else
				y.rightT = z;
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
		RBNode toDelete = this.binSearch(root, k); //Finding the node we want to delete
		if (toDelete == blank) //Key k not in tree
			return -1;
		this.array_status = false;
		if (size == 1){ //Remove the root
			this.root = blank;
			max = blank;
			min = blank;
			this.size = 0;
			return changes;
		}
		RBNode x, y;
		if (toDelete.leftT == blank || toDelete.rightT == blank) //If toDelete has at most
															//one child
			y = toDelete; //We now want to delete y
		else
			y = this.findSccr(toDelete); //We want to delete toDelete's successor
		if (y.leftT != blank)
			x = y.leftT;
		else
			x = y.rightT;
		x.parentT = y.parentT;
		if (y.parentT == blank){ //Deleting the root
			if (x.isRed()){
				x.color = Color.BLACK;
				changes++;
			}
			this.root = x; //x is the new root
		}
		else if (y == y.parentT.leftT) //Cutting links
			y.parentT.leftT = x;
		else
			y.parentT.rightT = x;
		if (y != toDelete){ //If we took successor
			toDelete.key = y.key; //Copying successor's key to his new spot
			toDelete.value = y.value; //Copying successor's value to his new spot
		}
		if (y.isBlack()){ //If we deleted a BLACK node
			changes += this.fixDelete(x); //Fixing the tree
		}
		size--; //Updating tree size
		if (min == toDelete)
			min = minNode();
		if (max == toDelete)
			max = maxNode();
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
		while (x.parentT != blank && x.isBlack()) { //x is not root and RED
			if (x == x.parentT.leftT) { //If x is left child
				w = x.parentT.rightT;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = Color.BLACK;
					if (x.parentT.isBlack())
						changes++;
					x.parentT.color = Color.RED;
					this.leftRotate(x.parentT);
					w = x.parentT.rightT;
				}
				if (w.leftT.isBlack() && w.rightT.isBlack()) { // Case 2
					if (w.isBlack())
						changes++;
					w.color = Color.RED;
					x = x.parentT;
				} else {
					if (w.rightT.isBlack()) { // Case 3
						if (w.leftT.isRed())
							changes++;
						w.leftT.color = Color.BLACK;
						if (w.isBlack())
							changes++;
						w.color = Color.RED;
						this.rightRotate(w);
						w = x.parentT.rightT;
					} // Case 4
					if (!w.color.equals(x.parentT.color))
						changes++;
					w.color = x.parentT.color;
					if (x.parentT.isRed())
						changes++;
					x.parentT.color = Color.BLACK;
					if (w.rightT.isRed())
						changes++;
					w.rightT.color =Color.BLACK;
					this.leftRotate(x.parentT);
					x = this.root;
				}
			} else {
				w = x.parentT.leftT;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = Color.BLACK;
					if (x.parentT.isBlack())
						changes++;
					x.parentT.color = Color.RED;
					this.rightRotate(x.parentT);
					w = x.parentT.leftT;
				}
				if (w.leftT.isBlack() && w.rightT.isBlack()) { // Case 2
					if (w.isBlack())
						changes++;
					w.color = Color.RED;
					x = x.parentT;
				} else {
					if (w.leftT.isBlack()) { // Case 3
						if (w.rightT.isRed())
							changes++;
						w.rightT.color = Color.BLACK;
						if (w.isBlack())
							changes++;
						w.color = Color.RED;
						this.leftRotate(w);
						w = x.parentT.leftT;
					} // Case 4
					if (!w.color.equals(x.parentT.color))
						changes++;
					w.color = x.parentT.color;
					if (x.parentT.isRed())
						changes++;
					x.parentT.color = Color.BLACK;
					if (w.leftT.isRed())
						changes++;
					w.leftT.color = Color.BLACK;
					this.rightRotate(x.parentT);
					x = this.root;
				}
			}
		}
		if (x.isRed()) //If x is root and RED we want to change
			changes++;
		x.color = Color.BLACK;
		return changes;
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
	 * 
	 * returns the node with the minimal key by iterating all the way to left
	 */
	private RBNode minNode() {
		if (empty())
			return blank;
		RBNode tmpNode = root;
		while (tmpNode.leftT != blank)
			tmpNode = tmpNode.leftT;
		return tmpNode; //Min node
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
	 * 
	 * returns the node with max key by iterating all the way to the right
	 */
	private RBNode maxNode(){
		if (empty())
			return blank;
		RBNode maxNode = root;
		while (maxNode.rightT != blank)
			maxNode = maxNode.rightT;
		return maxNode;
	}
	/**
	 * 
	 * @param node
	 * finding the successor in tree instance of RBNode node
	 */
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
		if(this.tree_array != null){
			int[] retArray = new int[this.tree_array.length];
			for (int i=0;i<this.tree_array.length;i++) {
				retArray[i] = this.tree_array[i].key;//Integer.parseInt(this.tree_array[i].key);
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
				retArray[i] = this.tree_array[i].value;
			}
			return retArray;
		} else {
			return new String[0];
		}
	}
	/**
	 * 
	 * @param minNode
	 * @return the array of all nodes in tree in-order
	 */
	private RBNode[] nodesToArray(RBNode minNode){
		RBNode[] arr = new RBNode[size]; //To be array of all nodes
		arr[0] = minNode; //Inserting node with smallest key
		RBNode s = findSccr(minNode);
		for (int i=1; i<size; i++){ //(n = size) n calls for successor
			arr[i] = s;
			s = findSccr(s);
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
		RBNode y = blank; //To be x's uncle
		while (x.parentT != blank && x.parentT.isRed()) { //x is not the root and child of RED node
			if (x.parentT == x.parentT.parentT.leftT) { //x's father is left child
				y = x.parentT.parentT.rightT;
				if (y.isRed()){ //Uncle is RED, case 1
					cntChanges += fixColor(x, y);
					x = x.parentT.parentT; //Bubbling-up the colors problem
				}
				else { //Case 2
					if (x == x.parentT.rightT) { //x is a right child
						x = x.parentT; 
						leftRotate(x); //Fixing the tree with left rotation
					}
					//Case 3
					cntChanges += 2;
					x.parentT.color = Color.BLACK; 
					x.parentT.parentT.color = Color.RED;
											//Changing colors to maintain invariants
					rightRotate(x.parentT.parentT); //Fix using right rotation
				}
			} else { //x's father is right child
				y = x.parentT.parentT.leftT; //Still his uncle
				if (y.isRed()){ //Case 1, same as before
					cntChanges += fixColor(x, y);
					x = x.parentT.parentT; //Bubbling-up the problem
				}
				else{
					if (x == x.parentT.leftT) { //Case 2
						x = x.parentT;
						rightRotate(x);
					}
					//Case 3
					cntChanges += 2;
					x.parentT.color = Color.BLACK;
					x.parentT.parentT.color = Color.RED;
					leftRotate(x.parentT.parentT);
				}
			}
		}
		if (root.isRed()) {
			root.color = Color.BLACK;
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
		x.parentT.color = Color.BLACK;
		y.color = Color.BLACK;
		x.parentT.parentT.color = Color.RED;
		return 3;
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
	 * private static void leftChild
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
		/*RBNode y = node.rightT; //node.rightT not null
		node.rightT = y.leftT; //Changing relations
		if (y.leftT != NIL)
			y.leftT.parentT = node;
		y.parentT = node.parentT; //Linking y to node's father
		if (node.parentT == NIL){ //Changing root
			root = y;
		}
		else if (node == node.parentT.leftT)
			node.parentT.leftT = y;
		else
			node.parentT.rightT = y;
		//Completing links
		y.leftT = node;
		node.parentT = y;*/
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
//Comments exactly like in leftRotate
//		RBNode y = node.leftT;//node.leftT not null
//		node.leftT = y.rightT;
//		if (y.rightT != blank)
//			y.rightT.parentT = node;
//		y.parentT = node.parentT;
//		if (node.parentT == blank){
//			root = y;
//		}
//		else if (node == node.parentT.rightT)
//			node.parentT.rightT = y;
//		else
//			node.parentT.leftT = y;
//		y.rightT = node;
//		node.parentT = y;
	}
	/**
	* public void print()
	*
	* Print the tree.
	*/
	public void print() {
		RBTree_or.printHelper(this.root, 0, this.blank);
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
	 * performs a binary search on the tree
	 * in attempt to find the node with k key.
	 * returns the node that has k as key.
	 * 
	 * @param root
	 * @param k
	 * @return
	 */
	public RBNode binSearch(RBNode root, int k) { // an added recursive function
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
	public RBNode findSccr(RBNode node) {
		if (node.rightT != this.blank) { // if node has a right sub-tree. 
			node = node.getRight();
			while(node.leftT != this.blank) {// has a left Subtree.
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


}
