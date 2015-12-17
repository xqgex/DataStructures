import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.text.StyledEditorKit.ForegroundAction;

class MyTree {
	private TreeSet<Integer> set;

	MyTree() {
		this.set = new TreeSet<Integer>();
	}

	public int size() {
		return this.set.size();
	}

	public boolean empty() {
		return this.set.isEmpty();
	}

	public void insert(int v) {
		this.set.add((Integer)v);
	}

	public void delete(int v) {
		this.set.remove((Integer)v);
	}

	public int min() {
		if (this.empty())
			return -1;
		return (int)(this.set.first());
	}

	public int max() {
		if (this.empty())
			return -1;
		return (int)(this.set.last());
	}

	public boolean contains(int v) {
		return this.set.contains((Integer)v);
	}

	public int[] array() {
		int[] arr = new int[this.size()];
		Iterator<Integer> itr = this.set.iterator();
		for (int i = 0; i < this.size(); i++)
			arr[i] = (int)(itr.next());
		return arr;
	}
}


class TestRun implements Runnable {
    private int test_num;
    public boolean success = false;

    public TestRun(int test_num) {
        this.test_num = test_num;
    }

    public void run() {
        try {
        	switch (this.test_num) {
        		case 0: this.success = Tester.emptyTreeTest();
        				break;
        		case 1: this.success = Tester.insertAndSearchTest();
        				break;
        		case 2: this.success = Tester.deleteAndSearchTest();
        				break;
        		case 3: this.success = Tester.insertAndMinMaxTest();
        				break;
        		case 4: this.success = Tester.deleteMinMaxTest();
        				break;
        		case 5: this.success = Tester.insertAndSizeEmptyTest();
        				break;
        		case 6: this.success = Tester.insertAndArraysTest();
        				break;
        		case 7: this.success = Tester.deleteAndArraysTest();
        				break;
        		case 8: this.success = Tester.doubleInsertTest();
        				break;
        		case 9: this.success = Tester.doubleDeleteTest();
        				break;
        	}
        } catch (Exception e) {
        	System.out.println("Exception on Test " + test_num + " : " + e);
        }
    }
}

public class Tester {
	// False appear when size cross 21 and when size cross 183 there is a stack over flow error
	public static final int SIZE = 8;//2048 //9+13+14+15 //StaMAX 183 ???

	public static int[] sortInts(int[] arr) {
		int[] sortedArr = new int[arr.length];
		for (int j = 0; j < arr.length; j++) {
			sortedArr[j] = arr[j];
		}
		Arrays.sort(sortedArr);
		return sortedArr;
	}

	public static boolean arraysIdentical(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int j = 0; j < arr1.length; j++) {
			if (arr1[j] != arr2[j]) {
				return false;
			}
		}
		return true;
	}

	public static int[] stringToInt(String[] arr) {
		int[] arr2 = new int[arr.length];
		for (int i = 0 ; i < arr2.length ; i++) {
			arr2[i] = Integer.parseInt(arr[i]);
		}
		return arr2;
	}

	public static int intValue(String str) {
		if (str == null)
			return -1;
		else
			return Integer.parseInt(str);
	}

	public static boolean checkEmpty(RBTree rbTree, MyTree myTree) {
		return rbTree.empty() == myTree.empty();
	}

	public static boolean checkSize(RBTree rbTree, MyTree myTree) {
		return rbTree.size() == myTree.size();
	}

	public static boolean checkMin(RBTree rbTree, MyTree myTree) {
		return intValue(rbTree.min()) == myTree.min();
	}

	public static boolean checkMax(RBTree rbTree, MyTree myTree) {
		return intValue(rbTree.max()) == myTree.max();
	}

	public static boolean checkKeysArray(RBTree rbTree, MyTree myTree) {
		return arraysIdentical(rbTree.keysToArray(),
							   sortInts(myTree.array()));
	}

	public static boolean checkValuesArray(RBTree rbTree, MyTree myTree) {
		return arraysIdentical(stringToInt(rbTree.valuesToArray()),
							   sortInts(myTree.array()));
	}

	public static boolean checkSearch(RBTree rbTree, MyTree myTree) {
		for (int i = 0; i < SIZE; i++) {
			int a = intValue(rbTree.search(i));
			boolean b = myTree.contains(i);
			/*if ( (a == 8)&&(rbTree.size() == 14) ) {
				rbTree.printlist();
				System.out.println("VVVVVV");
			}*/
			if ((intValue(rbTree.search(i)) == i) != myTree.contains(i)) {
				/*rbTree.print();
				rbTree.printlist();
				System.out.println("zzzzzz");*/
				return false;
			}
		}
		return true;
	}

	public static boolean checkAll(RBTree rbTree, MyTree myTree) {
		return (checkEmpty(rbTree, myTree) &&
				checkSize(rbTree, myTree) &&
				checkMin(rbTree, myTree) &&
				checkMax(rbTree, myTree) &&
				checkKeysArray(rbTree, myTree) &&
				checkValuesArray(rbTree, myTree));
	}

	public static void insert(RBTree rbTree, MyTree myTree, int[] keys) {
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			//rbTree.print();
			//System.out.println("111111111");
			myTree.insert(keys[j]);
		}
	}

	public static int[] generateKeys() {
		int[] arr = new int[SIZE];
	    for (int i = 0; i < SIZE; i++) {
	        arr[i] = i;
	    }
	    Collections.shuffle(Arrays.asList(arr), new Random(539996358));
	    // mid -> min_max sort
	    int tmp[] = Arrays.copyOf(arr, SIZE/4);
	   	Arrays.sort(tmp);
	   	for (int i = 0; i < tmp.length/2; i++) {
	   		arr[2*i] = tmp[tmp.length/2-1-i];
	   		arr[2*i+1] = tmp[tmp.length/2+i];
	   	}
	   	// max -> min sort
	    Arrays.sort(arr, SIZE/4, SIZE/2);
	    for (int i = 0; i < SIZE/8; i++) {
	    	int swapped = arr[SIZE/4+i];
	    	arr[SIZE/4+i] = arr[SIZE/2-1-i];
	    	arr[SIZE/2-1-i] = swapped;
	    }
	    // min -> max sort
	    Arrays.sort(arr, SIZE/2, 3*SIZE/4);
	    return arr;
	}

	public static boolean emptyTreeTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		if (!checkAll(rbTree, myTree))
			return false;
		//rbTree.insert(1, "1");
		//rbTree.delete(1);
		return checkAll(rbTree, myTree);
	}

	public static boolean insertAndSearchTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSearch(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean deleteAndSearchTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			if (j > -1) {
				System.out.println("HHHHHH Delete " + keys[j]);
				rbTree.print();
				rbTree.printlist();
			}
			rbTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkSearch(rbTree, myTree)) {
				System.out.println("JJJJJJ");
				rbTree.print();
				rbTree.printlist();
				return false;
			}
		}
		return true;
	}

	public static boolean insertAndMinMaxTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkMin(rbTree, myTree) || !checkMax(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean deleteMinMaxTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			rbTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkMin(rbTree, myTree) || !checkMax(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean insertAndSizeEmptyTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(rbTree, myTree) || !checkEmpty(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean insertAndArraysTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkKeysArray(rbTree, myTree))
				return false;
			if (!checkValuesArray(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean deleteAndArraysTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			//rbTree.print();
			rbTree.delete(keys[j]);
			//rbTree.print();
			//System.out.println("ffdf");
			myTree.delete(keys[j]);
			if (!checkKeysArray(rbTree, myTree))
				return false;
			if (!checkValuesArray(rbTree, myTree))
				return false;
		}
		return true;
	}

	public static boolean doubleInsertTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(rbTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			if (rbTree.insert(keys[j],""+(-1)) != -1)
				return false;
			if (!checkSize(rbTree, myTree))
				return false;
		}
		return checkValuesArray(rbTree, myTree);
	}

	public static boolean doubleDeleteTest() {
		RBTree rbTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			if (rbTree.delete(keys[j]) != -1)
				return false;
			rbTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(rbTree, myTree))
				return false;
		}
		return checkValuesArray(rbTree, myTree);
	}

	public static int parseArgs(String[] args) {
		int test_num;
		if (args.length != 1)
			return -1;
		try {
			test_num = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return -1;
		}
		if (test_num < 0 || test_num > 9)
			return -1;
		return test_num;
	}
	
	public static void main(String[] args) {
		/*int test_num = parseArgs(args);
		if (test_num == -1) {
			System.out.println("USAGE: java Tester <test_num>");
			System.exit(1);
		}*/
		
		long startTime;
		String res = "";
		for (int test_num=0; test_num<10; test_num++) {
			startTime = System.currentTimeMillis();
			if (test_num==1) {
				continue;
			}
			TestRun test_runner = new TestRun(test_num);
			test_runner.run();
			Thread test_thread = new Thread(test_runner);
			test_thread.start();
			try {
				test_thread.join(100000000);
				if (test_thread.isAlive())
					System.out.println("Timeout on Test " + test_num);
			}
			catch (Exception e) {
				System.out.println("Exception on Test " + test_num + " : " + e);
			}
			res+=("Result #" + test_num + ": " + test_runner.success + " ,Execution time: " + (System.currentTimeMillis() - startTime) + " milliseconds.\r\n");
		}
		System.out.println(res);
		System.exit(0);
		
		
		/*
		RBTree tree = new RBTree();
		int[][] keysSet = new int[2][];
		keysSet[0] = new int[] {1, 2, 0, 3, 4, 10, 9, 7, 8, 6, 5, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
		keysSet[1] = new int[] {1, 5, 2, 3, 6, 0, 10, 15, 12};
		for (int[] set : keysSet) {
			for (int key : set) {
				tree.insert(key, String.valueOf(key));
			}
			System.out.println("Tree:");
			tree.print();
			tree.printlist();
			for (int key : set) {
				tree.delete(key);
				System.out.println("Tree: Delete " + String.valueOf(key));
				tree.print();
			}
		}
		*/
		int times = 10000;
		for (int i = 1; i <= 10; i++) {
			times *= i;
			testRun(times);
		}
	}
	
	
		  public static void shuffleArra (int[] ar) {
		    Random rnd = ThreadLocalRandom.current();
		    for (int i = ar.length - 1; i > 0; i--) {
		      int index = rnd.nextInt(i + 1);
		      // Simple swap
		      int a = ar[index];
		      ar[index] = ar[i];
		      ar[i] = a;
		    }
		  }
		public static void testRun(int numberOf) {
			int[] list = new int[200000];
			for (int i = 1; i < list.length; i++) {
				list[i] = i;
			}
			shuffleArra (list);
			int cnt;
			int totalI = 0;
			int totalD = 0;
			RBTree tree = new RBTree();
			for (int i = 0; i <= numberOf; i++) {
				cnt = tree.insert(list[i], "value");
				if (cnt != -1) {
					totalI += cnt; 
				}
			}
			System.out.println("insert:"+numberOf +"=" + totalI/numberOf);
			for (int i = 0; i <= numberOf; i++) {
				cnt = tree.delete(list[i]);
				if (cnt != -1) {
					totalD += cnt; 
				}
			}
			System.out.println("delete:"+numberOf +"=" + totalD/numberOf);
		}
	
	}
