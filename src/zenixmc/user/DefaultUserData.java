package zenixmc.user;

import java.util.ArrayList;
import java.util.UUID;

import zenixmc.event.EventDispatcher;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;

public class DefaultUserData extends ZenixUserData {

	public DefaultUserData(ZenixUserInterface zenix, EventDispatcher eventDispatcher) {
		super();
		this.muted = false;
		this.frozen = false;
		this.godMode = false;
		this.vanished = false;
		this.socialSpy = false;
    	this.warning = new Warning(zenix, eventDispatcher);
    	this.homes = new ArrayList<Home>();
    	this.jail = null;
    	this.ignoredUsers = new ArrayList<UUID>();
    	this.startActivity = 0;
    	this.lastOnlineActivity = 0;
    	this.lastActivity = 0;
	}
}
