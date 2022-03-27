package work_manager.userPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import work_manager.comment.Comment;
import work_manager.milestone.Milestone;
import work_manager.project.Project;

/**
 * This class's overridden actionPerformed method calls a project to delete a
 * comment from one of its milestones, then calls the necessary methods to
 * update the views
 */
public class DeleteCommentListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;
    private final Milestone milestone;
    private final Comment comment;

    @Override
    public void actionPerformed(ActionEvent e) {
        project.deleteComment(milestone, comment);
        userPanel.updateProjectBodyPanel();
    }

    public DeleteCommentListener(UserPanel userPanel, Project project, Milestone milestone, Comment comment) {
        this.userPanel = userPanel;
        this.project = project;
        this.milestone = milestone;
        this.comment = comment;
    }

}