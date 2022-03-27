package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import work_manager.comment.Comment;
import work_manager.project.Project;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user (if he is the person who wrote the comment) can edit a given comment.
 * There is a single text area inside the panel for comment editing
 */
public class EditCommentListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;
    private final Comment comment;

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextArea commentText = new JTextArea(comment.getCommentText());
        commentText.setLineWrap(true);

        JScrollPane commentTextScroll = new JScrollPane(commentText);
        panel.add(commentTextScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Komment szerkesztése", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            project.updateCommentData(comment, commentText.getText());
            JOptionPane.getRootFrame().dispose();
            userPanel.updateProjectBodyPanel();
        }
    }

    public EditCommentListener(UserPanel userPanel, Project project, Comment comment) {
        this.userPanel = userPanel;
        this.project = project;
        this.comment = comment;
    }

}