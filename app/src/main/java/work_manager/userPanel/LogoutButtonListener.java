package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import work_manager.customUtils.CustomUtils;

/**
 * This class's overridden actionPerformed method calls the User class to
 * perform a logout, and then calls the userPanel to display the login panel
 */
class LogoutButtonListener implements ActionListener {

    private final UserPanel userPanel;

    LogoutButtonListener(UserPanel userPanel) {
        this.userPanel = userPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomUtils.logoutConfig();
        JOptionPane.getRootFrame().dispose();
        userPanel.getMainFrame().handleLogout();
    }
}