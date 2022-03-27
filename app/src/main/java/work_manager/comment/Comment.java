package work_manager.comment;

import org.json.simple.JSONObject;

/**
 * Class for all the logical operations a comment needs
 */
public class Comment {
    private String commenterID;
    private String commentText;

    /**
     * Constructor from JSON data
     * 
     * @param commentData
     */
    public Comment(JSONObject commentData) {
        this.commenterID = (String) commentData.get("commenterID");
        this.commentText = (String) commentData.get("commentText");
    }

    /**
     * Constructor from String data
     * 
     * @param commenterID
     * @param commentText
     */
    public Comment(String commenterID, String commentText) {
        this.commenterID = commenterID;
        this.commentText = commentText;
    }

    public String getCommenterID() {
        return commenterID;
    }

    public String getCommentText() {
        return commentText;
    }

    /**
     * Updates the comment's data if the received text is different from the old
     * one. Returns if there has been any modification
     * 
     * @param newText
     * @return
     */
    public boolean updateCommentData(String newText) {
        if (!commentText.equals(newText)) {
            commentText = newText;
            return true;
        }
        return false;
    }

    /**
     * Getting all the comment's data and returning it as a JSONObject
     * 
     * @return
     */
    public JSONObject getCommentData() {
        JSONObject commentData = new JSONObject();
        commentData.put("commenterID", commenterID);
        commentData.put("commentText", commentText);
        return commentData;
    }
}
