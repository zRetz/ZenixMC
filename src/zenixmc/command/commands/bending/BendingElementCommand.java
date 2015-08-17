package zenixmc.command.commands.bending;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.AbilityManager;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ElementSet;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class BendingElementCommand extends AbstractAbilitiesCommand {

    /**
     * Instantiate.
     *
     * @param zenix
     * 		The plugin.
     * @param manager
     * 		Manager for users.
     * @param abilityManager
     *      The ability manager.
     */
    public BendingElementCommand(ZenixMCInterface zenix, ZenixUserManager manager, AbilityManager abilityManager) {
        super(zenix, manager, abilityManager);
    }

    @Override
    public String getName() {
        return "element";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
            "e"
        };
    }

    @Override
    public String[] getHelp() {
        return null;
    }

    @Override
    public String getFormat() {
        return "[set|add|remove] [element] [player]";
    }

    @Override
    public String getDescription() {
        return "Gives or removes an element from an player";
    }
    
    @Override
    public boolean onCommand(ZenixCommandSender sender, String label,
            String[] args) {

        if (args.length  > 3) {
            return false;
        }

        final Element element = Element.getType(args[1]);

        if (element == null) {
            sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("Invalid element: <string>", args[1]), MessageOccasion.ERROR, zenix));
            return false;
        }

        // TODO implement [<player>]
        final BendingPlayerInterface player = sender.zui.getBendingPlayer();

        // TODO: fire events
        switch (args[0]) {
        case "set":
            final ElementSet elements = new ElementSet();
            elements.add(element);
            player.setElements(elements);
            break;
        case "add":
            final ElementSet elements1 = player.getElements();
            elements1.add(element);
            player.setElements(elements1);
            break;
        case "remove":
            final ElementSet elements2 = player.getElements();
            elements2.remove(element);
            player.setElements(elements2);
            break;
        case "native":
            player.setNativeElement(element);
            break;
        default:
        	sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("In-valid operator: <string>", args[0]), MessageOccasion.ERROR, zenix));
        	return false;
        }
        
        sender.zui.sendMessage("You're now a " + element.toString());
        return true;
    }

}
