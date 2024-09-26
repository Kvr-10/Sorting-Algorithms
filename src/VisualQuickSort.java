import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualQuickSort extends JFrame {
    private static final int BLOCK_SIZE = 50;
    private static final int DELAY = 500; // milliseconds
    private int[] array;
    private JLabel[] labels;
    private JTextField inputField;
    private JButton startButton;
    private JButton resetButton;
    private JButton infoButton;
    private JButton howItWorksButton;
    private JButton back;
    private JLabel pivotLabel;  // Label to display the pivot element
    private JPanel inputPanel;
    private JPanel sortingPanel;
    private int currentYOffset = 100; // Vertical offset for placing new sets of labels
    private JScrollPane scrollPane;

    public VisualQuickSort() {
        setTitle("Visual Quick Sort");
        setSize(970, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        initializeComponents();
        setButtonActions();
    }

    private void initializeComponents() {
        inputField = new JTextField(20);
        startButton = new JButton("Quick Sort");
        resetButton = new JButton("Reset");
        infoButton = new JButton("Show Info");
        howItWorksButton = new JButton("How It Works");
        back = new JButton("Back");
        pivotLabel = new JLabel("Pivot: ", JLabel.CENTER);

        inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter numbers separated by commas:"));
        inputPanel.add(inputField);
        inputPanel.add(startButton);
        inputPanel.add(resetButton);
        inputPanel.add(howItWorksButton);
        inputPanel.add(infoButton);
        inputPanel.add(back);
        add(inputPanel, BorderLayout.NORTH);

        sortingPanel = new JPanel();
        sortingPanel.setLayout(null);
        scrollPane = new JScrollPane(sortingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        pivotLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(pivotLabel, BorderLayout.SOUTH);
    }

    private void setButtonActions() {
        back.addActionListener(e -> {
            new Mainpage().setVisible(true);
            setVisible(false);
        });

        startButton.addActionListener(e -> {
            if (!initializeArrayFromInput()) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers separated by commas.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            renderArray();
            startSorting();
        });

        resetButton.addActionListener(e -> resetSorting());

        infoButton.addActionListener(e -> showInfoDialog());

        howItWorksButton.addActionListener(e -> showHowItWorksDialog());
    }

    private boolean initializeArrayFromInput() {
        try {
            String input = inputField.getText();
            String[] inputNumbers = input.split(",");
            array = new int[inputNumbers.length];
            labels = new JLabel[inputNumbers.length];
            for (int i = 0; i < inputNumbers.length; i++) {
                array[i] = Integer.parseInt(inputNumbers[i].trim());
            }
            return true;
        } catch (NumberFormatException e) {
            return false; // Input validation failed
        }
    }

    private void renderArray() {
        sortingPanel.removeAll();
        currentYOffset = 100; // Reset offset for new sorting visualization
        addLabels(array, currentYOffset);
        updateSortingPanelSize();
    }

    private void addLabels(int[] arr, int yOffset) {
        int panelWidth = sortingPanel.getWidth();
        int totalBlocksWidth = arr.length * BLOCK_SIZE;
        int startX = (panelWidth - totalBlocksWidth) / 2;

        for (int i = 0; i < arr.length; i++) {
            labels[i] = new JLabel(String.valueOf(arr[i]), SwingConstants.CENTER);
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.CYAN);
            labels[i].setBorder(new LineBorder(Color.BLACK));
            labels[i].setBounds(startX + i * BLOCK_SIZE, yOffset, BLOCK_SIZE, BLOCK_SIZE);
            sortingPanel.add(labels[i]);
        }
        updateSortingPanelSize();
    }

    private void showInfoDialog() {
        String info = "<html><body>"
                + "<h2>Quick Sort Algorithm</h2>"
                + "<p>Quick Sort is a highly efficient sorting algorithm that uses a divide-and-conquer approach to sort elements.</p>"
                + "<h3>Time Complexity:</h3>"
                + "<ul>"
                + "<li>Best Case: O(n log n)</li>"
                + "<li>Average Case: O(n log n)</li>"
                + "<li>Worst Case: O(n^2)</li>"
                + "</ul>"
                + "<h3>Space Complexity:</h3>"
                + "<ul>"
                + "<li>O(log n) due to the recursive stack.</li>"
                + "</ul>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, info, "Quick Sort Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHowItWorksDialog() {
        String explanation = "<html><body>"
                + "<h2>How Quick Sort Works</h2>"
                + "<p>Quick Sort is a highly efficient sorting algorithm that uses a divide-and-conquer approach to sort elements.<br>"
                + "It works by selecting a 'pivot' element from the array and partitioning the other elements into two sub-arrays,<br>"
                + "according to whether they are less than or greater than the pivot.</p>"
                + "<p>The algorithm follows these steps:</p>"
                + "<ul>"
                + "<li><strong>Choose a Pivot:</strong> Select an element from the array to be the pivot. In this implementation, the last element is chosen as the pivot.</li>"
                + "<li><strong>Partitioning:</strong> Reorder the array so that all elements with values less than the pivot come before it,<br>"
                + "and all elements with values greater than the pivot come after it.</li>"
                + "<li><strong>Recursive Sorting:</strong> Recursively apply the above steps to the sub-arrays formed by the partitioning.</li>"
                + "<li><strong>Base Case:</strong> The recursion terminates when the sub-array has fewer than two elements.</li>"
                + "</ul>"
                + "<h3>About the Code:</h3>"
                + "<p>The provided code visualizes the Quick Sort algorithm by updating the graphical representation of the array after each partition.<br>"
                + "Key elements in the code include:</p>"
                + "<ul>"
                + "<li><strong>Array Initialization:</strong> The input numbers are read from a text field and stored in an array.</li>"
                + "<li><strong>Label Creation:</strong> For each element, a label is created to visually represent it in the GUI.</li>"
                + "<li><strong>Sorting Process:</strong> The sorting logic is implemented in a separate thread to keep the GUI responsive.<br>"
                + "Each step of the sorting process is visualized with a delay.</li>"
                + "<li><strong>Color Indication:</strong> The current pivot is highlighted in red, while the sorted elements turn yellow upon completion.</li>"
                + "</ul>"
                + "<p>The visualization helps to understand how the algorithm processes the array and organizes elements around the pivot until the array is sorted.</p>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, explanation, "How It Works", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startSorting() {
        new Thread(() -> {
            try {
                quickSort(array, 0, array.length - 1);
                SwingUtilities.invokeLater(this::highlightSorted);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void quickSort(int[] arr, int low, int high) throws InterruptedException {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);
            SwingUtilities.invokeLater(() -> addLabels(arr.clone(), currentYOffset += BLOCK_SIZE + 10));
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) throws InterruptedException {
        int pivot = arr[high];
        int i = low - 1;

        // Update the pivot label and highlight the pivot
        SwingUtilities.invokeLater(() -> {
            labels[high].setBackground(Color.RED); // Highlight pivot in red
            pivotLabel.setText("Pivot: " + pivot);
        });

        for (int j = low; j < high; j++) {
            // Highlight the current element being compared
            int finalJ = j;
            SwingUtilities.invokeLater(() -> labels[finalJ].setBackground(Color.YELLOW)); // Highlight current element in yellow
            Thread.sleep(DELAY);
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                updateLabels(i, j);
            }
            // Reset the color of the current element after the comparison
            SwingUtilities.invokeLater(() -> labels[finalJ].setBackground(Color.CYAN));
        }
        swap(arr, i + 1, high); // Move pivot to the correct position
        updateLabels(i + 1, high);

        // Restore the pivot color after partitioning
        SwingUtilities.invokeLater(() -> {
            labels[high].setBackground(Color.CYAN);
            pivotLabel.setText(""); // Clear pivot label after sorting
        });
        return i + 1;
    }

    private void updateLabels(int i, int j) {
        SwingUtilities.invokeLater(() -> {
            labels[i].setText(String.valueOf(array[i]));
            labels[j].setText(String.valueOf(array[j]));
            labels[i].setBackground(Color.orange); // Color for swapped elements
            labels[j].setBackground(Color.orange); // Color for swapped elements
        });
    }

    private void highlightSorted() {
        for (JLabel label : labels) {
            label.setBackground(Color.GREEN); // Color for sorted elements
        }
    }

    private void resetSorting() {
        inputField.setText("");
        sortingPanel.removeAll();
        pivotLabel.setText("Pivot: ");
        currentYOffset = 100; // Reset offset
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    private void updateSortingPanelSize() {
        int width = sortingPanel.getPreferredSize().width;
        int height = currentYOffset + BLOCK_SIZE + 50; // Add extra height
        sortingPanel.setPreferredSize(new Dimension(width, height));
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisualQuickSort::new);
    }
}
