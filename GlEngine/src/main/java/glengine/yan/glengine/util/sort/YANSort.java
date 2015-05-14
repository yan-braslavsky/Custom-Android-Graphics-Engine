package glengine.yan.glengine.util.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yan-Home on 5/14/2015.
 */
public class YANSort {

    /**
     * Not efficient bubble sort , but without allocations
     */
    public static final <T> void bubbleSort(List<T> list, Comparator comparator) {
        int n = list.size();
        T temp;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (comparator.compare(list.get(j - 1), list.get(j)) > 0) {
                    temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    public static final <T> void sort(List<T> list, Comparator<T> comparator) {

        //FIXME : This sorting method allocates a lot of objects , it is better to replace it with something more efficient
        Collections.sort(list, comparator);
    }
}
