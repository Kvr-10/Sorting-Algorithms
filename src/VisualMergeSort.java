import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualMergeSort extends JFrame {
    private static final int BLOCK_SIZE = 50;
    private static final int DELAY = 500; // milliseconds
    private int[] array;
    private JLabel[] labels;
    private JTextField inputField;
    private JButton startButton;
    private JButton infoButton;
    private JButton resetButton;
    private JButton howItWorksButton;
    private JButton back;
    private JPanel inputPanel;
    private JPanel sortingPanel;
    private JScrollPane scrollPane;

    public VisualMergeSort() {
        setTitle("Visual Merge Sort");
        setSize(970, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set up the input field and buttons
        inputField = new JTextField(20);
        startButton = new JButton("Merge Sort");
        infoButton = new JButton("Show Info");
        howItWorksButton = new JButton("How It Works");
        resetButton = new JButton("Reset");
        back = new JButton("Back");
        inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter numbers separated by commas:"));
        inputPanel.add(inputField);
        inputPanel.add(startButton);
        inputPanel.add(resetButton);
        inputPanel.add(howItWorksButton);
        inputPanel.add(infoButton);
        inputPanel.add(back);
        add(inputPanel, BorderLayout.NORTH);

        // Set up the sorting panel and scroll pane
        sortingPanel = new JPanel();
        sortingPanel.setLayout(null);
        sortingPanel.setPreferredSize(new Dimension(800, 100)); // Initial height, will adjust dynamically

        scrollPane = new JScrollPane(sortingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Set up the start button action
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String[] inputNumbers = input.split(",");
                array = new int[inputNumbers.length];
                labels = new JLabel[inputNumbers.length];

                int panelWidth = sortingPanel.getWidth();
                int totalBlocksWidth = inputNumbers.length * BLOCK_SIZE;
                int startX = (panelWidth - totalBlocksWidth) / 2;

                for (int i = 0; i < inputNumbers.length; i++) {
                    array[i] = Integer.parseInt(inputNumbers[i].trim());
                    labels[i] = new JLabel(String.valueOf(array[i]), SwingConstants.CENTER);
                    labels[i].setOpaque(true);
                    labels[i].setBackground(Color.CYAN);
                    labels[i].setBorder(new LineBorder(Color.BLACK)); // Add border to each block
                    labels[i].setBounds(startX + i * BLOCK_SIZE, 100, BLOCK_SIZE, BLOCK_SIZE);
                    sortingPanel.add(labels[i]);
                }
                sortingPanel.revalidate();
                sortingPanel.repaint();
                startSorting();
            }
        });

        // Set up the info button action
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfoDialog();
            }
        });

        // Set up the reset button action
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSorting();
            }
        });
        howItWorksButton.addActionListener(e -> showHowItWorksDialog());

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mainpage mp = new Mainpage();
                mp.setVisible(true);
                setVisible(false);
            }
        });

        setVisible(true);
    }

    private void showInfoDialog() {
        String info = "<html><body>"
                + "<h2>Merge Sort Algorithm</h2>"
                + "<p>Merge Sort is a divide-and-conquer sorting algorithm that divides the array into halves, recursively </p>"
                + "<p>sorts each half, and then merges the sorted halves.</p>"
                + "<h3>Time Complexity:</h3>"
                + "<ul>"
                + "<li>Best Case: O(n log n)</li>"
                + "<li>Average Case: O(n log n)</li>"
                + "<li>Worst Case: O(n log n)</li>"
                + "</ul>"
                + "<h3>Space Complexity:</h3>"
                + "<ul>"
                + "<li>O(n) due to the additional space needed for merging.</li>"
                + "</ul>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, info, "Merge Sort Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHowItWorksDialog() {
        String explanation = "<html><body>"
                + "<h2>How Merge Sort Works</h2>"
                + "<p>Merge Sort is a divide-and-conquer sorting algorithm that efficiently sorts elements by dividing the array into halves,<br>"
                + "recursively sorting each half, and then merging the sorted halves back together.</p>"
                + "<p>The algorithm follows these steps:</p>"
                + "<ul>"
                + "<li><strong>Divide:</strong> The array is recursively divided into two halves until each sub-array contains a single element.</li>"
                + "<li><strong>Conquer:</strong> Each sub-array is sorted recursively.</li>"
                + "<li><strong>Merge:</strong> The sorted sub-arrays are merged back together to form a single sorted array.</li>"
                + "<li><strong>Base Case:</strong> The recursion terminates when the sub-array has one or no elements, which is inherently sorted.</li>"
                + "</ul>"
                + "<h3>About the Code:</h3>"
                + "<p>The provided code visualizes the Merge Sort algorithm by updating the graphical representation of the array during the sorting process.<br>"
                + "Key elements in the code include:</p>"
                + "<ul>"
                + "<li><strong>Array Initialization:</strong> The input numbers are read from a text field and stored in an array.</li>"
                + "<li><strong>Label Creation:</strong> For each element, a label is created to visually represent it in the GUI.</li>"
                + "<li><strong>Sorting Process:</strong> The sorting logic is implemented in a separate thread to keep the GUI responsive.<br>"
                + "Each step of the sorting process is visualized with a delay, showing both the division and merging phases.</li>"
                + "<li><strong>Color Indication:</strong> During the merging process, the current elements being merged are highlighted in yellow,<br>"
                + "while sorted elements turn green upon completion.</li>"
                + "</ul>"
                + "<p>This visualization helps users understand how the Merge Sort algorithm processes the array,<br>"
                + "by clearly showing the recursive division and the merging of elements until the array is fully sorted.</p>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, explanation, "How It Works", JOptionPane.INFORMATION_MESSAGE);
    }


    public void startSorting() {
        new Thread(() -> {
            try {
                mergeSort(array, 0, array.length - 1, 0); // Pass depth for visual
                highlightSorted();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void mergeSort(int[] arr, int left, int right, int depth) throws InterruptedException {
        if (left < right) {
            int mid = (left + right) / 2;

            // Display the breaking phase (division of subarrays)
            displayDivision(left, right, depth);  // This moves the subarray down

            // Recursively sort the two halves
            mergeSort(arr, left, mid, depth + 1);
            mergeSort(arr, mid + 1, right, depth + 1);

            // Merge the sorted halves
            displayMerging(left, right, depth); // Show the merging phase
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) throws InterruptedException {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            // Highlight merging elements
            labels[k].setBackground(Color.YELLOW); // Highlight current element
            labels[k + 1].setBackground(Color.YELLOW); // Highlight next element
            if (L[i] <= R[j]) {
                arr[k] = L[i++];
            } else {
                arr[k] = R[j++];
            }
            updateLabels();
            Thread.sleep(DELAY);
            labels[k].setBackground(Color.CYAN); // Reset color
            k++;
        }

        while (i < n1) {
            arr[k++] = L[i++];
            updateLabels();
            Thread.sleep(DELAY);
        }

        while (j < n2) {
            arr[k++] = R[j++];
            updateLabels();
            Thread.sleep(DELAY);
        }
    }

    private void updateLabels() {
        SwingUtilities.invokeLater(() -> {
            int panelWidth = sortingPanel.getWidth();
            int totalBlocksWidth = array.length * BLOCK_SIZE;
            int startX = (panelWidth - totalBlocksWidth) / 2;

            for (int i = 0; i < array.length; i++) {
                labels[i].setBounds(startX + i * BLOCK_SIZE, labels[i].getY(), BLOCK_SIZE, BLOCK_SIZE);
                labels[i].setText(String.valueOf(array[i])); // Update text
            }
            sortingPanel.revalidate();
            sortingPanel.repaint();
        });
    }

    private void displayDivision(int left, int right, int depth) throws InterruptedException {
        SwingUtilities.invokeLater(() -> {
            int offset = depth * 60; // Move blocks further with increasing depth for breaking
            int panelWidth = sortingPanel.getWidth();
            int totalBlocksWidth = array.length * BLOCK_SIZE;
            int startX = (panelWidth - totalBlocksWidth) / 2;

            for (int i = left; i <= right; i++) {
                labels[i].setBounds(startX + i * BLOCK_SIZE, 100 + offset, BLOCK_SIZE, BLOCK_SIZE);
            }

            // Dynamically adjust sorting panel height for deeper levels of recursion
            int newHeight = (depth + 3) * 60 + 200; // Added 200 to keep space for initial blocks
            sortingPanel.setPreferredSize(new Dimension(panelWidth, newHeight));
            sortingPanel.revalidate();
            sortingPanel.repaint();
        });
        Thread.sleep(DELAY);
    }

    private void displayMerging(int left, int right, int depth) throws InterruptedException {
        SwingUtilities.invokeLater(() -> {
            int offset = (depth + 1) * 60 + 100; // Move the merging subarrays lower than breaking phase
            int panelWidth = sortingPanel.getWidth();
            int totalBlocksWidth = array.length * BLOCK_SIZE;
            int startX = (panelWidth - totalBlocksWidth) / 2;

            // Display subarrays
            for (int i = left; i <= right; i++) {
                labels[i].setBounds(startX + i * BLOCK_SIZE, offset, BLOCK_SIZE, BLOCK_SIZE);
            }

            // Dynamically adjust sorting panel height for merging
            int newHeight = offset + 60 + 100; // Added space for merging
            sortingPanel.setPreferredSize(new Dimension(panelWidth, newHeight));
            sortingPanel.revalidate();
            sortingPanel.repaint();
        });
        Thread.sleep(DELAY);
    }

    private void highlightSorted() throws InterruptedException {
        for (int i = 0; i < array.length; i++) {
            labels[i].setBackground(Color.GREEN); // Highlight sorted elements
            updateLabels();
            Thread.sleep(DELAY);
        }
    }

    private void resetSorting() {
        sortingPanel.removeAll();
        inputField.setText("");
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisualMergeSort::new);
    }
}
