package the.david.tagSystem.util;

import static the.david.tagSystem.Main.plugin;

public class DebugOutputHandler{
	final static boolean debug = true;

	public static void sendDebugOutput(String message){
		if(debug){
			plugin.getLogger().info(message);
		}
	}
}
