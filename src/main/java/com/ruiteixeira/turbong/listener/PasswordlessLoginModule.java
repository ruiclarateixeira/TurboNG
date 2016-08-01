package com.ruiteixeira.turbong.listener;

import com.ruiteixeira.turbong.Interface.Action;
import com.ruiteixeira.turbong.player.Player;
import com.ruiteixeira.turbong.player.PlayersManager;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * Created by ruijorgeclarateixeira on 15/01/15.
 * Password-less login module.
 */
public abstract class PasswordlessLoginModule extends Player {
    @Override
    public boolean executeAction(Action action) {
        if(super.executeAction(action))
            return true;
        else if (action == null
                || action.getValueOf("type") == null
                || action.getValueOf("action") == null
                || !action.getValueOf("type").equals("login"))
            return false;

        if (action.getValueOf("action").equals("login")) {
            login(action.getValueOf("username"));
        }
        else if (action.getValueOf("action").equals("logout")) {
            logout();
        }

        return true;
    }

    public void login(String username) {
        if (username == null || !username.matches(usernameRegex)) {
            sendToClient(new Action("{type:login, action:username_invalid}"));
            return;
        }
        else if (this.username != null) {
            sendToClient(new Action("{type:login, action:already_logged_in}}"));
            return;
        }

        try {
            this.username = username;
            addToOnlinePlayers();
            sendToClient(new Action("{type:login, action:successful, username:" + this.username + "}"));
            for (Player player : PlayersManager.getAllPlayers()) {
                if (!player.username.equals(username))
                    player.sendToClient(new Action("{type:lobby, action:new_online, username:" + this.username + "}"));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (KeyAlreadyExistsException e) {
            this.username = null;
            sendToClient(new Action("{type:login, action:username_exists}"));
        }
    }

    public void logout() {
        if (this.username == null) {
            sendToClient(new Action("{type:login, action:not_logged_in}}"));
            return;
        }
        sendToClient(new Action("{type:login, action:logout}"));
        PlayersManager.removePlayer(this.username);
        this.disconnect();
    }
}
