package work_manager.user;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work_manager.customUtils.CustomUtils;
import work_manager.project.Project;

/**
 * Testing the methods connected to the User class
 */
public class UserTest {

	User testUser;
	String testUserID;

	/**
	 * Setting up a test user that is mainly used in the test cases
	 */
	@Before
	public void setup() {

		CustomUtils.checkDirectories();
		CustomUtils.checkFiles();

		UUID uuid = UUID.randomUUID();
		testUserID = new String("TestUser" + uuid.toString());
		User.registerUser("testUserName", testUserID, "testUserPass", "testUserPass");
		testUser = new User(testUserID);
	}

	/**
	 * Testing that in the setup part the newly registered user is registered for
	 * properly and cointains the needed information
	 */
	@Test
	public void userRegisterTest() {
		User registeredUser = new User(testUserID);
		assertEquals(registeredUser.getPassword(), "testUserPass");
		assertEquals(registeredUser.getUserID(), testUserID);
		assertEquals(registeredUser.getUserName(), "testUserName");
	}

	/**
	 * Updating the user data then testing that all the data has been updated
	 * properly
	 */
	@Test
	public void userDataUpdateTest() {
		testUser.updateUserData("New Test User Name", "newTestPass", "newTestPass", "testUserPass");

		assertEquals(testUser.getPassword(), "newTestPass");
		assertEquals(testUser.getUserName(), "New Test User Name");
	}

	/**
	 * Creating and deleting a project from the user meanwhile checking the users
	 * project count
	 */
	@Test
	public void userCreateDeleteProjectTest() {
		assertEquals(testUser.getProjects().size(), 0);
		testUser.createNewProject("Test Project Name", "Test project description");
		assertEquals(testUser.getProjects().size(), 1);
		Project testProject = testUser.getProjects().get(0);
		testUser.deleteProject(testProject);
		assertEquals(testUser.getProjects().size(), 0);
	}

	/**
	 * Creating a new project then adding a newly created user to it then removing
	 * the new user from the project
	 */
	@Test
	public void addingUserToProjectTest() {
		/** Creating the new project, testUser is the project owner */
		testUser.createNewProject("Test Project Name", "Test project description");
		/** Creating a new user */
		User.registerUser("Second Test User", "secondTestUser", "secPass", "secPass");
		Project testProject = testUser.getProjects().get(0);
		/** Adding the newly created user as an employee to the project */
		testProject.addNewMember("secondTestUser");
		/** Loading the new users data then checking it */
		User secondTestUser = new User("secondTestUser");
		assertEquals(secondTestUser.getProjects().size(), 1);
		assertEquals(secondTestUser.getProjects().get(0).getProjectID(), testProject.getProjectID());
		testProject.removeProjectFromUser(secondTestUser.getUserID());
		/** Reloading the users data then checking it after project removal */
		secondTestUser = new User("secondTestUser");
		assertEquals(secondTestUser.getProjects().size(), 0);
		/** Cleaning up object references and JSON files */
		testUser.deleteProject(testProject);
		User.deleteUser(secondTestUser.getUserID());
	}

	/** Checking the login */
	@Test
	public void userCredentialsTest() {
		assertEquals(User.checkUserCredentials(testUserID, "testUserPass"), true);
		assertEquals(User.checkUserCredentials(testUserID, "testUserPass" + "badPass"), false);
	}

	@After
	public void cleanup() {

		User.deleteUser(testUserID);
	}

}
