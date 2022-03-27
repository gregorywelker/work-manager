package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import work_manager.milestone.Milestone;
import work_manager.project.Project;
import work_manager.status.Status;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user (if has authority) can edit a milestone's settings. There are three
 * setting options. The user can change the milestone's name, description or
 * status
 */
public class MilestoneSettingsListener implements ActionListener {

    private final Project project;
    private final Milestone milestone;
    private final UserPanel userPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextField milestoneNameField = new JTextField(milestone.getMilestoneName(), 20);
        panel.add(milestoneNameField, BorderLayout.NORTH);

        JComboBox milestoneStatusBox = new JComboBox(Status.values());
        milestoneStatusBox.setSelectedItem(milestone.getMilestoneStatus());
        panel.add(milestoneStatusBox, BorderLayout.SOUTH);

        JTextArea milestoneDescriptionField = new JTextArea(milestone.getMilestoneDescription());
        milestoneDescriptionField.setLineWrap(true);

        JScrollPane milestoneDescriptionScroll = new JScrollPane(milestoneDescriptionField);
        panel.add(milestoneDescriptionScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Mérföldkő szerkesztése", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            project.updateMilestoneData(milestone, milestoneNameField.getText(), milestoneDescriptionField.getText(),
                    milestoneStatusBox.getSelectedItem().toString());
            userPanel.updateProjectBodyPanel();
        }
    }

    public MilestoneSettingsListener(Project project, Milestone milestone, UserPanel userPanel) {
        this.project = project;
        this.milestone = milestone;
        this.userPanel = userPanel;
    }
}