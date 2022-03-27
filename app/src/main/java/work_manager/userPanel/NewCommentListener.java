package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import work_manager.milestone.Milestone;
import work_manager.project.Project;
import work_manager.textFocusListener.TextFocusListener;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user can add a new comment to a milestone inside a project. The panel has one
 * field where the user can type in the desired comment
 */
public class NewCommentListener implements ActionListener {

    private final UserPanel userPanel;
    private final Milestone milestone;
    private final Project project;
    private final User user;

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextArea commentText = new JTextArea("Komment");
        commentText.addFocusListener(new TextFocusListener(commentText));
        commentText.setLineWrap(true);

        JScrollPane commentTextScroll = new JScrollPane(commentText);
        panel.add(commentTextScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Komment hozzáadása", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            project.addNewComment(user.getUserID(), milestone, commentText.getText());
            JOptionPane.getRootFrame().dispose();
            userPanel.updateProjectBodyPanel();
        }
    }

    public NewCommentListener(UserPanel userPanel, Project project, User user, Milestone milestone) {
        this.userPanel = userPanel;
        this.project = project;
        this.user = user;
        this.milestone = milestone;
    }
}