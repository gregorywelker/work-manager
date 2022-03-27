package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import work_manager.project.Project;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method calls the user to delete a
 * given project and then informs the userPanel to update the project view
 */
public class DeleteProjectListener implements ActionListener {

    private final UserPanel userPanel;
    private final User user;
    private final Project project;

    DeleteProjectListener(UserPanel userPanel, User user, Project project) {
        this.userPanel = userPanel;
        this.user = user;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        user.deleteProject(project);
        userPanel.removeProjectDisplay();
    }
}