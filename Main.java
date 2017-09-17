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
        
        result = ArrayMerger.merge(new int[][]{{5,4,3,2,1}}, true);
        ArrayUtil.printArray(result);
    }
}
