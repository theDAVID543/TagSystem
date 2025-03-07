package the.david.tagSystem.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PagingButtons;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import the.david.tagSystem.Main;
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
		gui.addPane(outlinePane);
		PaginatedPane pane = new PaginatedPane(1, 1, 7, 4);
		List<GuiItem> tagItems = new ArrayList<>();
		TagManager.getAllTags().forEach(tag -> {
			GuiItem guiItem;
			if(TagManager.hasTagPermission(player, tag)){
				guiItem = new GuiItem(
						tag.getIcon(),
						(e) -> e.setCancelled(true)
				);
			}else{
				ItemMeta itemMeta = tag.getIcon().getItemMeta();
				ItemStack itemStack = new ItemStack(Material.BARRIER);
				itemStack.setItemMeta(itemMeta);
				guiItem = new GuiItem(
						itemStack,
						(e) -> e.setCancelled(true)
				);
			}
			tagItems.add(guiItem);
//			for(int i = 0; i < 90; i++){
//				ItemStack testItem = new ItemStack(Material.STONE);
//				int finalI = i;
//				testItem.editMeta(itemMeta -> {
//					itemMeta.displayName(Component.text(String.valueOf(finalI)));
//				});
//				tagItems.add(new GuiItem(testItem));
//			}
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
