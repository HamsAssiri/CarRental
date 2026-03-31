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
import java.awt.*;
import java.util.List;

/**
 * Minimalist Receipt-Style GUI for Car Rental Application
 */
public class CarRentalGUI extends JFrame {
    
    // Color scheme - clean and professional
    private static final Color BACKGROUND = new Color(250, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color ACCENT = new Color(52, 73, 94);
    private static final Color BORDER = new Color(230, 230, 235);
    private static final Color SUCCESS = new Color(46, 125, 50);
    private static final Color WARNING = new Color(198, 40, 40);
    
    // Components
    private JTextField passengersField;
    private JTextField daysField;
    private JTextField mileageField;
    private JButton calculateButton;
    private JPanel receiptPanel;
    private JScrollPane receiptScrollPane;
    private JLabel statusLabel;
    
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
        setSize(850, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(BACKGROUND);
        mainContainer.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header
        mainContainer.add(createHeader(), BorderLayout.NORTH);
        
        // Center (Input + Receipt)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(BACKGROUND);
        centerPanel.add(createInputPanel());
        centerPanel.add(createReceiptPanel());
        
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        mainContainer.add(createFooter(), BorderLayout.SOUTH);
        
        add(mainContainer);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("CAR RENTAL SYSTEM");
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        title.setForeground(PRIMARY);
        
        JLabel subtitle = new JLabel("find your optimal ride");
        subtitle.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        subtitle.setForeground(ACCENT);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(BACKGROUND);
        textPanel.add(title);
        textPanel.add(subtitle);
        
        header.add(textPanel, BorderLayout.WEST);
        
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
        inputTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        inputTitle.setForeground(ACCENT);
        inputTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(inputTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Passengers
        panel.add(createFieldGroup("PASSENGERS", passengersField = createSmallField(), "1-7"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Days
        panel.add(createFieldGroup("DAYS", daysField = createSmallField(), "1-365"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Mileage
        panel.add(createFieldGroup("MILEAGE (miles)", mileageField = createSmallField(), "1-100,000"));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Gas info
        JLabel gasInfo = new JLabel("gas price: $2.25 / gallon");
        gasInfo.setFont(new Font("Helvetica Neue", Font.ITALIC, 10));
        gasInfo.setForeground(ACCENT);
        gasInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(gasInfo);
        
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Calculate button
        calculateButton = new JButton("CALCULATE");
        calculateButton.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        calculateButton.setBackground(PRIMARY);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorderPainted(false);
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.setPreferredSize(new Dimension(120, 40));
        
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
    
    private JPanel createFieldGroup(String label, JTextField field, String hint) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Helvetica Neue", Font.PLAIN, 10));
        labelComp.setForeground(ACCENT);
        
        JLabel hintComp = new JLabel(hint);
        hintComp.setFont(new Font("Helvetica Neue", Font.PLAIN, 9));
        hintComp.setForeground(new Color(180, 180, 180));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_BG);
        topPanel.add(labelComp, BorderLayout.WEST);
        topPanel.add(hintComp, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JTextField createSmallField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(180, 35));
        return field;
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
        receiptTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 11));
        receiptTitle.setForeground(ACCENT);
        
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
        
        JPanel welcome = createReceiptItem();
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel icon = new JLabel("◉");
        icon.setFont(new Font("Helvetica Neue", Font.PLAIN, 40));
        icon.setForeground(PRIMARY);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("ready");
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        title.setForeground(ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("enter trip details to calculate");
        desc.setFont(new Font("Helvetica Neue", Font.PLAIN, 10));
        desc.setForeground(new Color(160, 160, 160));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcome.add(icon);
        welcome.add(Box.createRigidArea(new Dimension(0, 15)));
        welcome.add(title);
        welcome.add(Box.createRigidArea(new Dimension(0, 5)));
        welcome.add(desc);
        
        receiptPanel.add(welcome);
        receiptPanel.add(Box.createVerticalGlue());
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
    
    private JPanel createReceiptItem() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        return panel;
    }
    
    private void setupEventHandlers() {
        calculateButton.addActionListener(e -> calculateBestCar());
        passengersField.addActionListener(e -> calculateBestCar());
        daysField.addActionListener(e -> calculateBestCar());
        mileageField.addActionListener(e -> calculateBestCar());
    }
    
    private void calculateBestCar() {
        showLoadingReceipt();
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private String error;
            private List<CarWithDetails> bestCars;
            private UserInput input;
            
            @Override
            protected Void doInBackground() {
                try {
                    int passengers, days, mileage;
                    
                    try {
                        passengers = Integer.parseInt(passengersField.getText().trim());
                        days = Integer.parseInt(daysField.getText().trim());
                        mileage = Integer.parseInt(mileageField.getText().trim());
                    } catch (NumberFormatException ex) {
                        error = "invalid input";
                        return null;
                    }
                    
                    if (!InputValidator.validatePassengers(passengers)) {
                        error = "passengers must be 1-7";
                        return null;
                    }
                    if (!InputValidator.validateDays(days)) {
                        error = "days must be 1-365";
                        return null;
                    }
                    if (!InputValidator.validateMileage(mileage)) {
                        error = "mileage must be 1-100,000";
                        return null;
                    }
                    
                    input = new UserInput(passengers, days, mileage);
                    
                    if (allCars == null || allCars.isEmpty()) {
                        error = "no cars available";
                        return null;
                    }
                    
                    List<CarWithDetails> eligibleCars = filterService.filterByPassengers(allCars, input);
                    
                    if (eligibleCars.isEmpty()) {
                        error = "no_cars";
                        return null;
                    }
                    
                    costCalculator.calculateAllCosts(eligibleCars, input);
                    bestCars = filterService.findBestCars(eligibleCars);
                    
                } catch (IllegalArgumentException ex) {
                    error = ex.getMessage();
                } catch (Exception ex) {
                    error = "system error";
                }
                return null;
            }
            
            @Override
            protected void done() {
                if (error != null) {
                    if (error.equals("no_cars")) {
                        showNoCarsReceipt(input.getPassengers());
                    } else {
                        showErrorReceipt(error);
                    }
                } else {
                    showReceipt(bestCars, input);
                    statusLabel.setText("● calculated: " + bestCars.size() + " best car(s)");
                }
            }
        };
        
        worker.execute();
    }
    
    private void showLoadingReceipt() {
        receiptPanel.removeAll();
        
        JPanel loading = createReceiptItem();
        loading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel text = new JLabel("calculating...");
        text.setFont(new Font("Helvetica Neue", Font.ITALIC, 11));
        text.setForeground(ACCENT);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loading.add(text);
        receiptPanel.add(loading);
        receiptPanel.revalidate();
        receiptPanel.repaint();
    }
    
    private void showReceipt(List<CarWithDetails> cars, UserInput input) {
        receiptPanel.removeAll();
        
        // Trip details section
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
        
        // Scroll to top
        SwingUtilities.invokeLater(() -> receiptScrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void addReceiptSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(CARD_BG);
        section.setBorder(new EmptyBorder(8, 0, 8, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 10));
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
        labelComp.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        labelComp.setForeground(new Color(100, 100, 100));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Helvetica Neue", Font.BOLD, 11));
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
        nameLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        nameLabel.setForeground(ACCENT);
        
        carPanel.add(nameLabel);
        carPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        // Details in columns
        JPanel details = new JPanel(new GridLayout(2, 3, 15, 6));
        details.setBackground(CARD_BG);
        details.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        details.add(createReceiptDetail("category", car.getCategory()));
        details.add(createReceiptDetail("class", car.getHierarchy()));
        details.add(createReceiptDetail("passengers", String.valueOf(car.getMaxPassengers())));
        details.add(createReceiptDetail("mpg", String.valueOf(car.getGas())));
        details.add(createReceiptDetail("daily rate", String.format("$%.2f", car.getDailyRate())));
        details.add(createReceiptDetail("comfort", car.getComfortLevel()));
        
        carPanel.add(details);
        
        // Total cost
        JPanel totalLine = new JPanel(new BorderLayout());
        totalLine.setBackground(CARD_BG);
        totalLine.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JLabel totalLabel = new JLabel("total cost");
        totalLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 10));
        totalLabel.setForeground(new Color(100, 100, 100));
        
        JLabel totalValue = new JLabel(String.format("$%.2f", car.getTotalCost()));
        totalValue.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
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
        labelComp.setFont(new Font("Helvetica Neue", Font.PLAIN, 9));
        labelComp.setForeground(new Color(140, 140, 140));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Helvetica Neue", Font.PLAIN, 10));
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
        
        JLabel note = new JLabel("◉ lowest cost • best comfort");
        note.setFont(new Font("Helvetica Neue", Font.PLAIN, 8));
        note.setForeground(new Color(160, 160, 160));
        note.setHorizontalAlignment(SwingConstants.CENTER);
        
        footer.add(sep, BorderLayout.NORTH);
        footer.add(note, BorderLayout.SOUTH);
        
        receiptPanel.add(footer);
    }
    
    private void showNoCarsReceipt(int passengers) {
        receiptPanel.removeAll();
        
        addReceiptSection("UNAVAILABLE");
        
        JPanel message = new JPanel();
        message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
        message.setBackground(CARD_BG);
        message.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel text = new JLabel("no cars available for");
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        text.setForeground(new Color(100, 100, 100));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel number = new JLabel(String.valueOf(passengers) + " passengers");
        number.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        number.setForeground(WARNING);
        number.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel tip = new JLabel("maximum capacity: 7");
        tip.setFont(new Font("Helvetica Neue", Font.ITALIC, 9));
        tip.setForeground(new Color(160, 160, 160));
        tip.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        message.add(text);
        message.add(Box.createRigidArea(new Dimension(0, 5)));
        message.add(number);
        message.add(Box.createRigidArea(new Dimension(0, 5)));
        message.add(tip);
        
        receiptPanel.add(message);
        receiptPanel.revalidate();
        receiptPanel.repaint();
        
        statusLabel.setText("● no cars for " + passengers + " passengers");
    }
    
    private void showErrorReceipt(String message) {
        receiptPanel.removeAll();
        
        addReceiptSection("ERROR");
        
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        errorPanel.setBackground(CARD_BG);
        errorPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel text = new JLabel("◉");
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
        text.setForeground(WARNING);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel errorMsg = new JLabel(message);
        errorMsg.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        errorMsg.setForeground(WARNING);
        errorMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        errorPanel.add(text);
        errorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        errorPanel.add(errorMsg);
        
        receiptPanel.add(errorPanel);
        receiptPanel.revalidate();
        receiptPanel.repaint();
        
        statusLabel.setText("● error: " + message);
    }
    
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BACKGROUND);
        footer.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        statusLabel = new JLabel("● ready");
        statusLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 10));
        statusLabel.setForeground(new Color(140, 140, 140));
        
        JLabel securityLabel = new JLabel("least privilege • fail safe • compartmentalized");
        securityLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 9));
        securityLabel.setForeground(new Color(180, 180, 180));
        
        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(securityLabel, BorderLayout.EAST);
        
        return footer;
    }
    
    private void updateStatus() {
        if (allCars != null && !allCars.isEmpty()) {
            statusLabel.setText("● ready | " + allCars.size() + " cars in fleet");
        } else {
            statusLabel.setText("● error: no cars loaded");
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