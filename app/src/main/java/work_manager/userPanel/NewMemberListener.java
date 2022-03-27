package work_manager.userPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import work_manager.project.Project;
import work_manager.textFocusListener.TextFocusListener;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user (if has authority) can add a new member to a project. The panel has one
 * field where the user has to input the userID of the person that needs to be
 * added to the project
 */
class NewMemberListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;

    NewMemberListener(UserPanel userPanel, Project project) {
        this.userPanel = userPanel;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(325, 75));

        JTextField userIDField = new JTextField("Felhasználói azonosító", 20);
        userIDField.addFocusListener(new TextFocusListener(userIDField));
        panel.add(userIDField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Felhasználó felvétele", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (project.addNewMember(userIDField.getText())) {
                userPanel.updateProjectUsersPanel();
            } else {
                JOptionPane.getRootFrame().dispose();
                JOptionPane.showMessageDialog(null,
                        "Felhasználó felvétele nem sikerült\nEllenőrizd, hogy helyesen írtad be a felhasználó nevet",
                        null, JOptionPane.WARNING_MESSAGE);
            }

        }
    }
}