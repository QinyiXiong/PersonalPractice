package JAVA泛型;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 19:14
 * @project_Name: PersonalPractice
 * @Name: multiple_arrays
 */
public class multiple_arrays {

    public static <T> void printArray( T[] inputarray){
        for (T t : inputarray) {
            System.out.print(t);
            System.out.print("  ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 创建不同类型数组： Integer, Double 和 Character
        Integer[] intArray = {1, 2, 3, 4, 5};
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
        Character[] charArray = {'H', 'E', 'L', 'L', 'O'};

        System.out.println("整型数组元素为:");
        printArray(intArray); // 传递一个整型数组

        System.out.println("\n双精度型数组元素为:");
        printArray(doubleArray); // 传递一个双精度型数组

        System.out.println("\n字符型数组元素为:");
        printArray(charArray); // 传递一个字符型数组
    }
}
