//:org.azeem.sortingAlgorithms/SortingAlgorithm.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.sortingAlgorithms;

public abstract class SortingAlgorithm {

	public abstract double[] sort();
	public abstract boolean isSorted();	
	
	public void print(boolean sorted, double[] array){
		if(sorted)
			System.out.print("Sorted Array: \t");
		else
			System.out.print("Array: \t\t");
		
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i] + "  ");
		}
		System.out.println();
	}
}
///:~
