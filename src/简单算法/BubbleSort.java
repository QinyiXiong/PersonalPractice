package 简单算法;

import java.util.concurrent.ForkJoinPool;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 20:32
 * @project_Name: PersonalPractice
 * @Name: BubbleSort
 */

/*冒泡排序
* 原理：比较两个相邻的元素，将值大的元素交换至右端。
*假设5个数，那么它需要4次比较才能确定顺序，每一次都会找出当前此的最大值
*思路：依次比较相邻的两个数，将小数放在前面，大数放在后面。即在第一趟：首先比较第1个和第2个数，将小数放前，大数放后。
* 然后比较第2个数和第3个数，将小数放前，大数放后，如此继续，直至比较最后两个数，将小数放前，大数放后。重复第一趟步骤，
* 直至全部排序完成。
第一趟比较完成后，最后一个数一定是数组中最大的一个数，所以第二趟比较的时候最后一个数不参与比较；*/
public class BubbleSort {

    public void Bubble_Sort(int []arr){
        int temp;
        for (int i = 0; i < arr.length-1 ; i++) {
            for (int j = 0; j < arr.length-i-1 ; j++) {
                if(arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    public static void main(String[] args) {
        int []arr = {9,5,4,2,77,10,23,6,4,20,78,1};
        new BubbleSort().Bubble_Sort(arr);
        for (int i : arr) {
            System.out.print(i);
            System.out.print("  ");
        }
    }
}
