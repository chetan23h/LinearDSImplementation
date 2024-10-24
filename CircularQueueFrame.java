package dsPage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.geom.RoundRectangle2D;

class CircularQueueFrame extends JFrame {
    private JTextField sizeField;
    private JTextField elementField;
    private JTextArea displayArea;
    private int[] circularQueue;
    private int front = -1, rear = -1;
    private int maxSize;
    
    private final Color BACKGROUND_COLOR_1 = new Color(44, 62, 80);    // Dark Blue
    private final Color BACKGROUND_COLOR_2 = new Color(52, 152, 219);  // Light Blue
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(231, 76, 60);         // Red
    private final Color BUTTON_HOVER_COLOR = new Color(192, 57, 43);   // Dark Red
    private final Color FIELD_BACKGROUND = new Color(236, 240, 241);   // Light Gray
    private final Color DISPLAY_AREA_BACKGROUND = new Color(248, 249, 249); // Off White

    public CircularQueueFrame() {
        setTitle("Circular Queue Operations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create gradient background panel as main container
        JPanel mainPanel = createGradientPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // Top Panel for Size
        JPanel topPanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel sizeLabel = createStyledLabel("Circular Queue Size:");
        sizeField = createStyledTextField();
        JButton createButton = createStyledButton("Create Circular Queue");
        createButton.addActionListener(e -> createCircularQueue());
        
        topPanel.add(sizeLabel);
        topPanel.add(sizeField);
        topPanel.add(createButton);

        // Middle Panel for Operations
        JPanel middlePanel = createGradientPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel elementLabel = createStyledLabel("Element:");
        elementField = createStyledTextField();
        
        JButton enqueueButton = createStyledButton("Enqueue");
        JButton dequeueButton = createStyledButton("Dequeue");
        JButton displayButton = createStyledButton("Display");

        enqueueButton.addActionListener(e -> enqueue());
        dequeueButton.addActionListener(e -> dequeue());
        displayButton.addActionListener(e -> displayQueue());

        middlePanel.add(elementLabel);
        middlePanel.add(elementField);
        middlePanel.add(enqueueButton);
        middlePanel.add(dequeueButton);
        middlePanel.add(displayButton);

        // Bottom Panel for Display
        displayArea = new JTextArea(10, 40);
        styleDisplayArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add all panels to main container
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
    }
    
    private JPanel createGradientPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                                   RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR_1, 
                                                    getWidth(), getHeight(), BACKGROUND_COLOR_2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(10);
        field.setPreferredSize(new Dimension(100, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(FIELD_BACKGROUND);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BACKGROUND_COLOR_2, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed() || getModel().isRollover()) {
                    g2d.setPaint(BUTTON_HOVER_COLOR);
                } else {
                    g2d.setPaint(BUTTON_COLOR);
                }

                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

                g2d.setColor(TEXT_COLOR);
                g2d.setFont(getFont());
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleDisplayArea() {
        displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        displayArea.setBackground(DISPLAY_AREA_BACKGROUND);
        displayArea.setForeground(BACKGROUND_COLOR_1);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayArea.setEditable(false);
    }

    // ... Rest of the methods (createCircularQueue, enqueue, dequeue, displayQueue) remain the same ...

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CircularQueueFrame frame = new CircularQueueFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createCircularQueue() {
        try {
            maxSize = Integer.parseInt(sizeField.getText());
            circularQueue = new int[maxSize];
            front = rear = -1;
            displayArea.setText("Circular Queue of size " + maxSize + " created.\n");
        } catch (NumberFormatException e) {
            displayArea.setText("Please enter a valid size.\n");
        }
    }

    private void enqueue() {
        try {
            if ((front == 0 && rear == maxSize - 1) || (rear + 1 == front)) {
                displayArea.append("Circular Queue is full!\n");
                return;
            }
            int element = Integer.parseInt(elementField.getText());
            if (front == -1) {
                front = 0;
            }
            rear = (rear + 1) % maxSize;
            circularQueue[rear] = element;
            displayArea.append("Enqueued: " + element + "\n");
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid number.\n");
        }
    }

    private void dequeue() {
        if (front == -1) {
            displayArea.append("Circular Queue is empty!\n");
            return;
        }
        int element = circularQueue[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % maxSize;
        }
        displayArea.append("Dequeued: " + element + "\n");
    }

    private void displayQueue() {
        if (circularQueue != null && front != -1) {
            StringBuilder sb = new StringBuilder("Circular Queue contents:\n");
            int i = front;
            if (front <= rear) {
                while (i <= rear) {
                    sb.append(circularQueue[i]).append("\n");
                    i++;
                }
            } else {
                while (i < maxSize) {
                    sb.append(circularQueue[i]).append("\n");
                    i++;
                }
                i = 0;
                while (i <= rear) {
                    sb.append(circularQueue[i]).append("\n");
                    i++;
                }
            }
            displayArea.setText(sb.toString());
        } else {
            displayArea.setText("Circular Queue is empty or not created yet.\n");
        }
    }
    class CircularQueueDemo {
        public static void main(String[] args) {
            EventQueue.invokeLater(() -> {
                try {
                    CircularQueueFrame frame = new CircularQueueFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
