package bartboy8.api;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class InvAPI {
	
	public static Inventory createManagedInventory(String menuTitle, int menuSlots, HashMap<Integer,ItemStack> map)
	{
		Inventory inventory = Bukkit.getServer().createInventory(null, menuSlots, menuTitle);
		for(int slot:map.keySet())
		{
			inventory.setItem(slot, map.get(slot));
		}
		return inventory;
	}
	
	public static Inventory createUnManagedInventory(String menuTitle, int menuSlots, List<ItemStack> map)
	{
		Inventory inventory = Bukkit.getServer().createInventory(null, menuSlots, menuTitle);
		for(ItemStack i:map)
		{
			inventory.addItem(i);
		}
		return inventory;
	}
	
	public static boolean clickBetweenSlots(InventoryClickEvent event, int slot1, int slot2)
	{
		if(event.getRawSlot() >= slot1 && event.getRawSlot() <= slot2)
		{
			return true;
		}
		return false;
	}
	
	public static boolean clickOnSlot(InventoryClickEvent event, int slot1)
	{
		if(event.getRawSlot() == slot1)
		{
			return true;
		}
		return false;
	}
	
	public static boolean clickInInventory(InventoryClickEvent event, String invTitle)
	{
		int i = event.getInventory().getSize();
		if(event.getInventory().getTitle().contains(invTitle) && event.getRawSlot() >= 0 && event.getRawSlot() <= i)
		{
			return true;
		}
		return false;
	}
	
    public static void closeThenOpenInv(final Player player, final Inventory inv,long ticks, JavaPlugin jp)
    {
    	player.closeInventory();
    	Bukkit.getScheduler().runTaskLater(jp, new Runnable() {
	         @Override
	         public void run() {
	        	 player.openInventory(inv);
	         }
	     }, ticks);
    }

}
