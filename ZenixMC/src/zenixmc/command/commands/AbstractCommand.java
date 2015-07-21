/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command.commands;

import zenixmc.utils.MessageManager;



/**
 * Common base class that most commands are based on.
 *
 */
public abstract class AbstractCommand implements CommandInterface {
    /**
     * The messagemanager to get format messages.
     */
    protected MessageManager messageManager;

    /**
     * Instantiate from messagemanager.
     *
     * @param messageManager
     *            Messagemanager to use.
     */
    public AbstractCommand(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

}
