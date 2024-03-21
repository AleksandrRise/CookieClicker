package Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Game extends JFrame implements ActionListener, ChangeListener {

    // Panels
    private JPanel mainPanel;
    private JPanel topLine;
    private JPanel bottomLine;
    private JPanel abilitiesPanel;

    // Fonts
    Font font1 = new Font("Palatino", Font.BOLD, 40);

    // Buttons
    private JButton cookieButton;

    // Sliders
    private JSlider musicSlider;

    // Images
    private ImageIcon cookieIcon = new ImageIcon(ImageIO.read(new File("images/cookies/cookie.png"))
            .getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    private ImageIcon cookieIcon2 = new ImageIcon(ImageIO.read(new File("images/cookies/cookie2.png"))
            .getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    private ImageIcon cookieIcon3 = new ImageIcon(ImageIO.read(new File("images/cookies/cookie3.png"))
            .getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    private ImageIcon cookieIcon4 = new ImageIcon(ImageIO.read(new File("images/cookies/cookie4.png"))
            .getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    private ImageIcon shiningImg = new ImageIcon(ImageIO.read(new File("images/others/shining.png"))
            .getScaledInstance(500, 500, Image.SCALE_DEFAULT));

    // Labels
    JLabel counter;
    JLabel shiningLabel;
    JLabel bonusTextLabel;
    JLabel slidersBg;

    // Other Variables
    Abilities autoClicker;
    Clip bgMusic;
    FloatControl control;
    private short counterLimClicks = 0;
    private int counterClicks = 0;

    public Game() throws IOException {
        // Setting the frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBounds(0, 0, 900, 600);
        this.setTitle("Cookie Clicker");
        this.setIconImage(cookieIcon.getImage());
        this.setResizable(true);
        this.setResizable(false);
        this.setFocusable(false);
        this.setLocationRelativeTo(null);

        // Creating the main container.
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(50, 30, 800, 500);
        cookieButton = new JButton(cookieIcon);
        cookieButton.setBorder(null);
        cookieButton.setLayout(new FlowLayout(FlowLayout.TRAILING));
        cookieButton.setBounds(150, ((mainPanel.getHeight()/2) - (cookieIcon.getIconHeight()/2)),
                cookieIcon.getIconWidth(), cookieIcon.getIconHeight());
        cookieButton.addActionListener(this);
        shiningLabel = new JLabel(shiningImg);
        shiningLabel.setBorder(null);
        shiningLabel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        shiningLabel.setBounds(0, ((mainPanel.getHeight()/2) - (shiningImg.getIconHeight()/2)),
                shiningImg.getIconWidth(), shiningImg.getIconHeight());

        mainPanel.add(cookieButton);
        mainPanel.add(shiningLabel);

        // Top Line
        topLine = new JPanel();
        topLine.setLayout(null);
        topLine.setBackground(Color.black);
        topLine.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        topLine.setBounds(0, 0, this.getWidth(), 80);

        // Bottom Line
        bottomLine = new JPanel();
        bottomLine.setLayout(null);
        bottomLine.setBackground(Color.black);
        bottomLine.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        bottomLine.setBounds(0, this.getHeight()-100, this.getWidth(), 80);

        // Top Line Counter
        counter = new JLabel("Clicks: 0");
        counter.setLayout(null);
        counter.setBounds(0, 0, topLine.getWidth(), topLine.getHeight());
        counter.setFont(font1);
        counter.setForeground(Color.white);
        counter.setHorizontalAlignment(JLabel.CENTER);
        counter.setVerticalTextPosition(JLabel.CENTER);
        topLine.add(counter);

        // Bottom Line Music Volume
        musicSlider = new JSlider(-79, 6, -37);
        musicSlider.setBounds(100, 0, 250 ,80);
        musicSlider.addChangeListener(this);
        slidersBg = new JLabel();
        slidersBg.setBounds(0, 37, musicSlider.getWidth(), 5);
        slidersBg.setBackground(Color.lightGray);
        slidersBg.setOpaque(true);
        slidersBg.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        musicSlider.add(slidersBg);
        bottomLine.add(musicSlider);

        // Abilities Panel
        abilitiesPanel = new JPanel();
        abilitiesPanel.setLayout(null);
        abilitiesPanel.setBounds(550, 145, 250, 300);
        abilitiesPanel.setBackground(Color.lightGray);
        abilitiesPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5, true));
        abilitiesPanel.setOpaque(true);

        // AutoClicker - 1st Ability
        autoClicker = new AutoClicker(
                0,
                abilitiesPanel.getWidth(),
                "images/others/cursor.png",
                "Auto Clicker",
                "Auto Clicks each 10 seconds",
                30
        );
        autoClicker.addActionListener(this);
        abilitiesPanel.add(autoClicker);

        // Adding everything to the frame and making it visible.
        this.add(abilitiesPanel);
        this.add(bottomLine);
        this.add(topLine);
        this.add(mainPanel);
        this.setVisible(true);

        // Background Music
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/bgMusic.wav"));
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioStream);
            bgMusic.start();
            bgMusic.loop(bgMusic.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Timer
        Timer timer = new Timer(1500, e1 -> bonusTextLabel.setVisible(false));

        // Cookie button clicking actions.
        if (e.getSource() == cookieButton) {
            counterLimClicks++; // Counter for changing images.
            counterClicks++; // General Counter of clicks.
            counter.setText("Clicks: " + counterClicks);

            if (counterLimClicks == 5) {
                cookieButton.setIcon(cookieIcon2);
                cookieButton.setBorder(null);
            }
            if (counterLimClicks == 15) {
                cookieButton.setIcon(cookieIcon3);
                cookieButton.setBorder(null);
            }
            if (counterLimClicks == 25) {
                cookieButton.setIcon(cookieIcon4);
                cookieButton.setBorder(null);
            }
            if (counterLimClicks == 30) {
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/cookieSound.wav"));
                    Clip cookieSound = AudioSystem.getClip();
                    cookieSound.open(audioStream);
                    cookieSound.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                } finally {
                    counterClicks++; // 1 more click for breaking.
                    bonusTextLabel = new JLabel("+2");
                    bonusTextLabel.setLayout(null);
                    bonusTextLabel.setBounds(200, 0, topLine.getWidth(), topLine.getHeight());
                    bonusTextLabel.setFont(font1);
                    bonusTextLabel.setForeground(Color.white);
                    bonusTextLabel.setHorizontalAlignment(JLabel.CENTER);
                    bonusTextLabel.setVerticalTextPosition(JLabel.CENTER);
                    topLine.add(bonusTextLabel);
                    counter.setText("Clicks: " + counterClicks);
                    timer.start();


                    counterLimClicks = 0;
                    cookieButton.setIcon(cookieIcon);
                    cookieButton.setBorder(null);
                }
            }
        }

        boolean isTimerWorks = false; // For checking whether the timer exists already.
        // Checking whether the autoclicker purchase button was clicked.
        if (e.getSource() == autoClicker) {
            if (counterClicks >= 30) { // If there's enough clicks, then do next.
                try { // Sound of successful purchase
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/purchase.wav"));
                    Clip purchaseSound = AudioSystem.getClip();
                    purchaseSound.open(audioStream);
                    purchaseSound.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
                if (!isTimerWorks) { // If there's no timer, then do next.
                    isTimerWorks = true; // Change isTimerWorks to true since the timer is going to be created.
                    counterClicks -= 30; // Take clicks for this purchase.
                    counter.setText("Clicks: " + counterClicks); // Changing the text.
                    AutoClicker.createClicker(); // Creating one more clicker.
                    // Creating a timer.
                    Timer clicksTimer = new Timer(10000, e1 -> cookieButton.doClick());
                    clicksTimer.setRepeats(true);
                    clicksTimer.start();
                } else {
                    counterClicks -= 30;
                    counter.setText("Clicks: " + counterClicks);
                    AutoClicker.createClicker();
                }
            } else {
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/error.wav"));
                    Clip errorSound = AudioSystem.getClip();
                    errorSound.open(audioStream);
                    errorSound.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // For changing volume of music
    @Override
    public void stateChanged(ChangeEvent e) {
        control = (FloatControl) bgMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(musicSlider.getValue());
    }
}
