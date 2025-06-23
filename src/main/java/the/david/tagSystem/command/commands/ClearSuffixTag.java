package the.david.tagSystem.command.commands;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.manager.PlayerTagManager;

import java.util.Map;

public class ClearSuffixTag implements SubCommand{
	@Override
	public Boolean opOnly(){
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		PlayerTagManager.clearPlayerSuffixTag(player);
	}
}
