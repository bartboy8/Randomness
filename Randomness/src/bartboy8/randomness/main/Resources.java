package bartboy8.randomness.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Resources {
	
	public static Material[] helmet = {Material.LEATHER_HELMET,Material.IRON_HELMET,Material.GOLD_HELMET,Material.DIAMOND_HELMET,Material.CHAINMAIL_HELMET};
	public static Material[] chestplate = {Material.LEATHER_CHESTPLATE,Material.IRON_CHESTPLATE,Material.GOLD_CHESTPLATE,Material.DIAMOND_CHESTPLATE,Material.CHAINMAIL_CHESTPLATE};
	public static Material[] leggings = {Material.LEATHER_LEGGINGS,Material.IRON_LEGGINGS,Material.GOLD_LEGGINGS,Material.DIAMOND_LEGGINGS,Material.CHAINMAIL_LEGGINGS};
	public static Material[] boots = {Material.LEATHER_BOOTS,Material.IRON_BOOTS,Material.GOLD_BOOTS,Material.DIAMOND_BOOTS,Material.CHAINMAIL_BOOTS};
	public static Material[] pickaxe = {Material.WOOD_PICKAXE,Material.STONE_PICKAXE,Material.IRON_PICKAXE,Material.GOLD_PICKAXE,Material.DIAMOND_PICKAXE};
	public static Material[] sword = {Material.WOOD_SWORD,Material.STONE_SWORD,Material.IRON_SWORD,Material.GOLD_SWORD,Material.DIAMOND_SWORD};
	
	public static boolean addEnchant(Material[] itemType, String enchantname, int minLevel, EnchantItemEvent event, JavaPlugin plugin)
	{
		for(Material m:itemType)
		{
		if(event.getItem().getType() == m)
		{
			if(event.getExpLevelCost() >= minLevel)
			{
				if(!plugin.getConfig().getBoolean("Enchantables.Enchantments."+ChatColor.stripColor(enchantname)+".Enabled")) return false;
				String percentage = plugin.getConfig().getString("Enchantables.Enchantments."+ChatColor.stripColor(enchantname)+".Chance");
				percentage = percentage.replace("%", "");
				double chance = Double.parseDouble(percentage)/100;
				if(new Random().nextDouble() <= chance)
				{
					addLore(enchantname,event.getItem());
					return true;
				}
			}
		}
		}
		return false;
	}
	
	public static void addLore(String addToLore, ItemStack itemToAddTo)
	{
		ItemMeta im = itemToAddTo.getItemMeta();
		List<String> l = new ArrayList<String>();
		if(im.getLore() != null){
		for(String s:im.getLore()){
			l.add(s);
		}
		}
		l.add(addToLore);
		im.setLore(l);
		itemToAddTo.setItemMeta(im);
	}
	
	public static boolean ItemHasStringInLore(ItemStack i, String string, JavaPlugin jp)
	{
		if(jp.getConfig().getBoolean("Enchantables.Enchantments."+string+".Enabled") && jp.getConfig().getBoolean("Enchantables.Enabled"))
		{
		if(i != null)
		{
			if(i.hasItemMeta())
			{
				if(i.getItemMeta().hasLore())
				{
					List<String> lore = i.getItemMeta().getLore();
					for(String s:lore)
					{
						if(ChatColor.stripColor(s).equals(ChatColor.stripColor(string))) return true;
					}
				}
			}
		}
		}
		return false;
	}
	
	public static void addAndRemoveEffect(Player p, PotionEffectType effect, int ticks, int intensity)
	{
		p.removePotionEffect(effect);
		p.addPotionEffect(new PotionEffect(effect, ticks, intensity));
	}

}
