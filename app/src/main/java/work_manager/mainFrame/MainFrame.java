package work_manager.mainFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import work_manager.loginPanel.LoginPanel;
import work_manager.registerPanel.RegisterPanel;
import work_manager.userPanel.UserPanel;

/**
 * The frame that holds the three main panels of the application
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout);

    private final int logRegPanelWidth = 400;
    private final int logRegPanelHeight = 300;
    private final int userPanelWidth = 1400;
    private final int userPanelHeight = 850;

    private UserPanel userPanel;

    /**
     * Constructor, if userID is not empty then the user's data corresponding to the
     * received userID gets loaded and user panel immediately comes up, otherwise
     * the login panel comes up
     * 
     * @param userID
     */
    public MainFrame(String userID) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Munka menedzser");
        setResizable(true);

        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        cards.add(loginPanel, "loginPanel");
        cards.add(registerPanel, "registerPanel");
        add(cards, BorderLayout.CENTER);

        if (userID.isEmpty()) {
            setSize(logRegPanelWidth, logRegPanelHeight);
            cardLayout.show(cards, "loginPanel");
        } else {
            showUserPanel(userID);
        }

    }

    /**
     * Displaying the registration panel
     */
    public void showRegisterPanel() {
        setSize(logRegPanelWidth, logRegPanelHeight);
        setResizable(true);
        cardLayout.show(cards, "registerPanel");
    }

    /**
     * Displaying the login panel
     */
    public void showLoginPanel() {
        setSize(logRegPanelWidth, logRegPanelHeight);
        setResizable(true);
        cardLayout.show(cards, "loginPanel");
    }

    /**
     * Handling logout, essentially leading back to the login panel
     */
    public void handleLogout() {
        setResizable(true);
        setSize(logRegPanelWidth, logRegPanelHeight);
        cardLayout.show(cards, "loginPanel");
        cards.remove(userPanel);

    }

    /**
     * Displaying the user panel with the user's data that can be loaded by the
     * userID
     * 
     * @param username
     */
    public void showUserPanel(String username) {
        userPanel = new UserPanel(this, username);
        cards.add(userPanel, "userPanel");
        setSize(userPanelWidth, userPanelHeight);
        setResizable(true);
        cardLayout.show(cards, "userPanel");
    }
}
