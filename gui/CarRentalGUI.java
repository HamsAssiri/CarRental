package gui;

import model.CarLoader;
import model.ICar;
import model.UserInput;
import model.CarWithDetails;
import service.CostCalculator;
import service.CarFilterService;
import validation.InputValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * Enhanced User-Friendly GUI for Car Rental Application
 */
public class CarRentalGUI extends JFrame {
    
    // Color scheme
    private static final Color BACKGROUND = new Color(250, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color ACCENT = new Color(52, 73, 94);
    private static final Color BORDER = new Color(230, 230, 235);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color WARNING = new Color(198, 40, 40);
    private static final Color ERROR_BG = new Color(255, 235, 235);
    private static final Color ERROR_BORDER = new Color(198, 40, 40);
    private static final Color INFO_ICON = new Color(100, 116, 139);
    
    // Components
    private JTextField passengersField;
    private JTextField daysField;
    private JTextField mileageField;
    private JButton calculateButton;
    private JPanel receiptPanel;
    private JScrollPane receiptScrollPane;
    private JLabel statusLabel;
    private JDialog errorDialog;
    private JTextArea errorMessageArea;
    
    // Store original borders for reset
    private javax.swing.border.Border defaultPassengerBorder;
    private javax.swing.border.Border defaultDaysBorder;
    private javax.swing.border.Border defaultMileageBorder;
    
    // Services
    private CarLoader carLoader;
    private CarFilterService filterService;
    private CostCalculator costCalculator;
    private List<ICar> allCars;
    
    public CarRentalGUI() {
        carLoader = new CarLoader();
        filterService = new CarFilterService();
        costCalculator = new CostCalculator();
        allCars = carLoader.loadCars();
        
        setupUI();
        setupEventHandlers();
        updateStatus();
    }
    
    private void setupUI() {
        setTitle("Car Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(950, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(BACKGROUND);
        mainContainer.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header
        mainContainer.add(createHeader(), BorderLayout.NORTH);
        
        // Center (Input + Receipt)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        centerPanel.setBackground(BACKGROUND);
        centerPanel.add(createInputPanel());
        centerPanel.add(createReceiptPanel());
        
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        mainContainer.add(createFooter(), BorderLayout.SOUTH);
        
        add(mainContainer);
        
        // Store default borders
        defaultPassengerBorder = passengersField.getBorder();
        defaultDaysBorder = daysField.getBorder();
        defaultMileageBorder = mileageField.getBorder();
        
        // Create error dialog
        createErrorDialog();
    }
    
    private void createErrorDialog() {
        errorDialog = new JDialog(this, "Input Error", true);
        errorDialog.setLayout(new BorderLayout());
        errorDialog.setSize(420, 200);
        errorDialog.setLocationRelativeTo(this);
        errorDialog.getContentPane().setBackground(Color.WHITE);
        
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBackground(Color.WHITE);
        errorPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Error icon and message
        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBackground(Color.WHITE);
        
        JLabel iconLabel = new JLabel("!");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        iconLabel.setForeground(WARNING);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        errorMessageArea = new JTextArea();
        errorMessageArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        errorMessageArea.setForeground(new Color(80, 80, 80));
        errorMessageArea.setEditable(false);
        errorMessageArea.setLineWrap(true);
        errorMessageArea.setWrapStyleWord(true);
        errorMessageArea.setBackground(Color.WHITE);
        errorMessageArea.setBorder(null);
        
        messagePanel.add(iconLabel, BorderLayout.WEST);
        messagePanel.add(errorMessageArea, BorderLayout.CENTER);
        
        // OK button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        okButton.setBackground(PRIMARY);
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.addActionListener(e -> errorDialog.setVisible(false));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);
        
        errorPanel.add(messagePanel, BorderLayout.CENTER);
        errorPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        errorDialog.add(errorPanel, BorderLayout.CENTER);
        errorDialog.getRootPane().setDefaultButton(okButton);
    }
    
    private void showErrorDialog(String message) {
        if (errorMessageArea != null) {
            errorMessageArea.setText(message);
        }
        errorDialog.setVisible(true);
    }
    
    private JPanel createHeader() {
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(BACKGROUND);
    header.setBorder(new EmptyBorder(0, 0, 20, 0));
    
    JLabel title = new JLabel("GoDrive Rental");
    title.setFont(new Font("Segoe UI", Font.BOLD, 28));
    title.setForeground(PRIMARY);
    
    JLabel subtitle = new JLabel("find your optimal ride");
    subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    subtitle.setForeground(ACCENT);
    
    JPanel textPanel = new JPanel(new GridLayout(2, 1));
    textPanel.setBackground(BACKGROUND);
    textPanel.add(title);
    textPanel.add(subtitle);
    
    // Info icon with tooltip
    JLabel infoIcon = new JLabel("( i )");
    infoIcon.setFont(new Font("Segoe UI", Font.BOLD, 12));
    infoIcon.setForeground(PRIMARY);
    infoIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
    infoIcon.setToolTipText("<html><body style='width:220px; padding:10px; font-family:Segoe UI;'>"
        + "<b>Car Capacity Guide</b><br><br>"
        + "• Economy: 1-4 passengers<br>"
        + "• Intermediate: 1-4 passengers<br>"
        + "• Standard: 1-5 passengers<br>"
        + "• Van: 1-7 passengers<br><br>"
        + "<i>Gas price: $2.25 per gallon</i>"
        + "</body></html>");

        // Alternative: Show tooltip on click if hover doesn't work
    infoIcon.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Tooltip should show on hover automatically
        // This is just to ensure the component is interactive
    }
});
    
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightPanel.setBackground(BACKGROUND);
    rightPanel.add(infoIcon);
    
    header.add(textPanel, BorderLayout.WEST);
    header.add(rightPanel, BorderLayout.EAST);
    
    return header;
}
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        // Input title
        JLabel inputTitle = new JLabel("TRIP DETAILS");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputTitle.setForeground(PRIMARY);
        inputTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(inputTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Passengers with placeholder
        passengersField = createStyledTextField("Insert a Number");
        panel.add(createFieldGroup("NUMBER OF PASSENGERS", passengersField, "1-7 people"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Days with placeholder
        daysField = createStyledTextField("Insert a Number");
        panel.add(createFieldGroup("RENTAL DAYS", daysField, "1-365 days"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Mileage with placeholder
        mileageField = createStyledTextField("Insert a Number");
        panel.add(createFieldGroup("TRIP MILEAGE", mileageField, "1-100,000 miles"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Gas price info
        JLabel gasInfo = new JLabel("Gas price: $2.25 per gallon");
        gasInfo.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        gasInfo.setForeground(ACCENT);
        gasInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(gasInfo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Calculate button
        calculateButton = new JButton("FIND BEST CAR");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        calculateButton.setBackground(PRIMARY);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorderPainted(false);
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.setPreferredSize(new Dimension(180, 45));
        
        calculateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                calculateButton.setBackground(PRIMARY.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                calculateButton.setBackground(PRIMARY);
            }
        });
        
        panel.add(calculateButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(200, 40));
        
        // Add placeholder text
        field.putClientProperty("placeholder", placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
                resetFieldBorder(field);
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        
        // Set initial placeholder
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        
        return field;
    }
    
    private void resetFieldBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setBackground(Color.WHITE);
    }
    
    private void highlightInvalidField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ERROR_BORDER, 2, true),
            new EmptyBorder(9, 11, 9, 11)
        ));
        field.setBackground(ERROR_BG);
    }
    
    private JPanel createFieldGroup(String label, JTextField field, String hint) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 11));
        labelComp.setForeground(ACCENT);
        
        JLabel hintComp = new JLabel(hint);
        hintComp.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        hintComp.setForeground(new Color(160, 160, 160));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_BG);
        topPanel.add(labelComp, BorderLayout.WEST);
        topPanel.add(hintComp, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Receipt header
        JPanel receiptHeader = new JPanel(new BorderLayout());
        receiptHeader.setBackground(CARD_BG);
        receiptHeader.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel receiptTitle = new JLabel("RECEIPT");
        receiptTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        receiptTitle.setForeground(PRIMARY);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        
        receiptHeader.add(receiptTitle, BorderLayout.WEST);
        receiptHeader.add(separator, BorderLayout.CENTER);
        
        panel.add(receiptHeader, BorderLayout.NORTH);
        
        // Receipt content
        receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBackground(CARD_BG);
        
        receiptScrollPane = new JScrollPane(receiptPanel);
        receiptScrollPane.setBorder(null);
        receiptScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        receiptScrollPane.setBackground(CARD_BG);
        
        panel.add(receiptScrollPane, BorderLayout.CENTER);
        
        // Show welcome receipt
        showWelcomeReceipt();
        
        return panel;
    }
    
    private void showWelcomeReceipt() {
        receiptPanel.removeAll();
        
        JPanel welcome = new JPanel();
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
        welcome.setBackground(CARD_BG);
        welcome.setBorder(new EmptyBorder(40, 20, 40, 20));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel icon = new JLabel("[ ]");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 56));
        icon.setForeground(PRIMARY);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Ready to find your car");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("Enter your trip details on the left");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc.setForeground(new Color(120, 120, 120));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc2 = new JLabel("and click FIND BEST CAR");
        desc2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc2.setForeground(new Color(120, 120, 120));
        desc2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcome.add(icon);
        welcome.add(Box.createRigidArea(new Dimension(0, 15)));
        welcome.add(title);
        welcome.add(Box.createRigidArea(new Dimension(0, 8)));
        welcome.add(desc);
        welcome.add(desc2);
        
        receiptPanel.add(welcome);
        receiptPanel.add(Box.createVerticalGlue());
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
    
    private void resetFieldBorders() {
        resetFieldBorder(passengersField);
        resetFieldBorder(daysField);
        resetFieldBorder(mileageField);
    }
    
    private String getActualText(JTextField field, String placeholder) {
        String text = field.getText().trim();
        if (text.equals(placeholder)) {
            return "";
        }
        return text;
    }
    
    private void setupEventHandlers() {
        calculateButton.addActionListener(e -> calculateBestCar());
    }
    
    private void calculateBestCar() {
        resetFieldBorders();
        showLoadingReceipt();
        
        String passengersPlaceholder = "Insert a Number";
        String daysPlaceholder = "Insert a Number";
        String mileagePlaceholder = "Insert a Number";
        
        String passengersStr = getActualText(passengersField, passengersPlaceholder);
        String daysStr = getActualText(daysField, daysPlaceholder);
        String mileageStr = getActualText(mileageField, mileagePlaceholder);
        
        int passengers = 0, days = 0, mileage = 0;
        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();
        
        // Validate passengers
        if (passengersStr.isEmpty()) {
            highlightInvalidField(passengersField);
            errorMsg.append("• Please enter number of passengers\n");
            hasError = true;
        } else {
            try {
                passengers = Integer.parseInt(passengersStr);
                
                // Check if exceeds maximum capacity first
                if (passengers > 7) {
                    highlightInvalidField(passengersField);
                    errorMsg.append("• Maximum capacity is 7 passengers, Please try with fewer passengers.\n");
                    hasError = true;
                }
                // Then check if below minimum
                else if (passengers < 1) {
                    highlightInvalidField(passengersField);
                    errorMsg.append("• Passengers must be at least 1\n");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                highlightInvalidField(passengersField);
                errorMsg.append("• Passengers must be a valid number\n");
                hasError = true;
            }
        }
        
        // Validate days
        if (daysStr.isEmpty()) {
            highlightInvalidField(daysField);
            errorMsg.append("• Please enter number of rental days\n");
            hasError = true;
        } else {
            try {
                days = Integer.parseInt(daysStr);
                if (!InputValidator.validateDays(days)) {
                    highlightInvalidField(daysField);
                    errorMsg.append("• Rental days must be between 1 and 365\n");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                highlightInvalidField(daysField);
                errorMsg.append("• Rental days must be a valid number\n");
                hasError = true;
            }
        }
        
        // Validate mileage
        if (mileageStr.isEmpty()) {
            highlightInvalidField(mileageField);
            errorMsg.append("• Please enter trip mileage");
            hasError = true;
        } else {
            try {
                mileage = Integer.parseInt(mileageStr);
                if (!InputValidator.validateMileage(mileage)) {
                    highlightInvalidField(mileageField);
                    errorMsg.append("• Mileage must be between 1 and 100,000\n");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                highlightInvalidField(mileageField);
                errorMsg.append("• Mileage must be a valid number\n");
                hasError = true;
            }
        }
        
        if (hasError) {
            showErrorDialog(errorMsg.toString());
            showWelcomeReceipt();
            statusLabel.setText("! error: please fix the highlighted fields");
            return;
        }
        
        UserInput input;
        try {
            input = new UserInput(passengers, days, mileage);
        } catch (IllegalArgumentException ex) {
            showErrorDialog(ex.getMessage());
            showWelcomeReceipt();
            statusLabel.setText("! error: " + ex.getMessage());
            return;
        }
        
        if (allCars == null || allCars.isEmpty()) {
            showErrorDialog("No cars available. Please check data/cars.json");
            showWelcomeReceipt();
            statusLabel.setText("! error: no cars loaded");
            return;
        }
        
        List<CarWithDetails> eligibleCars = filterService.filterByPassengers(allCars, input);
        
        if (eligibleCars.isEmpty()) {
            showNoCarsReceipt(passengers);
            statusLabel.setText("! no cars for " + passengers + " passengers");
            return;
        }
        
        costCalculator.calculateAllCosts(eligibleCars, input);
        List<CarWithDetails> bestCars = filterService.findBestCars(eligibleCars);
        
        showReceipt(bestCars, input);
        statusLabel.setText("v calculated: " + bestCars.size() + " best car(s)");
    }
    
    private void showLoadingReceipt() {
        receiptPanel.removeAll();
        
        JPanel loading = new JPanel();
        loading.setLayout(new BoxLayout(loading, BoxLayout.Y_AXIS));
        loading.setBackground(CARD_BG);
        loading.setBorder(new EmptyBorder(50, 20, 50, 20));
        loading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel text = new JLabel("Calculating best car for your trip...");
        text.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        text.setForeground(ACCENT);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loading.add(progressBar);
        loading.add(Box.createRigidArea(new Dimension(0, 15)));
        loading.add(text);
        
        receiptPanel.add(loading);
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
    
    private void showReceipt(List<CarWithDetails> cars, UserInput input) {
        receiptPanel.removeAll();
        
        addReceiptSection("TRIP SUMMARY");
        addReceiptLine("passengers", String.valueOf(input.getPassengers()));
        addReceiptLine("rental days", String.valueOf(input.getDays()));
        addReceiptLine("mileage", String.format("%,d miles", input.getMileage()));
        addReceiptLine("gas price", "$2.25 / gallon");
        addReceiptSpacing();
        
        if (cars.size() == 1) {
            addReceiptSection("BEST CAR");
        } else {
            addReceiptSection(String.format("BEST CARS (%d)", cars.size()));
        }
        
        for (int i = 0; i < cars.size(); i++) {
            if (i > 0) {
                addReceiptDivider();
            }
            addCarToReceipt(cars.get(i), i + 1);
        }
        
        addReceiptSpacing();
        addReceiptFooter();
        
        receiptPanel.add(Box.createVerticalGlue());
        receiptPanel.revalidate();
        receiptPanel.repaint();
        
        SwingUtilities.invokeLater(() -> receiptScrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void addReceiptSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(CARD_BG);
        section.setBorder(new EmptyBorder(8, 0, 8, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(PRIMARY);
        
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        
        section.add(titleLabel, BorderLayout.WEST);
        section.add(sep, BorderLayout.CENTER);
        
        receiptPanel.add(section);
    }
    
    private void addReceiptLine(String label, String value) {
        JPanel line = new JPanel(new BorderLayout());
        line.setBackground(CARD_BG);
        line.setBorder(new EmptyBorder(3, 0, 3, 0));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelComp.setForeground(new Color(100, 100, 100));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 11));
        valueComp.setForeground(ACCENT);
        
        line.add(labelComp, BorderLayout.WEST);
        line.add(valueComp, BorderLayout.EAST);
        
        receiptPanel.add(line);
    }
    
    private void addCarToReceipt(CarWithDetails car, int index) {
        JPanel carPanel = new JPanel();
        carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
        carPanel.setBackground(CARD_BG);
        carPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        
        JLabel nameLabel = new JLabel(String.format("%d. %s", index, car.getName()));
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(PRIMARY);
        
        carPanel.add(nameLabel);
        carPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        JPanel details = new JPanel(new GridLayout(2, 3, 12, 6));
        details.setBackground(CARD_BG);
        details.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        details.add(createReceiptDetail("category", car.getCategory()));
        details.add(createReceiptDetail("class", car.getHierarchy()));
        details.add(createReceiptDetail("passengers", String.valueOf(car.getMaxPassengers())));
        details.add(createReceiptDetail("mpg", String.valueOf(car.getGas())));
        details.add(createReceiptDetail("daily rate", String.format("$%.2f", car.getDailyRate())));
        details.add(createReceiptDetail("comfort", car.getComfortLevel()));
        
        carPanel.add(details);
        
        JPanel totalLine = new JPanel(new BorderLayout());
        totalLine.setBackground(CARD_BG);
        totalLine.setBorder(new EmptyBorder(8, 0, 0, 0));
        
        JLabel totalLabel = new JLabel("TOTAL COST");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        totalLabel.setForeground(ACCENT);
        
        JLabel totalValue = new JLabel(String.format("$%.2f", car.getTotalCost()));
        totalValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalValue.setForeground(SUCCESS);
        
        totalLine.add(totalLabel, BorderLayout.WEST);
        totalLine.add(totalValue, BorderLayout.EAST);
        
        carPanel.add(totalLine);
        
        receiptPanel.add(carPanel);
    }
    
    private JPanel createReceiptDetail(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        labelComp.setForeground(new Color(140, 140, 140));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        valueComp.setForeground(ACCENT);
        
        panel.add(labelComp, BorderLayout.NORTH);
        panel.add(valueComp, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void addReceiptDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setBorder(new EmptyBorder(8, 0, 8, 0));
        receiptPanel.add(sep);
    }
    
    private void addReceiptSpacing() {
        receiptPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }
    
    private void addReceiptFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CARD_BG);
        footer.setBorder(new EmptyBorder(12, 0, 5, 0));
        
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        
        JLabel note = new JLabel("lowest total cost  |  best comfort level");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        note.setForeground(new Color(160, 160, 160));
        note.setHorizontalAlignment(SwingConstants.CENTER);
        
        footer.add(sep, BorderLayout.NORTH);
        footer.add(note, BorderLayout.SOUTH);
        
        receiptPanel.add(footer);
    }
    
    private void showNoCarsReceipt(int passengers) {
        receiptPanel.removeAll();
        
        JPanel message = new JPanel();
        message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
        message.setBackground(CARD_BG);
        message.setBorder(new EmptyBorder(40, 20, 40, 20));
        
        JLabel icon = new JLabel("[-]");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        icon.setForeground(WARNING);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("No Cars Available");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(WARNING);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel text = new JLabel("No cars can accommodate " + passengers + " passengers");
        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel tip = new JLabel("Maximum capacity is 7 passengers");
        tip.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        tip.setForeground(new Color(140, 140, 140));
        tip.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        message.add(icon);
        message.add(Box.createRigidArea(new Dimension(0, 15)));
        message.add(title);
        message.add(Box.createRigidArea(new Dimension(0, 8)));
        message.add(text);
        message.add(Box.createRigidArea(new Dimension(0, 5)));
        message.add(tip);
        
        receiptPanel.add(message);
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
    
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BACKGROUND);
        footer.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        statusLabel = new JLabel("● ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(80, 80, 80));
        
        JLabel securityLabel = new JLabel("Software Security Project |  Hams Rawan Shahad ");
        securityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        securityLabel.setForeground(new Color(160, 160, 160));
        
        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(securityLabel, BorderLayout.EAST);
        
        return footer;
    }
    
    private void updateStatus() {
        if (allCars != null && !allCars.isEmpty()) {
            statusLabel.setText("● ready  |  " + allCars.size() + " vehicles in fleet");
        } else {
            statusLabel.setText("! error: no vehicles loaded");
        }
    }
    
    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        System.setProperty("user.country", "US");
        System.setProperty("user.language", "en");
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default
        }
        
        SwingUtilities.invokeLater(() -> {
            CarRentalGUI gui = new CarRentalGUI();
            gui.setVisible(true);
        });
    }
}