package the.david.tagSystem.command.commands;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.data.ConfigManager;

import java.util.Map;

public class Reload implements SubCommand{
	@Override
	public Boolean opOnly(){
		return true;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		ConfigManager.reload();
	}
}
