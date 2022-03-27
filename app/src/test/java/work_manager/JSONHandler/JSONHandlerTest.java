package work_manager.JSONHandler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work_manager.customUtils.CustomUtils;
import work_manager.project.Project;
import work_manager.status.Status;
import work_manager.user.User;

/**
 * Testing the JSONHandler, making sure that it saves everything properly. Upon
 * user and project creation the JSONHandler creates files and writes them to
 * the proper directory. The files then are loaded through the JSONHandler which
 * reads them from the written files. We are testing that everything that has
 * been written into the files are the same after reading them
 */
public class JSONHandlerTest {

	User testUser;
	String testUserID;
	String testUserName = "Test User Name";
	String testUserPass = "testPass";

	Project testProject;
	String testProjectID;

	/**
	 * Setup is for creating a user who holds a project
	 */
	@Before
	public void setup() {

		CustomUtils.checkDirectories();
		CustomUtils.checkFiles();

		UUID uuid = UUID.randomUUID();
		testUserID = new String("TestUser" + uuid.toString());
		User.registerUser(testUserName, testUserID, testUserPass, testUserPass);
		User tempUser = new User(testUserID);
		tempUser.createNewProject("Test Project", "Test project description");

		/**
		 * Reading the newly created user and its data from JSON
		 */
		testUser = new User(testUserID);

		testProject = testUser.getProjects().get(0);
		testProjectID = testProject.getProjectID();
	}

	/**
	 * Checking that the new user has been properly inserted into the project list
	 */
	@Test
	public void userInsertedTest() {
		ArrayList<String> users = JSONHandler.getUserList();
		int res = users.indexOf(testUserID);
		assertThat(res, not(equalTo(-1)));
	}

	/**
	 * Testing that the written user data is the same as the read data
	 */
	@Test
	public void userReadWriteTest() {
		JSONObject testUserData = JSONHandler.readUserData(testUserID);

		assertEquals(testUserData.get("userName"), testUser.getUserName());
		assertEquals(testUserData.get("userID"), testUser.getUserID());
		assertEquals(testUserData.get("password"), testUser.getPassword());
	}

	/**
	 * Updating the user's data then reading it and checking if the new data is what
	 * we want in to be
	 */
	@Test
	public void userDataUpdateTest() {
		testUser.updateUserData("New User Name", "newTestPass", "newTestPass", testUserPass);

		JSONObject readUser = JSONHandler.readUserData(testUserID);
		assertEquals(readUser.get("userName"), "New User Name");
		assertEquals(readUser.get("password"), "newTestPass");
	}

	/**
	 * Testing that the written project data is the same as the read project data
	 */
	@Test
	public void projectReadWriteTest() {
		JSONObject projectData = JSONHandler.readProjectData(testProjectID);
		assertEquals(projectData.get("projectID"), testProject.getProjectID());
		assertEquals(projectData.get("projectName"), testProject.getProjectName());
		assertEquals(projectData.get("projectStatus"), testProject.getProjectStatus().toString());
		assertEquals(projectData.get("projectDescription"), testProject.getProjectDescription());
	}

	/**
	 * Updating the project's data then reading it and checking if the new data is
	 * what we want it to be
	 */
	@Test
	public void projectDataUpdateTest() {
		testProject.updateProjectData("New Test Project Name", "New test project description",
				Status.Elkészült.toString());

		JSONObject readProject = JSONHandler.readProjectData(testProjectID);
		assertEquals(readProject.get("projectName"), "New Test Project Name");
		assertEquals(readProject.get("projectDescription"), "New test project description");
		assertEquals(readProject.get("projectStatus"), "Elkészült");
	}

	/**
	 * Testing project deletion
	 */
	@Test
	public void projectDeleteTest() {
		testUser.deleteProject(testProject);
		File f = new File("data/project/" + testProjectID + ".json");
		assertEquals(f.exists(), false);
	}

	@After
	public void cleanup() {
		/**
		 * Deleting the created project if it has not been deleted before
		 */
		JSONHandler.deleteProjectData(testProjectID);

		/**
		 * Deleting the created test user
		 */
		User.deleteUser(testUserID);
	}
}
