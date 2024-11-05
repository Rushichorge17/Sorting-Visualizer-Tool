import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class SortingVisualizer extends JPanel {
    private static final int SCREEN_WIDTH = 910;
    private static final int SCREEN_HEIGHT = 750;
    private static final int ARR_SIZE = 130;
    private static final int RECT_SIZE = 7;

    private int[] arr = new int[ARR_SIZE];
    private int[] barr = new int[ARR_SIZE];
    private boolean complete = false;

    public SortingVisualizer() {
        randomizeAndSaveArray();
        loadArr();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer visualizer = new SortingVisualizer();
        frame.add(visualizer);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        visualizer.execute();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        visualize(g);
    }

    private void visualize(Graphics g) {
        int j = 0;
        for (int i = 0; i <= SCREEN_WIDTH - RECT_SIZE; i += RECT_SIZE) {
            int height = arr[j];
            g.setColor(complete ? new Color(100, 180, 100) : new Color(170, 183, 184));
            g.fillRect(i, SCREEN_HEIGHT - height, RECT_SIZE, height);
            j++;
        }
    }

    private void execute() {
        JOptionPane.showMessageDialog(this, "Press OK to start Sorting Visualizer");
        int choice = Integer.parseInt(JOptionPane.showInputDialog("Choose an algorithm:\n"
                + "1: Selection Sort\n2: Insertion Sort\n3: Bubble Sort\n4: Merge Sort\n"
                + "5: Quick Sort\n6: Heap Sort\n0: Generate new random list\nq: Quit"));

        switch (choice) {
            case 1:
                selectionSort();
                break;
            case 2:
                insertionSort();
                break;
            case 3:
                bubbleSort();
                break;
            case 4:
                mergeSort(arr, 0, ARR_SIZE - 1);
                break;
            case 5:
                quickSort(arr, 0, ARR_SIZE - 1);
                break;
            case 6:
                inplaceHeapSort(arr, ARR_SIZE);
                break;
            case 0:
                randomizeAndSaveArray();
                break;
            default:
                System.exit(0);
        }
        complete = true;
        repaint();
    }

    private void loadArr() {
        System.arraycopy(barr, 0, arr, 0, ARR_SIZE);
    }

    private void randomizeAndSaveArray() {
        Random rand = new Random();
        for (int i = 0; i < ARR_SIZE; i++) {
            barr[i] = rand.nextInt(SCREEN_HEIGHT);
        }
    }

    private void selectionSort() {
        for (int i = 0; i < ARR_SIZE - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < ARR_SIZE; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
            repaint();
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void insertionSort() {
        for (int i = 1; i < ARR_SIZE; i++) {
            int j = i - 1;
            int temp = arr[i];
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
            repaint();
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void bubbleSort() {
        for (int i = 0; i < ARR_SIZE - 1; i++) {
            for (int j = 0; j < ARR_SIZE - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                repaint();
                try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    private void mergeSort(int[] a, int si, int ei) {
        if (si >= ei) return;
        int mid = (si + ei) / 2;
        mergeSort(a, si, mid);
        mergeSort(a, mid + 1, ei);
        merge2SortedArrays(a, si, ei);
    }

    private void merge2SortedArrays(int[] a, int si, int ei) {
        int mid = (si + ei) / 2;
        int[] temp = new int[ei - si + 1];
        int i = si, j = mid + 1, k = 0;

        while (i <= mid && j <= ei) {
            if (a[i] <= a[j]) temp[k++] = a[i++];
            else temp[k++] = a[j++];
        }

        while (i <= mid) temp[k++] = a[i++];
        while (j <= ei) temp[k++] = a[j++];

        System.arraycopy(temp, 0, a, si, temp.length);
        repaint();
        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private void quickSort(int[] a, int si, int ei) {
        if (si >= ei) return;
        int pivotIndex = partitionArray(a, si, ei);
        quickSort(a, si, pivotIndex - 1);
        quickSort(a, pivotIndex + 1, ei);
    }

    private int partitionArray(int[] a, int si, int ei) {
        int pivot = a[si], count = 0;
        for (int i = si + 1; i <= ei; i++) if (a[i] <= pivot) count++;
        int pivotIndex = si + count;
        int temp = a[pivotIndex]; a[pivotIndex] = a[si]; a[si] = temp;
        int i = si, j = ei;
        while (i < pivotIndex && j > pivotIndex) {
            while (a[i] <= pivot) i++;
            while (a[j] > pivot) j--;
            if (i < pivotIndex && j > pivotIndex) {
                int temp_ij = a[i]; a[i] = a[j]; a[j] = temp_ij;
            }
        }
        repaint();
        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        return pivotIndex;
    }

    private void inplaceHeapSort(int[] input, int n) {
        for (int i = 1; i < n; i++) {
            int childIndex = i;
            while (childIndex > 0) {
                int parentIndex = (childIndex - 1) / 2;
                if (input[childIndex] > input[parentIndex]) {
                    int temp = input[parentIndex];
                    input[parentIndex] = input[childIndex];
                    input[childIndex] = temp;
                } else break;
                childIndex = parentIndex;
            }
        }
        for (int heapLast = n - 1; heapLast >= 0; heapLast--) {
            int temp = input[0];
            input[0] = input[heapLast];
            input[heapLast] = temp;
            int parentIndex = 0;
            int leftChild = 2 * parentIndex + 1;
            int rightChild = 2 * parentIndex + 2;
            while (leftChild < heapLast) {
                int maxIndex = parentIndex;
                if (input[leftChild] > input[maxIndex]) maxIndex = leftChild;
                if (rightChild < heapLast && input[rightChild] > input[maxIndex]) maxIndex = rightChild;
                if (maxIndex == parentIndex) break;
                int temp_max = input[parentIndex];
                input[parentIndex] = input[maxIndex];
                input[maxIndex] = temp_max;
                parentIndex = maxIndex;
                leftChild = 2 * parentIndex + 1;
                rightChild = 2 * parentIndex + 2;
            }
        }
        repaint();
    }
}
