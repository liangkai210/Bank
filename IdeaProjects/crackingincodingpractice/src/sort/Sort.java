package sort;

public class Sort {

    int[] a = {7, 2, 5, 8, 1, 4};

    public void insertionSort() {

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j - 1] > a[j]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
        }
    }


}
