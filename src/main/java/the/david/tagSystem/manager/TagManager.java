package the.david.tagSystem.manager;

import the.david.tagSystem.data.ConfigHandler;
import the.david.tagSystem.data.ConfigManager;
import the.david.tagSystem.impl.Tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TagManager{
	private static final Map<String, Tag> tags = new HashMap<>();
	public TagManager(){

	}
	public void loadTags(){
		ConfigHandler config = ConfigManager.tagConfig;
		if(config.getKeys("tags") == null){
			return;
		}
		config.getKeys("tags").forEach(key -> {
			String id = key;
			String text = config.getString("tags." + key + ".text");
			String description = config.getString("tags." + key + ".description");
			Tag tag = new Tag(id, text, description);
			tags.put(id, tag);
		});
	}
	public static void addTag(Tag tag){
		tags.put(tag.getId(), tag);
		ConfigManager.addTagToConfig(tag);
	}
	public static Tag getTag(String tagId){
		return tags.get(tagId);
	}
	public static Collection<Tag> getTagCollision(){
		return tags.values();
	}
}
