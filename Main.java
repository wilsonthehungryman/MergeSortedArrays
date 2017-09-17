import java.util.function.Supplier;

public class Main
{
    public static void main(String[] args){
        sameOrder();
        differentOrder();
    }
    
    public static void sameOrder(){
        System.out.println("\nSame Order");
        
        int[][] original = {{0,3,4,5},{0,1,3,4,5,7,8,10},{7,8}};
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
        
        result = ArrayMerger.merge(new int[][]{{5,4,3,2,1}}, true);
        ArrayUtil.printArray(result);
    }
    
    public static void differentOrder(){
        System.out.println("\nDifferent Orders");
        
        int[][] original = {{0,3,4,5},{10,8,7,5,4,3,1,0},{7,8}};
        Supplier<int[][]> copy = () -> ArrayUtil.copy(original);
        Supplier<int[][]> copyAndReverse = () -> ArrayUtil.reverse(ArrayUtil.copy(original));
        
        int[] result = ArrayMerger.mergeDifferentOrders(copy.get(), false);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.mergeDifferentOrders(copy.get(), true);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.mergeDifferentOrders(copyAndReverse.get(), false);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.mergeDifferentOrders(copyAndReverse.get(), true);
        ArrayUtil.printArray(result);
        
        result = ArrayMerger.mergeDifferentOrders(new int[][]{{5,4,3,2,1}}, true);
        ArrayUtil.printArray(result);
    }
}
