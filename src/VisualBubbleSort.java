import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VisualBubbleSort extends JFrame {
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
    private Voice currentVoice = null;

    public VisualBubbleSort() {
        setTitle("VizNum - Bubble Sort");
        ImageIcon frameIcon = new ImageIcon(ClassLoader.getSystemResource("Icon/sorting-6.png"));
        setIconImage(frameIcon.getImage());
        setSize(970, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);

        initializeComponents();
        setButtonActions();
    }

    private void initializeComponents() {
        inputField = new JTextField(20);
        startButton = new JButton("Bubble Sort");
        resetButton = new JButton("Reset");
        infoButton = new JButton("Time Complexity");
        back = new JButton("Back");
        howItWorksButton = new JButton("How It Works");
        currentElementLabel = new JLabel("Current Element: ", JLabel.CENTER);

        startButton.setToolTipText("Start the sorting process using the Bubble Sort algorithm.");
        resetButton.setToolTipText("Reset the input field and clear the visualization to start over.");
        infoButton.setToolTipText("View the time and space complexity of the Bubble Sort algorithm.");
        howItWorksButton.setToolTipText("Learn how the Bubble Sort algorithm works step by step.");
        back.setToolTipText("Return to the main menu.");

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
                + "<h2>Bubble Sort Algorithm</h2>"
                + "<p>Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.</p>"
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

        JOptionPane.showMessageDialog(this, info, "Bubble Sort Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHowItWorksDialog() {
        String explanation = "<html><body>"
                + "<h2>How Bubble Sort Works</h2>"
                + "<p>Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.</p>"
                + "<p>The algorithm works as follows:</p>"
                + "<ul>"
                + "<li><strong>Initial Pass:</strong> The algorithm starts from the first element, comparing it with the next element.</li>"
                + "<li><strong>Swapping:</strong> If the current element is greater than the next element, they are swapped.</li>"
                + "<li><strong>Repeat:</strong> This process repeats for each pair of adjacent elements until the entire array is sorted.</li>"
                + "</ul>"
                + "<h3>About the Code:</h3>"
                + "<p>The provided code visualizes the Bubble Sort algorithm by updating the graphical representation of the array after each swap.</p>"
                + "<p>Key elements in the code include:</p>"
                + "<ul>"
                + "<li><strong>Array Initialization:</strong> The input numbers are read from a text field and stored in an array.</li>"
                + "<li><strong>Label Creation:</strong> For each element, a label is created to visually represent it in the GUI.</li>"
                + "<li><strong>Sorting Process:</strong> The sorting logic is implemented in a separate thread to keep the GUI responsive.<br> Each step of the sorting process is visualized with a delay.</li>"
                + "<li><strong>Color Indication:</strong> The elements being compared are highlighted in yellow,<br> while the sorted elements turn orange upon completion.</li>"
                + "</ul>"
                + "<p>The visualization helps to understand how the algorithm processes the array and moves elements around until the array is sorted.</p>"
                + "</body></html>";

        String explanationText = "How Bubble Sort Works. Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order. "

                + "The algorithm starts from the first element, comparing it with the next element. "
                + "If the current element is greater than the next element, they are swapped. "
                + "This process repeats for each pair of adjacent elements until the entire array is sorted. "
                + "The visualization updates the graphical representation after each swap step.";

        // Start speech in a new thread so that it begins immediately
        Thread speechThread = new Thread(() -> speakText(explanationText));
        speechThread.start();

        // Show the modal dialog; this blocks until the user presses OK
        JOptionPane.showMessageDialog(this, explanation, "How It Works", JOptionPane.INFORMATION_MESSAGE);

        // When the dialog is dismissed, cancel the speech if it's still in progress
        if (currentVoice != null && currentVoice.getAudioPlayer() != null) {
            currentVoice.getAudioPlayer().cancel();
        }
    }

    private void speakText(String text) {
        // Specify only the Kevin voice directory to avoid casting issues
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        currentVoice = VoiceManager.getInstance().getVoice("kevin16");
        if (currentVoice != null) {
            currentVoice.allocate();
            currentVoice.setRate(150);   // Speed (default ~160)
            currentVoice.setPitch(100);  // Adjust pitch
            currentVoice.setVolume(1.0f); // Volume (0.0 - 1.0)
            currentVoice.speak(text);
            currentVoice.deallocate();
        } else {
            System.err.println("Voice not found!");
        }
    }

    private void startSorting() {
        new Thread(() -> {
            try {
                bubbleSort(array);
                SwingUtilities.invokeLater(this::highlightSorted);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void bubbleSort(int[] arr) throws InterruptedException {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Highlight the elements being compared
                final int currentJ = j;
                final int nextJ = j + 1;
                SwingUtilities.invokeLater(() -> {
                    labels[currentJ].setBackground(Color.YELLOW);
                    labels[nextJ].setBackground(Color.YELLOW);
                    currentElementLabel.setText("Comparing: " + arr[currentJ] + " and " + arr[nextJ]);
                });
                Thread.sleep(DELAY);

                if (arr[j] > arr[j + 1]) {
                    // Swap the elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    // Update the array visualization after swapping
                    SwingUtilities.invokeLater(() -> {
                        labels[currentJ].setText(String.valueOf(arr[currentJ]));
                        labels[nextJ].setText(String.valueOf(arr[nextJ]));
                    });
                    Thread.sleep(DELAY);
                }

                // Reset the colors after comparison
                SwingUtilities.invokeLater(() -> {
                    labels[currentJ].setBackground(Color.CYAN);
                    labels[nextJ].setBackground(Color.CYAN);
                });
            }

            // Mark the last element as sorted
            final int sortedIndex = n - i - 1;
            SwingUtilities.invokeLater(() -> labels[sortedIndex].setBackground(Color.ORANGE));

            // Move the visualization downwards
            currentYOffset += BLOCK_SIZE + 10; // Increment Y-offset for the next step
            SwingUtilities.invokeLater(() -> {
                addLabels(arr.clone(), currentYOffset); // Re-render the array at the new Y-offset
                updateSortingPanelSize(); // Update the panel size
            });
            Thread.sleep(DELAY);
        }
    }

    private void highlightSorted() {
        SwingUtilities.invokeLater(() -> {
            for (JLabel label : labels) {
                label.setBackground(Color.GREEN);
            }

            currentElementLabel.setText("");
        });
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
            new VisualBubbleSort().setVisible(true);
        });
    }
}