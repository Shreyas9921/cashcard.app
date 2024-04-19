package shreyas.dsa.mergesortingalgo;

class DsAlgoUtil {

	public static Integer[] mergeSort(Integer[] numsArray) {

		Integer arrayLength =numsArray.length;
		
		if(arrayLength < 2) {
			return numsArray;
		}
		Integer midIndex = arrayLength / 2;
		
		Integer[] leftHalf = new Integer[midIndex];
		Integer[] rightHalf = new Integer[arrayLength - midIndex];
		
		for(int i = 0; i < midIndex; i++) {
			leftHalf[i] = numsArray[i];
		}
		
		for(int i = midIndex; i < arrayLength; i++) {
			rightHalf[i-midIndex] = numsArray[midIndex];
		}
		//Divide - Sort
		mergeSort(leftHalf);
		mergeSort(rightHalf);
		
		//Conquer - sort
		merge(numsArray, leftHalf, rightHalf);
		
		return numsArray;
	}
	
	private static void merge(Integer[] numsArray, Integer[] leftArray, Integer[] rightArray) {
		
		int i = 0, j = 0, k = 0;
		int sizeOfLeft = leftArray.length;
		int sizeOfRight = rightArray.length;
		
		while(i < sizeOfLeft && j < sizeOfRight) {
			
			if(leftArray[i] <= rightArray[j]) {
				numsArray[k] = leftArray[i];
				i++;
			} else {
				numsArray[k] = rightArray[j];
				j++;
			}
			k++;
		}
		
		while(i < sizeOfLeft) {
			numsArray[k] = leftArray[i];
			i++;
			k++;
		}
		
		while(j < sizeOfRight) {
			numsArray[k] = rightArray[j];
			j++;
			k++;
		}
	}
}
