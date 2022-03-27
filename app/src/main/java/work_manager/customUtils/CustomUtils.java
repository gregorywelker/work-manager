package work_manager.customUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import work_manager.JSONHandler.JSONHandler;

/**
 * This class contiains methods that cannot be strictly associated with any
 * other class, utility methods
 */
public class CustomUtils {

    /**
     * Checks the necessary directory structure of the project, if any folder is
     * missing this method creates it
     */
    public static void checkDirectories() {
        File f = new File("data");
        ArrayList<String> subDirList = new ArrayList<>();
        if (f.exists() && f.isDirectory()) {
            if (!(new File("data/common").exists())) {
                subDirList.add("common");
            }
            if (!(new File("data/project").exists())) {
                subDirList.add("project");
            }
            if (!(new File("data/user").exists())) {
                subDirList.add("user");
            }
        } else {
            System.out.println("Creating directory: " + f.getName());
            f.mkdir();
            subDirList.add("common");
            subDirList.add("project");
            subDirList.add("user");
        }
        if (subDirList.size() > 0) {
            System.out.println("Initializing subdirectories");
            for (String dirName : subDirList) {
                System.out.println("Creating directory: data/" + dirName);
                new File("data/" + dirName).mkdir();
            }
        }
        System.out.println("Directories ok");
    }

    /**
     * Checks the necessary files that are needed for the program to work properly,
     * if any file is missing this method creates it
     */
    public static void checkFiles() {
        File config = new File("data/common/config.json");
        File users = new File("data/common/users.json");

        try {
            if (!config.exists()) {
                System.out.println("Creating file: data/common/" + config.getName());
                config.createNewFile();
            }
            if (!users.exists()) {
                System.out.println("Creating file: data/common/" + users.getName());
                users.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config.length() == 0) {
            System.out.println(config.getName() + " file empty, filling up with necessary data");
            JSONObject configData = new JSONObject();
            configData.put("loggedin", "");
            JSONHandler.writeJSONFile(config.getPath(), configData);
        }
        if (users.length() == 0) {
            System.out.println(users.getName() + " file empty, filling up with necessary data");
            JSONObject usersData = new JSONObject();
            usersData.put("users", new JSONArray());
            JSONHandler.writeJSONFile(users.getPath(), usersData);
        }
    }

    /**
     * After logging in to the application this method sets up the login config. If
     * someone does not log out before closing the application, that user's data
     * will be loaded upon application startup
     * 
     * @param userID
     */
    public static void loginConfig(String userID) {
        JSONObject configData = JSONHandler.readConfigData();
        configData.put("loggedin", userID);
        JSONHandler.updateConfigData(configData);
    }

    /**
     * Logs the currently logged in user out
     */
    public static void logoutConfig() {
        JSONObject configData = JSONHandler.readConfigData();
        configData.put("loggedin", "");
        JSONHandler.updateConfigData(configData);
    }
}
