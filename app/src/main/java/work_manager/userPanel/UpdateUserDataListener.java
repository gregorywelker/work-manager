package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import work_manager.customUtils.CustomUtils;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method prompts the User class to
 * update an user's data with the received new data
 */
class UpdateUserDataListener implements ActionListener {

    private final UserPanel userPanel;
    private final User user;

    private final JTextField newUserName;
    private final JPasswordField newPassword;
    private final JPasswordField newPasswordRepeat;
    private final JPasswordField currentPassword;

    UpdateUserDataListener(UserPanel userPanel, User user, JTextField newUserName, JPasswordField newPassword,
            JPasswordField newPasswordRepeat, JPasswordField currentPassword) {
        this.userPanel = userPanel;
        this.user = user;
        this.newUserName = newUserName;
        this.newPassword = newPassword;
        this.newPasswordRepeat = newPasswordRepeat;
        this.currentPassword = currentPassword;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (user.updateUserData(newUserName.getText(), new String(newPassword.getPassword()),
                new String(newPasswordRepeat.getPassword()), new String(currentPassword.getPassword()))) {
            JOptionPane.getRootFrame().dispose();
            JOptionPane.showMessageDialog(null,
                    "Az adatok frissítésre kerültek, a változtatások érvényesítéséhez újra be kell jelentkezned", null,
                    JOptionPane.WARNING_MESSAGE);
            JOptionPane.getRootFrame().dispose();
            CustomUtils.logoutConfig();
            userPanel.getMainFrame().showLoginPanel();
        } else {
            JOptionPane.getRootFrame().dispose();
            JOptionPane.showMessageDialog(null, "Az adatok frissítése nem sikerült", null, JOptionPane.WARNING_MESSAGE);
        }
    }
}