package work_manager.loginPanel;

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
 * The panel where users can log in
 */
public class LoginPanel extends JPanel {

    private JTextField userIDField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private MainFrame mainFrame;

    /**
     * Constructor, a Main Frame is needed for callback actions
     * 
     * @param mainFrame
     */
    public LoginPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        setupHeaderPanel();
        setupInputPanel();
        setupLoginPanel();
        setupRegisterPanel();
    }

    /**
     * Setting up the login panel header part
     */
    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel();
        add(headerPanel);

        JLabel loginLabel = new JLabel("Bejelentkezés");
        headerPanel.add(loginLabel);
    }

    /**
     * Setting up input panels, namely user identification and password field
     */
    private void setupInputPanel() {

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(inputPanel);

        userIDField = new JTextField("Felhasználónév", 20);
        userIDField.setHorizontalAlignment(JTextField.CENTER);
        userIDField.addFocusListener(new TextFocusListener(userIDField));
        inputPanel.add(userIDField);

        passwordField = new JPasswordField("Jelszó", 20);
        passwordField.addFocusListener(new passwordFocusListener(passwordField));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        inputPanel.add(passwordField);
    }

    /**
     * Setting up panel with the login button
     */
    private void setupLoginPanel() {
        JPanel loginPanel = new JPanel();
        add(loginPanel);

        loginButton = new JButton("Bejelentkezés");
        loginButton.addActionListener(new LoginListener(this));
        loginPanel.add(loginButton);
    }

    /**
     * Setting up panel with a button that lead to the register panel
     */
    private void setupRegisterPanel() {
        JPanel registerPanel = new JPanel();
        add(registerPanel);

        registerButton = new JButton("Regisztráció");
        registerButton.addActionListener(new RegisterPanelListener(this));
        registerPanel.add(registerButton);
    }

    public JTextField getUserIDField() {
        return userIDField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
