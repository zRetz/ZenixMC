package zenixmc.event.update;

import zenixmc.ZenixMCInterface;

public class Updater implements Runnable {
	
	private ZenixMCInterface plugin;

	public Updater(ZenixMCInterface plugin) {
		this.plugin = plugin;
		this.plugin.scheduleSyncRepeatingTask(this, 0L, 1L);
	}
	
	@Override
	public void run() {
		for (UpdateType updateType : UpdateType.values()) {
			if (updateType.elapsed()) {
				plugin.getServer().getPluginManager()
						.callEvent(new UpdateEvent(updateType));
			}
		}
	}
}