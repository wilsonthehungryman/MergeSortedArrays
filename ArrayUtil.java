
class ArrayUtil{
    public static int[][] reverse(int[][] arrays){
        for(int i = 0; i < arrays.length; i++){
            reverse(arrays[i]);
        }
        return arrays;
    }
    
    public static void reverse(int[] array){
        for(int i = 0; i < array.length / 2; i++){
            int tmp = array[i];
            array[i] = array[array.length -1 - i];
            array[array.length -1 - i] = tmp;
        }
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
