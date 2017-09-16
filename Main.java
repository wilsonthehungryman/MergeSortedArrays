import java.util.function.IntFunction;
import java.util.function.BiPredicate;

public class Main
{
    public static void main(String[] args){
        int[][] arrays = {{0,3,4,5},{1,3,5,7,10},{7,8}};
        
        int[] result = ArrayMerger.merge(arrays, false);
        printArray(result);
    }
    
    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++)
            System.out.print(array[i] + "|");
        System.out.println();
    }
    
    static class ArrayMerger{
        public static int[] merge(int[][] arrays, boolean outputLowToHigh){
            if(arrays[0][0] < arrays[0][arrays[0].length -1])
                return merge(arrays, outputLowToHigh, true);
            return merge(arrays, outputLowToHigh, false);
        }
        
        public static int[] merge(int[][] arrays, boolean outputLowToHigh, boolean inputsLowToHigh){
            int[] currentValues = new int[arrays.length];
            int[] pointers = new int[arrays.length];
            int[] result = initialize(arrays, pointers, currentValues);
            int length = currentValues.length;
            
            // Not sure if the saved equality checks are worth the cost of lambdas...
            BiPredicate<Integer, Integer> comparer = getInnerLoopComparer(inputsLowToHigh);
            IntFunction<Integer> resultIndex = getResultIndexFunction(inputsLowToHigh && outputLowToHigh, result.length);;

            for(int i = 0; i < result.length; i++){
                int current = currentValues[0];
                int currentPointer = 0;
                for(int j = 1; j < length; j++){
                    if(comparer.test(currentValues[j], current)){ //< current && inputsLowToHigh){
                        current = currentValues[j];
                        currentPointer = j;
                    }
                }
                
                result[resultIndex.apply(i)] = current;
                pointers[currentPointer] += 1;
                
                if(pointers[currentPointer] == arrays[currentPointer].length){
                    shrink(currentPointer, arrays, pointers, currentValues);
                    length--;
                }else
                    currentValues[currentPointer] = arrays[currentPointer][pointers[currentPointer]];
            }
            return result;
        }
        
        private static int[] initialize(int[][] arrays, int[] pointers, int[] currentValues){
            int totalLength = 0;
            
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
}
