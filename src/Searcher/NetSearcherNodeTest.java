package Searcher;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class NetSearcherNodeTest {

	@Test
	public void test() {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
		list1.add("I");
		list1.add("am");
		list1.add("Josh");
		
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("You");
		list2.add("Blue");
		list2.add("Cat");
		list2.add("Yummy");
		
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("Period");
		list3.add("I");
		list3.add("Hope");
		list3.add("this");
		
		ArrayList<String> list4 = new ArrayList<String>();
		list4.add("test");
		list4.add("works");
		list4.add("well");
		list4.add("plus");
		
		ArrayList<String> list5 = new ArrayList<String>();
		list5.add("no");
		list5.add("errors");
		list5.add("blue");
		list5.add("blue");
		
		String query1 = "blue";
		
		NetSearcherNode test1 = new NetSearcherNode(list1, query1);
		NetSearcherNode test2 = new NetSearcherNode(list2, query1);
		NetSearcherNode test3 = new NetSearcherNode(list3, query1);
		NetSearcherNode test4 = new NetSearcherNode(list4, query1);
		NetSearcherNode test5 = new NetSearcherNode(list5, query1);
		
		test1.setNext1(test2);
		test2.setNext2(test3);
		
		test2.setPrev1(test1);
		test2.setNext1(test3);
		test2.setNext2(test4);
		
		test3.setPrev2(test1);
		test3.setPrev1(test2);
		test3.setNext1(test4);
		test3.setNext2(test5);
		
		test4.setPrev2(test2);
		test4.setPrev1(test3);
		test4.setNext1(test5);
		
		test5.setPrev2(test3);
		test5.setPrev1(test4);

		assertTrue(test1.getMatchValue() == 0.0);
		assertTrue(test2.getMatchValue() == 0.0);
		assertTrue(test3.getMatchValue() == 4.0);
		assertTrue(test4.getMatchValue() == 4.0);
		assertTrue(test5.getMatchValue() == 8.0);
		}

}
