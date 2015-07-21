/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command.commands.bending;

import zenixmc.bending.AbilityManager;
import zenixmc.command.commands.AbstractCommand;
import zenixmc.utils.MessageManager;

/**
 * For commands that need an abilityManager.
 * @author james
 */
public abstract class AbstractAbilitiesCommand extends AbstractCommand {

    /**
     * The {@link AbilityManager} to use.
     */
    protected AbilityManager abilityManager;

    /**
     * Instantiate.
     *
     * @param abilityManager
     *            The ability manager to use.
     * @param messageManager
     *            The message manager to use.
     */
    public AbstractAbilitiesCommand(AbilityManager abilityManager,
            MessageManager messageManager) {
        super(messageManager);
        this.abilityManager = abilityManager;
    }

}
