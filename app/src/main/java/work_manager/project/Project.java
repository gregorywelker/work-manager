package work_manager.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import work_manager.JSONHandler.JSONHandler;
import work_manager.comment.Comment;
import work_manager.milestone.Milestone;
import work_manager.pair.Pair;
import work_manager.role.Role;
import work_manager.status.Status;

/**
 * Class for all the logical operations that a project needs
 */
public class Project {
    private String projectID;
    private String projectName;
    private Status projectStatus;
    private String projectDescription;
    private ArrayList<String> projectUserIDs = new ArrayList<>();
    private Map<String, Pair<String, Role>> projectUsersData = new HashMap<String, Pair<String, Role>>();
    private ArrayList<Milestone> projectMilestones = new ArrayList<>();

    /**
     * Empty constructor
     */
    public Project() {

    }

    /**
     * Constructor form a projectID, by the projectID the data gets loaded with the
     * JSONHandler
     * 
     * @param projectID
     */
    public Project(String projectID) {

        JSONObject projectData = JSONHandler.readProjectData(projectID);
        loadProjectData(projectData);
    }

    /**
     * Constructor from a JSONObject
     */
    public Project(JSONObject projectData) {
        loadProjectData(projectData);
    }

    /**
     * Constructor from project data, used in new project creation
     * 
     * @param projectName
     * @param projectDescription
     * @param projectOwnerID
     * @param projectOwnerName
     */
    public Project(String projectName, String projectDescription, String projectOwnerID, String projectOwnerName) {
        JSONObject projectData = createNewProject(projectName, projectDescription, projectOwnerID);
        loadProjectData(projectData);
    }

    /**
     * With the received projectData object all the project data gets loaded into
     * the proper fields
     * 
     * @param projectData
     */
    private void loadProjectData(JSONObject projectData) {
        this.projectID = (String) projectData.get("projectID");
        this.projectName = (String) projectData.get("projectName");
        this.projectStatus = Status.valueOf((String) projectData.get("projectStatus"));
        this.projectDescription = (String) projectData.get("projectDescription");

        JSONArray projectUsers = (JSONArray) projectData.get("users");
        for (int i = 0; i < projectUsers.size(); i++) {
            JSONObject userData = (JSONObject) projectUsers.get(i);
            this.projectUserIDs.add((String) userData.get("userID"));
            this.projectUsersData.put((String) userData.get("userID"),
                    new Pair<String, Role>(
                            (String) JSONHandler.readUserData((String) userData.get("userID")).get("userName"),
                            Role.valueOf((String) userData.get("userRole"))));
        }

        JSONArray milestoneData = (JSONArray) projectData.get("milestones");
        for (int i = 0; i < milestoneData.size(); i++) {
            this.projectMilestones.add(new Milestone((JSONObject) milestoneData.get(i)));
        }
    }

    /**
     * Deletes a project by its projectID, first finds all the users associated with
     * the project, removes the particular project from the users then calls the
     * JSONHandler to delete the corresponding JSON project file
     * 
     * @param projectID
     */
    public void deleteProject() {
        JSONArray usersData = (JSONArray) JSONHandler.readProjectData(projectID).get("users");

        for (int i = 0; i < usersData.size(); i++) {
            JSONObject projectUserData = (JSONObject) usersData.get(i);
            String userID = (String) projectUserData.get("userID");

            removeProjectFromUser(userID);
        }
        JSONHandler.deleteProjectData(projectID);
    }

    /**
     * Writes all the project's data to a JSONObject and calls the JSONHandler to
     * save it to a JSON file
     */
    public void saveProject() {
        JSONObject projectData = new JSONObject();
        projectData.put("projectName", projectName);
        projectData.put("projectDescription", projectDescription);
        projectData.put("projectStatus", projectStatus.toString());
        projectData.put("projectID", projectID);
        JSONArray usersArray = new JSONArray();

        for (int i = 0; i < projectUserIDs.size(); i++) {
            JSONObject userData = new JSONObject();
            userData.put("userID", projectUserIDs.get(i));
            userData.put("userRole", projectUsersData.get(projectUserIDs.get(i)).getRight().toString());
            usersArray.add(userData);
        }
        projectData.put("users", usersArray);

        JSONArray milestonesData = new JSONArray();
        for (int i = 0; i < projectMilestones.size(); i++) {
            milestonesData.add(projectMilestones.get(i).getMilestoneData());
        }
        projectData.put("milestones", milestonesData);
        JSONHandler.updateProjectData(projectData, projectID);
    }

    /**
     * Adds a new member to the project. First checks if the user is not already
     * added to the project, if not it adds the project to the new user and then
     * adds the user to the project and everything gets saved to the corresponding
     * JSON files
     * 
     * @param userID
     */
    public boolean addNewMember(String userID) {
        ArrayList<String> users = JSONHandler.getUserList();
        if (users.contains(userID) && !projectUserIDs.contains(userID)) {
            addProjectToUser(userID);

            projectUserIDs.add(userID);
            projectUsersData.put(userID, new Pair<String, Role>(
                    (String) JSONHandler.readUserData(userID).get("userName"), Role.Alkalmazott));

            saveProject();
            return true;
        }
        return false;
    }

    /**
     * Removes a project from the given user
     * 
     * @param userID the user's id to know who to remove from the project
     */
    public void removeProjectFromUser(String userID) {
        JSONObject userData = JSONHandler.readUserData(userID);
        JSONArray userProjects = (JSONArray) userData.get("projectIDs");
        userProjects.remove(projectID);
        userData.put("projectIDs", userProjects);
        JSONHandler.updateUserData(userData);
    }

    /**
     * Adds the project to the given user
     * 
     * @param userID the user's id to add the project to
     */
    public void addProjectToUser(String userID) {
        JSONObject userData = JSONHandler.readUserData(userID);
        JSONArray userProjects = (JSONArray) userData.get("projectIDs");
        userProjects.add(projectID);
        userData.put("projectIDs", userProjects);
        JSONHandler.updateUserData(userData);
    }

    /**
     * Updates the project data with the new data if the new data is different from
     * the old data, then saves the project to the corresponding JSON file
     * 
     * @param newName
     * @param newDescription
     * @param newStatus
     */
    public void updateProjectData(String newName, String newDescription, String newStatus) {
        if (!this.projectName.equals(newName) || !this.projectDescription.equals(newDescription)
                || !this.projectStatus.toString().equals(newStatus)) {

            if (!this.projectName.equals(newName)) {
                this.projectName = newName;
            }
            if (!this.projectDescription.equals(newDescription)) {
                this.projectDescription = newDescription;
            }
            if (!this.projectStatus.toString().equals(newStatus)) {
                this.projectStatus = Status.valueOf(newStatus);
            }
            saveProject();
        }
    }

    /**
     * Updates the milestone's data, then the project gets saved to the
     * corresponding JSON file
     * 
     * @param milestone
     * @param newName
     * @param newDescription
     * @param newStatus
     */
    public void updateMilestoneData(Milestone milestone, String newName, String newDescription, String newStatus) {
        if (milestone.updateMilestoneData(newName, newDescription, newStatus)) {
            saveProject();
        }
    }

    /**
     * Updates a given comment's data then saves the project
     * 
     * @param comment
     * @param newText
     */
    public void updateCommentData(Comment comment, String newText) {
        if (comment.updateCommentData(newText)) {
            saveProject();
        }
    }

    /**
     * Deletes a milestone from the project then save the project
     */
    public void deleteMilestone(Milestone milestone) {
        projectMilestones.remove(milestone);
        saveProject();
    }

    /**
     * Removes the project from the received user then removes the user from the
     * project and finally saves the project
     * 
     * @param userID The user to remove the project from and the user to remove from
     *               the project
     */
    public void removeMember(String userID) {
        removeProjectFromUser(userID);

        projectUserIDs.remove(userID);
        saveProject();
    }

    /**
     * Updates a role in the project
     * 
     * @param userID  The user whose role needs to be updated
     * @param newRole The new role
     */
    public void updateRole(String userID, Role newRole) {
        String name = projectUsersData.get(userID).getLeft();
        projectUsersData.put(userID, new Pair<String, Role>(name, newRole));

        saveProject();
    }

    /**
     * Adds a new milestone to the project then saves it
     * 
     * @param milestoneName
     * @param milestoneDescription
     */
    public void addNewMilestone(String milestoneName, String milestoneDescription) {
        projectMilestones.add(new Milestone(milestoneName, milestoneDescription));
        saveProject();
    }

    /**
     * Adds a new comment to a milestone inside the project then saves the project
     * 
     * @param commenterID
     * @param milestone
     * @param commentText
     */
    public void addNewComment(String commenterID, Milestone milestone, String commentText) {
        milestone.addComment(commenterID, commentText);
        saveProject();
    }

    /**
     * Deletes a comment from a milestone inside the project then saves the project
     * 
     * @param milestoneIndex
     * @param commentIndex
     */
    public void deleteComment(Milestone milestone, Comment comment) {
        milestone.deleteComment(comment);
        saveProject();
    }

    /**
     * Constructs a new JSONObject with the predefined project structure and calls
     * the JSONHandler to save it as a JSON file
     * 
     * @param projectName
     * @param projectDescription
     * @param projectOwnerID
     * @return
     */
    private static JSONObject createNewProject(String projectName, String projectDescription, String projectOwnerID) {
        JSONObject projectData = new JSONObject();
        projectData.put("projectName", projectName);
        projectData.put("projectDescription", projectDescription);
        projectData.put("projectStatus", Status.Folyamatban.toString());

        UUID projectID = UUID.randomUUID();
        projectData.put("projectID", projectID.toString());

        JSONObject ownerData = new JSONObject();
        ownerData.put("userID", projectOwnerID);
        ownerData.put("userRole", Role.Készítő.toString());

        JSONArray usersArray = new JSONArray();
        usersArray.add(ownerData);
        projectData.put("users", usersArray);

        JSONArray milestonesData = new JSONArray();
        projectData.put("milestones", milestonesData);

        JSONHandler.saveNewProject(projectData, projectID.toString(), projectOwnerID);
        return projectData;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public Status getProjectStatus() {
        return projectStatus;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public ArrayList<String> getUserIDs() {
        return projectUserIDs;
    }

    public Role getUserRole(String userID) {
        return projectUsersData.get(userID).getRight();
    }

    public String getUserName(String userID) {
        return projectUsersData.get(userID).getLeft();
    }

    public ArrayList<Milestone> getMilestones() {
        return projectMilestones;
    }

}
