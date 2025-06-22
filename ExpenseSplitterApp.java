import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.Timer;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;

public class ExpenseSplitterApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseSplitterApp app = new ExpenseSplitterApp();
            app.initialize();
        });
    }

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ExpenseSplitter splitter;

    public ExpenseSplitterApp() {
        splitter = new ExpenseSplitter();
    }

    private void initialize() {
        frame = new JFrame("Expense Splitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        frame.add(mainPanel);

        // Create a reusable styling method for the app
        createWelcomePage();

        frame.setVisible(true);
    }

    // Styling utility to make buttons consistent
    private void styleButton(JButton button, Color bgColor, Color fgColor, int fontSize) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, fontSize));
    }

    // Styling utility for labels
    private void styleLabel(JLabel label, Color fgColor, int fontSize) {
        label.setForeground(fgColor);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
    }

    @SuppressWarnings("unused")
    private void createWelcomePage() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(230, 240, 255)); // Light blue

        JLabel welcomeLabel = new JLabel("Welcome to SplitWit", JLabel.CENTER);
        styleLabel(welcomeLabel, new Color(30, 60, 90), 32);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        Timer timer = new Timer(2000, e -> createLoginPage());
        timer.setRepeats(false);
        timer.start();

        mainPanel.add(welcomePanel, "Welcome");
        cardLayout.show(mainPanel, "Welcome");
    }

    @SuppressWarnings("unused")
    private void createLoginPage() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255)); // Light blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginLabel = new JLabel("Login");
        styleLabel(loginLabel, new Color(30, 60, 90), 28);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        styleLabel(usernameLabel, new Color(30, 60, 90), 18);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        styleLabel(passwordLabel, new Color(30, 60, 90), 18);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(70, 130, 180), Color.WHITE, 18);
        loginButton.addActionListener(
                e -> validateLogin(usernameField.getText(), new String(passwordField.getPassword())));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        JButton signupButton = new JButton("Sign Up");
        styleButton(signupButton, new Color(205, 92, 92), Color.WHITE, 18);
        signupButton.addActionListener(e -> createSignUpPage());
        gbc.gridy = 4;
        loginPanel.add(signupButton, gbc);

        mainPanel.add(loginPanel, "Login");
        cardLayout.show(mainPanel, "Login");
    }

    private void validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Please enter both username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else if (!splitter.checkLogin(username, password)) {  // NEW CHECK
            JOptionPane.showMessageDialog(frame, 
                "Wrong username or password", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(frame, 
                "Login successful!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            createMainMenu();
        }
    }

    @SuppressWarnings("unused")
    private void createSignUpPage() {
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBackground(new Color(230, 240, 255)); // Light blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel signupLabel = new JLabel("Sign Up");
        styleLabel(signupLabel, new Color(30, 60, 90), 28);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signupPanel.add(signupLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        styleLabel(usernameLabel, new Color(30, 60, 90), 18);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        signupPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        signupPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        styleLabel(passwordLabel, new Color(30, 60, 90), 18);
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        signupPanel.add(passwordField, gbc);

        JButton signupButton = new JButton("Sign Up");
    styleButton(signupButton, new Color(76, 175, 80), Color.WHITE, 18); // Green color
    
    // 2. Configure layout constraints (replace existing gbc settings)
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(15, 50, 5, 50); // Top, left, bottom, right padding
    
    // 3. Add hover effects
    signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            signupButton.setBackground(new Color(56, 142, 60)); // Darker green
            signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            signupButton.setBackground(new Color(76, 175, 80));
        }
    });
    
    // 4. Keep original action with press animation
    signupButton.addActionListener(e -> {
        signupButton.setBackground(new Color(46, 125, 50)); // Pressed state
        Timer timer = new Timer(200, event -> {
            signupButton.setBackground(new Color(76, 175, 80));
            ((Timer)event.getSource()).stop();
            
            // Original signup logic
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String result = splitter.signUpUser(username, password);
            
            if (result.contains("successful")) {
                usernameField.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(frame, result, "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);
                createLoginPage();
            } else {
                JOptionPane.showMessageDialog(frame, result, "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        timer.start();
    });
    
    signupPanel.add(signupButton, gbc);
    
    // 5. Adjust back button spacing (modify existing back button constraints)
    gbc.gridy = 4;
    gbc.insets = new Insets(5, 50, 15, 50); // Reduced top padding, increased bottom


        JButton backButton = new JButton("Back to Login");
        styleButton(backButton, new Color(205, 92, 92), Color.WHITE, 18);
        backButton.addActionListener(e -> createLoginPage());
        gbc.gridy = 4;
        signupPanel.add(backButton, gbc);

        mainPanel.add(signupPanel, "Sign Up");
        cardLayout.show(mainPanel, "Sign Up");
    }

    @SuppressWarnings("unused")
    private void createMainMenu() {
        // Create the main menu panel with a modern background color
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding for all components

        // Title Label
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        menuLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainMenuPanel.add(menuLabel, gbc);

        // Create Group Button
        JButton createGroupButton = new JButton("Create Group");
        styleButton(createGroupButton, new Color(70, 130, 180), Color.WHITE, 18); // Reuse existing styleButton method
        createGroupButton.addActionListener(e -> createGroupPage());
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainMenuPanel.add(createGroupButton, gbc);

        // Add Expense Button
        JButton addExpenseButton = new JButton("Add Expense");
        styleButton(addExpenseButton, new Color(70, 130, 180), Color.WHITE, 18); // Reuse existing styleButton method
        addExpenseButton.addActionListener(e -> addExpensePage());
        gbc.gridy = 2;
        mainMenuPanel.add(addExpenseButton, gbc);

        // Display Summary Button
        JButton displaySummaryButton = new JButton("Display Summary");
        styleButton(displaySummaryButton, new Color(70, 130, 180), Color.WHITE, 18); // Reuse existing styleButton
                                                                                     // method
        displaySummaryButton.addActionListener(e -> displaySummaryPage());
        gbc.gridy = 3;
        mainMenuPanel.add(displaySummaryButton, gbc);

        // Settle Expense Button
        JButton settleExpenseButton = new JButton("Settle Expense");
        styleButton(settleExpenseButton, new Color(70, 130, 180), Color.WHITE, 18); // Reuse existing styleButton method
        settleExpenseButton.addActionListener(e -> settleExpensePage());
        gbc.gridy = 4;
        mainMenuPanel.add(settleExpenseButton, gbc);

        // Save/Load Data Button
        JButton saveLoadButton = new JButton("Save/Load Data");
        styleButton(saveLoadButton, new Color(70, 130, 180), Color.WHITE, 18); // Reuse existing styleButton method
        saveLoadButton.addActionListener(e -> saveLoadPage());
        gbc.gridy = 5;
        mainMenuPanel.add(saveLoadButton, gbc);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(205, 92, 92), Color.WHITE, 18); // Red button for logout
        logoutButton.addActionListener(e -> createLoginPage());
        gbc.gridy = 6;
        mainMenuPanel.add(logoutButton, gbc);

        // Add the main menu panel to the main layout and show it
        mainPanel.add(mainMenuPanel, "Main Menu");
        cardLayout.show(mainPanel, "Main Menu");
    }

    @SuppressWarnings("unused")
    private void createGroupPage() {
        // Create the panel with a modern layout and background color
        JPanel createGroupPanel = new JPanel(new GridBagLayout());
        createGroupPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding for all components

        // Title Label
        JLabel groupLabel = new JLabel("Create Group");
        groupLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        groupLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        createGroupPanel.add(groupLabel, gbc);

        // Group Name Label
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        groupNameLabel.setForeground(new Color(30, 60, 90));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        createGroupPanel.add(groupNameLabel, gbc);

        // Group Name Field
        JTextField groupNameField = new JTextField(20);
        groupNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        createGroupPanel.add(groupNameField, gbc);

        // Members Label
        JLabel membersLabel = new JLabel("Members (comma-separated):");
        membersLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        membersLabel.setForeground(new Color(30, 60, 90));
        gbc.gridx = 0;
        gbc.gridy = 2;
        createGroupPanel.add(membersLabel, gbc);

        // Members Field
        JTextField membersField = new JTextField(20);
        membersField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        createGroupPanel.add(membersField, gbc);

        // Create Button
        JButton createButton = new JButton("Create");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        createButton.setBackground(new Color(70, 130, 180)); // Steel blue button
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            String[] members = membersField.getText().split(",");
            if (groupName.isEmpty() || members.length == 0) {
                JOptionPane.showMessageDialog(frame, "Group name and members cannot be empty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String result = splitter.createGroup(groupName, Arrays.asList(members));
                JOptionPane.showMessageDialog(frame, result, "Create Group", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        createGroupPanel.add(createButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBackground(new Color(205, 92, 92)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> createMainMenu());
        gbc.gridy = 4;
        createGroupPanel.add(backButton, gbc);

        // Add the panel to the main layout
        mainPanel.add(createGroupPanel, "Create Group");
        cardLayout.show(mainPanel, "Create Group");
    }

    @SuppressWarnings("unused")
    private void addExpensePage() {
        // Panel Setup
        JPanel addExpensePanel = new JPanel(new GridBagLayout());
        addExpensePanel.setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel expenseLabel = new JLabel("Add Expense");
        styleLabel(expenseLabel, new Color(30, 60, 90), 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addExpensePanel.add(expenseLabel, gbc);

        // Group Name
        JLabel groupNameLabel = new JLabel("Group Name:");
        styleLabel(groupNameLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        addExpensePanel.add(groupNameLabel, gbc);

        JTextField groupNameField = new JTextField(20);
        groupNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        addExpensePanel.add(groupNameField, gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        styleLabel(descriptionLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addExpensePanel.add(descriptionLabel, gbc);

        JTextField descriptionField = new JTextField(20);
        descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        addExpensePanel.add(descriptionField, gbc);

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        styleLabel(amountLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 3;
        addExpensePanel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(20);
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        addExpensePanel.add(amountField, gbc);

        // Payer
        JLabel payerLabel = new JLabel("Payer:");
        styleLabel(payerLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 4;
        addExpensePanel.add(payerLabel, gbc);

        JTextField payerField = new JTextField(20);
        payerField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        addExpensePanel.add(payerField, gbc);

        // Split Type (ComboBox)
        JLabel splitTypeLabel = new JLabel("Split Type:");
        styleLabel(splitTypeLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 5;
        addExpensePanel.add(splitTypeLabel, gbc);

        JComboBox<String> splitTypeCombo = new JComboBox<>(new String[] { "equally", "unequally" });
        splitTypeCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 5;
        addExpensePanel.add(splitTypeCombo, gbc);

        // Involved Members
        JLabel involvedMembersLabel = new JLabel("Involved Members (comma-separated):");
        styleLabel(involvedMembersLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 6;
        addExpensePanel.add(involvedMembersLabel, gbc);

        JTextField involvedMembersField = new JTextField(20);
        involvedMembersField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 6;
        addExpensePanel.add(involvedMembersField, gbc);

        // Dynamic Amount Fields Panel
        JPanel splitAmountsPanel = new JPanel();
        splitAmountsPanel.setLayout(new BoxLayout(splitAmountsPanel, BoxLayout.Y_AXIS));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addExpensePanel.add(splitAmountsPanel, gbc);

        // Manual Trigger Button (Simpler than FocusListener)
        JButton showAmountFieldsBtn = new JButton("Show Amount Fields");
        styleButton(showAmountFieldsBtn, new Color(100, 150, 200), Color.WHITE, 14);
        showAmountFieldsBtn
                .addActionListener(e -> updateAmountFields(splitTypeCombo, involvedMembersField, splitAmountsPanel));
        gbc.gridx = 1;
        gbc.gridy = 8;
        addExpensePanel.add(showAmountFieldsBtn, gbc);

        // Add Expense Button
        JButton addExpenseButton = new JButton("Add Expense");
        styleButton(addExpenseButton, new Color(70, 130, 180), Color.WHITE, 16);
        addExpenseButton.addActionListener(e -> {
            try {
                // Validate inputs
                String groupName = groupNameField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String payer = payerField.getText();
                String splitType = (String) splitTypeCombo.getSelectedItem();
                List<String> involvedMembers = Arrays.asList(involvedMembersField.getText().split("\\s*,\\s*"));

                // Handle unequal splits
                List<Double> splitAmounts = null;
                if ("unequally".equals(splitType)) {
                    splitAmounts = new ArrayList<>();
                    Component[] rows = splitAmountsPanel.getComponents();
                    for (Component row : rows) {
                        if (row instanceof JPanel) {
                            for (Component comp : ((JPanel) row).getComponents()) {
                                if (comp instanceof JTextField) {
                                    splitAmounts.add(Double.parseDouble(((JTextField) comp).getText()));
                                }
                            }
                        }
                    }

                    // Validate sum
                    double sum = splitAmounts.stream().mapToDouble(d -> d).sum();
                    if (Math.abs(sum - amount) > 0.01) {
                        JOptionPane.showMessageDialog(frame,
                                String.format("Sum of amounts (%.2f) must equal total (%.2f)", sum, amount),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Add expense
                String result = splitter.addExpense(groupName, description, amount, payer, splitType, involvedMembers,
                        splitAmounts);
                JOptionPane.showMessageDialog(frame, result, "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        addExpensePanel.add(addExpenseButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        styleButton(backButton, new Color(205, 92, 92), Color.WHITE, 16);
        backButton.addActionListener(e -> createMainMenu());
        gbc.gridy = 10;
        addExpensePanel.add(backButton, gbc);

        mainPanel.add(addExpensePanel, "Add Expense");
        cardLayout.show(mainPanel, "Add Expense");
    }

    private void updateAmountFields(JComboBox<String> splitTypeCombo,
            JTextField involvedMembersField,
            JPanel splitAmountsPanel) {
        splitAmountsPanel.removeAll();
        if ("unequally".equals(splitTypeCombo.getSelectedItem())) {
            String[] members = involvedMembersField.getText().split("\\s*,\\s*");
            for (String member : members) {
                if (!member.isEmpty()) {
                    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                    row.add(new JLabel(member + ": "));
                    row.add(new JTextField(10));
                    splitAmountsPanel.add(row);
                }
            }
        }
        splitAmountsPanel.revalidate();
        splitAmountsPanel.repaint();
    }

    @SuppressWarnings("unused")
    private void displaySummaryPage() {
        // Create the panel with a light blue background
        JPanel displaySummaryPanel = new JPanel(new GridBagLayout());
        displaySummaryPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for all components

        // Title Label
        JLabel summaryLabel = new JLabel("Display Summary");
        styleLabel(summaryLabel, new Color(30, 60, 90), 24);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        displaySummaryPanel.add(summaryLabel, gbc);

        // Instruction Label
        JLabel instructionLabel = new JLabel("Enter Group Details to View Summary");
        styleLabel(instructionLabel, new Color(30, 60, 90), 18);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        displaySummaryPanel.add(instructionLabel, gbc);

        // Group Name Label
        JLabel groupNameLabel = new JLabel("Group Name:");
        styleLabel(groupNameLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        displaySummaryPanel.add(groupNameLabel, gbc);

        // Group Name Text Field
        JTextField groupNameField = new JTextField(20);
        groupNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        displaySummaryPanel.add(groupNameField, gbc);

        // Text Area for Summary with improved styling
        JLabel summaryTextLabel = new JLabel("Expense Summary:");
        styleLabel(summaryTextLabel, new Color(30, 60, 90), 16);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        displaySummaryPanel.add(summaryTextLabel, gbc);

        JTextArea summaryTextArea = new JTextArea(15, 40);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        summaryTextArea.setEditable(false);
        summaryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        summaryTextArea.setForeground(new Color(60, 60, 60));
        summaryTextArea.setBackground(new Color(255, 255, 255));
        summaryTextArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        displaySummaryPanel.add(scrollPane, gbc);

        // Display Summary Button
        JButton displayButton = new JButton("Display Summary");
        styleButton(displayButton, new Color(70, 130, 180), Color.WHITE, 16);
        displayButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            if (groupName.isEmpty()) {
                summaryTextArea.setText("Error: Please enter a group name to view summary.");
            } else {
                String result = splitter.displaySummary(groupName);
                summaryTextArea.setText(result);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        displaySummaryPanel.add(displayButton, gbc);

        // Back to Main Menu Button
        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton, new Color(205, 92, 92), Color.WHITE, 16);
        backButton.addActionListener(e -> createMainMenu());
        gbc.gridx = 1;
        gbc.gridy = 5;
        displaySummaryPanel.add(backButton, gbc);

        // Add the panel to the main layout
        mainPanel.add(displaySummaryPanel, "Display Summary");
        cardLayout.show(mainPanel, "Display Summary");
    }

    @SuppressWarnings("unused")
    private void settleExpensePage() {
        // Create the panel with a light blue background
        JPanel settleExpensePanel = new JPanel(new GridBagLayout());
        settleExpensePanel.setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for all components

        // Title Label
        JLabel settleLabel = new JLabel("Settle Expense");
        settleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        settleLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        settleExpensePanel.add(settleLabel, gbc);

        // Group Name Label
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        groupNameLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        settleExpensePanel.add(groupNameLabel, gbc);

        // Group Name Text Field
        JTextField groupNameField = new JTextField(20);
        groupNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        settleExpensePanel.add(groupNameField, gbc);

        // Payer Label
        JLabel payerLabel = new JLabel("Payer:");
        payerLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        payerLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 2;
        settleExpensePanel.add(payerLabel, gbc);

        // Payer Text Field
        JTextField payerField = new JTextField(20);
        payerField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        settleExpensePanel.add(payerField, gbc);

        // Payee Label
        JLabel payeeLabel = new JLabel("Payee:");
        payeeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        payeeLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 3;
        settleExpensePanel.add(payeeLabel, gbc);

        // Payee Text Field
        JTextField payeeField = new JTextField(20);
        payeeField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        settleExpensePanel.add(payeeField, gbc);

        // Amount Label
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        amountLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 4;
        settleExpensePanel.add(amountLabel, gbc);

        // Amount Text Field
        JTextField amountField = new JTextField(20);
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 4;
        settleExpensePanel.add(amountField, gbc);

        // Settle Expense Button
        JButton settleButton = new JButton("Settle Expense");
        settleButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        settleButton.setBackground(new Color(70, 130, 180)); // Steel blue button
        settleButton.setForeground(Color.WHITE);
        settleButton.setFocusPainted(false);
        settleButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            String payer = payerField.getText();
            String payee = payeeField.getText();
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid amount!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String result = splitter.settleExpense(groupName, payer, payee, amount);
            JOptionPane.showMessageDialog(frame, result, "Settle Expense", JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        settleExpensePanel.add(settleButton, gbc);

        // Back to Main Menu Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBackground(new Color(205, 92, 92)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> createMainMenu());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        settleExpensePanel.add(backButton, gbc);

        // Add the panel to the main layout
        mainPanel.add(settleExpensePanel, "Settle Expense");
        cardLayout.show(mainPanel, "Settle Expense");
    }

    @SuppressWarnings("unused")
    private void saveLoadPage() {
        // Create the panel with a light blue background
        JPanel saveLoadPanel = new JPanel(new GridBagLayout());
        saveLoadPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding for all components

        // Title Label
        JLabel saveLoadLabel = new JLabel("Save/Load Data");
        saveLoadLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        saveLoadLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        saveLoadPanel.add(saveLoadLabel, gbc);

        // Filename Label
        JLabel filenameLabel = new JLabel("Filename:");
        filenameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        filenameLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        saveLoadPanel.add(filenameLabel, gbc);

        // Filename Text Field
        JTextField filenameField = new JTextField(20);
        filenameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        saveLoadPanel.add(filenameField, gbc);

        // Save Button
        JButton saveButton = new JButton("Save Data");
        styleButton(saveButton, new Color(70, 130, 180), Color.WHITE, 18); // Steel blue button
        saveButton.addActionListener(e -> {
            String filename = filenameField.getText();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Filename cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = splitter.saveToFile(filename);
                JOptionPane.showMessageDialog(frame, result, "Save Data", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        saveLoadPanel.add(saveButton, gbc);

        // Load Button
        JButton loadButton = new JButton("Load Data");
        styleButton(loadButton, new Color(70, 130, 180), Color.WHITE, 18); // Steel blue button
        loadButton.addActionListener(e -> {
            String filename = filenameField.getText();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Filename cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = splitter.loadFromFile(filename);
                JOptionPane.showMessageDialog(frame, result, "Load Data", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 1;
        saveLoadPanel.add(loadButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        styleButton(backButton, new Color(205, 92, 92), Color.WHITE, 18); // Red button
        backButton.addActionListener(e -> createMainMenu());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        saveLoadPanel.add(backButton, gbc);

        // Add the panel to the main layout
        mainPanel.add(saveLoadPanel, "Save/Load Data");
        cardLayout.show(mainPanel, "Save/Load Data");
    }

    public class ExpenseSplitter {

        private Map<String, Group> groups;
        private Map<String, String> users;

        public ExpenseSplitter() {
            groups = new HashMap<>();
            users = new HashMap<>();
        }

    // NEW: Method to register users
    public String signUpUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "Username/password cannot be empty";
        }
        if (users.containsKey(username)) {
            return "Username already exists";
        }
        users.put(username, password);
        return "Signup successful!";
}

    // NEW: Method to check login credentials
    public boolean checkLogin(String username, String password) {
        return users.containsKey(username) && 
        users.get(username).equals(password);
}       

        public String createGroup(String groupName, List<String> members) {
            if (groups.containsKey(groupName)) {
                return "Group '" + groupName + "' already exists.";
            }
            Group newGroup = new Group(groupName, members);
            groups.put(groupName, newGroup);
            return "Group '" + groupName + "' created successfully!";
        }

        public String addExpense(String groupName, String description, double amount, String payer, String splitType,
                List<String> involvedMembers, List<Double> splitAmounts) {
            if (!groups.containsKey(groupName)) {
                return "Group '" + groupName + "' does not exist.";
            }

            Group group = groups.get(groupName);

            if (splitType.equalsIgnoreCase("equally")) {
                return group.addEqualExpense(description, amount, payer);
            } else if (splitType.equalsIgnoreCase("unequally")) {
                if (involvedMembers == null || splitAmounts == null || involvedMembers.size() != splitAmounts.size()) {
                    return "Error: Split amounts must match the number of involved members.";
                }
                return group.addUnequalExpense(description, amount, payer, involvedMembers, splitAmounts);
            } else {
                return "Invalid split type. Use 'equally' or 'unequally'.";
            }
        }

        public String displaySummary(String groupName) {
            if (!groups.containsKey(groupName)) {
                return "Group '" + groupName + "' does not exist.";
            }

            Group group = groups.get(groupName);
            return group.getSummary();
        }

        public String settleExpense(String groupName, String payer, String payee, double amount) {
            if (!groups.containsKey(groupName)) {
                return "Group '" + groupName + "' does not exist.";
            }

            Group group = groups.get(groupName);
            return group.settleBalance(payer, payee, amount);
        }

        public String saveToFile(String filename) {
            try (FileWriter file = new FileWriter(filename)) {
                JSONObject json = new JSONObject();
                
                // Save groups
                JSONObject groupsJson = new JSONObject();
                for (Map.Entry<String, Group> entry : groups.entrySet()) {
                    groupsJson.put(entry.getKey(), entry.getValue().toJSON());
                }
                json.put("groups", groupsJson);
                
                // Save users
                json.put("users", new JSONObject(users));
                
                file.write(json.toString(4));
                return "Data saved to file successfully!";
            } catch (IOException e) {
                return "Error saving to file: " + e.getMessage();
            }
        }

        public String loadFromFile(String filename) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                StringBuilder jsonText = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonText.append(line);
                }
        
                JSONObject json = new JSONObject(jsonText.toString());
                groups.clear();
                
                // Load groups
                JSONObject groupsJson = json.getJSONObject("groups");
                for (String key : groupsJson.keySet()) {
                    Group group = Group.fromJSON(groupsJson.getJSONObject(key));
                    groups.put(key, group);
                }
                
                // Load users
                if (json.has("users")) {  // Check for backward compatibility
                    JSONObject usersJson = json.getJSONObject("users");
                    users.clear();
                    for (String key : usersJson.keySet()) {
                        users.put(key, usersJson.getString(key));
                    }
                }
                
                return "Data loaded from file successfully!";
            } catch (IOException e) {
                return "Error loading from file: " + e.getMessage();
            }
        }

        // Inner Group class
        private static class Group {
            private String name;
            private List<String> members;
            private List<Expense> expenses;
            private Map<String, Double> balances;

            public Group(String name, List<String> members) {
                this.name = name;
                this.members = new ArrayList<>(members);
                this.expenses = new ArrayList<>();
                this.balances = new HashMap<>();
                for (String member : members) {
                    balances.put(member, 0.0);
                }
            }

            public String addEqualExpense(String description, double amount, String payer) {
                if (!members.contains(payer)) {
                    return "Payer '" + payer + "' is not a member of this group.";
                }

                double share = amount / members.size();
                for (String member : members) {
                    if (!member.equals(payer)) {
                        balances.put(member, balances.get(member) - share);
                        balances.put(payer, balances.get(payer) + share);
                    }
                }

                expenses.add(new Expense(description, amount, payer, "equally", members, null));
                return "Expense added successfully!";
            }

            public String addUnequalExpense(String description, double amount, String payer,
                    List<String> involvedMembers,
                    List<Double> splitAmounts) {
                if (!members.contains(payer)) {
                    return "Payer '" + payer + "' is not a member of this group.";
                }

                if (involvedMembers == null || splitAmounts == null || involvedMembers.size() != splitAmounts.size()) {
                    return "Error: Split amounts must match the number of involved members.";
                }

                for (int i = 0; i < involvedMembers.size(); i++) {
                    String member = involvedMembers.get(i);
                    double share = splitAmounts.get(i);

                    if (!member.equals(payer)) {
                        balances.put(member, balances.get(member) - share);
                        balances.put(payer, balances.get(payer) + share);
                    }
                }

                expenses.add(new Expense(description, amount, payer, "unequally", involvedMembers, splitAmounts));
                return "Expense added successfully!";
            }

            public String getSummary() {
                StringBuilder summary = new StringBuilder();
                summary.append("--- Group: ").append(name).append(" ---\n\n");

                summary.append("Members:\n");
                for (String member : members) {
                    summary.append("- ").append(member).append("\n");
                }

                summary.append("\nExpenses:\n");
                for (Expense expense : expenses) {
                    summary.append(expense).append("\n");
                }

                summary.append("\nBalances:\n");
                for (Map.Entry<String, Double> entry : balances.entrySet()) {
                    summary.append(entry.getKey()).append(": ₹").append(String.format("%.2f", entry.getValue()))
                            .append("\n");
                }

                return summary.toString();
            }

            public String settleBalance(String payer, String payee, double amount) {
                if (!balances.containsKey(payer) || !balances.containsKey(payee)) {
                    return "Payer or payee is not a member of this group.";
                }

                balances.put(payer, balances.get(payer) - amount);
                balances.put(payee, balances.get(payee) + amount);
                return "Payment settled successfully!";
            }

            public JSONObject toJSON() {
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("members", members);
                JSONArray expenseArray = new JSONArray();
                for (Expense expense : expenses) {
                    expenseArray.put(expense.toJSON());
                }
                json.put("expenses", expenseArray);
                json.put("balances", balances);
                return json;
            }

            public static Group fromJSON(JSONObject json) {
                List<String> members = new ArrayList<>();
                for (Object member : json.getJSONArray("members").toList()) {
                    members.add(member.toString()); // Convert each member to a String
                }
                Group group = new Group(json.getString("name"), members);
                JSONArray expenseArray = json.getJSONArray("expenses");
                for (int i = 0; i < expenseArray.length(); i++) {
                    group.expenses.add(Expense.fromJSON(expenseArray.getJSONObject(i)));
                }
                JSONObject balances = json.getJSONObject("balances");
                for (String key : balances.keySet()) {
                    group.balances.put(key, balances.getDouble(key));
                }
                return group;
            }
        }

        private static class Expense {
            private String description;
            private double amount;
            private String payer;
            private String splitType;
            private List<String> involvedMembers;
            private List<Double> splitAmounts;

            public Expense(String description, double amount, String payer, String splitType,
                    List<String> involvedMembers,
                    List<Double> splitAmounts) {
                this.description = description;
                this.amount = amount;
                this.payer = payer;
                this.splitType = splitType;
                this.involvedMembers = involvedMembers;
                this.splitAmounts = splitAmounts;
            }

            public Expense(String string, double double1, String string2, String string3, List<Object> list,
                    Object splitAmounts2) {

            }

            @Override
            public String toString() {
                return description + ": ₹" + String.format("%.2f", amount) + " paid by " + payer + " (" + splitType
                        + ")";
            }

            public JSONObject toJSON() {
                JSONObject json = new JSONObject();
                json.put("description", description);
                json.put("amount", amount);
                json.put("payer", payer);
                json.put("splitType", splitType);
                json.put("involvedMembers", involvedMembers);
                json.put("splitAmounts", splitAmounts);
                return json;
            }

            public static Expense fromJSON(JSONObject json) {
                return new Expense(
                        json.getString("description"),
                        json.getDouble("amount"),
                        json.getString("payer"),
                        json.getString("splitType"),
                        json.getJSONArray("involvedMembers").toList(),
                        json.has("splitAmounts") ? json.getJSONArray("splitAmounts").toList() : null);
            }
        }
    }
}