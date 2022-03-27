package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class's overridden actionPerformed method prompts the userPanel to load
 * the project panel indside the application. When the action is performed the
 * contained projectID gets passed to the userPanel then the userPanel handles
 * the loading part
 */
class ProjectButtonListener implements ActionListener {

    private final UserPanel userPanel;
    private final String projectID;

    @Override
    public void actionPerformed(ActionEvent e) {
        userPanel.setActiveProject(projectID);
        userPanel.updateProjectHeaderPanel();
        userPanel.updateProjectBodyPanel();
        userPanel.updateAddMemberButton();
        userPanel.updateProjectUsersPanel();
    }

    public ProjectButtonListener(UserPanel userPanel, String projectID) {
        this.userPanel = userPanel;
        this.projectID = projectID;
    }
}