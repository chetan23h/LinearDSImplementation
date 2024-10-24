package dsPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

class LinkedListFrame extends JFrame {
    public class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private JTextField elementField;
    private JTextField positionField;
    private JTextArea displayArea;
    
    private final Color BACKGROUND_COLOR_1 = new Color(44, 62, 80);    // Dark Blue
    private final Color BACKGROUND_COLOR_2 = new Color(52, 152, 219);  // Light Blue
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(231, 76, 60);         // Red
    private final Color BUTTON_HOVER_COLOR = new Color(192, 57, 43);   // Dark Red
    private final Color FIELD_BACKGROUND = new Color(236, 240, 241);   // Light Gray
    private final Color DISPLAY_AREA_BACKGROUND = new Color(248, 249, 249); // Off White

    public LinkedListFrame() {
        setTitle("Linked List Operations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel for Input
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("Element:"));
        elementField = new JTextField(10);
        topPanel.add(elementField);
        topPanel.add(new JLabel("Position:"));
        positionField = new JTextField(5);
        topPanel.add(positionField);

        // Middle Panel for Operations
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton insertFirstButton = new JButton("Insert First");
        JButton insertLastButton = new JButton("Insert Last");
        JButton insertAtButton = new JButton("Insert At");
        JButton deleteFirstButton = new JButton("Delete First");
        JButton deleteLastButton = new JButton("Delete Last");
        JButton deleteAtButton = new JButton("Delete At");
        JButton displayButton = new JButton("Display");

        insertFirstButton.addActionListener(e -> insertFirst());
        insertLastButton.addActionListener(e -> insertLast());
        insertAtButton.addActionListener(e -> insertAt());
        deleteFirstButton.addActionListener(e -> deleteFirst());
        deleteLastButton.addActionListener(e -> deleteLast());
        deleteAtButton.addActionListener(e -> deleteAt());
        displayButton.addActionListener(e -> displayList());

        middlePanel.add(insertFirstButton);
        middlePanel.add(insertLastButton);
        middlePanel.add(insertAtButton);
        middlePanel.add(deleteFirstButton);
        middlePanel.add(deleteLastButton);
        middlePanel.add(deleteAtButton);
        middlePanel.add(displayButton);

        // Bottom Panel for Display
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add all panels
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        
        applyTheme();
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
    
    private void applyTheme() {
        // Frame styling
        this.getContentPane().setBackground(BACKGROUND_COLOR_1);
        
        // Style text fields
        styleTextField(elementField);
        styleTextField(positionField);  // Add this line
        
        // Style labels
        styleLabels(this);
        
        // Style display area
        styleDisplayArea();
        
        // Rest of the method remains the same...
        for (Component comp : this.getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setOpaque(false); // Make panels transparent to show gradient
                for (Component button : panel.getComponents()) {
                    if (button instanceof JButton) {
                        JButton btn = (JButton) button;
                        styleButton(btn);
                    }
                }
            }
        }
    }
    
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
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

    private void insertFirst() {
        try {
            int element = Integer.parseInt(elementField.getText());
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
            displayArea.append("Inserted " + element + " at the beginning\n");
            displayList();
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid number.\n");
        }
    }

    private void insertLast() {
        try {
            int element = Integer.parseInt(elementField.getText());
            Node newNode = new Node(element);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            displayArea.append("Inserted " + element + " at the end\n");
            displayList();
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid number.\n");
        }
    }

    private void insertAt() {
        try {
            int element = Integer.parseInt(elementField.getText());
            int position = Integer.parseInt(positionField.getText());
            
            if (position < 0) {
                displayArea.append("Invalid position\n");
                return;
            }
            
            Node newNode = new Node(element);
            if (position == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                for (int i = 0; i < position - 1 && current != null; i++) {
                    current = current.next;
                }
                if (current == null) {
                    displayArea.append("Position out of bounds\n");
                    return;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
            displayArea.append("Inserted " + element + " at position " + position + "\n");
            displayList();
        } catch (NumberFormatException e) {
            displayArea.append("Please enter valid numbers.\n");
        }
    }

    private void deleteFirst() {
        if (head == null) {
            displayArea.append("List is empty\n");
            return;
        }
        int deleted = head.data;
        head = head.next;
        displayArea.append("Deleted " + deleted + " from the beginning\n");
        displayList();
    }

    private void deleteLast() {
        if (head == null) {
            displayArea.append("List is empty\n");
            return;
        }
        if (head.next == null) {
            int deleted = head.data;
            head = null;
            displayArea.append("Deleted " + deleted + " from the end\n");
            displayList();
            return;
        }
        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        int deleted = current.next.data;
        current.next = null;
        displayArea.append("Deleted " + deleted + " from the end\n");
        displayList();
    }

    private void deleteAt() {
        try {
            int position = Integer.parseInt(positionField.getText());
            if (head == null) {
                displayArea.append("List is empty\n");
                return;
            }
            if (position < 0) {
                displayArea.append("Invalid position\n");
                return;
            }
            if (position == 0) {
                int deleted = head.data;
                head = head.next;
                displayArea.append("Deleted " + deleted + " from position " + position + "\n");
                displayList();
                return;
            }
            Node current = head;
            for (int i = 0; i < position - 1 && current != null; i++) {
                current = current.next;
            }
            if (current == null || current.next == null) {
                displayArea.append("Position out of bounds\n");
                return;
            }
            int deleted = current.next.data;
            current.next = current.next.next;
            displayArea.append("Deleted " + deleted + " from position " + position + "\n");
            displayList();
        } catch (NumberFormatException e) {
            displayArea.append("Please enter a valid position.\n");
        }
    }

    private void displayList() {
        StringBuilder sb = new StringBuilder("Linked List contents:\n");
        Node current = head;
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        sb.append("null\n");
        displayArea.setText(sb.toString());
    }
}
class LinkedListDemo {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LinkedListFrame frame = new LinkedListFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}