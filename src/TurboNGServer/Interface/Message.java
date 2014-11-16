package TurboNGServer.Interface;

import libraries.json.JSONObject;
import libraries.json.JSONException;

/**
 * Created by ruijorgeclarateixeira on 03/10/14.
 * This class holds a Json Object and performs all the operations on it
 * It only receives and returns Strings to be used as abstraction of the used Json API
 */
public class Message {
    private JSONObject jsonMessage = null;

    /**
     * Check if the json message is in valid format.
     * @return True if the message is in correct json format. False Otherwise.
     */
    public boolean isValid() {
        if (jsonMessage == null) {
            return false;
        }
        return true;
    }

    /**
     * Parses a string representing a json object into and JsonObject
     * @param strJsonInput String representing a json object
     */
    public Message(String strJsonInput) {
        try {
            jsonMessage = new JSONObject(strJsonInput);
        }
        catch (JSONException e) {
            System.out.println("Invalid json message!");
        }
    }

    /**
     * Gets the value for a given key
     * @param key Value's key
     * @return returns the required value or null if key does not exist
     */
    public String getValueOf(String key) {
        try {
            return jsonMessage.get(key).toString();
        } catch(JSONException e) {
            return null;
        }
    }

    /**
     * Adds a given object into
     * @param key Object's Key
     * @param value Object's value
     */
    public void addOjject(String key, String value) {
        if(this.jsonMessage != null) {
            jsonMessage.append(key, value);
        }
    }

    @Override
    public String toString() {
        if(jsonMessage != null)
            return jsonMessage.toString();
        else
            return null;
    }
}
