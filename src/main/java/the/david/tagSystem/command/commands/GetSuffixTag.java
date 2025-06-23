package the.david.tagSystem.command.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.PlayerTagManager;

import java.util.Map;

public class GetSuffixTag implements SubCommand{
	@Override
	public Boolean opOnly(){
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		Tag playerTag = PlayerTagManager.getPlayerSuffixTag(player);
		if(playerTag == null){
			player.sendMessage("您未設置後綴稱號");
			return;
		}
		String playerTagId = playerTag.getId();
		Component component = Component.text()
				.append(Component.text("目前設置的稱號: ", NamedTextColor.AQUA))
				.append(Component.text(playerTagId))
				.build();
		player.sendMessage(component);
	}
}
