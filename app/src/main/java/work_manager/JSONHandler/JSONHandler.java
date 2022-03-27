package work_manager.JSONHandler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class for handling everything that involves the file system
 */
public class JSONHandler {

    /**
     * Reading a user's data by the user's userID
     * 
     * @param userID
     * @return
     */
    public static JSONObject readUserData(String userID) {
        return readJSONFile("data/user/" + userID + ".json");
    }

    /**
     * Reading a project's data by the project's projectID
     * 
     * @param projectID
     * @return
     */
    public static JSONObject readProjectData(String projectID) {
        return readJSONFile("data/project/" + projectID + ".json");
    }

    /**
     * Getting all the users that are registered into the application
     * 
     * @return
     */
    public static ArrayList<String> getUserList() {
        return new ArrayList<String>((JSONArray) readJSONFile("data/common/users.json").get("users"));
    }

    /**
     * Writing a new user into a JSON file
     * 
     * @param userData
     */
    public static void writeNewUser(JSONObject userData) {
        String userID = (String) userData.get("userID");
        appendToUsers(userID);
        writeJSONFile("data/user/" + userID + ".json", userData);
    }

    /**
     * Deleting an user, used only for testing, by design there is no user delete
     * option in the program for now
     * 
     * @param userData
     */
    public static void deleteUser(String userID) {
        removeFromUsers(userID);
        File userFile = new File("data/user/" + userID + ".json");
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    /**
     * Adding a new user to the users that are registered into the application
     * 
     * @param userID
     */
    private static void appendToUsers(String userID) {
        ArrayList<String> users = getUserList();
        users.add(userID);
        JSONObject usersJSON = new JSONObject();
        usersJSON.put("users", users);
        writeJSONFile("data/common/users.json", usersJSON);
    }

    /**
     * Removing a user from the users list, used only for testing, by design there
     * is no user delete option in the program for now
     * 
     * @param userID
     */
    private static void removeFromUsers(String userID) {
        ArrayList<String> users = getUserList();
        users.remove(userID);
        JSONObject usersJSON = new JSONObject();
        usersJSON.put("users", users);
        writeJSONFile("data/common/users.json", usersJSON);
    }

    /**
     * Writing a project's projectID into a user's projects
     * 
     * @param userID
     * @param projectID
     */
    private static void addProjectToUser(String userID, String projectID) {
        JSONObject userData = readUserData(userID);
        JSONArray userProjects = (JSONArray) userData.get("projectIDs");
        userProjects.add(projectID);
        userData.put("projectIDs", userProjects);
        writeJSONFile("data/user/" + userID + ".json", userData);
    }

    /**
     * Saving a new project to JSON file
     * 
     * @param projectData
     * @param projectID
     * @param projectOwnerID
     */
    public static void saveNewProject(JSONObject projectData, String projectID, String projectOwnerID) {
        addProjectToUser(projectOwnerID, projectID);
        writeJSONFile("data/project/" + projectID + ".json", projectData);
    }

    /**
     * Updating an existing project's data by overwriting the old JSON file with the
     * new data
     * 
     * @param projectData
     * @param projectID
     */
    public static void updateProjectData(JSONObject projectData, String projectID) {
        writeJSONFile("data/project/" + projectID + ".json", projectData);
    }

    /**
     * Updating an existing user's data
     * 
     * @param userData
     */
    public static void updateUserData(JSONObject userData) {
        String userID = (String) userData.get("userID");
        writeJSONFile("data/user/" + userID + ".json", userData);
    }

    /**
     * Deleting a project's JSON file
     * 
     * @param projectID
     */
    public static void deleteProjectData(String projectID) {
        File projectFile = new File("data/project/" + projectID + ".json");
        if (projectFile.exists()) {
            projectFile.delete();
        }
    }

    /**
     * Reading the configuration data of the application
     * 
     * @return
     */
    public static JSONObject readConfigData() {
        return readJSONFile("data/common/config.json");
    }

    /**
     * Updating the configuration data of the project
     * 
     * @param configData
     */
    public static void updateConfigData(JSONObject configData) {
        writeJSONFile("data/common/config.json", configData);
    }

    /**
     * Reading a JSON file by its name
     * 
     * @param filename
     * @return
     */
    public static JSONObject readJSONFile(String filename) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            Reader reader = new FileReader(filename);
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Writing a JSON file by its name
     * 
     * @param filename
     * @param jsonObject
     */
    public static void writeJSONFile(String filename, JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
