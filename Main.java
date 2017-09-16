public class Main
{
    public static void main(String[] args){
        int[][] arrays = {{0,3,4,5},{1,3,5,7,10},{7,8}};
        
        int[] result = ArrayMerger.merge(arrays);
        printArray(result);
    }
    
    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++)
            System.out.print(array[i] + "|");
        System.out.println();
    }
    
    static class ArrayMerger{
        public static int[] merge(int[][] arrays){
            int[] currentValues = new int[arrays.length];
            int[] pointers = new int[arrays.length];
            int[] result = initialize(arrays, pointers, currentValues);
            
            for(int i = 0; i < result.length; i++){
                int min = currentValues[0];
                int minPointer = 0;
                for(int j = 1; j < currentValues.length; j++){
                    if(currentValues[j] < min){
                        min = currentValues[j];
                        minPointer = j;
                    }
                }
                
                result[i] = min;
                pointers[minPointer] += 1;
                
                if(pointers[minPointer] == arrays[minPointer].length){
                    int[] newPointers = new int[pointers.length -1];
                    int[] newCurrentValues = new int[pointers.length -1];
                    shrink(minPointer, arrays, pointers, newPointers, currentValues, newCurrentValues);
                    currentValues = newCurrentValues;
                    pointers = newPointers;
                }else
                    currentValues[minPointer] = arrays[minPointer][pointers[minPointer]];
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
        
        private static void shrink(int pointer, int[][] arrays, int[] pointers, int[] newPointers, int[] values, int[] newValues){
            for(int i = 0; i < pointer; i++){
                newPointers[i] = pointers[i];
                newValues[i] = values[i];
            }
            
            for(int i = pointer + 1; i < pointers.length; i++){
                newPointers[i -1] = pointers[i];
                newValues[i -1] = values[i];
                arrays[i -1] = arrays[i];
            }
        }
    }   
}
