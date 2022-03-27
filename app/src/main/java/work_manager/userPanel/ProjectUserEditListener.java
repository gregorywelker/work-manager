package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import work_manager.project.Project;
import work_manager.role.Role;
import work_manager.roleManager.RoleManager;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user (if he has authority) can edit an other user's role and presence inside
 * a given project.
 */
public class ProjectUserEditListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;
    private final String targetUserID;
    private final User user;

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 135));

        JPanel nameRow = new JPanel(new FlowLayout());
        panel.add(nameRow);

        JLabel nameLabel = new JLabel("Név: " + project.getUserName(targetUserID));
        nameRow.add(nameLabel);

        JPanel roleRow = new JPanel(new FlowLayout());
        panel.add(roleRow);

        JLabel roleLabel = new JLabel("Szerep: ");
        roleRow.add(roleLabel);

        Role clientRole = project.getUserRole(user.getUserID());
        Role targetRole = project.getUserRole(targetUserID);

        JComboBox roleBox = new JComboBox<>();

        if (RoleManager.greaterClientAuthority(clientRole, targetRole)) {

            roleBox = new JComboBox<>(RoleManager.getGrantableRoles());
            roleBox.setSelectedItem(targetRole);
            roleRow.add(roleBox);

            JPanel buttonRow = new JPanel(new FlowLayout());
            panel.add(buttonRow, BorderLayout.SOUTH);
            JButton removeUserButton = new JButton("Felhasználó eltávolítása");
            removeUserButton.addActionListener(new RemoveMemberListener(userPanel, project, targetUserID));
            buttonRow.add(removeUserButton);

        } else {
            JLabel targetRoleLabel = new JLabel(targetRole.toString());
            roleRow.add(targetRoleLabel);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Felhasználó szerkesztése",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Role newRole = (Role) roleBox.getSelectedItem();
            if (newRole != null && newRole != targetRole) {
                project.updateRole(targetUserID, newRole);
                userPanel.updateProjectBodyPanel();
                userPanel.updateProjectUsersPanel();
            }
        }
    }

    public ProjectUserEditListener(UserPanel userPanel, Project project, User user, String userID) {
        this.userPanel = userPanel;
        this.project = project;
        this.user = user;
        this.targetUserID = userID;
    }
}