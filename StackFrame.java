package dsPage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.SwingUtilities;

class StackFrame extends JFrame {
    private JTextField sizeField;
    private JTextField elementField;
    private JTextArea displayArea;
    private int[] stack;
    private int top = -1;
    private int maxSize;

    private final Color BACKGROUND_COLOR_1 = new Color(44, 62, 80);    
    private final Color BACKGROUND_COLOR_2 = new Color(52, 152, 219);  
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(231, 76, 60);       
    private final Color BUTTON_HOVER_COLOR = new Color(192, 57, 43);   
    private final Color FIELD_BACKGROUND = new Color(236, 240, 241);  
    private final Color DISPLAY_AREA_BACKGROUND = new Color(248, 249, 249); 

    public StackFrame() {
        setTitle("Stack Operations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel for Size
        JPanel topPanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel sizeLabel = new JLabel("Stack Size:");
        sizeField = new JTextField(10);
        JButton createButton = new JButton("Create Stack");
        createButton.addActionListener(e -> createStack());
        topPanel.add(sizeLabel);
        topPanel.add(sizeField);
        topPanel.add(createButton);

        // Middle Panel for Operations
        JPanel middlePanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel elementLabel = new JLabel("Element:");
        elementField = new JTextField(10);
        middlePanel.add(elementLabel);
        middlePanel.add(elementField);

        JButton pushButton = new JButton("Push");
        JButton popButton = new JButton("Pop");
        JButton displayButton = new JButton("Display");

        pushButton.addActionListener(e -> push());
        popButton.addActionListener(e -> pop());
        displayButton.addActionListener(e -> displayStack());

        middlePanel.add(pushButton);
        middlePanel.add(popButton);
        middlePanel.add(displayButton);

        // Bottom Panel for Display
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add all panels
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

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

    private void applyTheme() {
        // Frame styling
        this.getContentPane().setBackground(BACKGROUND_COLOR_1);
        
        // Style text fields
        styleTextField(sizeField);
        styleTextField(elementField);
        
        // Style labels
        styleLabels(this);
        
        // Style display area
        styleDisplayArea();
        
        // Style buttons
        styleButtons(this);
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
        displayArea.setForeground(Color.BLACK);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void styleButtons(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                button.setForeground(TEXT_COLOR);
                button.setBackground(BUTTON_COLOR);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setPreferredSize(new Dimension(120, 40));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            if (comp instanceof Container) {
                styleButtons((Container) comp);
            }
        }
    }

    private void createStack() {
        try {
            maxSize = Integer.parseInt(sizeField.getText());
            stack = new int[maxSize];
            top = -1;
            displayArea.setText("Stack of size " + maxSize + " created.\n");
        } catch (NumberFormatException e) {
            displayArea.setText("Please enter a valid size.\n");
        }
    }

    private void push() {
        try {
            if (top >= maxSize - 1) {
                displayArea.append("Stack Overflow!\n");
                return;
            }
            int element = Integer.parseInt(elementField.getText());
            stack[++top] = element;
            displayArea.append("Pushed: " + element + "\n");
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid number.\n");
        }
    }

    private void pop() {
        if (top < 0) {
            displayArea.append("Stack Underflow!\n");
            return;
        }
        displayArea.append("Popped: " + stack[top--] + "\n");
    }

    private void displayStack() {
        if (stack != null) {
            StringBuilder sb = new StringBuilder("Stack contents (top to bottom):\n");
            for (int i = top; i >= 0; i--) {
                sb.append(stack[i]).append("\n");
            }
            displayArea.setText(sb.toString());
        } else {
            displayArea.setText("Stack not created yet.\n");
        }
    }
}


 class StackOperationsDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StackFrame frame = new StackFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
