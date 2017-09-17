
/**
 * ArrayMerger merges any number of arrays (in the form of a staggered 2d array).
 * Inputs must be sorted low to high, output will be low to high.
 * <pre>
 * Author: Wilson Reid
 * Date: September 17, 2017
 * </pre>
 */
class ArrayMergerSimple{
    
    /**
     * Merges multiple arrays into one. Runs in about O(n) worst and best.
     * 
     * @param arrays The arrays to merge
     * 
     * @return The merged arrays
     */
    public static int[] merge(int[][] arrays){
        if(arrays == null)
            return null;
        
        return performMerge(arrays);
    }
    
    private static int[] performMerge(int[][] arrays){
        if(arrays.length == 1)
            return arrays[1];
        
        int[] currentValues = new int[arrays.length];
        int[] pointers = new int[arrays.length];
        
        // Initializes pointers, currentValues, and then returns the results array
        int[] result = initialize(arrays, pointers, currentValues);
        
        // length = number of subarrays
        int length = currentValues.length;

        // loop through the results array, appending one value each time
        for(int i = 0; i < result.length; i++){
            int current = currentValues[0];
            int currentPointer = 0;
            
            for(int j = 1; j < length; j++){
                if(current > currentValues[j]){
                    current = currentValues[j];
                    currentPointer = j;
                }
            }
            
            result[i] = current;
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
}
