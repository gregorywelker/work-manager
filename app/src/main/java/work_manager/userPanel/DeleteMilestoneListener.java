package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import work_manager.milestone.Milestone;
import work_manager.project.Project;

/**
 * This class's overridden actionPerformed method calls a project to delete a
 * given milestone from the project, then calls the necessary methods to update
 * the views
 */
public class DeleteMilestoneListener implements ActionListener {
    private final Project project;
    private final Milestone milestone;
    private final UserPanel userPanel;

    public DeleteMilestoneListener(Project project, Milestone milestone, UserPanel userPanel) {
        this.project = project;
        this.milestone = milestone;
        this.userPanel = userPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        project.deleteMilestone(milestone);
        userPanel.updateProjectBodyPanel();
    }
}
