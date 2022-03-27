package work_manager.loginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import work_manager.user.User;

/**
 * This class's overridden actionPerformed method calls the User class and gives
 * it the user credentials that the user entered throught the input panels in
 * the login panel
 */
class LoginListener implements ActionListener {

    private final LoginPanel loginPanel;

    LoginListener(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (User.checkUserCredentials(loginPanel.getUserIDField().getText(),
                new String(loginPanel.getPasswordField().getPassword()))) {
            loginPanel.getMainFrame().showUserPanel(loginPanel.getUserIDField().getText());
        } else {
            JOptionPane.showMessageDialog(null, "Hibás felhasználónév vagy jelszó", null, JOptionPane.WARNING_MESSAGE);
        }
    }
}