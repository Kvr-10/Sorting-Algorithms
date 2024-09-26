import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualInsertionSort extends JFrame {
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
    private JLabel currentElementLabel;
    private JPanel inputPanel;
    private JPanel sortingPanel;
    private int currentYOffset = 100; // Vertical offset for placing new sets of labels
    private JScrollPane scrollPane;

    public VisualInsertionSort() {
        setTitle("Visual Insertion Sort");
        setSize(920, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        initializeComponents();
        setButtonActions();
    }

    private void initializeComponents() {
        inputField = new JTextField(20);
        startButton = new JButton("Insertion Sort");
        resetButton = new JButton("Reset");
        infoButton = new JButton("Show Info");
        back = new JButton("Back");
        howItWorksButton = new JButton("How It Works");
        currentElementLabel = new JLabel("Current Element: ", JLabel.CENTER);

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

        currentElementLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(currentElementLabel, BorderLayout.SOUTH);
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
                + "<h2>Insertion Sort Algorithm</h2>"
                + "<p>Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time.</p>"
                + "<h3>Time Complexity:</h3>"
                + "<ul>"
                + "<li>Best Case: O(n)</li>"
                + "<li>Average Case: O(n^2)</li>"
                + "<li>Worst Case: O(n^2)</li>"
                + "</ul>"
                + "<h3>Space Complexity:</h3>"
                + "<ul>"
                + "<li>O(1) since no extra space is used.</li>"
                + "</ul>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, info, "Insertion Sort Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHowItWorksDialog() {
        String explanation = "<html><body>"
                + "<h2>How Insertion Sort Works</h2>"
                + "<p>Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time.</p>"
                + "<p>The algorithm works as follows:</p>"
                + "<ul>"
                + "<li><strong>Initial Pass:</strong> The algorithm starts from the second element, comparing it with the elements before it.</li>"
                + "<li><strong>Shifting:</strong> If the current element (key) is smaller than the compared elements,<br>"+ "those elements are shifted one position to the right to make space for the key.</li>"
                + "<li><strong>Insertion:</strong> Once the correct position for the key is found, it is inserted into that position.</li>"
                + "<li><strong>Repeat:</strong> This process repeats for each element in the array until the entire array is sorted.</li>"
                + "</ul>"
                + "<h3>About the Code:</h3>"
                + "<p>The provided code visualizes the Insertion Sort algorithm by updating the graphical representation of the array after each insertion.</p>"
                + "<p>Key elements in the code include:</p>"
                + "<ul>"
                + "<li><strong>Array Initialization:</strong> The input numbers are read from a text field and stored in an array.</li>"
                + "<li><strong>Label Creation:</strong> For each element, a label is created to visually represent it in the GUI.</li>"
                + "<li><strong>Sorting Process:</strong> The sorting logic is implemented in a separate thread to keep the GUI responsive.<br>"+ " Each step of the sorting process is visualized with a delay.</li>"
                + "<li><strong>Color Indication:</strong> The current key being compared is highlighted in yellow, <br>"+ "while the sorted elements turn green upon completion.</li>"
                + "</ul>"
                + "<p>The visualization helps to understand how the algorithm processes the array and moves elements around until the array is sorted.</p>"
                + "</body></html>";

        JOptionPane.showMessageDialog(this, explanation, "How It Works", JOptionPane.INFORMATION_MESSAGE);
    }


    private void startSorting() {
        new Thread(() -> {
            try {
                insertionSort(array);
                SwingUtilities.invokeLater(this::highlightSorted);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void insertionSort(int[] arr) throws InterruptedException {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Highlight the current key being sorted
            int finalI = i;
            SwingUtilities.invokeLater(() -> {
                labels[finalI].setBackground(Color.YELLOW); // Highlight the current element in yellow
                currentElementLabel.setText("Current Element: " + key); // Update current element label
            });
            Thread.sleep(DELAY);

            while (j >= 0 && arr[j] > key) {
                // Highlight the element being compared
                final int currentJ = j; // capture `j` for use in lambda
                SwingUtilities.invokeLater(() -> {
                    labels[currentJ].setBackground(Color.ORANGE); // Compare element
                    labels[finalI].setBackground(Color.YELLOW); // Keep key highlighted
                });
                Thread.sleep(DELAY);

                arr[j + 1] = arr[j];
                j = j - 1;

                // Update the array visualization at each step
                final int[] currentArray = arr.clone();
                SwingUtilities.invokeLater(() -> updateLabels());
                Thread.sleep(DELAY);

                // Reset the compared element's color back to cyan
                SwingUtilities.invokeLater(() -> labels[currentJ].setBackground(Color.CYAN));
            }

            arr[j + 1] = key;

            // Re-add the labels after placing the key in the correct spot
            SwingUtilities.invokeLater(() -> {
                labels[finalI].setBackground(Color.CYAN); // Reset key color after placing
                addLabels(arr.clone(), currentYOffset += BLOCK_SIZE + 10); // Move to new Y-offset for the next step
            });
            Thread.sleep(DELAY);
        }
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
        currentElementLabel.setText("Current Element: ");
        currentYOffset = 100; // Reset offset when resetting
        updateSortingPanelSize(); // Reset panel size when clearing content
        sortingPanel.revalidate();
        sortingPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VisualInsertionSort().setVisible(true);
        });
    }
}