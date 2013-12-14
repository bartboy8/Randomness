package bartboy8.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemAPI {
	
	public static ItemStack createItem(Material material, int durability, int amount, String title, String[] lore)
	{
		ItemStack item = new ItemStack(material,amount,(short) durability);
		if(lore == null)
		{
			return setDisplayName(item, title);
		}
		return setLore(setDisplayName(item, title), lore);
	}
	
	public static ItemStack setDisplayName(ItemStack item, String displayName)
	{
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(displayName);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, String[] lore)
	{
		if(lore != null){
		List<String> list = new ArrayList<String>();
		for(String s:lore)
		{
			list.add(s);
		}
		ItemMeta im = item.getItemMeta();
		im.setLore(list);
		item.setItemMeta(im);
		return item;
		}
		return item;
	}
	
    public static void toggleLoreWithTimer(ItemStack i, String[] lore, long ticksDelay, JavaPlugin jp)
    {
    	final ItemStack item = i;
    	final List<String> storedLore;
    	List<String> tempLore = new ArrayList<String>();
    	for(String s:lore)
    	{
    		tempLore.add(s);
    	}
    	if(i != null)
    	{
    		if(i.hasItemMeta() && i.getItemMeta().hasLore())
    		{
    			storedLore = i.getItemMeta().getLore();
    			ItemMeta im = i.getItemMeta();
    			im.setLore(tempLore);
    			i.setItemMeta(im);
    			
    			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(jp, new Runnable() {public void run() {
    				ItemMeta itemMeta = item.getItemMeta();
    				itemMeta.setLore(storedLore);
    				item.setItemMeta(itemMeta);
    			}}, ticksDelay);
    		}
    	}
    }
    
    public static void toggleDisplayNameWithTimer(ItemStack i, String displayName, long ticksDelay, JavaPlugin jp)
    {
    	final ItemStack item = i;
    	final String storedDisplayName;
    	if(i != null)
    	{
    		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName())
    		{
    			storedDisplayName = i.getItemMeta().getDisplayName();
    			final ItemMeta im = i.getItemMeta();
    			im.setDisplayName(displayName);
    			i.setItemMeta(im);
    			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(jp, new Runnable() {public void run() {
    				ItemMeta tempMeta = item.getItemMeta();
    				tempMeta.setDisplayName(storedDisplayName);
    				item.setItemMeta(im);
    			}}, ticksDelay);
    		}
    	}
    }
	
    public static void toggleItemTypeWithTimer(ItemStack i, Material type, long ticksDelay, JavaPlugin jp)
    {
    	final ItemStack item = i;
    	final Material storedMaterial;
    	if(i != null)
    	{
    		storedMaterial = i.getType();
    		i.setType(type);
    		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(jp, new Runnable() {public void run() {
    			item.setType(storedMaterial);
    		}}, ticksDelay);
    	}
    }

}
