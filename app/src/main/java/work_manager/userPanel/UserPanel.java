package work_manager.userPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import work_manager.comment.Comment;
import work_manager.mainFrame.MainFrame;
import work_manager.milestone.Milestone;
import work_manager.project.Project;
import work_manager.user.User;

/**
 * This class displays the user panel that comes up after someone succesfully
 * logs in. It is separated into three main parts. There is a header part that
 * contains the user's name and a settings button for the user. There is a left
 * side panel that contains all the projects for the given user, here the user
 * can choose from these project to display the content of them. The third,
 * center panel, comes up when the user clicks a project in the left panel, then
 * all of the selected project's data gets displayed in the center panel. The
 * center panel is also divided into three other parts. It has a header part for
 * the project header data, a body part for the project description,milestones
 * and comments data and a right side panel where the users participating in the
 * project can be seen
 */
public class UserPanel extends JPanel {

    private MainFrame mainFrame;

    private JPanel headerPanel;

    private User user;
    private Project activeProject;

    private JPanel projectUsersPanel;

    private JPanel projectButtonsListPanel;

    private JPanel projectBody;

    private JPanel projectHeaderPanel;

    private JPanel projectCenterPanel;

    private JPanel projectUsersListPanel;
    private JPanel projectUsersHeaderPanel;
    private JButton newUserButton;

    /**
     * UserPanel constructor
     * 
     * @param mainFrame A MainFrame is needed for callback upon logout
     * @param userID    A String user id is needed to know which user's data needs
     *                  to be loaded inside the UserPanel
     */
    public UserPanel(MainFrame mainFrame, String userID) {

        this.mainFrame = mainFrame;

        user = new User(userID);
        setLayout(new BorderLayout());

        drawHeaderPanel();
        setupProjectButtons();

    }

    /**
     * Setting up the header panel for the user, containing the user's name and user
     * data settings button
     */
    public void drawHeaderPanel() {

        headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        // headerPanel.setBackground(Color.yellow);
        add(headerPanel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(user.getUserName());
        headerPanel.add(nameLabel);

        JButton settingsButton = new JButton("\u2699");
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 30));
        settingsButton.addActionListener(new UserSettingsListener(this, user));
        headerPanel.add(settingsButton);

    }

    /**
     * This sets up the left side buttons panel and all the buttons inside that
     * panel for the projects that the user participates in
     */
    private void setupProjectButtons() {

        JPanel userProjectsPanel = new JPanel(new BorderLayout());
        add(userProjectsPanel, BorderLayout.WEST);

        JPanel projectsHeaderPanel = new JPanel(new FlowLayout());
        projectsHeaderPanel.setPreferredSize(new Dimension(170, 40));
        projectsHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        userProjectsPanel.add(projectsHeaderPanel, BorderLayout.NORTH);

        JLabel projectsLabel = new JLabel("Projektek");
        projectsHeaderPanel.add(projectsLabel);

        JButton newProjectButton = new JButton("+");
        newProjectButton.addActionListener(new NewProjectListener(this, user));
        projectsHeaderPanel.add(newProjectButton);

        projectButtonsListPanel = new JPanel();
        projectButtonsListPanel.setLayout(new BoxLayout(projectButtonsListPanel, BoxLayout.Y_AXIS));
        JScrollPane projectButtonsScroll = new JScrollPane(projectButtonsListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        userProjectsPanel.add(projectButtonsScroll);

        updateProjectButtonsPanel();
    }

    /**
     * Displaying the buttons separately, this is used when an update needs to be
     * performed inside the panel
     */
    public void updateProjectButtonsPanel() {
        projectButtonsListPanel.removeAll();
        for (int i = 0; i < user.getProjects().size(); i++) {

            JButton projectButton = new JButton(user.getProjects().get(i).getProjectName());
            projectButton.addActionListener(new ProjectButtonListener(this, user.getProjects().get(i).getProjectID()));
            projectButton.setPreferredSize(new Dimension(150, 75));
            projectButton.setMaximumSize(new Dimension(150, 75));
            projectButtonsListPanel.add(projectButton);
        }
        projectButtonsListPanel.revalidate();
        projectButtonsListPanel.repaint();
    }

    /**
     * This method loads the center part of the project, the header and the body
     * part, including its name, status, description, moreover all the milestone
     * data including its name, status, description and all the comments. This does
     * not load the project-associated user data panel which is located at the right
     * of the project panel. The two panels are separated because upon editing they
     * can be updated separately
     *
     */
    private void setupProjectCenterPanel() {

        /**
         * Setting up the center panel, this include the header and body of the project
         */

        projectCenterPanel = new JPanel(new BorderLayout());
        add(projectCenterPanel, BorderLayout.CENTER);

        /**
         * Setting up the project header panel
         */
        projectHeaderPanel = new JPanel();
        projectHeaderPanel.setLayout(new BoxLayout(projectHeaderPanel, BoxLayout.Y_AXIS));
        projectHeaderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        projectCenterPanel.add(projectHeaderPanel, BorderLayout.NORTH);
        updateProjectHeaderPanel();

        /**
         * Creating the project body panel under the project header containing project
         * description and all milestones in a JScrollPanel
         */
        projectBody = new JPanel();
        projectBody.setLayout(new BoxLayout(projectBody, BoxLayout.Y_AXIS));
        JScrollPane projectBodyScroll = new JScrollPane(projectBody, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        projectCenterPanel.add(projectBodyScroll, BorderLayout.CENTER);
        updateProjectBodyPanel();

    }

    /**
     * Side panel inside the project showing all the users and their roles who
     * participate in the particular project
     */
    private void setupProjectUsersListPanel() {

        projectUsersPanel = new JPanel(new BorderLayout());
        add(projectUsersPanel, BorderLayout.EAST);

        /**
         * Header part of the panel for holding the label and possibly displaying the
         * button to add a new member to the project if the user has authority
         */
        projectUsersHeaderPanel = new JPanel(new FlowLayout());
        projectUsersHeaderPanel.setPreferredSize(new Dimension(170, 40));
        projectUsersHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        projectUsersPanel.add(projectUsersHeaderPanel, BorderLayout.NORTH);

        JLabel newUserLabel = new JLabel("Felhasználók");
        projectUsersHeaderPanel.add(newUserLabel);

        updateAddMemberButton();
        /**
         * The list that holds all the users, embedded into a JScrollPane in case it
         * grows too big
         */
        projectUsersListPanel = new JPanel();
        projectUsersListPanel.setLayout(new BoxLayout(projectUsersListPanel, BoxLayout.Y_AXIS));
        // projectUsersListPanel.setBackground(Color.LIGHT_GRAY);
        JScrollPane projectUsersScroll = new JScrollPane(projectUsersListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        projectUsersPanel.add(projectUsersScroll, BorderLayout.EAST);

        updateProjectUsersPanel();
    }

    /**
     * This recreates the add new member button and updates the listener inside the
     * project
     */
    public void updateAddMemberButton() {
        if (projectUsersHeaderPanel != null && activeProject != null) {
            if (newUserButton != null) {
                projectUsersHeaderPanel.remove(newUserButton);
            }
            if (user.projectManagementAutority(activeProject)) {
                newUserButton = new JButton("+");
                newUserButton.setMaximumSize(new Dimension(10, 10));
                newUserButton.addActionListener(new NewMemberListener(this, activeProject));
                projectUsersHeaderPanel.add(newUserButton);
            }
            projectUsersHeaderPanel.revalidate();
            projectUsersHeaderPanel.repaint();
        }
    }

    /**
     * Displaying the users inside the scroll panel is separated because like this
     * it can be updated when needed
     */
    public void updateProjectUsersPanel() {

        if (projectUsersListPanel != null) {

            projectUsersListPanel.removeAll();

            if (activeProject != null) {
                ArrayList<String> userIDs = activeProject.getUserIDs();

                for (int i = 0; i < userIDs.size(); i++) {
                    String userID = userIDs.get(i);
                    /**
                     * Setting up all the labels that display the data of all users for the project
                     */
                    JPanel userHolder = new JPanel();
                    userHolder.setLayout(new BoxLayout(userHolder, BoxLayout.X_AXIS));
                    userHolder.setMaximumSize(new Dimension(300, 40));
                    projectUsersListPanel.add(userHolder);

                    userHolder.add(Box.createRigidArea(new Dimension(5, 0)));
                    JTextField userField = new JTextField(activeProject.getUserName(userID) + " ("
                            + activeProject.getUserRole(userID).toString() + ")", 15);
                    userField.setEditable(false);
                    userHolder.add(userField);

                    /** If the logged in user has authority here we are adding the edit button */
                    if (user.projectManagementAutority(activeProject)) {
                        userHolder.add(Box.createHorizontalGlue());
                        JButton userEdit = new JButton("\u2699");
                        userEdit.setPreferredSize(new Dimension(25, 25));
                        userEdit.setFont(new Font("Arial", Font.PLAIN, 25));
                        userEdit.addActionListener(new ProjectUserEditListener(this, activeProject, user, userID));
                        userHolder.add(userEdit);
                    }
                }
            }
            projectUsersListPanel.revalidate();
            projectUsersListPanel.repaint();
        }
    }

    /**
     * Displaying the project header, this method is used in project setup and also
     * this is used when some data needs to be rerendered inside the header part of
     * the project
     */
    public void updateProjectHeaderPanel() {

        if (projectHeaderPanel != null) {

            projectHeaderPanel.removeAll();

            if (activeProject != null) {
                projectHeaderPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                JPanel headerFlow = new JPanel(new FlowLayout());
                projectHeaderPanel.add(headerFlow);

                JLabel projectNameLabel = new JLabel(activeProject.getProjectName());
                projectNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                projectNameLabel.setFont(new Font("Arial", Font.PLAIN, 30));
                headerFlow.add(projectNameLabel);

                projectHeaderPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                if (user.projectManagementAutority(activeProject)) {
                    JButton projectSettingsButton = new JButton("\u2699");
                    projectSettingsButton.setFont(new Font("Arial", Font.PLAIN, 25));
                    projectSettingsButton.addActionListener(new ProjectSettingsListener(this, activeProject));
                    headerFlow.add(projectSettingsButton);
                }

                JLabel projectStatus = new JLabel(activeProject.getProjectStatus().toString());
                projectStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
                projectHeaderPanel.add(projectStatus);

                projectHeaderPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                JLabel userRole = new JLabel(activeProject.getUserRole(user.getUserID()).toString());
                userRole.setAlignmentX(Component.CENTER_ALIGNMENT);
                projectHeaderPanel.add(userRole);

                projectHeaderPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                JPanel headerBottom = new JPanel();
                headerBottom.setLayout(new BoxLayout(headerBottom, BoxLayout.X_AXIS));
                projectHeaderPanel.add(headerBottom);

                if (user.projectDeleteAuthority(activeProject)) {
                    JButton deleteProjectButton = new JButton("Projekt törlése");
                    deleteProjectButton.addActionListener(new DeleteProjectListener(this, user, activeProject));
                    headerBottom.add(deleteProjectButton);
                    headerBottom.add(Box.createHorizontalGlue());
                }
                if (user.projectManagementAutority(activeProject)) {
                    headerBottom.add(Box.createHorizontalGlue());
                    JButton newMilestoneButton = new JButton("Új mérföldkő");
                    newMilestoneButton.addActionListener(new NewMilestoneListener(this, activeProject));
                    headerBottom.add(newMilestoneButton);
                }
            }

            projectHeaderPanel.revalidate();
            projectHeaderPanel.repaint();
        }
    }

    /**
     * Displaying the project body, used for project setup and also for project data
     * update
     */
    public void updateProjectBodyPanel() {
        if (projectBody != null) {

            projectBody.removeAll();

            if (activeProject != null) {
                projectBody.add(Box.createRigidArea(new Dimension(0, 25)));

                /** Flow layout for centering the scroll panel of the project description */
                JPanel descScrollFlow = new JPanel();
                projectBody.add(descScrollFlow);
                /** Project description part of the project body */
                JTextArea projectDescription = new JTextArea(
                        "Projekt leírása: " + activeProject.getProjectDescription());
                projectDescription.setFont(new Font("Arial", Font.PLAIN, 15));
                projectDescription.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                projectDescription.setLineWrap(true);
                projectDescription.setEditable(false);
                /** Scroll panel for the project body if the description grows too big */
                JScrollPane projectDescScroll = new JScrollPane(projectDescription,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                projectDescScroll.setMaximumSize(new Dimension(900, 100));
                projectDescScroll.setPreferredSize(new Dimension(900, 100));
                descScrollFlow.add(projectDescScroll);

                /** Separator and rigid areas between description and milestones */
                projectBody.add(Box.createRigidArea(new Dimension(0, 20)));
                projectBody.add(new JSeparator());
                projectBody.add(Box.createRigidArea(new Dimension(0, 20)));

                /**
                 * Looping through milestones and loading all the data from them to display
                 */
                ArrayList<Milestone> milestones = activeProject.getMilestones();

                for (int i = 0; i < milestones.size(); i++) {
                    Milestone milestone = milestones.get(i);

                    /** This panel provides additional spacing for milestones */
                    JPanel milestoneHolderPanel = new JPanel();
                    milestoneHolderPanel.setLayout(new BoxLayout(milestoneHolderPanel, BoxLayout.Y_AXIS));
                    milestoneHolderPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    projectBody.add(milestoneHolderPanel);

                    /** Actual milestone holder with all the data and comments */
                    JPanel milestonePanel = new JPanel();
                    milestonePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    milestonePanel.setLayout(new BoxLayout(milestonePanel, BoxLayout.Y_AXIS));
                    milestonePanel.setMaximumSize(new Dimension(875, 200));
                    milestoneHolderPanel.add(milestonePanel);

                    milestonePanel.add(Box.createRigidArea(new Dimension(0, 10)));

                    /**
                     * Header part of the milestone containing its name and if the user has
                     * authority over the project the edit button
                     */
                    JPanel milestoneHeaderFlow = new JPanel(new FlowLayout());
                    milestoneHeaderFlow.setAlignmentX(Component.CENTER_ALIGNMENT);
                    milestoneHeaderFlow.setMaximumSize(new Dimension(850, 50));
                    milestonePanel.add(milestoneHeaderFlow);

                    JLabel milestoneName = new JLabel(milestone.getMilestoneName());
                    milestoneName.setFont(new Font("Arial", Font.PLAIN, 22));
                    milestoneName.setAlignmentX(Component.CENTER_ALIGNMENT);
                    milestoneHeaderFlow.add(milestoneName);

                    if (user.projectManagementAutority(activeProject)) {
                        JButton milestoneSettingsButton = new JButton("\u2699");
                        milestoneSettingsButton.setFont(new Font("Arial", Font.PLAIN, 20));
                        milestoneSettingsButton
                                .addActionListener(new MilestoneSettingsListener(activeProject, milestone, this));
                        milestoneHeaderFlow.add(milestoneSettingsButton);
                    }

                    milestonePanel.add(Box.createRigidArea(new Dimension(0, 5)));

                    /** Status part for the milestone */
                    JLabel milestoneStatus = new JLabel(milestone.getMilestoneStatus().toString());
                    milestoneStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
                    milestonePanel.add(milestoneStatus);

                    milestonePanel.add(Box.createRigidArea(new Dimension(0, 10)));

                    /**
                     * Milestone description, embedded into a JScrollPane in case it grows too big
                     */
                    JTextArea milestoneDescription = new JTextArea(
                            "Mérföldkő leírása: " + milestone.getMilestoneDescription());
                    milestoneDescription.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    milestoneDescription.setLineWrap(true);
                    milestoneDescription.setEditable(false);

                    JScrollPane milestoneDescScroll = new JScrollPane(milestoneDescription,
                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    milestoneDescScroll.setMaximumSize(new Dimension(825, 80));
                    milestoneDescScroll.setPreferredSize(new Dimension(825, 80));
                    milestonePanel.add(milestoneDescScroll);

                    milestonePanel.add(Box.createRigidArea(new Dimension(0, 10)));

                    /**
                     * Bottom part of the milestone containing the milestone delete and comment
                     * button
                     */
                    JPanel milestoneBottom = new JPanel();
                    milestoneBottom.setLayout(new BoxLayout(milestoneBottom, BoxLayout.X_AXIS));
                    milestonePanel.add(milestoneBottom);

                    milestoneBottom.add(Box.createRigidArea(new Dimension(35, 0)));

                    if (user.projectManagementAutority(activeProject)) {
                        JButton deleteMilestoneButton = new JButton("Mérföldkő törlése");
                        deleteMilestoneButton
                                .addActionListener(new DeleteMilestoneListener(activeProject, milestone, this));
                        milestoneBottom.add(deleteMilestoneButton);
                    }

                    milestoneBottom.add(Box.createHorizontalGlue());

                    JButton newCommentButton = new JButton("Új komment");
                    newCommentButton.addActionListener(new NewCommentListener(this, activeProject, user, milestone));
                    milestoneBottom.add(newCommentButton);

                    milestoneBottom.add(Box.createRigidArea(new Dimension(35, 0)));

                    milestoneHolderPanel.add(Box.createRigidArea(new Dimension(0, 5)));

                    ArrayList<Comment> comments = milestone.getMilestoneComments();
                    /**
                     * Comment part of the milestone, looping through all the milestone comments and
                     * appending them to the bottom of the milestone panel
                     */
                    for (int x = 0; x < comments.size(); x++) {
                        Comment comment = comments.get(x);
                        /** Holder panel for the comments */
                        JPanel commentPanel = new JPanel();
                        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
                        commentPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                        commentPanel.setMaximumSize(new Dimension(800, 150));
                        commentPanel.setPreferredSize(new Dimension(800, 150));
                        milestoneHolderPanel.add(commentPanel);

                        commentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        /**
                         * Comment text area embedded into a JScrollPane in case the text grows too big
                         */
                        JTextArea commentTextArea = new JTextArea();
                        commentTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        commentTextArea.setText(
                                activeProject.getUserName(comment.getCommenterID()) + ": " + comment.getCommentText());
                        commentTextArea.setLineWrap(true);
                        commentTextArea.setEditable(false);

                        JScrollPane commentTextScroll = new JScrollPane(commentTextArea,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        commentTextScroll.setMaximumSize(new Dimension(750, 100));
                        commentTextScroll.setPreferredSize(new Dimension(750, 100));
                        commentPanel.add(commentTextScroll);

                        commentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        /** Bottom part of the comment holding the delete and edit buttons */
                        JPanel commentBottom = new JPanel();
                        commentBottom.setLayout(new BoxLayout(commentBottom, BoxLayout.X_AXIS));
                        commentPanel.add(commentBottom);

                        commentBottom.add(Box.createRigidArea(new Dimension(35, 0)));
                        if (user.projectManagementAutority(activeProject)
                                || comment.getCommenterID().equals(user.getUserID())) {
                            JButton commentDeleteButton = new JButton("Törlés");
                            commentDeleteButton.addActionListener(
                                    new DeleteCommentListener(this, activeProject, milestone, comment));
                            commentBottom.add(commentDeleteButton);
                        }

                        commentBottom.add(Box.createHorizontalGlue());

                        if (comment.getCommenterID().equals(user.getUserID())) {
                            JButton commentEditButton = new JButton("Szerkesztés");
                            commentEditButton.addActionListener(new EditCommentListener(this, activeProject, comment));
                            commentBottom.add(commentEditButton);
                        }
                        commentBottom.add(Box.createRigidArea(new Dimension(35, 0)));
                        milestoneHolderPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    }

                    milestoneHolderPanel.add(Box.createRigidArea(new Dimension(0, 25)));
                    milestoneHolderPanel.add(new JSeparator());
                }
            }
            projectBody.revalidate();
            projectBody.repaint();
        }
    }

    /**
     * Removing the displayed project, this method is called when a project gets
     * deleted, the project panel is emptied out and the associated project button
     * from the project buttons panel is removed
     */
    public void removeProjectDisplay() {
        activeProject = null;
        updateProjectButtonsPanel();
        updateProjectHeaderPanel();
        updateProjectBodyPanel();
        updateProjectUsersPanel();
    }

    /**
     * Setting the active project, if the holder panels are not yet initialized then
     * it means this is the first call to this method and also the panels need to be
     * initialized for holding the project's data
     * 
     * @param projectID
     */
    public void setActiveProject(String projectID) {
        activeProject = user.getProjectByID(projectID);
        if (projectCenterPanel == null || projectUsersPanel == null) {
            setupProjectCenterPanel();
            setupProjectUsersListPanel();
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
