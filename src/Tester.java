import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

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
	public static int SIZE = 50;
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
	public static boolean checkEmpty(RBTree RBTree, MyTree myTree) {
		return RBTree.empty() == myTree.empty();
	}
	public static boolean checkSize(RBTree RBTree, MyTree myTree) {
		return RBTree.size() == myTree.size();
	}
	public static boolean checkMin(RBTree RBTree, MyTree myTree) {
		return intValue(RBTree.min()) == myTree.min();
	}
	public static boolean checkMax(RBTree RBTree, MyTree myTree) {
		return intValue(RBTree.max()) == myTree.max();
	}
	public static boolean checkKeysArray(RBTree RBTree, MyTree myTree) {
		return arraysIdentical(RBTree.keysToArray(),
							   sortInts(myTree.array()));
	}
	public static boolean checkValuesArray(RBTree RBTree, MyTree myTree) {
		return arraysIdentical(stringToInt(RBTree.valuesToArray()),
							   sortInts(myTree.array()));
	}
	public static boolean checkSearch(RBTree RBTree, MyTree myTree) {
		for (int i = 0; i < SIZE; i++) {
			if ((intValue(RBTree.search(i)) == i) != myTree.contains(i)) {
				return false;
			}
		}
		return true;
	}
	public static boolean checkAll(RBTree RBTree, MyTree myTree) {
		return (checkEmpty(RBTree, myTree) &&
				checkSize(RBTree, myTree) &&
				checkMin(RBTree, myTree) &&
				checkMax(RBTree, myTree) &&
				checkKeysArray(RBTree, myTree) &&
				checkValuesArray(RBTree, myTree));
	}
	public static void insert(RBTree RBTree, MyTree myTree, int[] keys) {
		for (int j = 0; j < keys.length; j++) {
			RBTree.insert(keys[j],(""+keys[j]));
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
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		if (!checkAll(RBTree, myTree))
			return false;
		//RBTree.insert(1, "1");
		//RBTree.delete(1);
		return checkAll(RBTree, myTree);
	}
	public static boolean insertAndSearchTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			RBTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSearch(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean deleteAndSearchTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(RBTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			RBTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkSearch(RBTree, myTree)) {
				return false;
			}
		}
		return true;
	}
	public static boolean insertAndMinMaxTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			RBTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkMin(RBTree, myTree) || !checkMax(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean deleteMinMaxTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(RBTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			RBTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkMin(RBTree, myTree) || !checkMax(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean insertAndSizeEmptyTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			RBTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(RBTree, myTree) || !checkEmpty(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean insertAndArraysTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			RBTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkKeysArray(RBTree, myTree))
				return false;
			if (!checkValuesArray(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean deleteAndArraysTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(RBTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			RBTree.delete(keys[j]);
			myTree.delete(keys[j]);
			if (!checkKeysArray(RBTree, myTree))
				return false;
			if (!checkValuesArray(RBTree, myTree))
				return false;
		}
		return true;
	}
	public static boolean doubleInsertTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		insert(RBTree, myTree, keys);
		for (int j = 0; j < keys.length; j++) {
			if (RBTree.insert(keys[j],""+(-1)) != -1)
				return false;
			if (!checkSize(RBTree, myTree))
				return false;
		}
		return checkValuesArray(RBTree, myTree);
	}
	public static boolean doubleDeleteTest() {
		RBTree RBTree = new RBTree();
		MyTree myTree = new MyTree();
		int[] keys = generateKeys();
		for (int j = 0; j < keys.length; j++) {
			if (RBTree.delete(keys[j]) != -1)
				return false;
			RBTree.insert(keys[j],(""+keys[j]));
			myTree.insert(keys[j]);
			if (!checkSize(RBTree, myTree))
				return false;
		}
		return checkValuesArray(RBTree, myTree);
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
	public static void smallTest() {
		int cntI, cntD;
		RBTree tree = new RBTree();
		int[][] keysSet = new int[2][];
		keysSet[0] = new int[] {1, 2, 0, 3, 4, 10, 9, 7, 8, 6, 5, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
		keysSet[1] = new int[] {1, 5, 2, 3, 6, 0, 10, 15, 12};
		for (int[] set : keysSet) {
			cntI = 0;
			cntD = 0;
			for (int key : set) {
				cntI += tree.insert(key, String.valueOf(key));
				System.out.println("Tree: Insert " + String.valueOf(key));
			}
			for (int key : set) {
				cntD += tree.delete(key);
				System.out.println("Tree: Delete " + String.valueOf(key));
			}
			System.out.println("Made " + cntI + " color changes at insert, And " + cntD + " at delete");
		}
	}
	public static void hugeTest() {
		long startTime;
		for (int i=0;i<=128;i++) {
			String res = "Check for " + i*16 + " elements";
			Tester.SIZE = i*8;
			for (int test_num=0; test_num<10; test_num++) {
				startTime = System.currentTimeMillis();
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
				if (!test_runner.success) {
					res+=("Result #" + test_num + ": " + test_runner.success + " ,Execution time: " + (System.currentTimeMillis() - startTime) + " milliseconds.\r\n");
				}
			}
			System.out.println(res);
		}
		System.exit(0);
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
	public static void loopTest() {
		int numberOf;
		double tmp = 0.0;
		for (int i = 1; i <= 10; i++) {
			numberOf = i*10000;
			int[] list = new int[numberOf+1];
			for (int j = 1; j < list.length; j++) {
				list[j] = j;
			}
			shuffleArra (list);
			int cnt;
			int totalI = 0;
			int totalD = 0;
			RBTree tree = new RBTree();
			for (int j = 0; j <= numberOf; j++) {
				cnt = tree.insert(list[j], "value");
				if (cnt != -1) {
					totalI += cnt; 
				}
			}
			tmp = Float.valueOf(totalI/numberOf);
			System.out.println(String.format("insert: %d nodes, Total of %d color changes, AVG: %,.3f",numberOf,totalI,tmp));
			shuffleArra (list);
			for (int j = 0; j <= numberOf; j++) {
				cnt = tree.delete(list[j]);
				if (cnt != -1) {
					totalD += cnt; 
				}
			}
			System.out.println("delete:" + numberOf + " nodes, Total of " + totalD + " color changes, AVG: " + totalD/numberOf);
		}
	}

	public static void main(String[] args) {
		loopTest();
		//hugeTest();
		//smallTest();
	}
}
