package work_manager.app;

import org.json.simple.JSONObject;

import work_manager.JSONHandler.JSONHandler;
import work_manager.customUtils.CustomUtils;
import work_manager.mainFrame.MainFrame;

public class App {

    public static void main(String[] args) throws Exception {
        /**
         * Checking directories and files
         */
        CustomUtils.checkDirectories();
        CustomUtils.checkFiles();

        /**
         * Reading confing data
         */
        JSONObject configData = JSONHandler.readConfigData();
        String userID = (String) configData.get("loggedin");

        /**
         * Constructing main frame and starting application
         */
        MainFrame mainFrame = new MainFrame(userID);
        mainFrame.setVisible(true);
    }

}
