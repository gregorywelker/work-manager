package work_manager.user;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import work_manager.JSONHandler.JSONHandler;
import work_manager.customUtils.CustomUtils;
import work_manager.project.Project;
import work_manager.roleManager.RoleManager;

/**
 * This class handles all the logical actions that are connected to the user
 */
public class User {

    private String userName;
    private String userID;
    private String password;
    private ArrayList<Project> projects = new ArrayList<>();

    /**
     * Constructor from a userID, the user data gets loaded from the corresponding
     * userID's JSON file
     * 
     * @param userID
     */
    public User(String userID) {
        JSONObject userData = JSONHandler.readUserData(userID);
        this.userName = (String) userData.get("userName");
        this.userID = (String) userData.get("userID");
        this.password = (String) userData.get("password");

        JSONArray projectIDs = (JSONArray) userData.get("projectIDs");

        for (int i = 0; i < projectIDs.size(); i++) {
            projects.add(new Project((String) projectIDs.get(i)));
        }
    }

    /**
     * Returns a project from the projects that are associated with the user by its
     * projectID
     * 
     * @param projectID
     * @return
     */
    public Project getProjectByID(String projectID) {
        Project project = new Project();
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectID().equals(projectID)) {
                project = projects.get(i);
                break;
            }
        }
        return project;
    }

    /**
     * Creates a new project in the user's projects list and also the project's
     * particular constructor creates a new JSON file for the new project
     * 
     * @param projectName
     * @param projectDescription
     */
    public void createNewProject(String projectName, String projectDescription) {
        projects.add(new Project(projectName, projectDescription, userID, userName));
    }

    /**
     * Deletes the project from the user's projects list and also deletes the
     * project's file
     * 
     * @param project
     */
    public void deleteProject(Project project) {
        projects.remove(project);
        project.deleteProject();
    }

    /**
     * Handles the user's data update process, compares new and old data and if
     * there is any data update the old data gets overridden
     * 
     * @param newUserName
     * @param newPassword
     * @param newPasswordRepeat
     * @param currentPassword
     * @return whether the updata update was successful or not
     */
    public boolean updateUserData(String newUserName, String newPassword, String newPasswordRepeat,
            String currentPassword) {
        if ((!newUserName.equals(userName) && !newUserName.isBlank() || !newPassword.isBlank()
                && newPassword.equals(newPasswordRepeat) && !newPassword.equals(password) && newPassword.length() >= 3)
                && currentPassword.equals(password)) {
            JSONObject userData = JSONHandler.readUserData(userID);

            if (!newUserName.equals(userName) && !newUserName.isBlank()) {
                this.userName = newUserName;
                userData.put("userName", newUserName);
            }
            if (!newPassword.isBlank() && newPassword.equals(newPasswordRepeat) && !newPassword.equals(password)
                    && newPassword.length() >= 3) {
                this.password = newPassword;
                userData.put("password", newPassword);
            }
            JSONHandler.updateUserData(userData);
            return true;
        }
        return false;
    }

    /**
     * Returns whether the user has project management authority in a project
     * 
     * @param project
     * @return
     */
    public boolean projectManagementAutority(Project project) {
        return RoleManager.projectManagementAuthority(project.getUserRole(userID));
    }

    /**
     * Returns whether the user has user management authority in a project
     * 
     * @param project
     * @return
     */
    public boolean userManagementAuthority(Project project) {
        return RoleManager.userManagementAuthority(project.getUserRole(userID));
    }

    /**
     * Returns whether the user has project delete authority
     * 
     * @param project
     * @return
     */
    public boolean projectDeleteAuthority(Project project) {
        return RoleManager.projectDeleteAuthority(project.getUserRole(userID));
    }

    /**
     * Checks whether the received user credentials are correct
     * 
     * @param userID
     * @param password
     * @return whether the credentials are correct or not and the login is
     *         successful
     */
    public static boolean checkUserCredentials(String userID, String password) {
        ArrayList<String> users = JSONHandler.getUserList();
        if (users.contains(userID)) {
            JSONObject userData = JSONHandler.readUserData(userID);
            if (userData.get("password").equals(password)) {

                CustomUtils.loginConfig(userID);
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new user with the given credentials, the userID is checked to
     * meake sure there is no duplication
     * 
     * @param userName       the desired username
     * @param userID         the desired user identification string
     * @param password       the desired password
     * @param passwordRepeat the desired password again for checking
     * @return whether the registration was successful or not
     */
    public static boolean registerUser(String userName, String userID, String password, String passwordRepeat) {
        if (!userName.isEmpty() && !userID.isEmpty() && !password.isEmpty() && password.length() >= 3
                && password.equals(passwordRepeat)) {

            ArrayList<String> users = JSONHandler.getUserList();
            if (users.contains(userID)) {
                return false;
            }

            JSONObject userData = new JSONObject();
            userData.put("userName", userName);
            userData.put("userID", userID);
            userData.put("password", password);
            userData.put("projectIDs", new JSONArray());
            JSONHandler.writeNewUser(userData);

            return true;
        }
        return false;
    }

    /**
     * Method for deleting user, used only for testing, by design there is no user
     * deletion option in the program for now
     * 
     * @param userID
     */
    public static void deleteUser(String userID) {
        JSONHandler.deleteUser(userID);
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

}
