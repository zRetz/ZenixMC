package zenixmc.command.commands.bending;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.AbilityManager;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class BendingBindCommand extends AbstractAbilitiesCommand {

    /**
     * Instantiate.
     *
     * @param zenix
     * 		The plugin.
     * @param manager
     * 		Manager for users.
     * @param abilityManager
     *            The ability manager.
     */
    public BendingBindCommand(ZenixMCInterface zenix, ZenixUserManager manager, AbilityManager abilityManager) {
        super(zenix, manager, abilityManager);
    }

    @Override
    public String getName() {
        return "bind";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
            "b"
        };
    }

    @Override
    public String[] getHelp() {
        return null;
    }

    @Override
    public String getFormat() {
        return "[ability] [slot] [user]";
    }

    @Override
    public String getDescription() {
        return "Binds an ability to a slot";
    }

    @Override
    public boolean onCommand(ZenixCommandSender sender, String label,
            String[] args) {
        final BendingPlayerInterface player = sender.zui.getBendingPlayer();
        // TODO: other player

        if (args.length == 1 && player != null) {
        	System.out.println(player.getZenixUser());
        	System.out.println(player.getZenixUser().getInventory());
        	System.out.println(player.getZenixUser().getInventory().getHeldItemSlot());
            final int slot = player.getZenixUser().getInventory()
                    .getHeldItemSlot();

            if ("none".equalsIgnoreCase(args[0])) {
                abilityManager.bindAbility(player, slot, null);
            } else {
                final AbilityInterface ability = abilityManager
                        .findNamedAbility(args[0],
                                abilityManager.getAbilitiesOf(player));

                if (ability != null) {
                    if (ability.isPassive()) {
                        sender.zui.sendMessage(StringFormatter.format("Cannot bind passive abilityManager.", MessageOccasion.ERROR, zenix));
                    } else {
                        abilityManager.bindAbility(player, slot, ability);
                    }
                } else {
                	sender.zui.sendMessage(StringFormatter.format("In-valid ability name.", MessageOccasion.ERROR, zenix));
                }
            }
            return true;
        }

        return false;
    }

}