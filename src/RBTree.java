import java.awt.Color;

public class RBTree {
	private RBNode root; // all of these must be implemented while making, and changed while changing the tree.
	private RBNode min;
	private RBNode max;
	private RBNode[] tree_array; // an array of all the nods by order.
	private boolean array_status; // is the array above up to date.
	protected int size; // size of the tree
	private static final int INDENT_STEP = 4;
	//private final RBNode blank = new RBNode(null,null,null,null,"-1",Color.BLACK);
	private final RBNode blank = new RBNode("-1", null, Color.BLACK, null, null);
	/**
	 * public RBTree()
	 * 
	 * a builder of the tree
	 * 
	 * sets all the fields of tree tree to 
	 * be empty at start
	 */
	public RBTree() {
		this.root = this.blank;
		this.min = this.blank;
		this.max = this.blank;
		this.tree_array = null;
		this.array_status = false;
		//this.size = 0;
		//this.blank.parentT =this.blank;
		/*this.blank.leftT = this.blank;
		this.blank.rightT = this.blank;
		this.blank.parentT = this.blank;*/
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
	/*private void transplant(RBNode x, RBNode y) {
		if(x.parentT.leftT == x) { // x is a left child
			leftChild(x.parentT,y); // Update y as a left child instead of x
		} else { // x is a right child
			rightChild(x.parentT,y); // Update y as a right child instead of x
		}
	}*/
	/**
	 * private static void replace(RBNode x, RBNode y)
	 * 
	 * Replace old node (x) with a new one (y)
	 * @param x
	 * @param y
	 */
	/*private void replace(RBNode x, RBNode y) {
		boolean father = false;
		RBNode origFather = y.parentT;;
		if (y.parentT == x) {
			father = true;
		}
		//this.print();
		//System.out.println("cccccc");
		if (x.parentT != this.blank) {
			transplant(x,y);
		} else { // x was the root
			y.parentT = this.blank;
			this.root = y;
		}
		//this.print();
		//System.out.println("bbbbbb");
		if ( (y != x.leftT)&&(x.leftT != this.blank) ) {
			leftChild(y,x.leftT);
		}
		//this.print();
		//System.out.println("kkkkkk");
		if ( (y != x.rightT)&&(x.rightT != this.blank) ) {
			rightChild(y,x.rightT);
		}
		//if (!father) { // Make x father forget he ever was a son
			if (origFather.leftT == y) {
				origFather.leftT = this.blank;
			} else if (origFather.rightT == y) {
				origFather.rightT = this.blank;
			}
		//}
		//this.print();
		//System.out.println("aaaaaa");
	}*/
	/**
	 * Performs a left rotation on node x,
	 * according to the left rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void leftRotate(RBNode node){
		RBNode y = node.rightT; //node.right not null
		node.rightT = y.leftT; //Changing relations
		if (y.leftT != this.blank)
			y.leftT.parentT = node;
		y.parentT = node.parentT; //Linking y to node's father
		if (node.parentT == this.blank){ //Changing root
			root = y;
		}
		else if (node == node.parentT.leftT)
			node.parentT.leftT = y;
		else
			node.parentT.rightT = y;
		//Completing links
		y.leftT = node;
		node.parentT = y;
	}
	/*private void leftRotateOLD(RBNode x) {
		RBNode y = x.rightT;
		if (x.parentT != this.blank) {
			transplant(x,y);
		} else { // x was the root
			y.parentT = this.blank;
			this.root = y;
		}
		if (y.leftT != this.blank) {
			rightChild(x,y.leftT);
		} else {
			x.rightT = this.blank;
		}
		leftChild(y,x);
	}*/
	/**
	 * Performs a right rotation on node x,
	 * according to the right rotation specifics 
	 * learned in class   
	 * 
	 * @param x
	 */
	private void rightRotate(RBNode node){
		//Comments exactly like in leftRotate
		RBNode y = node.leftT;//node.left not null
		node.leftT = y.rightT;
		if (y.rightT != this.blank)
			y.rightT.parentT = node;
		y.parentT = node.parentT;
		if (node.parentT == this.blank){
			root = y;
		}
		else if (node == node.parentT.rightT)
			node.parentT.rightT = y;
		else
			node.parentT.leftT = y;
		y.rightT = node;
		node.parentT = y;
	}
	/*private void rightRotateOLD(RBNode y) {
		RBNode x = y.leftT;
		if (y.parentT != this.blank) {
			transplant(y,x);
		} else {
			x.parentT = this.blank;
			this.root = y;
		}
		if (x.rightT != this.blank) {
			leftChild(y,x.rightT);
		} else {
			y.leftT = this.blank;
		}
		rightChild(x,y);
	}*/
	/**
	* public class RBNode
	*/
	public class RBNode {
		private RBNode(){}
		/**
		 * 
		 * RBNode constructor that creates a RED node with key and value
		 */
		private RBNode(String key, String info) {
			this.key = key;
			this.info = info;
			this.color = Color.RED;
		}
		/**
		 * 
		 * @param key
		 * @param value
		 * @param color
		 * RBNode constructor that creates a node in Color color with key and value
		 */
		private RBNode(String key, String info, Color color) {
			this.key = key;
			this.info = info;
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
		private RBNode(String k, String v, Color c, RBNode l, RBNode r){
			key = k;
			info = v;
			color = c;
			leftT = l;
			rightT = r;
		}
		Color color;
		String key;
		String info;
		RBNode leftT = blank;
		RBNode rightT = blank;
		RBNode parentT = blank;
		/**
		 * 
		 * @return true if node is internal leaf
		 * 
		 */
		public boolean isLeaf() {
			return leftT == blank && rightT == blank;
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
			return color == Color.RED;
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
		String getKey() {
			return this.key;
		}
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
	}
	/*public class RBNode { // is it alive?
		RBNode leftT = blank;
		RBNode parentT = blank;
		RBNode rightT = blank;
		String info;
		String key;
		Color color;
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
		/*public boolean isRed() { // returns true if is red. 
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
		/*public RBNode getLeft() {
			return this.leftT;
		}
		/**
		 * returns the right son of the node.
		 * 
		 * @return
		 */
		/*public RBNode getRight() {
			return this.rightT;
		}
		/*public RBNode getParent() {
			return this.parentT;
		}
		/**
		 * returns the value of the node.
		 * 
		 * @return
		 */
		/*public int getValue() {
			return Integer.parseInt(this.info);
		}
		/**
		 * returns the key of the node.
		 * 
		 * @return
		 */
		/*public String getKey() {
			return this.key;
		}
		/**
		 * public void changeColor()
		 * 
		 * resets the color of the node from red 
		 * to black and vise versa.
		 * 
		 */
		/*public int changeColor(Color color) {
			if (this.color == color) {
				return 0;
			} else  {
				this.color = color;
				return 1;
			}
		}
		/*public int changeColor(RBNode node) {
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
		/*public int changeColor(String string) {
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
		/*public boolean recognizeBlank(RBNode node) {
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
		/*public boolean barren() {
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
		/*public RBNode oneChild() {
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
		/*public boolean twoChilds() {
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
		/*public boolean mILeftchild() {
			if (this == this.parentT.leftT) {
				return true;
			} else {
				return false;
			}
		}
	}*/
	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 */
	/*public RBNode getRoot() {
			if (this.root == this.blank) {
				return null;
			} else {
				return this.root;
			}
	}*/
	private RBNode searchNode(int k) {
		RBNode tmpNode = root;
		int found = -1; //indicator
		while (found == -1 && tmpNode != this.blank) { //binary tree search
			if (tmpNode.key == String.valueOf(k))
				found = 1;
			else if (Integer.parseInt(tmpNode.key) < k)
				tmpNode = tmpNode.rightT;
			else
				tmpNode = tmpNode.leftT;
		}
		if (found == -1) //node NOT FOUND
			return null;
		return tmpNode;
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
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) { // envelop function
		RBNode node = binSearch(this.root,k,this.blank);
		if(node != this.blank){
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
		if (root == this.blank) {
			ansNode = this.blank;
		} else if (Integer.parseInt(root.key) == k) {
			ansNode = root;
		} else if ( (Integer.parseInt(root.key) < k)&&(root.rightT != this.blank) ) {
			root = root.rightT;
			ansNode = binSearch(root, k,ansNode);
		} else if ( (Integer.parseInt(root.key) > k)&&(root.leftT != this.blank) ) {
			root = root.leftT;
			ansNode = binSearch(root, k,ansNode);
		} else {
			ansNode = this.blank;
		}
		return ansNode;
	}
	private int fixColor(RBNode x, RBNode y) {
		x.parentT.color = Color.BLACK;
		y.color = Color.BLACK;
		x.parentT.parentT.color = Color.RED;
		return 3;
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
		RBNode z = new RBNode(String.valueOf(k), v, Color.RED); //Inserting z
		if (k > Integer.parseInt(this.max.key))
			this.max = z;
		if (k < Integer.parseInt(this.min.key))
			this.min = z;
		RBNode x = root;
		int changes = 0; //Color changes
		if (empty()) { //If tree is empty - insert as root
			z.parentT = this.blank;
			root = z;
			z.color = Color.BLACK;
			changes = 1;
			this.min = z;
			this.max = z;
			size++;
		} else { // Binary tree insert
			RBNode y = this.blank;
			while (x != this.blank) { //Finding the spot to enter
				y = x;
				if (k < Integer.parseInt(x.key)) //Should be entered in left subtree
					x = x.leftT;
				else if (k > Integer.parseInt(x.key)) //Should be entered in right subtree
					x = x.rightT;
				else //If node with same key exists in tree
					return -1;
			}
			z.parentT = y;
			if (Integer.parseInt(z.key) < Integer.parseInt(y.key)) //Linking z to parent y
				y.leftT = z;
			else
				y.rightT = z;
			size++;
			changes += fixInsert(z);
		}
		return changes;
	}
	/*public int insertOLD(int k, String v) {
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
		 *//*
		if ( (this.root != this.blank)&&(search(k) != null) ) {
			return -1;
		} else {
			this.array_status = false;
			this.size++;
			RBNode newBaby = new RBNode(String.valueOf(k), v, Color.RED, this.blank, this.blank);
			if (this.root == this.blank) { //First node at the tree
				this.root = newBaby;
				this.max = newBaby;
				this.min = newBaby;
				changes += this.root.changeColor("switch");
				return changes;
			} else {
				RBNode father = whereToInsert(this.root, newBaby);
				if (Integer.parseInt(newBaby.key) < Integer.parseInt(father.key)) {
					leftChild(father, newBaby);
				} else {
					rightChild(father, newBaby);
				}
				//this.print();
				//System.out.println("llllll");
				if (father.isRed()) {
					changes += fixInsert(newBaby);
					//this.print();
					//System.out.println("mmmmmm");
				}
				upDate(newBaby);
				/*if (this.size > 6) {
					this.print();
					System.out.println("dddddd");
				}*//*
				return changes;
			}
		}
	}*/
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
			if(root.leftT != this.blank) {
				ans = whereToInsert(root.leftT, node);
			}
		} else if(Integer.parseInt(node.key) > Integer.parseInt(root.key)) {
			if(root.rightT != this.blank) {
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
	private int fixInsert(RBNode x) {
		int cntChanges = 0; //Color changes
		RBNode y = this.blank; //To be x's uncle
		while (x.parentT != this.blank && x.parentT.isRed()) { //x is not the root and child of RED node
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
	/*public int fixInsertOLD(RBNode node) {
		int count = 0;
		while ( (node.parentT != this.blank)&&(node.parentT.isRed()) ) {
			//this.print();
			//System.out.println("wwwwww");
			if (node.parentT == node.parentT.parentT.leftT) {
				RBNode uncle = node.parentT.parentT.rightT;
				if ( (uncle == this.blank)||(!uncle.isRed()) ) {
					if (node == node.parentT.rightT) { // Case 2
						//this.print();
						//System.out.println("nnnnnn");
						node = node.parentT; //			//	      Y
						leftRotate(node); //			//	    /   \
						//this.print();
						//System.out.println("qqqqqq");
					} // Case 3 //						//	   <X>  d
					count += node.parentT.parentT.changeColor(Color.RED); //	  /   \
					count += node.parentT.changeColor(Color.BLACK); //		//	 <Z>  c
					//count += 2; //						//	/   \
					//node = node.parentT; //				//	a   b
					rightRotate(node.parentT.parentT);
					//this.print();
					//System.out.println("pppppp");
				} else { // Case 1						//	      Y
					//this.print();
					//System.out.println("oooooo");
					count += node.parentT.parentT.changeColor(Color.RED); //	    /   \
					count += node.parentT.changeColor(Color.BLACK); //		//	 <X>     <W>
					count += uncle.changeColor(Color.BLACK); //				//	/   \   /   \
					//count += 3; //						//	a  <Z>  d   e
					node = node.parentT.parentT; //		//	  /   \
				} //									//	  b   c
			} else {
				RBNode uncle = node.parentT.parentT.leftT;
				if ( (uncle == this.blank)||(!uncle.isRed()) ) {
					if (node == node.parentT.leftT) { // Case 2
						//this.print();
						//System.out.println("rrrrrr");
						node = node.parentT; //			//	      Y
						rightRotate(node); //			//	    /   \
						//this.print();
						//System.out.println("ssssss");
					} // Case 3 //						//	   a   <X>
					count += node.parentT.parentT.changeColor(Color.RED); //	      /   \
					count +=node.parentT.changeColor(Color.BLACK); //		//	     b   <Z>
					//count += 2; //						//	        /   \
					//node = node.parentT; //				//	        c   d
					leftRotate(node.parentT.parentT);
					//this.print();
					//System.out.println("tttttt");
				} else { // Case 1						//	      Y
					//this.print();
					//System.out.println("uuuuuu");
					count += node.parentT.parentT.changeColor(Color.RED); //	    /   \
					count += node.parentT.changeColor(Color.BLACK); //		//	 <W>     <X>
					count += uncle.changeColor(Color.BLACK); //				//	/   \   /   \
					//count += 3; //						//	a   b   c  <Z>
					node = node.parentT.parentT; //		//	          /   \
					//this.print();
					//System.out.println("xxxxxx");
				} //									//	          d   e
			}
		}
		if (node.parentT == this.blank) {
			this.root = node;
			count += this.root.changeColor(Color.BLACK);
		}
		return count; //+1?
	}*/
	public int fixDelete(RBNode node) {
		RBNode w; //x's "uncle"
		int changes = 0; //Color changes
		while ((node.parentT != this.blank) && (node.color == Color.BLACK)) { //x is not root and RED
			if (node == node.parentT.leftT) { //If x is left child
				w = node.parentT.rightT;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = Color.BLACK;
					if (node.parentT.color == Color.BLACK)
						changes++;
					node.parentT.color = Color.RED;
					this.leftRotate(node.parentT);
					w = node.parentT.rightT;
				}
				if ((w.leftT.color == Color.BLACK) && (w.rightT.color == Color.BLACK)) { // Case 2
					if (w.color == Color.BLACK)
						changes++;
					w.color = Color.RED;
					node = node.parentT;
				} else {
					if (w.rightT.color == Color.BLACK) { // Case 3
						if (w.leftT.isRed())
							changes++;
						w.leftT.color = Color.BLACK;
						if (w.color == Color.BLACK)
							changes++;
						w.color = Color.RED;
						this.rightRotate(w);
						w = node.parentT.rightT;
					} // Case 4
					if (!w.color.equals(node.parentT.color))
						changes++;
					w.color = node.parentT.color;
					if (node.parentT.isRed())
						changes++;
					node.parentT.color = Color.BLACK;
					if (w.rightT.isRed())
						changes++;
					w.rightT.color = Color.BLACK;
					this.leftRotate(node.parentT);
					node = this.root;
				}
			} else {
				w = node.parentT.leftT;
				if (w.isRed()) { // Case 1
					changes++;
					w.color = Color.BLACK;
					if (node.parentT.color == Color.BLACK)
						changes++;
					node.parentT.color = Color.RED;
					this.rightRotate(node.parentT);
					w = node.parentT.leftT;
				}
				if ((w.leftT.color == Color.BLACK) && (w.rightT.color == Color.BLACK)) { // Case 2
					if (w.color == Color.BLACK)
						changes++;
					w.color = Color.RED;
					node = node.parentT;
				} else {
					if (w.leftT.color == Color.BLACK) { // Case 3
						if (w.rightT.isRed())
							changes++;
						w.rightT.color = Color.BLACK;
						if (w.color == Color.BLACK)
							changes++;
						w.color = Color.RED;
						this.leftRotate(w);
						w = node.parentT.leftT;
					} // Case 4
					if (!w.color.equals(node.parentT.color))
						changes++;
					w.color = node.parentT.color;
					if (node.parentT.isRed())
						changes++;
					node.parentT.color = Color.BLACK;
					if (w.leftT.isRed())
						changes++;
					w.leftT.color = Color.BLACK;
					this.rightRotate(node.parentT);
					node = this.root;
				}
			}
		}
		if (node.isRed()) //If x is root and RED we want to change
			changes++;
		node.color = Color.BLACK;
		return changes;
	}
/*	public int fixDeleteOLDADT(RBNode node) {
		int count = 0;
		RBNode brtr;
		while ( (node!=this.root)&&(node.color == Color.DARK_GRAY) ) {
			if (node == node.parentT.leftT) {
				brtr = node.parentT.rightT; // Case 1
				if (brtr.color == Color.RED) {
					brtr.color = Color.BLACK;
					node.parentT.color = Color.RED;
					leftRotate(node.parentT);
					brtr = node.parentT.rightT;
				}
				if (brtr != this.blank) {
					if ( (brtr.leftT.color == Color.BLACK)&&(brtr.rightT.color == Color.BLACK) ) {
						brtr.color = Color.RED; // Case 2
						node = node.parentT;
					} else {
						if (brtr.rightT.color == Color.BLACK) {
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
					node = node.parentT;
				}
			} else {
				brtr = node.parentT.leftT; // Case 1
				if (brtr.color == Color.RED) {
					brtr.color = Color.BLACK;
					node.parentT.color = Color.RED;
					leftRotate(node.parentT);
					brtr = node.parentT.leftT;
				}
				if (brtr != this.blank) {
					if ( (brtr.rightT.color == Color.BLACK)&&(brtr.leftT.color == Color.BLACK) ) {
						brtr.color = Color.RED; // Case 2
						node = node.parentT;
					} else {
						if (brtr.leftT.color == Color.BLACK) {
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
				} else {
					node = node.parentT;
				}
			}
		}
		node.color = Color.BLACK;
		return count;
	}*/
	/**
	 * Rebalance the tree after the deletion 
	 * of the node.
	 * 
	 * @param node
	 * @return color changes count
	 */
/*	public int fixDeleteBackUp(RBNode node) { // TODO Delete me
		int count = 0;
		while ( (node.parentT != this.blank)&&(node.color == Color.DARK_GRAY) ) {
			//this.print();
			//System.out.println("dddddd");
			if (node == node.parentT.leftT) {
				RBNode brtr = node.parentT.rightT;
				if (brtr == this.blank) { // Node don't have brothers (Only child)
					count += node.changeColor(Color.black);
					node = node.parentT;
					count += node.changeColor("darken");
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.leftT != this.blank)&&(brtr.leftT.isRed()) ) { // Case 3
						rightRotate(brtr); //			//	      ?Y?
						count += brtr.changeColor("darken"); //			//	     /   \
						count += brtr.parentT.changeColor("switch"); //	//	  |X|      W
						brtr = brtr.parentT; //			//	 /   \   /   \
										//	?a? ?b? <c> ?d?
					}
					if ( (brtr.rightT != this.blank)&&(brtr.rightT.isRed()) ) { // Case 4
						leftRotate(node.parentT);
						count += brtr.changeColor(node.parentT); //				//	      ?Y?
						count += node.changeColor(Color.BLACK); //					//	     /   \
						count += node.parentT.changeColor(Color.BLACK); //			//	  |X|      W
						count += node.parentT.parentT.rightT.changeColor("switch"); //	//	 /   \   /   \
						node = node.parentT.parentT; //					//	?a? ?b? ?c? <d>
					} else { // Case 2
						count += node.changeColor(Color.BLACK); //		//	      ?Y?
						count += node.parentT.changeColor("darken");
															//			//	     /   \
						count += brtr.changeColor("switch"); //				//	  |X|      W 
						count += 3; //						//	 /   \   /   \
						node = node.parentT; //				//	?a? ?b?  c   d
						//this.print();
						//System.out.println("ffffff");
					}
				} else { // Case 1
					if (node.parentT.parentT == this.blank) {
						this.root = node.parentT.rightT;
					} //									//	      Y
					leftRotate(node.parentT); //			//      /   \
					count += node.parentT.parentT.changeColor("switch"); //	//   |X|     <W>
					count += node.parentT.changeColor("switch"); //			//	/   \   /   \
												//	a   b   c   d
				}
			} else {
				RBNode brtr = node.parentT.leftT;
				if (brtr == this.blank) { // Node don't have brothers (Only child)
					count += node.changeColor(Color.BLACK);
					node = node.parentT;
					count += node.changeColor("darken");
					
				} else if (!brtr.isRed()) { // I have a black brother 8-)
					if ( (brtr.rightT != this.blank)&&(brtr.rightT.isRed()) ) { // Case 3
						leftRotate(brtr); //			//	      ?Y?
						count += brtr.changeColor("switch"); //			//	     /   \
						count += brtr.parentT.changeColor("switch"); //	//	   X      |W|
											//	 /   \   /   \
					} //								//	?a? <b> ?c? ?d?
					if ( (brtr.leftT != this.blank)&&(brtr.leftT.isRed()) ) { // Case 4
						rightRotate(node.parentT);
						count += brtr.changeColor(node.parentT.color); //				//	      ?Y?
						count += node.changeColor(Color.BLACK); //					//	     /   \
						count += node.parentT.changeColor(Color.BLACK); //			//	   X      |W|
						count += node.parentT.parentT.leftT.changeColor("switch"); //	//	 /   \   /   \
						node = node.parentT.parentT; //					//	<a> ?b? ?c? ?d?
						
					} else { // Case 2
						//this.print();
						//System.out.println("gggggg");
						count += node.changeColor(Color.BLACK); //		//	      ?Y?
						count += node.parentT.changeColor("darken"); //			//	     /   \
						count += brtr.changeColor("switch"); //				//	   X      |W|
						 //						//	 /   \   /   \
						node = node.parentT; //				//	 a   b  ?c? ?d?
					}
				} else { // Case 1
					if (node.parentT.parentT == this.blank) {
						this.root = node.parentT.leftT;
					} //									//	      Y
					rightRotate(node.parentT); //			//	    /   \
					count += node.parentT.parentT.changeColor("switch"); //	//	 <X>     |W|
					count += node.parentT.changeColor("switch"); //			//	/   \   /   \
												//	a   b   c   d
				}
			}
		}
		if (node.parentT == this.blank) { // node is the root
			this.root = node;
			count += this.root.changeColor(Color.BLACK);
		}
		return count;
	}*/
	/**
	 * finds the successor of the node,
	 * and return it
	 *  
	 * @param node
	 * @return node successor
	 */
	public RBNode findSccr(RBNode node) {
		RBNode successor = this.blank;
		if (node != this.getMax(this.root)){ //Has successor
			successor = node.rightT;
			if (successor != this.blank){ //If node has right child -
				//then go right and all the way to the left
				while (successor.leftT != this.blank)
					successor = successor.leftT;
			} else { //Go up to the left, and take the first right
				successor = node.parentT;
				if (node == node.parentT.rightT){
					while (successor == successor.parentT.rightT)
						successor = successor.parentT;
					if (successor != root)
						successor = successor.parentT;
				}
			}
		}
		return successor; //Successor node
	}/*
	public RBNode findSccrOLD(RBNode node) {
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
	}*/
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
		int changes = 0; //Color changes
		RBNode toDelete = this.searchNode(k); //Finding the node we want to delete
		if (toDelete == null) //Key k not in tree
			return -1;
		if (size == 1){ //Remove the root
			this.root = this.blank;
			this.max = this.blank;
			this.min = this.blank;
			this.size = 0;
			return changes;
		}
		RBNode x, y;
		if (toDelete.leftT == this.blank || toDelete.rightT == this.blank) //If toDelete has at most
															//one child
			y = toDelete; //We now want to delete y
		else
			y = this.findSccr(toDelete); //We want to delete toDelete's successor
		if (y.leftT != this.blank)
			x = y.leftT;
		else
			x = y.rightT;
		x.parentT = y.parentT;
		if (y.parentT == this.blank){ //Deleting the root
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
			toDelete.info = y.info; //Copying successor's value to his new spot
		}
		if (y.color == Color.BLACK){ //If we deleted a BLACK node
			changes += this.fixDelete(x); //Fixing the tree
		}
		size--; //Updating tree size
		if (this.min == toDelete)
			this.min = getMin(root);
		if (this.max == toDelete)
			this.max = getMax(root);
		return changes;
	}
/*	public int deleteOLD(int k) {
		if(binSearch(this.root, k, this.blank) == null){
			return -1;
		}
		RBNode centenarian = binSearch(this.root, k, this.blank); // "A centenarian is a person who lives to or beyond the age of 100 years" (from Wikipedia)
		//RBNode sccr = centenarian;
		//RBNode brdr = null;
		int count = 0;
		// what about all the fields if node - this.root
		Color backupColor = centenarian.color;
		count += centenarian.changeColor("darken");
		if (centenarian.leftT == this.blank) {
			brdr = centenarian.rightT;
			transplant(centenarian, centenarian.rightT);
		} else if (centenarian.rightT == this.blank) {
			brdr = centenarian.leftT;
			transplant(centenarian, centenarian.leftT);
		} else {
			sccr = findSccr(centenarian);
			backupColor = sccr.color;
			brdr = sccr.rightT;
			if (sccr.parentT == centenarian) {
				brdr.parentT = sccr;
			} else {
				transplant(sccr, sccr.rightT);
				sccr.rightT = centenarian.rightT;
				sccr.rightT.parentT = sccr;
			}
			transplant(centenarian, sccr);
			sccr.leftT = centenarian.leftT;
			sccr.leftT.parentT = sccr;
			sccr.color = centenarian.color;
		}
		if (backupColor == Color.BLACK) {
			fixDelete(centenarian);
		}
		return count;
	}*/
/*	public int deleteBackUp(int k) {
		int count = 0;
		// Delete: Case 1: x’s sibling w is red
		// Delete: Case 2: x’s sibling w is black, and both children of w are black
		// Delete: Case 3: x’s sibling w is black, w’s left child is red, and w’s right child is black
		// Delete: Case 4: x’s sibling w is black, and w’s right child is red
		/*
		 *	 If the node to be deleted has two children, we delete its successor from the tree and use it to replace the node to be deleted
		 *		Deleted node has at most one child!!!
		 *//*
		//this.print();
		//System.out.println("kkkkkk");
		RBNode centenarian = binSearch(this.root, k, this.blank); // "A centenarian is a person who lives to or beyond the age of 100 years" (from Wikipedia)
		if(centenarian == this.blank){ // No such key
			return -1;
		} else {
			this.array_status = false;
			this.size--;
			RBNode child;
			if (centenarian.barren()) { // The centenarian don't have child's
				if (!centenarian.isRed()) { // i am leaf and I'm black
					count += centenarian.changeColor("darken");
					//this.print();
					//System.out.println("hhhhhh");
					count += fixDelete(centenarian);
				} // We can safely delete the centenarian
				if (centenarian.parentT != this.blank) {
					//centenarian.darken();
					//changes += fixDelete(centenarian);
					//this.print();
					//System.out.println("jjjjjj");
					if (centenarian.mILeftchild()) {
						centenarian.parentT.leftT = this.blank;
					} else {
						centenarian.parentT.rightT = this.blank;
					}
				} else { // Delete the root
					this.root = this.blank;
				}
			} else if ((child = centenarian.oneChild()) != this.blank) { // The centenarian have only one child
				if (!centenarian.isRed()) { // We can safely bridge the centenarian
					count += child.changeColor("darken");
					//this.print();
					//System.out.println("aaaaaa");
					count += fixDelete(child);
					//this.print();
					//System.out.println("bbbbbb");
				}
				replace(centenarian,child); // This will make the centenarian to disappear because no one is looking at the poor guy
				if (centenarian.parentT == this.blank) {
					this.root = child;
				}
			} else { // The centenarian have two children
				RBNode sccr = findSccr(centenarian);
				count += sccr.changeColor(centenarian);
				//this.print();
				//System.out.println("cccccc");
				//changes += fixDelete(sccr);
				if ( (sccr.rightT != this.blank)&&(centenarian != sccr.parentT) ) {
					count += sccr.rightT.changeColor(sccr.color);
					sccr.parentT.leftT = sccr.rightT;
				} else if ( (sccr.rightT != this.blank)&&(centenarian.leftT != this.blank) ) {
					count += sccr.rightT.changeColor(centenarian.leftT);
				}
				//this.print();
				//System.out.println("eeeeee");
				replace(centenarian,sccr); // This will make the centenarian to disappear because no one is looking at the poor guy
				if (centenarian.parentT == this.blank) {
					this.root = sccr;
				}
			}
			upDateDel(centenarian);
			return count;
		}
	}*/
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
		if ( (root2 != this.blank)&&(root2.rightT != this.blank) ) {
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
		if ( (root2 != this.blank)&&(root2.leftT != this.blank) ) {
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
		if (this.min != this.blank){
			return this.min.key; 
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
		if (this.max != this.blank){
			return this.max.key; 
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
		if(root == this.blank) {
			arr = null;
		} else {
			//arr[cnt] = root;
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
		RBTree.printHelper(this.root, 0, this.blank);
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
			if (node.parentT != this.blank) {p=node.parentT.key;} else {p="null";}
			if (node.leftT != this.blank) {l=node.leftT.key;} else {l="null";}
			if (node.rightT != this.blank) {r=node.rightT.key;} else {r="null";}
			System.out.println("Node " + node.key + "\t parent is " + p + ",\t Left child is: " + l + ",\t Right child is: " + r);
		}
	}
}

