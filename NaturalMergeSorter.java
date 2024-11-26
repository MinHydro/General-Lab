public class NaturalMergeSorter {
	public int getSortedRunLength(int[] array, int arrayLength, int startIndex) {
    // If startIndex is out of bounds, return 0
    if (startIndex < 0 || startIndex >= arrayLength) {
        return 0;
    }
    
    // If it's the last element, a single element is a sorted run of length 1
    if (startIndex == arrayLength - 1) {
        return 1;
    }
    
    int runLength = 1;
    
    // Continue checking adjacent elements while they are in ascending order
    for (int i = startIndex + 1; i < arrayLength; i++) {
        // If the current element is less than the previous, the run ends
        if (array[i] < array[i - 1]) {
            break;
        }
        runLength++;
    }
    
    return runLength;
	}

	public void naturalMergeSort(int[] array, int arrayLength) {
	// If array is too small to sort, return
	if (array == null || arrayLength <= 1) {
			return;
	}
	
	int i = 0;
	while (true) {
			// Get the length of the first sorted run
			int firstRunLength = getSortedRunLength(array, arrayLength, i);
			
			// If first run covers the entire array, we're done
			if (firstRunLength == arrayLength) {
					return;
			}
			
			// If first run ends at the end of the array, restart from beginning
			if (i + firstRunLength >= arrayLength) {
					i = 0;
					continue;
			}
			
			// Get the length of the second sorted run
			int secondRunLength = getSortedRunLength(array, arrayLength, i + firstRunLength);
			
			// Merge the two runs
			merge(array, i, i + firstRunLength - 1, i + firstRunLength + secondRunLength - 1);
			
			// Reassign i to the first index after the second run
			i = (i + firstRunLength + secondRunLength >= arrayLength) ? 0 : i + firstRunLength + secondRunLength;
	}
}
	
	public void merge(int[] numbers, int leftFirst, int leftLast, 
	   int rightLast) {
		int mergedSize = rightLast - leftFirst + 1;
		int[] mergedNumbers = new int[mergedSize];
		int mergePos = 0;
		int leftPos = leftFirst;
		int rightPos = leftLast + 1;
      
      // Add smallest element from left or right partition to merged numbers
		while (leftPos <= leftLast && rightPos <= rightLast) {
			if (numbers[leftPos] <= numbers[rightPos]) {
				mergedNumbers[mergePos] = numbers[leftPos];
				leftPos++;
			}
			else {
				mergedNumbers[mergePos] = numbers[rightPos];
				rightPos++;
			}
			mergePos++;
		}
      
      // If left partition isn't empty, add remaining elements to mergedNumbers
		while (leftPos <= leftLast) {
			mergedNumbers[mergePos] = numbers[leftPos];
			leftPos++;
			mergePos++;
		}
      
      // If right partition isn't empty, add remaining elements to mergedNumbers
		while (rightPos <= rightLast) {
			mergedNumbers[mergePos] = numbers[rightPos];
			rightPos++;
			mergePos++;
		}
      
      // Copy merged numbers back to numbers
		for (mergePos = 0; mergePos < mergedSize; mergePos++) {
			numbers[leftFirst + mergePos] = mergedNumbers[mergePos];
		}
      
      // Free temporary array
		mergedNumbers = null;
	}
}