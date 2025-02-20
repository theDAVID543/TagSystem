package the.david.tagSystem.command.commands.manage;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.TagManager;

import java.util.Map;

public class SetTagIcon implements SubCommand{
	@Override
	public Boolean opOnly(){
		return true;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String id = parsedArgs.get("id");
		Tag tag = TagManager.getTag(id);
		TagManager.setTagIcon(tag, player.getInventory().getItemInMainHand());
	}
}
