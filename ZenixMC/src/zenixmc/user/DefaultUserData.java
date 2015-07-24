package zenixmc.user;

import java.util.ArrayList;
import java.util.List;

import zenixmc.event.EventDispatcher;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;

public class DefaultUserData extends ZenixUserData {

	public DefaultUserData(ZenixUserInterface zenix, EventDispatcher eventDispatcher) {
		setMuted(false);
    	setFrozen(false);
    	setGodMode(false);
    	setVanished(false);
    	setSocialSpy(false);
    	setWarning(new Warning(zenix, eventDispatcher));
    	setHomes(new ArrayList<Home>());
    	setJail(null);
    	List<String> ius = new ArrayList<String>();
    	ius.add("");
    	setIgnoredUsers(ius);
    	setLastOnlineActivity(0);
    	setLastActivity(0);
	}
}
