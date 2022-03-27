package work_manager.milestone;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work_manager.JSONHandler.JSONHandler;
import work_manager.comment.Comment;
import work_manager.customUtils.CustomUtils;
import work_manager.project.Project;
import work_manager.status.Status;
import work_manager.user.User;

/** Testing the methods connected to the Milestone class */
public class MilestoneTest {

	String testUserID;

	Project testProject;
	String testProjectID;

	Milestone testMilestone;

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

		testProject.addNewMilestone("Test Milestone Name", "Test milestone description");
		testMilestone = (Milestone) testProject.getMilestones().get(0);
	}

	/**
	 * Testing the milestone created in the setup method
	 */
	@Test
	public void milestoneCreationTest() {
		assertEquals(testProject.getMilestones().size(), 1);
		assertEquals(testMilestone.getMilestoneName(), "Test Milestone Name");
		assertEquals(testMilestone.getMilestoneDescription(), "Test milestone description");
		assertEquals(testMilestone.getMilestoneStatus(), Status.Folyamatban);
		testProject.deleteMilestone(testMilestone);
		assertEquals(testProject.getMilestones().size(), 0);
	}

	/**
	 * Editing a project's milestone then checking that the contained data inside
	 * the milestone is what we want it to be
	 */
	@Test
	public void milestoneEditTest() {
		assertEquals(testProject.getMilestones().size(), 1);
		testProject.updateMilestoneData(testMilestone, "New Test Milestone Name", "New test milestone description",
				Status.Meghiúsult.toString());

		assertEquals(testMilestone.getMilestoneName(), "New Test Milestone Name");
		assertEquals(testMilestone.getMilestoneDescription(), "New test milestone description");
		assertEquals(testMilestone.getMilestoneStatus(), Status.Meghiúsult);
	}

	/**
	 * Testing comment addition to milestone and also comment editing and comment
	 * deletion
	 */
	@Test
	public void milestoneCommentTest() {
		assertEquals(testMilestone.getMilestoneComments().size(), 0);
		testMilestone.addComment(testUserID, "Test comment text");
		assertEquals(testMilestone.getMilestoneComments().size(), 1);
		Comment testComment = (Comment) testMilestone.getMilestoneComments().get(0);
		assertEquals(testComment.getCommenterID(), testUserID);
		assertEquals(testComment.getCommentText(), "Test comment text");
		testComment.updateCommentData("New test comment text");
		assertEquals(testComment.getCommentText(), "New test comment text");
		testMilestone.deleteComment(testComment);
		assertEquals(testMilestone.getMilestoneComments().size(), 0);
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
