package dsPage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;

public class WelcomePage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Color primaryColor = new Color(123, 36, 20);  
    private Color secondaryColor = new Color(211, 84, 0);  
    private Color buttonHoverColor = new Color(192, 57, 43).brighter();  

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                WelcomePage frame = new WelcomePage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public WelcomePage() {
        setTitle("Data Structures Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 73, 94),
                        getWidth(), getHeight(), new Color(211, 84, 0));  
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 20, 1000, 60);
        titlePanel.setOpaque(false);
        contentPane.add(titlePanel);

        JLabel titleLabel = new JLabel("Data Structures Visualization");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(100, 120, 800, 300);
        buttonPanel.setOpaque(false);
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        String[] buttonLabels = {"Array", "Stack", "Queue", "Circular Queue", "Linked List"};
        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            button.addActionListener(e -> openDataStructureFrame(label));
            buttonPanel.add(button);
        }
    }

    private void openDataStructureFrame(String type) {
        switch (type) {
            case "Array":
                new ArrayFrame().setVisible(true);
                break;
            case "Stack":
                new StackFrame().setVisible(true);
                break;
            case "Queue":
                new QueueFrame().setVisible(true);
                break;
            case "Circular Queue":
                new CircularQueueFrame().setVisible(true);
                break;
            case "Linked List":
                new LinkedListFrame().setVisible(true);
                break;
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setPaint(primaryColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setPaint(buttonHoverColor);
                } else {
                    g2d.setPaint(primaryColor);
                }

                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);
            }
        };

        button.setPreferredSize(new Dimension(160, 100)); 
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}
