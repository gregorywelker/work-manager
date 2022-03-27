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

import work_manager.project.Project;
import work_manager.status.Status;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user (if has authority) can edit a project's settings. There are three
 * setting options, namely: editing the project's name, description and status
 */
public class ProjectSettingsListener implements ActionListener {

    private final UserPanel userPanel;
    private final Project project;

    ProjectSettingsListener(UserPanel userPanel, Project project) {
        this.userPanel = userPanel;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextField projectNameField = new JTextField(project.getProjectName(), 20);
        panel.add(projectNameField, BorderLayout.NORTH);

        JComboBox projectStatusBox = new JComboBox(Status.values());
        projectStatusBox.setSelectedItem(project.getProjectStatus());
        panel.add(projectStatusBox, BorderLayout.SOUTH);

        JTextArea projectDescriptionField = new JTextArea(project.getProjectDescription());
        projectDescriptionField.setLineWrap(true);

        JScrollPane projectDescriptionScroll = new JScrollPane(projectDescriptionField);
        panel.add(projectDescriptionScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Projekt szerkesztése", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            project.updateProjectData(projectNameField.getText(), projectDescriptionField.getText(),
                    projectStatusBox.getSelectedItem().toString());
            userPanel.updateProjectHeaderPanel();
            userPanel.updateProjectBodyPanel();
            userPanel.updateProjectButtonsPanel();
        }
    }
}