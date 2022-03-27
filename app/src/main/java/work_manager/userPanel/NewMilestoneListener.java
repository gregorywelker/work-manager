package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import work_manager.project.Project;
import work_manager.textFocusListener.TextFocusListener;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user can create a new milestone for a given project. Two fields are shown, in
 * the first one the user can specify the milestones's name, in the second one
 * the milestones's description
 */
public class NewMilestoneListener implements ActionListener {

    private final Project project;
    private final UserPanel userPanel;

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextField milestoneName = new JTextField("Mérföldkő neve", 20);
        milestoneName.addFocusListener(new TextFocusListener(milestoneName));
        panel.add(milestoneName, BorderLayout.NORTH);

        JTextArea milestoneDescription = new JTextArea("Mérföldkő leírása");
        milestoneDescription.addFocusListener(new TextFocusListener(milestoneDescription));
        milestoneDescription.setLineWrap(true);
        JScrollPane milestoneDescScroll = new JScrollPane(milestoneDescription);
        panel.add(milestoneDescScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Új mérföldkő adatai", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            project.addNewMilestone(milestoneName.getText(), milestoneDescription.getText());
            JOptionPane.getRootFrame().dispose();
            userPanel.updateProjectBodyPanel();
        }
    }

    public NewMilestoneListener(UserPanel userPanel, Project project) {
        this.userPanel = userPanel;
        this.project = project;
    }
}