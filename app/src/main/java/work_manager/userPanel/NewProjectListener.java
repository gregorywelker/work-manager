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

import work_manager.textFocusListener.TextFocusListener;
import work_manager.user.User;

/**
 * This class's overridden actionPerformed method brings up a panel where the
 * user can create a new project. Two fields are shown, in the first one the
 * user can specify the project's name, in the second one the project's
 * description
 */
class NewProjectListener implements ActionListener {

    private final UserPanel userPanel;
    private final User user;

    NewProjectListener(UserPanel userPanel, User user) {
        this.userPanel = userPanel;
        this.user = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 200));

        JTextField projectName = new JTextField("Projekt név", 20);
        projectName.addFocusListener(new TextFocusListener(projectName));
        panel.add(projectName, BorderLayout.NORTH);

        JTextArea projectDescription = new JTextArea("Projekt leírás");
        projectDescription.addFocusListener(new TextFocusListener(projectDescription));
        projectDescription.setLineWrap(true);
        JScrollPane projectDescScroll = new JScrollPane(projectDescription);
        panel.add(projectDescScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Új projekt adatai", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            user.createNewProject(projectName.getText(), projectDescription.getText());
            userPanel.updateProjectButtonsPanel();
        }

    }
}
