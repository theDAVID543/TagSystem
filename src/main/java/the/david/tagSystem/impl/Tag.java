package the.david.tagSystem.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import the.david.tagSystem.util.DebugOutputHandler;

import java.util.ArrayList;
import java.util.List;

public class Tag{
	private final String id;
	private String text;
	private String description;
	private ItemStack icon;
	private TagType tagType;

	public Tag(String id, String text, String description, TagType tagType){
		this.id = id;
		this.text = text;
		this.description = description;
		this.tagType = tagType;
		initializeIcon(new ItemStack(Material.NAME_TAG));
	}

	public void initializeIcon(@NotNull ItemStack icon){
		icon.editMeta(itemMeta -> {
			itemMeta.displayName(LegacyComponentSerializer.legacy('&').deserialize(getText()).decoration(TextDecoration.ITALIC, false));
			List<Component> descriptionLineList = new ArrayList<>();
			for(String oneDescriptionLine : getDescription().split("\\\\n")){
				descriptionLineList.add(LegacyComponentSerializer.legacy('&').deserialize(oneDescriptionLine).decoration(TextDecoration.ITALIC, false));
				DebugOutputHandler.sendDebugOutput(oneDescriptionLine);
			}
			itemMeta.lore(descriptionLineList);
		});
		setIcon(icon);
	}
	public void setType(TagType tagType){
		this.tagType = tagType;
	}
	public void setIcon(@NotNull ItemStack icon){
		this.icon = icon;
	}

	public void setText(String text){
		this.text = text;
	}

	public void setDescription(String description){
		this.description = description;
	}

	@NotNull
	public String getId(){
		return id;
	}

	@NotNull
	public String getText(){
		return text;
	}

	@NotNull
	public String getDescription(){
		return description;
	}

	@NotNull
	public ItemStack getIcon(){
		return icon;
	}
	public TagType getTagType(){
		return tagType;
	}

	public enum TagType{
		SUFFIX,
		PREFIX
	}
}
