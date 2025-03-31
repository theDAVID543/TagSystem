package the.david.tagSystem.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PagingButtons;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import the.david.tagSystem.Main;
import the.david.tagSystem.impl.Tag;
import the.david.tagSystem.manager.PlayerTagManager;
import the.david.tagSystem.manager.TagManager;

import java.util.ArrayList;
import java.util.List;

public class MainGUI{
	Main plugin;
	public MainGUI(Main plugin){
		this.plugin = plugin;
	}
	static final GuiItem outlineItem = new GuiItem(
			new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
			(e) -> e.setCancelled(true)
	);
	static final ItemStack clearTagItem = new ItemStack(Material.BARRIER){{
		editMeta(meta -> {
			meta.displayName(
					Component.text("清除稱號", NamedTextColor.RED)
							.decoration(TextDecoration.ITALIC, false)
							.decoration(TextDecoration.BOLD, true)
			);
		});
	}};
	static final GuiItem clearTagGuiItem = new GuiItem(
			clearTagItem,
			(e) -> {
				Player player = (Player) e.getWhoClicked();
				PlayerTagManager.clearPlayerTag(player);
				player.sendMessage(
						Component.text()
								.append(Component.text("已清除稱號", NamedTextColor.GREEN))
				);
				showGUI(player);
				e.setCancelled(true);
			}
	);
	public static void showGUI(Player player){
		ChestGui gui = new ChestGui(6, "TagSystem");
		StaticPane outlinePane = new StaticPane(0,0, 9, 6, Pane.Priority.LOWEST);
		for(int i = 0; i < 9; i++){
			outlinePane.addItem(outlineItem, i, 0);
		}
		for(int i = 1; i < 6; i++){
			outlinePane.addItem(outlineItem, 0, i);
		}
		for(int i = 1; i < 6; i++){
			outlinePane.addItem(outlineItem, 8, i);
		}
		for(int i = 1; i < 8; i++){
			outlinePane.addItem(outlineItem, i, 5);
		}
		outlinePane.addItem(clearTagGuiItem, 4, 5);
		Tag playerTag = PlayerTagManager.getPlayerTag(player);
		ItemStack showTagItem = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) showTagItem.getItemMeta();
		skullMeta.setOwningPlayer(player);
		showTagItem.setItemMeta(skullMeta);
		showTagItem.editMeta(meta ->{
			meta.displayName(
					Component.text()
							.append(Component.text("稱號資訊", NamedTextColor.AQUA))
							.decoration(TextDecoration.ITALIC, false)
							.build()
			);
			meta.lore(new ArrayList<>(){{
				add(
						Component.text()
								.append(Component.text("使用中: ", NamedTextColor.GOLD))
								.append(
										playerTag == null ?
												Component.text("無", NamedTextColor.RED) :
												LegacyComponentSerializer.legacy('&').deserialize(playerTag.getText())
								)
								.decoration(TextDecoration.ITALIC, false)
								.build()
				);
			}});
		});
		GuiItem showTagGuiItem = new GuiItem(
				showTagItem,
				e -> e.setCancelled(true)
		);
		outlinePane.addItem(showTagGuiItem, 4, 0);
		gui.addPane(outlinePane);
		PaginatedPane pane = new PaginatedPane(1, 1, 7, 4);
		List<GuiItem> tagItems = new ArrayList<>();
		TagManager.getAllTags().forEach(tag -> {
			GuiItem guiItem;
			if(TagManager.hasTagPermission(player, tag)){
				ItemStack itemStack = tag.getIcon().clone();
				itemStack.removeEnchantments();
				if(playerTag != null){
//					DebugOutputHandler.sendDebugOutput("playerTag.getId(): " + playerTag.getId() + ", tag.getId(): " + tag.getId());
					if(playerTag.equals(tag)){
						itemStack.editMeta(itemMeta -> {
							itemMeta.displayName(itemMeta.displayName().append(Component.text(" (使用中) ", NamedTextColor.GREEN)));
							itemMeta.addEnchant(Enchantment.INFINITY, 1, false);
							itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						});
					}
				}
				guiItem = new GuiItem(
						itemStack,
						(e) -> {
							e.setCancelled(true);
							PlayerTagManager.setPlayerTag(player, tag);
							showGUI(player);
						}
				);
			}else{
				ItemMeta itemMeta = tag.getIcon().getItemMeta();
				ItemStack itemStack = new ItemStack(Material.BARRIER);
				itemStack.setItemMeta(itemMeta);
				guiItem = new GuiItem(
						itemStack,
						(e) -> {
							e.setCancelled(true);
							showGUI(player);
						}
				);
			}
			tagItems.add(guiItem);
		});
		pane.populateWithGuiItems(tagItems);
		PagingButtons pagingButtons = new PagingButtons(Slot.fromXY(1, 5), 7, Pane.Priority.HIGH, pane);
		ItemStack backwardButtonItemStack = new ItemStack(Material.ARROW);
		backwardButtonItemStack.editMeta(itemMeta -> {
			itemMeta.displayName(Component.text("上一頁").decoration(TextDecoration.ITALIC, false));
		});
		pagingButtons.setBackwardButton(new GuiItem(backwardButtonItemStack));
		ItemStack forwardButtonItemStack = new ItemStack(Material.ARROW);
		forwardButtonItemStack.editMeta(itemMeta -> {
			itemMeta.displayName(Component.text("下一頁").decoration(TextDecoration.ITALIC, false));
		});
		pagingButtons.setForwardButton(new GuiItem(forwardButtonItemStack));
		gui.addPane(pagingButtons);
		gui.addPane(pane);
		gui.show(player);
	}
}
