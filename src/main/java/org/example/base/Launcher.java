package org.example.base;

import org.example.server.Server;

import javax.swing.*;

/**
 * @author Simon Jern
 * The launcher for the game.
 */
public class Launcher {
    public static void main(String[] args) {
        ContentManager content = new ContentManager();
        content.loadContent();
        String ipAddress = null;
        int result = JOptionPane.showConfirmDialog(null,"Welcome! This is a two player game played on a local " +
                        "network. One of the players need to start a server, is that you?", "Server or " +
                        "Client?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            Server server = new Server(content);
            ipAddress = "localhost";
        }else if (result == JOptionPane.NO_OPTION){
            ipAddress = (String)JOptionPane.showInputDialog(
                    null,
                    "Enter the IP address of the user hosting the server",
                    "Enter IP Address of server",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Ex: 192.168.0.1");
        }else {
            System.exit(0);
        }
        Game game = new Game(content, ipAddress);
        GameLoop gameLoop = new GameLoop(game);
        gameLoop.start();
    }
}
