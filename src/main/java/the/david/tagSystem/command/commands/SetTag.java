package the.david.tagSystem.command.commands;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.PlayerTagManager;
import the.david.tagSystem.manager.TagManager;

import java.util.Map;

public class SetTag implements SubCommand{
	@Override
	public Boolean opOnly(){
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String id = parsedArgs.get("id");
		Tag tag = TagManager.getTag(id);
		if(tag == null){
			player.sendMessage("無此稱號");
			return;
		}
		PlayerTagManager.setPlayerTag(player, tag);
	}
}
