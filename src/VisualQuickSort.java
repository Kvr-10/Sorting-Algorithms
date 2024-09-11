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
    private JButton back;
    private JPanel inputPanel;
    private JPanel sortingPanel;
    private int currentYOffset = 100; // Vertical offset for placing new sets of labels
    private JScrollPane scrollPane;

    public VisualQuickSort() {
        setTitle("Visual Quick Sort");
        setSize(850, 600);
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
        back = new JButton("Back");

        inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter numbers separated by commas:"));
        inputPanel.add(inputField);
        inputPanel.add(startButton);
        inputPanel.add(resetButton);
        inputPanel.add(infoButton);
        inputPanel.add(back);
        add(inputPanel, BorderLayout.NORTH);

        sortingPanel = new JPanel();
        sortingPanel.setLayout(null);
        scrollPane = new JScrollPane(sortingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
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
        updateSortingPanelSize(); // Update size of the sorting panel after adding labels
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
        updateSortingPanelSize(); // Ensure the panel's size reflects the content
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
            SwingUtilities.invokeLater(() -> addLabels(arr.clone(), currentYOffset += BLOCK_SIZE + 10)); // Add new visualization after partition
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) throws InterruptedException {
        int pivot = arr[high];
        int i = low - 1;

        SwingUtilities.invokeLater(() -> labels[high].setBackground(Color.RED)); // Highlight pivot
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
                updateLabels();
                Thread.sleep(DELAY);
            }
        }
        swap(arr, i + 1, high);
        updateLabels();
        Thread.sleep(DELAY);
        SwingUtilities.invokeLater(() -> labels[high].setBackground(Color.CYAN)); // Reset pivot color
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        // Swap labels
        SwingUtilities.invokeLater(() -> {
            String tempText = labels[i].getText();
            labels[i].setText(labels[j].getText());
            labels[j].setText(tempText);
        });
    }

    private void updateLabels() {
        SwingUtilities.invokeLater(() -> {
            int panelWidth = sortingPanel.getWidth();
            int totalBlocksWidth = array.length * BLOCK_SIZE;
            int startX = (panelWidth - totalBlocksWidth) / 2;

            for (int i = 0; i < array.length; i++) {
                labels[i].setBounds(startX + i * BLOCK_SIZE, currentYOffset, BLOCK_SIZE, BLOCK_SIZE);
            }
            updateSortingPanelSize(); // Update sorting panel size after updating labels
        });
    }

    private void highlightSorted() {
        for (JLabel label : labels) {
            label.setBackground(Color.GREEN);
        }
    }

    private void updateSortingPanelSize() {
        int panelHeight = currentYOffset + BLOCK_SIZE + 20; // Add some extra padding
        sortingPanel.setPreferredSize(new Dimension(sortingPanel.getWidth(), panelHeight));
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    private void resetSorting() {
        inputField.setText("");
        sortingPanel.removeAll();
        currentYOffset = 100; // Reset offset when resetting
        updateSortingPanelSize(); // Reset panel size when clearing content
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VisualQuickSort().setVisible(true);
        });
    }
}
