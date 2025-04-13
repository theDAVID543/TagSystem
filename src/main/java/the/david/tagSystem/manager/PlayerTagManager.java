package the.david.tagSystem.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.entity.Player;
import the.david.tagSystem.impl.Tag;

import java.util.Collection;

import static the.david.tagSystem.Main.luckPerms;

public class PlayerTagManager{
	public PlayerTagManager(){

	}
	public static void setPlayerTag(Player player, Tag tag){
		if(!TagManager.hasTagPermission(player, tag)){
			player.sendMessage(Component.text("You don't have permission to use this command!", NamedTextColor.RED));
			return;
		}
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		if(user == null){
			return;
		}
		clearPlayerTag(player);
		SuffixNode suffixNode = SuffixNode.builder(tag.getText(), 1).build();
		Node node = Node.builder("tagsystem.tagid." + tag.getId()).build();
		user.data().add(suffixNode);
		user.data().add(node);
		luckPerms.getUserManager().saveUser(user);
		player.sendMessage(Component.text("成功設定稱號為 ", NamedTextColor.GREEN).append(LegacyComponentSerializer.legacy('&').deserialize(tag.getText())));
	}

	public static void clearPlayerTag(Player player){
		User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
		user.data().clear(e -> e.getKey().startsWith("tagsystem.tagid"));
		user.data().clear(e -> e.getType() == NodeType.SUFFIX);
		luckPerms.getUserManager().saveUser(user);
	}

	public static Tag getPlayerTag(Player player){
		User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
		Collection<Node> nodes = user.getNodes();
		Node tagIdNode = null;
		for(Node node : nodes){
			if(!node.getKey().startsWith("tagsystem")){
				continue;
			}
			if(!node.getType().equals(NodeType.SUFFIX)){
				tagIdNode = node;
			}
		}
		if(tagIdNode == null){
			return null;
		}
		String tagId = tagIdNode.getKey().replaceFirst("tagsystem.tagid.", "");
		return TagManager.getTag(tagId);
	}
}
