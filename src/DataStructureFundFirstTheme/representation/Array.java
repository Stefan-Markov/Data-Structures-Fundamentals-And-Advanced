package DataStructureFundFirstTheme.representation;

public class Array {
    public static int[] elements = new int[4];

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            if (i == elements.length) {
                elements = grow();
            }
            elements[i] = 5 + i;
        }

        for (int element : elements) {
            System.out.println(element);
        }
    }

    private static int[] grow() {
        int[] arr = new int[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            arr[i] = elements[i];
        }
//        System.arraycopy(elements, 0, arr, 0, elements.length);

        return arr;
    }
}
