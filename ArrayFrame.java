package dsPage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;

class ArrayFrame extends JFrame {
    private JTextField sizeField;
    private JTextField elementField;
    private JTextField indexField;
    private JTextArea displayArea;
    private int[] array;

    // Theme colors
    private final Color BACKGROUND_COLOR_1 = new Color(44, 62, 80);    // Dark Blue
    private final Color BACKGROUND_COLOR_2 = new Color(52, 152, 219);  // Light Blue
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(231, 76, 60);         // Red
    private final Color BUTTON_HOVER_COLOR = new Color(192, 57, 43);   // Dark Red
    private final Color FIELD_BACKGROUND = new Color(236, 240, 241);   // Light Gray
    private final Color DISPLAY_AREA_BACKGROUND = new Color(248, 249, 249); // Off White

    public ArrayFrame() {
        setTitle("Array Operations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel
        JPanel topPanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel sizeLabel = new JLabel("Array Size:");
        sizeField = new JTextField(10);
        JButton createButton = createStyledButton("Create Array");
        createButton.addActionListener(e -> createArray());
        topPanel.add(sizeLabel);
        topPanel.add(sizeField);
        topPanel.add(createButton);

        // Middle Panel
        JPanel middlePanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel elementLabel = new JLabel("Element:");
        elementField = new JTextField(10);
        JLabel indexLabel = new JLabel("Index:");
        indexField = new JTextField(5);

        JButton addButton = createStyledButton("Add");
        JButton removeButton = createStyledButton("Remove");
        JButton displayButton = createStyledButton("Display");

        addButton.addActionListener(e -> addElement());
        removeButton.addActionListener(e -> removeElement());
        displayButton.addActionListener(e -> displayArray());

        middlePanel.add(elementLabel);
        middlePanel.add(elementField);
        middlePanel.add(indexLabel);
        middlePanel.add(indexField);
        middlePanel.add(addButton);
        middlePanel.add(removeButton);
        middlePanel.add(displayButton);

        // Display Area
        displayArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Apply theme
        applyTheme();
    }

    private JPanel createGradientPanel(LayoutManager layout) {
        return new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                                   RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR_1, 
                                                    w, h, BACKGROUND_COLOR_2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setPaint(BUTTON_HOVER_COLOR);
                } else if (getModel().isRollover()) {
                    g2d.setPaint(BUTTON_HOVER_COLOR);
                } else {
                    g2d.setPaint(BUTTON_COLOR);
                }

                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

                g2d.setColor(TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(text)) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.drawString(text, x, y);
            }
        };

        button.setPreferredSize(new Dimension(120, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void applyTheme() {
        // Frame styling
        this.getContentPane().setBackground(BACKGROUND_COLOR_1);
        
        // Style text fields
        styleTextField(sizeField);
        styleTextField(elementField);
        styleTextField(indexField);
        
        // Style labels
        styleLabels(this);
        
        // Style display area
        styleDisplayArea();
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(100, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(FIELD_BACKGROUND);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BACKGROUND_COLOR_2, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void styleLabels(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setForeground(TEXT_COLOR);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            }
            if (comp instanceof Container) {
                styleLabels((Container) comp);
            }
        }
    }

    private void styleDisplayArea() {
        displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        displayArea.setBackground(DISPLAY_AREA_BACKGROUND);
        displayArea.setForeground(BACKGROUND_COLOR_1);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayArea.setEditable(false);
    }

    // Array Operations Methods
    private void createArray() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            array = new int[size];
            displayArea.setText("Array of size " + size + " created.\n");
        } catch (NumberFormatException e) {
            displayArea.setText("Please enter a valid size.\n");
        }
    }

    private void addElement() {
        try {
            int element = Integer.parseInt(elementField.getText());
            int index = Integer.parseInt(indexField.getText());
            if (array != null && index >= 0 && index < array.length) {
                array[index] = element;
                displayArea.append("Added " + element + " at index " + index + "\n");
            } else {
                displayArea.append("Invalid index.\n");
            }
        } catch (NumberFormatException e) {
            displayArea.append("Please enter valid numbers.\n");
        }
    }

    private void removeElement() {
        try {
            int index = Integer.parseInt(indexField.getText());
            if (array != null && index >= 0 && index < array.length) {
                array[index] = 0;  // or any default value
                displayArea.append("Removed element at index " + index + "\n");
            } else {
                displayArea.append("Invalid index.\n");
            }
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid index.\n");
        }
    }

    private void displayArray() {
        if (array != null) {
            StringBuilder sb = new StringBuilder("Array contents:\n");
            for (int i = 0; i < array.length; i++) {
                sb.append("Index ").append(i).append(": ").append(array[i]).append("\n");
            }
            displayArea.setText(sb.toString());
        } else {
            displayArea.setText("Array not created yet.\n");
        }
    }
}

// Main class to run the application
 class ArrayOperations {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ArrayFrame frame = new ArrayFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}