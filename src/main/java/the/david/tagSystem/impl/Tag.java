package the.david.tagSystem.impl;

public class Tag{
	private final String id;
	private final String text;
	private final String description;
	public Tag(String id, String text, String description){
		this.id = id;
		this.text = text;
		this.description = description;
	}
	public String getId(){
		return id;
	}
	public String getText(){
		return text;
	}
	public String getDescription(){
		return description;
	}
}
