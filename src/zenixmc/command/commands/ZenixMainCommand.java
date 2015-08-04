package zenixmc.command.commands;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;

public class ZenixMainCommand extends AbstractMainCommand {

	public ZenixMainCommand(ZenixMCInterface zenix) {
		super(zenix);
	}

	@Override
	public String getName() {
		return "z";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Zenix Command";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		return false;
	}

}
