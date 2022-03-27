package work_manager.project;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work_manager.JSONHandler.JSONHandler;
import work_manager.customUtils.CustomUtils;
import work_manager.role.Role;
import work_manager.status.Status;
import work_manager.user.User;

/** Testing the methods that are conntected to the Project class */
public class ProjectTest {

	String testUserID;

	Project testProject;
	String testProjectID;

	/** User and project setup for the test cases */
	@Before
	public void setup() {
		CustomUtils.checkDirectories();
		CustomUtils.checkFiles();

		UUID uuid = UUID.randomUUID();
		testUserID = new String("TestUser" + uuid.toString());
		User.registerUser("testUserName", testUserID, "testUserPass", "testUserPass");
		User tempUser = new User(testUserID);
		tempUser.createNewProject("Test Project", "Test project description");

		testProject = tempUser.getProjects().get(0);
		testProjectID = testProject.getProjectID();
	}

	/**
	 * Testing that the project in the setup method has been initialized correctly
	 */
	@Test
	public void projectCreationTest() {
		assertEquals(testProject.getProjectName(), "Test Project");
		assertEquals(testProject.getProjectDescription(), "Test project description");
	}

	/**
	 * Updating the test project's data and checking whether the contained
	 * information is what we want it to be
	 */
	@Test
	public void projectDataUpdateTest() {
		testProject.updateProjectData("New Test Project Name", "New project description", Status.Elkészült.toString());

		assertEquals(testProject.getProjectName(), "New Test Project Name");
		assertEquals(testProject.getProjectDescription(), "New project description");
	}

	/** Checking user addition to the project */
	@Test
	public void projectNewUserTest() {
		User.registerUser("Second Test User", "secondTestUser", "secPass", "secPass");
		testProject.addNewMember("secondTestUser");
		assertEquals(testProject.getUserIDs().contains("secondTestUser"), true);
		testProject.removeMember("secondTestUser");
		assertEquals(testProject.getUserIDs().contains("secondTestUser"), false);
		User.deleteUser("secondTestUser");
	}

	/**
	 * Testing user role change inside a project
	 */
	@Test
	public void projectUserRoleChangeTest() {
		User.registerUser("Second Test User", "secondTestUser", "secPass", "secPass");
		testProject.addNewMember("secondTestUser");
		assertEquals(testProject.getUserIDs().contains("secondTestUser"), true);
		testProject.updateRole("secondTestUser", Role.Admin);
		assertEquals(testProject.getUserRole("secondTestUser"), Role.Admin);
		User.deleteUser("secondTestUser");
	}

	@After
	public void cleanup() {
		/**
		 * Deleting the created project
		 */
		JSONHandler.deleteProjectData(testProjectID);

		/**
		 * Deleting the created test user
		 */
		User.deleteUser(testUserID);
	}

}
