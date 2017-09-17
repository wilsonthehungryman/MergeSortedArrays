import java.util.function.IntFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class Main
{
    public static void main(String[] args){
        int[][] original = {{0,3,4,5},{1,3,5,7,10},{7,8}};
        Supplier<int[][]> copy = () -> ArrayUtil.copy(original);
        Supplier<int[][]> copyAndReverse = () -> ArrayUtil.reverse(ArrayUtil.copy(original));
        
        int[] result = ArrayMerger.merge(copy.get(), false);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.merge(copy.get(), true);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.merge(copyAndReverse.get(), false);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.merge(copyAndReverse.get(), true);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.merge(new int[][]{{5,4,3,2,1}}, false);
        ArrayUtil.printArray(result);
    }
    
    static class ArrayMerger{
        public static int[] merge(int[][] arrays, boolean outputLowToHigh){
            if(arrays == null)
                return null;
            
            if(arrays[0][0] < arrays[0][arrays[0].length -1])
                return merge(arrays, outputLowToHigh, true);
            
            return merge(arrays, outputLowToHigh, false);
        }
        
        private static int[] merge(int[][] arrays, boolean outputLowToHigh, boolean inputsLowToHigh){
            if(arrays.length == 1)
                return singleArrayCase(arrays, outputLowToHigh, inputsLowToHigh);
            
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
                    if(comparer.test(currentValues[j], current)){
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
        
        private static int[] singleArrayCase(int[][] arrays, boolean lowToHigh, boolean inputLowToHigh){
            if(lowToHigh && inputLowToHigh)
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
    
    static class ArrayUtil{
        public static int[][] reverse(int[][] arrays){
            for(int i = 0; i < arrays.length; i++){
                for(int j = 0; j < arrays[i].length / 2; j++){
                    int tmp = arrays[i][j];
                    arrays[i][j] = arrays[i][arrays[i].length -1 - j];
                    arrays[i][arrays[i].length -1 - j] = tmp;
                }
            }
            return arrays;
        }
    
        public static int[][] copy(int[][] arrays){
            int[][] copy = new int[arrays.length][];
            
            for(int i = 0; i < arrays.length; i++){
                copy[i] = new int[arrays[i].length];
                for(int j = 0; j < arrays[i].length; j++)
                    copy[i][j] = arrays[i][j];
            }
            
            return copy;
        }
    
        public static void printArray(int[] array){
            System.out.print("|");
            for(int i = 0; i < array.length; i++)
                System.out.print(array[i] + "|");
            System.out.println();
        }
        
        public static void printArray(int[][] arrays){
            for(int i = 0; i < arrays.length; i++){
                System.out.print("|");
                for(int j = 0; j < arrays[i].length; j++)
                    System.out.print(arrays[i][j] + "|");
                System.out.println();
            }
            System.out.println();
        }
    }
}
