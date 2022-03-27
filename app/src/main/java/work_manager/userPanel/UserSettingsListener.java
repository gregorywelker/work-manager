package work_manager.userPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import work_manager.passwordFocusListener.passwordFocusListener;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user can change his account's settings. There are three options, the user can
 * change his name and his password or the user can log out
 */
class UserSettingsListener implements ActionListener {

    private final UserPanel userPanel;
    private final User user;

    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JPasswordField newPasswordRepeatField;
    private JPasswordField currentPasswordField;

    UserSettingsListener(UserPanel userPanel, User user) {
        this.userPanel = userPanel;
        this.user = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * GridLayout for the two columns
         */
        JPanel panel = new JPanel(new GridLayout(1, 2));

        /**
         * Left panel for the data update fields
         */
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        panel.add(leftPanel);

        JPanel userIDFlow = new JPanel(new FlowLayout());
        userIDFlow.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(userIDFlow);

        JLabel userIDLabel = new JLabel("User ID: " + user.getUserID());
        userIDFlow.add(userIDLabel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel userNameLabel = new JLabel("Névváltoztatás: " + user.getUserName());
        userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(userNameLabel);

        newUsernameField = new JTextField("", 20);
        newUsernameField.setHorizontalAlignment(JTextField.CENTER);
        newUsernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(newUsernameField);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel passwordLabel = new JLabel("Jelszóváltoztatás");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(passwordLabel);

        newPasswordField = new JPasswordField("Új jelszó", 20);
        newPasswordField.setHorizontalAlignment(JTextField.CENTER);
        newPasswordField.addFocusListener(new passwordFocusListener(newPasswordField));
        newPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(newPasswordField);

        newPasswordRepeatField = new JPasswordField("Új jelszó megerősítése", 20);
        newPasswordRepeatField.setHorizontalAlignment(JTextField.CENTER);
        newPasswordRepeatField.addFocusListener(new passwordFocusListener(newPasswordRepeatField));
        newPasswordRepeatField.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(newPasswordRepeatField);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel promptLabel = new JLabel("Változtatások megerősítése");
        promptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(promptLabel);

        currentPasswordField = new JPasswordField("Jelenlegi jelszó", 20);
        currentPasswordField.setHorizontalAlignment(JTextField.CENTER);
        currentPasswordField.addFocusListener(new passwordFocusListener(currentPasswordField));
        currentPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(currentPasswordField);

        JPanel saveButtonFlow = new JPanel(new FlowLayout());
        saveButtonFlow.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(saveButtonFlow);

        JButton saveEditsButton = new JButton("Változtatások mentése");
        saveEditsButton.addActionListener(new UpdateUserDataListener(userPanel, user, newUsernameField,
                newPasswordField, newPasswordRepeatField, currentPasswordField));
        saveButtonFlow.add(saveEditsButton);

        /**
         * Right panel for logout
         */
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        panel.add(rightPanel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 125)));

        JButton logoutButton = new JButton("Kijelentkezés");
        logoutButton.setMaximumSize(new Dimension(200, 75));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(new LogoutButtonListener(userPanel));
        rightPanel.add(logoutButton);

        JOptionPane.showOptionDialog(null, panel, "Felhasználói adatok", JOptionPane.PLAIN_MESSAGE,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

    }
}