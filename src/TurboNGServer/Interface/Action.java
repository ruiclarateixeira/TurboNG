package TurboNGServer.Interface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruijorgeclarateixeira on 03/10/14.
 * This class holds a Json Object and performs all the operations on it
 * It only receives and returns Strings to be used as abstraction of the used Json API
 */
public class Action {
    private JSONObject jsonMessage = null;

    /**
     * Parses a string representing a json object into and JsonObject
     * @param strJsonInput String representing a json object
     */
    public Action(String strJsonInput) {
        try {
            if(strJsonInput != null)
                jsonMessage = new JSONObject(strJsonInput);
        }
        catch (JSONException e) {
            jsonMessage = null;
        }
    }

    /**
     * Check if the json message is in valid format.
     * @return True if the message is in correct json format. False Otherwise.
     */
    public boolean isValid() {
        return jsonMessage != null;
    }

    /**
     * Gets the value for a given key
     * @param key Value's key
     * @return returns the required value or null if key does not exist
     */
    public String getValueOf(String key) {
        try {
            if(key != null && this.isValid())
                return jsonMessage.get(key).toString();
            else
                return null;
        } catch(JSONException e) {
            return null;
        }
    }

    /**
     * Adds a given object into the json object. Creates an empty one and adds to it
     * if current object is not valid.
     * @param key Object's Key
     * @param value Object's value
     */
    public void addObject(String key, String value) {
        if(!this.isValid()) {
            this.jsonMessage = new JSONObject("{}");
        }

        if(key != null && value != null) {
            jsonMessage.put(key, value);
        }
    }

    @Override
    public String toString() {
        if(jsonMessage != null)
            return jsonMessage.toString();
        else
            return null;
    }

    /**
     * General actions
     */
    public static final Action ERROR_101_ACTION_NOT_FOUND =  new Action("{ type: response, "
            + "action: error, "
            + "code: 101, "
            + "message: Action not found }");

    public static final Action ERROR_102_ILLEGAL_FORMAT =  new Action("{ type: response, "
            + "action: error, "
            + "code: 102, "
            + "message: Illegal Format }");
}
