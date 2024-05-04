package edu.citmss4semjp.atmsimulator;

//import javafx.*;
import javax.swing.*; // Package for GUI components
import javax.swing.border.EmptyBorder; // Class for creating empty borders for Swing components
import java.awt.*; // Package for Abstract Window Toolkit components

public class ATMUISwing extends JFrame {
    // Text variables for UI
    String TITLE = "ATM Simulator";
    String BANK_NAME = "KASE Bank";
    String WELCOME_TEXT = "welcomes you!";
    String WELCOME_INS = "Please insert card";
    String RECEIPT_TITLE = "RECEIPT: ";
    String ACC_NO = "Account Number: ";
    String PIN = "Pin: ";
    int WIDTH = 1000;
    int HEIGHT = 600;

    public ATMUISwing() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create panels for UI components
        JPanel leftPanel1 = new JPanel();
        JPanel leftPanel2 = new JPanel();
        JPanel leftPanel3 = new JPanel();
        JPanel rightPanel1 = new JPanel();
        JPanel rightPanel2 = new JPanel();
        JPanel rightPanel3 = new JPanel();

        // Panel coloring
        leftPanel1.setBackground(new Color(11, 58, 151));
        leftPanel2.setBackground(new Color(11, 58, 151));
        leftPanel3.setBackground(new Color(11, 58, 151));

        // Left pane splits
        // Split the left pane for LBorder & Display
        JSplitPane leftSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel1, leftPanel2);
        leftSplitPane1.setResizeWeight(0.1); // Proportional pane split
        leftSplitPane1.setEnabled(false); // disables panel size adjustments

        // Split the left pane for (LBorder & Display) & RBorder
        JSplitPane leftSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane1, leftPanel3);
        leftSplitPane2.setResizeWeight(0.9);
        leftSplitPane2.setEnabled(false);

        // Right pane splits
        // Split the right pane for Receipt View & NumPad
        JSplitPane rightSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightPanel1, rightPanel2);
        rightSplitPane1.setResizeWeight(0.5);
        rightSplitPane1.setEnabled(false);

        // Split the right pane for (Receipt View & NumPad) & User Details
        JSplitPane rightSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightSplitPane1, rightPanel3);
        rightSplitPane2.setResizeWeight(0.85);
        rightSplitPane2.setEnabled(false);

        // Main frame split
        // Split the main frame for Left Pane & Right Pane
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane2, rightSplitPane2);
        mainSplitPane.setResizeWeight(0.8);
        mainSplitPane.setEnabled(false);
        
        // Panel Contents:-
        // Left Pane:
        // LBorder Panel
        JPanel sideBtns1 = new JPanel(new GridLayout(4, 1, 0, 10));
        sideBtns1.setBorder(new EmptyBorder(1, 1, 1, 1));
        sideBtns1.setBackground(new Color(11, 58, 151));
        // >1
        JButton lBtn1 = new JButton(">");
        lBtn1.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns1.add(lBtn1); // Add the button to the grid
        lBtn1.setBackground(new Color(57, 160, 228));

        // >2
        JButton lBtn2 = new JButton(">");
        lBtn2.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns1.add(lBtn2);
        lBtn2.setBackground(new Color(57, 160, 228));

        // >3
        JButton lBtn3 = new JButton(">");
        lBtn3.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns1.add(lBtn3);
        lBtn3.setBackground(new Color(57, 160, 228));

        // >4
        JButton lBtn4 = new JButton(">");
        lBtn4.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns1.add(lBtn4);
        lBtn4.setBackground(new Color(57, 160, 228));
        // Add the grid to LBorder
        leftPanel1.add(sideBtns1);

        // Display Panel
        leftPanel2.setLayout(new BoxLayout(leftPanel2, BoxLayout.Y_AXIS));
        JLabel bankName = new JLabel(BANK_NAME, SwingConstants.CENTER);
        bankName.setFont(new Font("Arial", Font.BOLD, 50));
        leftPanel2.add(bankName, BorderLayout.NORTH);
        leftPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        bankName.setForeground(new Color(255, 255, 255));

        JLabel welcomeText = new JLabel(WELCOME_TEXT, SwingConstants.CENTER);
        welcomeText.setFont(new Font("Arial", Font.BOLD, 30));
        leftPanel2.add(welcomeText, BorderLayout.NORTH);
        welcomeText.setForeground(new Color(255, 255, 255));

        JLabel welcomeIns = new JLabel(WELCOME_INS, SwingConstants.CENTER);
        welcomeIns.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel2.add(welcomeIns, BorderLayout.SOUTH);
        welcomeIns.setForeground(new Color(255, 255, 255));

        // RBorder Panel
        JPanel sideBtns2 = new JPanel(new GridLayout(4, 1, 0, 10));
        sideBtns2.setBorder(new EmptyBorder(1, 1, 1, 1));
        sideBtns2.setBackground(new Color(11, 58, 151));

        // <1
        JButton rBtn1 = new JButton("<");
        rBtn1.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns2.add(rBtn1);
        rBtn1.setBackground(new Color(57, 160, 228));

        // <2
        JButton rBtn2 = new JButton("<");
        rBtn2.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns2.add(rBtn2);
        rBtn2.setBackground(new Color(57, 160, 228));

        // <3
        JButton rBtn3 = new JButton("<");
        rBtn3.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns2.add(rBtn3);
         rBtn3.setBackground(new Color(57, 160, 228));

        // <4
        JButton rBtn4 = new JButton("<");
        rBtn4.setFont(new Font("Arial", Font.BOLD, 20));
        sideBtns2.add(rBtn4);
        rBtn4.setBackground(new Color(57, 160, 228));

        // Add to RBorder
        leftPanel3.add(sideBtns2);

        // Right Pane:
        // Receipt Panel
        JLabel receiptTitle = new JLabel(RECEIPT_TITLE, SwingConstants.CENTER);
        receiptTitle.setFont(new Font("Arial", Font.BOLD, 50));
        rightPanel1.add(receiptTitle, BorderLayout.NORTH);

        // Numpad Panel
        // Numpad Grid
        JPanel numPadGrid = new JPanel(new GridLayout(4, 4, 5, 5));
        numPadGrid.setBorder(new EmptyBorder(5, 5, 5, 5));
        numPadGrid.setBackground(new Color(11, 58, 151));
        // 1
        JButton btn1 = new JButton("1");
        btn1.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn1);
        // 2
        JButton btn2 = new JButton("2");
        btn2.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn2);
        // 3
        JButton btn3 = new JButton("3");
        btn3.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn3);
        // Cancel
        JButton cnlBtn = new JButton("Cancel");
        cnlBtn.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(cnlBtn);
        // 4
        JButton btn4 = new JButton("4");
        btn4.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn4);
        // 5
        JButton btn5 = new JButton("5");
        btn5.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn5);
        // 6
        JButton btn6 = new JButton("6");
        btn6.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn6);
        // Clear
        JButton clrBtn = new JButton("Clear");
        clrBtn.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(clrBtn);
        // 7
        JButton btn7 = new JButton("7");
        btn7.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn7);
        // 8
        JButton btn8 = new JButton("8");
        btn8.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn8);
        // 9
        JButton btn9 = new JButton("9");
        btn9.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn9);
        // Enter
        JButton enrBtn = new JButton("Enter");
        enrBtn.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(enrBtn);
        // Empty Button 1
        JButton mtBtn1 = new JButton("");
        mtBtn1.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(mtBtn1);
        // 0
        JButton btn0 = new JButton("0");
        btn0.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(btn0);
        // Empty Button 2
        JButton mtBtn2 = new JButton("");
        mtBtn2.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(mtBtn2);
        // Empty Button 3
        JButton mtBtn3 = new JButton("");
        mtBtn3.setFont(new Font("Arial", Font.BOLD, 16));
        numPadGrid.add(mtBtn3);
        // Set the NumPad grid in the NumPad panel
        rightSplitPane1.setBottomComponent(numPadGrid);

        // User Details Panel
        JPanel usrDeets = new JPanel(new GridLayout(3, 2, 0, 5));
        usrDeets.setBorder(new EmptyBorder(1, 1, 1, 1));
        usrDeets.setBackground(new Color(11, 58, 151));

        JLabel usrAccNo = new JLabel("Account Number", SwingConstants.CENTER);
        usrAccNo.setFont(new Font("Arial", Font.BOLD, 16));
        usrDeets.add(usrAccNo, BorderLayout.NORTH);
        welcomeText.setForeground(new Color(255, 255, 255));

        JTextField txf = new JTextField("Enter Account Number");
        txf.setForeground(new Color(255, 255, 255));

        rightPanel3.add(usrDeets);

        // Add main pane to frame
        getContentPane().add(mainSplitPane);
    }

    public static void main(String[] args) {
        ATMUISwing atmUI = new ATMUISwing();
        atmUI.setVisible(true);
    }
}
