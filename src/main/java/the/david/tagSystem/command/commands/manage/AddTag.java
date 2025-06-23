package the.david.tagSystem.command.commands.manage;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.TagManager;

import java.util.Map;

public class AddTag implements SubCommand{
	@Override
	public Boolean opOnly(){
		return true;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String type = parsedArgs.get("type");
		Tag.TagType tagType;
		try{
			tagType = Tag.TagType.valueOf(type.toUpperCase());
		}catch(IllegalArgumentException e){
			player.sendMessage("type not found");
			return;
		}
		String id = parsedArgs.get("id");
		String text = parsedArgs.get("text");
		String description = parsedArgs.get("description");
		Tag tag = new Tag(id, text, description, tagType);
		TagManager.addTag(tag);
	}
}
