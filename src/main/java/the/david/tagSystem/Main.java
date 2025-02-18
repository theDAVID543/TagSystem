package the.david.tagSystem;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.tagSystem.command.CommandManager;
import the.david.tagSystem.command.TabCompleteManager;

public final class Main extends JavaPlugin{
	public CommandManager commandManager;
	public TabCompleteManager tabCompleteManager;
	public static JavaPlugin instance;
	public static Main plugin;
	public static LuckPerms luckPerms;

	@Override
	public void onEnable(){
		// Plugin startup logic
		luckPerms = getServer().getServicesManager().load(LuckPerms.class);
		plugin = this;
		instance = this;
		commandManager = new CommandManager();
		Bukkit.getPluginCommand("tagsystem").setExecutor(commandManager);
		tabCompleteManager = new TabCompleteManager();
		Bukkit.getPluginCommand("parkourpractice").setTabCompleter(tabCompleteManager);
	}

	@Override
	public void onDisable(){
		// Plugin shutdown logic
	}
}
