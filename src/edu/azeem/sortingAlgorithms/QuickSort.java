//:org.azeem.sortingAlgorithms/QuickSort.java
/**
 * @author Azeem Mumtaz - CB003033 - GF1041SE
 * @title Natural Language Dialogue System (Chatterbot) to Obtain Relevant & Concise Information
 * @description 
 */
package edu.azeem.sortingAlgorithms;

public class QuickSort extends SortingAlgorithm{

	private double[] _array;
	private boolean _sorted = false;
	
	public QuickSort(double[] array){
		this._array = array;
	}
	@Override
	public double[] sort() {
		_sorted = false;
		quickSort(_array, 0, _array.length - 1);
		return _array;
	}

	private void swap(double[] arr, int first, int second){
		double temp = arr[first];
		arr[first] = arr[second];
		arr[second] = temp;
	}
	
	private int partition(double arr[], int p, int r){
		double x = arr[r];					//Pivot element
		int i = p - 1;
		for(int j = p; j < r; j++){
			if(arr[j] <= x){
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, r);
		return i + 1;		
	}
	
	private void quickSort(double arr[], int p, int r) {
		if(p < r){		
			int q = partition(arr, p, r);			
			if (p < q - 1)
				quickSort(arr, p, q - 1);	
			if (q < r)
				quickSort(arr, q + 1, r);
		}
	}
	
	@Override
	public boolean isSorted() {
		return _sorted;
	}
}
///:~
