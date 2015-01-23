package TurboNGServer.Modules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.PlayersManager;

/**
 * Created by ruijorgeclarateixeira on 23/01/15.
 * Invite by username module.
 */
public abstract class InviteByUsernamePlayer extends PasswordlessLoginPlayer {
    @Override
    public boolean executeAction(Action action) {
        if (!super.executeAction(action)) {
            if (action.getValueOf("type") != null && action.getValueOf("type").equals("invite")) {
                switch (action.getValueOf("action")) {
                    case "invite":
                        if (action.getValueOf("target") != null) {
                            sendGameInvite(action.getValueOf("target"));
                        }
                        break;
                    case "acceptinvite":
                        if (action.getValueOf("source") != null) {
                            acceptInviteFrom(action.getValueOf("source"));
                        }
                }
                return true;
            }
            else // action not executed
                return false;
        }
        else // action executed by parent class
            return true;
    }

    /**
     * This should be part of the framework.
     * @param source Source of the invitation.
     */
    public void acceptInviteFrom(String source) {
        this.game = PlayersManager.getPlayer(source).game;
        this.game.join(this);
    }

    /**
     * Invite another player to the game this is currently in.
     * @param target Username of the player to gameInviteReceived.
     */
    public void sendGameInvite(String target) {
        if(this.game != null)
            this.game.invite(this.username, target);
    }
}
