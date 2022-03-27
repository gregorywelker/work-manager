package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import work_manager.project.Project;

/**
 * This class's overridden actionPerformed method prompts a project to remove a
 * member from the particular project
 */
class RemoveMemberListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;
    private final String userID;

    @Override
    public void actionPerformed(ActionEvent e) {
        project.removeMember(userID);
        JOptionPane.getRootFrame().dispose();
        userPanel.updateProjectUsersPanel();
    }

    public RemoveMemberListener(UserPanel userPanel, Project project, String userID) {
        this.userPanel = userPanel;
        this.project = project;
        this.userID = userID;
    }
}