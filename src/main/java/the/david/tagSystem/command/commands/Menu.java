package the.david.tagSystem.command.commands;

import org.bukkit.entity.Player;
import the.david.tagSystem.command.SubCommand;
import the.david.tagSystem.gui.MainGUI;

import java.util.Map;

public class Menu implements SubCommand{
	@Override
	public Boolean opOnly(){
		return false;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		MainGUI.showGUI(player);
	}
}
