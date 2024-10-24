package dsPage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class QueueFrame extends JFrame {
    private JTextField sizeField;
    private JTextField elementField;
    private JTextArea displayArea;
    private int[] queue;
    private int front = -1, rear = -1;
    private int maxSize;
    
    private final Color BACKGROUND_COLOR_1 = new Color(44, 62, 80);    // Dark Blue
    private final Color BACKGROUND_COLOR_2 = new Color(52, 152, 219);  // Light Blue
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(231, 76, 60);         // Red
    private final Color BUTTON_HOVER_COLOR = new Color(192, 57, 43);   // Dark Red
    private final Color FIELD_BACKGROUND = new Color(236, 240, 241);   // Light Gray
    private final Color DISPLAY_AREA_BACKGROUND = new Color(248, 249, 249); // Off White

    public QueueFrame() {
        setTitle("Queue Operations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel for Size
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("Queue Size:"));
        sizeField = new JTextField(10);
        JButton createButton = new JButton("Create Queue");
        createButton.addActionListener(e -> createQueue());
        topPanel.add(sizeField);
        topPanel.add(createButton);

        // Middle Panel for Operations
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        middlePanel.add(new JLabel("Element:"));
        elementField = new JTextField(10);
        middlePanel.add(elementField);

        JButton enqueueButton = new JButton("Enqueue");
        JButton dequeueButton = new JButton("Dequeue");
        JButton displayButton = new JButton("Display");

        enqueueButton.addActionListener(e -> enqueue());
        dequeueButton.addActionListener(e -> dequeue());
        displayButton.addActionListener(e -> displayQueue());

        middlePanel.add(enqueueButton);
        middlePanel.add(dequeueButton);
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

    private void applyTheme() {
        // Frame styling
        this.getContentPane().setBackground(BACKGROUND_COLOR_1);
        
        // Style text fields
        styleTextField(sizeField);
        styleTextField(elementField);
        
        // Style panels
        JPanel[] panels = {(JPanel)getContentPane().getComponent(0), 
                          (JPanel)getContentPane().getComponent(1)};
        for (JPanel panel : panels) {
            panel.setBackground(BACKGROUND_COLOR_1);
            styleLabels(panel);
            styleButtons(panel);
        }
        
        // Style display area
        styleDisplayArea();
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(100, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(FIELD_BACKGROUND);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BACKGROUND_COLOR_2, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

            private void styleLabels(Container container) {
                for (Component comp : container.getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel label = (JLabel) comp;
                        label.setForeground(TEXT_COLOR);
                        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    }
                }
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
                        
                        // Add hover effect
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                button.setBackground(BUTTON_HOVER_COLOR);
                            }
                            public void mouseExited(MouseEvent evt) {
                                button.setBackground(BUTTON_COLOR);
                            }
                        });
                    }
                }
            }

            private void styleDisplayArea() {
                displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                displayArea.setBackground(DISPLAY_AREA_BACKGROUND);
                displayArea.setForeground(Color.BLACK);
                displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }

            private void createQueue() {
                try {
                    maxSize = Integer.parseInt(sizeField.getText());
                    queue = new int[maxSize];
                    front = rear = -1;
                    displayArea.setText("Queue of size " + maxSize + " created.\n");
                } catch (NumberFormatException e) {
                    displayArea.setText("Please enter a valid size.\n");
                }
            }

            private void enqueue() {
                try {
                    if (rear == maxSize - 1) {
                        displayArea.append("Queue is full!\n");
                        return;
                    }
                    int element = Integer.parseInt(elementField.getText());
                    if (front == -1) {
                        front = 0;
                    }
                    queue[++rear] = element;
                    displayArea.append("Enqueued: " + element + "\n");
                } catch (NumberFormatException e) {
                    displayArea.append("Please enter a valid number.\n");
                }
            }

            private void dequeue() {
                if (front == -1 || front > rear) {
                    displayArea.append("Queue is empty!\n");
                    return;
                }
                displayArea.append("Dequeued: " + queue[front++] + "\n");
                if (front > rear) {
                    front = rear = -1; // Reset queue when empty
                }
            }

            private void displayQueue() {
                if (queue != null && front != -1) {
                    StringBuilder sb = new StringBuilder("Queue contents (front to rear):\n");
                    for (int i = front; i <= rear; i++) {
                        sb.append(queue[i]).append(" ");
                    }
                    sb.append("\n");
                    displayArea.setText(sb.toString());
                } else {
                    displayArea.setText("Queue is empty or not created yet.\n");
                }
            }
        }

         class QueueOperationsDemo {
            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    QueueFrame frame = new QueueFrame();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                });
            }
        }
                    