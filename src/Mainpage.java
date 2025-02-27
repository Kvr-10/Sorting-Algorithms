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

        qk.setToolTipText("Divide and Conquer: Partitions array around a pivot.");
        mrq.setToolTipText("Divide and Conquer: Divides the array and merges sorted halves.");
        is.setToolTipText("A simple sorting algorithm that builds the final sorted array one item at a time.");
        aboutUs.setToolTipText("Learn more about the developers.");


        setTitle("VizNum");
        ImageIcon frameIcon = new ImageIcon(ClassLoader.getSystemResource("Icon/sorting-6.png"));
        setIconImage(frameIcon.getImage());
        setSize(800, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xD2C6EC));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel topLabel = new JLabel("Explore Sorting Algorithms", JLabel.CENTER);
        topLabel.setBounds(400, 100, 400, 30);
        topLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(topLabel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/sort1.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 363, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 400, 363);
        add(image);

        ImageIcon ii2 = new ImageIcon(ClassLoader.getSystemResource("Icon/Bvp.png"));
        Image ii3 = ii2.getImage().getScaledInstance(360, 70, Image.SCALE_SMOOTH);
        ImageIcon i4 = new ImageIcon(ii3);
        JLabel bvp = new JLabel(i4);
        bvp.setBounds(390, 20, 400, 70);
        add(bvp);

        // Configure buttons
        is.setBounds(530, 145, 120, 22);
        is.setBackground(new Color(0xC5BE67));
        is.setForeground(Color.white);
        is.addActionListener(this);

        mrq.setBounds(530, 195, 120, 22);
        mrq.setBackground(new Color(0xC5BE67));
        mrq.setForeground(Color.white);
        mrq.addActionListener(this);

        qk.setBounds(530, 245, 120, 22);
        qk.setBackground(new Color(0xC5BE67));
        qk.setForeground(Color.white);
        qk.addActionListener(this);

        aboutUs.setBounds(675, 330, 100, 21);
        aboutUs.setBackground(new Color(0x0E90BA));
        aboutUs.setForeground(Color.white);
        aboutUs.addActionListener(this);

        // Add buttons to the frame
        add(qk);
        add(is);
        add(mrq);

        add(aboutUs);

        // Add description labels (taglines) under each algorithm button
        JLabel isDesc = new JLabel("Builds sorted array gradually", JLabel.CENTER);
        isDesc.setBounds(525, 165, 130, 15);
        isDesc.setFont(new Font("Serif", Font.ITALIC, 10));
        add(isDesc);

        JLabel mrqDesc = new JLabel("Merges sorted sub-arrays", JLabel.CENTER);
        mrqDesc.setBounds(530, 215, 120, 15);
        mrqDesc.setFont(new Font("Serif", Font.ITALIC, 10));
        add(mrqDesc);

        JLabel qkDesc = new JLabel("Partitions array using pivot", JLabel.CENTER);
        qkDesc.setBounds(530, 265, 120, 15);
        qkDesc.setFont(new Font("Serif", Font.ITALIC, 10));
        add(qkDesc);

        JLabel version = new JLabel("Version: 3.0.1.1", JLabel.CENTER);
        version.setBounds(400, 340, 120, 15);
        version.setFont(new Font("Serif", Font.PLAIN, 13));
        add(version);

        // Display the frame
        setResizable(false);
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
            String message = "Developed By:\n"
                    + "           Aryan Sharma\n"
                    + "           Chaman Sinha\n"
                    + "           Sanjana Ghadge\n"
                    + "           Sneha Bansal\n\n"
                    + "Under the guidance of:\n"
                    +"        Prof. Sheetal Patil\n"
                    +"        Bharati Vidyapeeth,\n"
                    +"        College of Engineering,Pune\n\n"
                    + "Reach us at:\n"
                    + "          viznum2025@gmail.com\n";

            JOptionPane.showMessageDialog(this, message, "About Us", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
