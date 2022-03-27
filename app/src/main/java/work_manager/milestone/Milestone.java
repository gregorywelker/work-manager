package work_manager.milestone;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import work_manager.comment.Comment;
import work_manager.status.Status;

/**
 * Class for all the logical operations a milestone needs
 */
public class Milestone {
    private String milestoneName;
    private ArrayList<Comment> comments = new ArrayList<>();
    private Status milestoneStatus;
    private String milestoneDescription;

    /**
     * Constructor from JSONObject
     * 
     * @param milestoneData
     */
    public Milestone(JSONObject milestoneData) {
        this.milestoneName = (String) milestoneData.get("milestoneName");
        this.milestoneStatus = Status.valueOf((String) milestoneData.get("milestoneStatus"));
        this.milestoneDescription = (String) milestoneData.get("milestoneDescription");
        JSONArray commentsData = (JSONArray) milestoneData.get("comments");
        for (int i = 0; i < commentsData.size(); i++) {
            this.comments.add(new Comment((JSONObject) commentsData.get(i)));
        }
    }

    /**
     * Constructor from Strings of data
     * 
     * @param milestoneName
     * @param milestoneDescription
     */
    public Milestone(String milestoneName, String milestoneDescription) {
        this.milestoneName = milestoneName;
        this.milestoneStatus = Status.Folyamatban;
        this.milestoneDescription = milestoneDescription;
    }

    /**
     * Adding comment to the milestone
     * 
     * @param commenterID
     * @param commentText
     */
    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    /**
     * Adding comment to the milestone
     * 
     * @param commenterID
     * @param commentText
     */
    public void addComment(String commenterID, String commentText) {
        comments.add(new Comment(commenterID, commentText));
    }

    /**
     * Updates the milestone's data if the received data is different from the old
     * data. The return value tells if there has been any modification
     * 
     * @param newName
     * @param newDescription
     * @param newStatus
     * @return
     */
    public boolean updateMilestoneData(String newName, String newDescription, String newStatus) {
        if (!milestoneName.equals(newName) || !milestoneDescription.equals(newDescription)
                || !milestoneStatus.toString().equals(newStatus)) {
            if (!milestoneName.equals(newName)) {
                milestoneName = newName;
            }
            if (!milestoneDescription.equals(newDescription)) {
                milestoneDescription = newDescription;
            }
            if (!milestoneStatus.toString().equals(newStatus)) {
                milestoneStatus = Status.valueOf(newStatus);
            }
            return true;
        }
        return false;
    }

    /**
     * Getting all the data of the milestone, used to gather data from the milestone
     * to write it into a JSON file
     * 
     * @return
     */
    public JSONObject getMilestoneData() {
        JSONObject milestoneData = new JSONObject();
        milestoneData.put("milestoneName", milestoneName);
        milestoneData.put("milestoneDescription", milestoneDescription);
        milestoneData.put("milestoneStatus", milestoneStatus.toString());
        JSONArray commentsData = new JSONArray();
        for (int i = 0; i < comments.size(); i++) {
            commentsData.add(comments.get(i).getCommentData());
        }
        milestoneData.put("comments", commentsData);
        return milestoneData;

    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public Status getMilestoneStatus() {
        return milestoneStatus;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public ArrayList<Comment> getMilestoneComments() {
        return comments;
    }

}
