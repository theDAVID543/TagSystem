package the.david.tagSystem.manager;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import the.david.tagSystem.data.ConfigHandler;
import the.david.tagSystem.data.ConfigManager;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.util.DebugOutputHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TagManager{
	private static Map<String, Tag> tags = new HashMap<>();
	public TagManager(){

	}
	public static void loadTags(){
		ConfigHandler config = ConfigManager.tagConfig;
		if(config.getKeys("tags") == null){
			return;
		}
		tags = new HashMap<>();
		config.getKeys("tags").forEach(key -> {
			String id = key;
			String text = config.getString("tags." + key + ".text");
			String description = config.getString("tags." + key + ".description");
			Tag tag = new Tag(id, text, description);
			try{
				ItemStack itemStack = config.getItemStack("tags." + key + ".icon");
				tag.setIcon(itemStack);
			}catch(Exception e){
				e.printStackTrace();
			}
			tags.put(id, tag);
		});
	}
	public static void addTag(Tag tag){
		tags.put(tag.getId(), tag);
		ConfigManager.setTagToConfig(tag);
	}
	public static Tag getTag(String tagId){
		return tags.get(tagId);
	}
	public static Collection<Tag> getTagCollision(){
		return tags.values();
	}
	public static void setTagIcon(Tag tag, ItemStack icon){
		tag.setIcon(icon);
		ConfigManager.setTagToConfig(tag);
	}
	public static void setTagText(Tag tag, String text){
		tag.setText(text);
		ConfigManager.setTagToConfig(tag);
	}
	public static void setTagDescription(Tag tag, String text){
		tag.setDescription(text);
		ConfigManager.setTagToConfig(tag);
	}
}
