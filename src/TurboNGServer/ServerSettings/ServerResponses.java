package TurboNGServer.ServerSettings;

/**
 * Created by ruijorgeclarateixeira on 04/10/14.
 *
 * This class holds the content of the standard actions sent
 * to the user.
 */
public class ServerResponses {
    /**
     * General actions
     */
    public static final String ERROR_101_ACTION_NOT_FOUND =  "{ type: response, "
                                                            + "action: error, "
                                                            + "code: 101, "
                                                            + "message: Action not found }";

    public static final String ERROR_102_ILLEGAL_FORMAT =  "{ type: response, "
                                                            + "action: error, "
                                                            + "code: 102, "
                                                            + "message: Illegal Format }";
}
