package the.david.tagSystem.command.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.TagManager;

import java.util.Map;

import static the.david.tagSystem.Main.luckPerms;

public class SetTag implements SubCommand{
	@Override
	public Boolean opOnly(){
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		String id = parsedArgs.get("id");
		Tag tag = TagManager.getTag(id);
		if(!TagManager.hasTagPermission(player, tag)){
			player.sendMessage(Component.text("You don't have permission to use this command!", NamedTextColor.RED));
			return;
		}
		User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
		PrefixNode node = PrefixNode.builder(tag.getText(), 1).build();
		user.data().clear(e -> e.getType().equals(NodeType.PREFIX));
		user.data().add(node);
		luckPerms.getUserManager().saveUser(user);
	}
}
