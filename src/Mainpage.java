import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainpage extends JFrame implements ActionListener {
    private JButton qk, mrq, is, aboutUs;

    public Mainpage() {
        // Initialize the buttons
        qk = new JButton("Quick Sort");
        mrq = new JButton("Merge Sort");
        is = new JButton("Insertion Sort");
        aboutUs = new JButton("About Us");

        // Set frame properties
        setTitle("Sorting Visualizer");
        setSize(800, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xF0D8FF));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Add top label with information
        JLabel topLabel = new JLabel("Sorting using Divide and Conquer approach", JLabel.CENTER);
        topLabel.setBounds(400, 20, 400, 30);
        topLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(topLabel);

        // Add image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/sort1.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 363, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 400, 363);
        add(image);

        // Configure buttons
        qk.setBounds(530, 100, 120, 22);
        qk.setBackground(new Color(0xC5BE67));
        qk.setForeground(Color.white);
        qk.addActionListener(this);

        is.setBounds(530, 150, 120, 22);
        is.setBackground(new Color(0xC5BE67));
        is.setForeground(Color.white);
        is.addActionListener(this);

        mrq.setBounds(530, 200, 120, 22);
        mrq.setBackground(new Color(0xC5BE67));
        mrq.setForeground(Color.white);
        mrq.addActionListener(this);

        aboutUs.setBounds(675, 330, 100, 21);
        aboutUs.setBackground(new Color(0x0E90BA));
        aboutUs.setForeground(Color.white);
        aboutUs.addActionListener(this);

        // Add buttons to the frame
        add(qk);
        add(is);
        add(mrq);

        add(aboutUs);

        // Display the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mainpage::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == qk) {
            // Open the Quick Sort visualizer and hide the main page
            new VisualQuickSort();
            setVisible(false);
        } else if (e.getSource() == mrq) {
            // Open the Merge Sort visualizer and hide the main page
            new VisualMergeSort();
            setVisible(false);
        } else if (e.getSource() == is) {
            // Open the Insertion Sort visualizer and hide the main page
            new VisualInsertionSort();
            setVisible(false);
        } else if (e.getSource() == aboutUs) {
            // Display 'About Us' information using JOptionPane
            String message = "Made By:\n"
                    + "             Chaman Sinha\n"
                    + "             Aryan Sharma\n"
                    + "             Tanmay Malav\n"
                    + "             Soumyajit Khan\n"
                    + "             Pranav Hendre\n\n"
                    + "Reach Us At:\n"
                    + "https://github.com/Kvr-10/Sorting-Algorithms\n";

            JOptionPane.showMessageDialog(this, message, "About Us", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
