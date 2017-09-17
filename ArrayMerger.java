import java.util.function.IntFunction;
import java.util.function.BiPredicate;

/**
 * ArrayMerger merges any number of arrays (in the form of a staggered 2d array).
 * The output can be low to high, or high to low.
 * <pre>
 * Author: Wilson Reid
 * Date: September 17, 2017
 * </pre>
 */
class ArrayMerger{
    
    /**
     * Merges multiple arrays into one. Runs in about O(n) worst and best.
     * 
     * @param arrays The arrays to merge
     * @param outputLowToHigh When true the output array will be low to high, otherwise high to low
     * 
     * @return The merged arrays
     */
    public static int[] merge(int[][] arrays, boolean outputLowToHigh){
        if(arrays == null)
            return null;
        
        // TODO prone to failure/exceptions
        if(arrays[0][0] < arrays[0][arrays[0].length -1])
            return merge(arrays, outputLowToHigh, true);
        
        return merge(arrays, outputLowToHigh, false);
    }
    
    private static int[] merge(int[][] arrays, boolean outputLowToHigh, boolean inputsLowToHigh){
        if(arrays.length == 1)
            return singleArrayCase(arrays, outputLowToHigh, inputsLowToHigh);
        
        int[] currentValues = new int[arrays.length];
        int[] pointers = new int[arrays.length];
        
        // Initializes pointers, currentValues, and then returns the results array
        int[] result = initialize(arrays, pointers, currentValues);
        
        // length = number of subarrays
        int length = currentValues.length;
        
        // Not sure if the saved equality checks are worth the cost of lambdas...
        BiPredicate<Integer, Integer> comparer = getInnerLoopComparer(inputsLowToHigh);
        IntFunction<Integer> resultIndex = getResultIndexFunction(inputsLowToHigh == outputLowToHigh, result.length);

        // loop through the results array, appending one value each time
        for(int i = 0; i < result.length; i++){
            int current = currentValues[0];
            int currentPointer = 0;
            
            for(int j = 1; j < length; j++){
                // comparer will ensure the proper comparison is used 
                if(comparer.test(currentValues[j], current)){
                    current = currentValues[j];
                    currentPointer = j;
                }
            }
            
            result[resultIndex.apply(i)] = current;
            pointers[currentPointer] += 1;
            
            // if the current pointer is at the end of the array, remove that subarray
            if(pointers[currentPointer] == arrays[currentPointer].length){
                shrink(currentPointer, arrays, pointers, currentValues);
                length--;
            }else
                // Otherwise, move the pointer
                currentValues[currentPointer] = arrays[currentPointer][pointers[currentPointer]];
        }
        return result;
    }
    
    private static int[] initialize(int[][] arrays, int[] pointers, int[] currentValues){
        int totalLength = 0;
        
        // TODO will fail if subarray is null
        for(int i = 0; i < arrays.length; i++){
            totalLength += arrays[i].length;
            currentValues[i] = arrays[i][0];
        }
        return new int[totalLength];
    }
    
    private static void shrink(int pointer, int[][] arrays, int[] pointers, int[] values){
        for(int i = pointer + 1; i < pointers.length; i++){
            pointers[i -1] = pointers[i];
            values[i -1] = values[i];
            arrays[i -1] = arrays[i];
        }
    }
    
    private static int[] singleArrayCase(int[][] arrays, boolean lowToHigh, boolean inputLowToHigh){
        if(lowToHigh == inputLowToHigh)
            return arrays[0];
        
        for(int i = 0; i < arrays[0].length / 2; i++){
            int tmp = arrays[0][i];
            arrays[0][i] = arrays[0][arrays[0].length -1 - i];
            arrays[0][arrays[0].length -1 - i] = tmp;
        }
        return arrays[0];
    }
    
    private static BiPredicate<Integer,Integer> getInnerLoopComparer(boolean inputsLowToHigh){
        if(inputsLowToHigh)
            return (current, other) -> current < other;
        else
            return (current, other) -> current > other;
    }
    
    private static IntFunction getResultIndexFunction(boolean sameOrder, int resultLength){
        if(sameOrder)
            return i -> i;
        return i -> { return resultLength - 1 - i; };
    }
}
