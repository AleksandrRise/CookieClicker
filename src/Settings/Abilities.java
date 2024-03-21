package Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public abstract class Abilities extends JButton {

    private JLabel abilityName;
    private JLabel abilityDesc;
    private ImageIcon icon;
    private JLabel iconLabel;
    private JPanel rightSide;
    private JLabel priceLabel;

    // Fonts
    Font nameFont = new Font("Futura", Font.BOLD, 15);
    Font descFont = new Font("Futura", Font.PLAIN, 8);
    Font priceFont = new Font("Trebuchet", Font.ITALIC, 18);

    public void setSettings(int amountOfAbilities, int width, String image, String name, String description, int price)
            throws IOException {

        int height = (100 * amountOfAbilities) + 20;

        priceLabel = new JLabel(price + " ||");
        priceLabel.setFont(priceFont);
        this.add(priceLabel);

        icon = new ImageIcon(ImageIO.read(new File(image))
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        iconLabel = new JLabel(icon);
        iconLabel.setLayout(null);
        iconLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        this.add(iconLabel);

        rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(0, 1));
        rightSide.setBounds(0, 0, this.getWidth(), this.getHeight());
        rightSide.setBackground(Color.white);
        rightSide.setOpaque(true);
        this.add(rightSide);

        abilityName = new JLabel(name);
        abilityName.setLayout(null);
        abilityName.setBounds(0, 0, rightSide.getWidth(), rightSide.getHeight());
        abilityName.setFont(nameFont);
        rightSide.add(abilityName);

        abilityDesc = new JLabel(description);
        abilityDesc.setFont(descFont);
        rightSide.add(abilityDesc);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setFocusable(true);
        this.setBounds(8, height, width-16, 60);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        this.setBackground(Color.white);
        this.setOpaque(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color color = new Color(255, 255, 200);
                setBackground(color);
                rightSide.setBackground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Color color = Color.white;
                setBackground(color);
                rightSide.setBackground(color);
            }
        });

    }
}
