/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command.commands;

import zenixmc.ZenixMCInterface;

/**
 * Common base class that most commands are based on.
 *
 */
public abstract class AbstractCommand implements CommandInterface {

	/**
	 * The plugin.
	 */
	protected ZenixMCInterface zenix;
	
    //TODO: Add MessageManager

    /**
     * Instantiate.
     * @param zenix
     * 		The plugin.
     */
    public AbstractCommand(ZenixMCInterface zenix) {
        this.zenix = zenix;
    }

}
