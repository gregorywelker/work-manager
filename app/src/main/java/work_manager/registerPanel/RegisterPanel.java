package work_manager.registerPanel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import work_manager.mainFrame.MainFrame;
import work_manager.passwordFocusListener.passwordFocusListener;
import work_manager.textFocusListener.TextFocusListener;

/**
 * The panel where users can resigter a new account
 */
public class RegisterPanel extends JPanel {

    private JTextField userNameField;
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JPasswordField passwordRepField;
    private JButton registerButton;
    private JButton backButton;
    private MainFrame mainFrame;

    /**
     * Constructor, creates the different parts of the panel
     * 
     * @param mainFrame
     */
    public RegisterPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        setupHeaderPanel();
        setupInputPanel();
        setupRegisterPanel();
        setupBackPanel();
    }

    /**
     * Creating the header panel which contains the header label
     */
    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel();
        add(headerPanel);

        JLabel loginLabel = new JLabel("Regisztráció");
        headerPanel.add(loginLabel);
    }

    /**
     * Setting up input panels for registration
     */
    private void setupInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(inputPanel);

        userNameField = new JTextField("Teljes név", 20);
        userNameField.setHorizontalAlignment(JTextField.CENTER);
        userNameField.addFocusListener(new TextFocusListener(userNameField));
        inputPanel.add(userNameField);

        userIDField = new JTextField("Felhasználó név", 20);
        userIDField.setHorizontalAlignment(JTextField.CENTER);
        userIDField.addFocusListener(new TextFocusListener(userIDField));
        inputPanel.add(userIDField);

        passwordField = new JPasswordField("Jelszó", 20);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.addFocusListener(new passwordFocusListener(passwordField));
        inputPanel.add(passwordField);

        passwordRepField = new JPasswordField("Jelszó megerősítése", 20);
        passwordRepField.setHorizontalAlignment(JTextField.CENTER);
        passwordRepField.addFocusListener(new passwordFocusListener(passwordRepField));
        inputPanel.add(passwordRepField);
    }

    /**
     * Setting up register panel with the register button
     */
    private void setupRegisterPanel() {
        JPanel registerPanel = new JPanel();
        add(registerPanel);

        registerButton = new JButton("Regisztráció");
        registerButton.addActionListener(new RegisterListener(this));
        registerPanel.add(registerButton);
    }

    /**
     * Setup back panel which takes the user back to the login panel
     */
    private void setupBackPanel() {
        JPanel backPanel = new JPanel();
        add(backPanel);

        backButton = new JButton("Vissza");
        backButton.addActionListener(new LoginPanelListener(this));
        backPanel.add(backButton);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public JTextField getUserNameField() {
        return userNameField;
    }

    public JTextField getUserIDField() {
        return userIDField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getPasswordRepField() {
        return passwordRepField;
    }
}
