package bartboy8.randomness.main;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import bartboy8.api.ItemAPI;

public class Randomness extends JavaPlugin implements Listener {
	
	public void onEnable(){
		File f = new File(this.getDataFolder(), "config.yml");
		if(!f.exists())
		{
			this.saveDefaultConfig();
		}
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new EnchantablesListeners(this), this);
		if(getConfig().getBoolean("Enchantables.Enabled")) EnchantablesListeners.startTask(this);
		
		if(getConfig().getBoolean("ExperienceTome.Enabled"))
		{
		ItemStack tome = ItemAPI.createItem(Material.ENCHANTED_BOOK, 0, 1, ChatColor.DARK_PURPLE+""+ChatColor.BOLD+""+ChatColor.UNDERLINE+"Experience Tome", new String[]{ChatColor.RED+"EXP: "+ChatColor.GREEN+"0"});
		ShapedRecipe expTomeRecipe = new ShapedRecipe(tome).
		shape(" d ", "dbd", " d ").setIngredient('b', Material.BOOK_AND_QUILL).
		setIngredient('d', Material.DIAMOND);
		getServer().addRecipe(expTomeRecipe);
		}
	}
	
	public void onDisable(){}

	@EventHandler
	public void Stackables(PlayerInteractEntityEvent event)
	{
		if(getConfig().getBoolean("Stackables.Enabled"))
		{
			if(event.getRightClicked() instanceof LivingEntity)
			{
				if(event.getRightClicked() instanceof Player && getConfig().getBoolean("Stackables.StackPlayers") && event.getPlayer().getPassenger() == null)
				{
					if(event.getPlayer().isSneaking() && getConfig().getBoolean("Stackables.Shift-Stack") && event.getPlayer().getPassenger() == null)
					{
						event.getPlayer().setPassenger(event.getRightClicked());
						return;
					}
					Entity entity = event.getRightClicked();
					entity.setPassenger(event.getPlayer());
				}else if(event.getPlayer().getPassenger() == null){
					if(event.getPlayer().isSneaking() && getConfig().getBoolean("Stackables.Shift-Stack") && event.getPlayer().getPassenger() == null)
					{
						event.getPlayer().setPassenger(event.getRightClicked());
						return;
					}
					Entity entity = event.getRightClicked();
					entity.setPassenger(event.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void BlockFall(EntityChangeBlockEvent event)
	{
		if(getConfig().getBoolean("StopBlockFall.Enabled"))
		{
			if(getConfig().getBoolean("StopBlockFall.Anvil") && event.getBlock().getType() == Material.ANVIL)
			{
				event.setCancelled(true);
			}
			if(getConfig().getBoolean("StopBlockFall.Sand") && event.getBlock().getType() == Material.SAND)
			{
				event.setCancelled(true);
			}
			if(getConfig().getBoolean("StopBlockFall.Gravel") && event.getBlock().getType() == Material.GRAVEL)
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void ExperienceTome(PlayerInteractEvent event)
	{
		if(getConfig().getBoolean("ExperienceTome.Enabled"))
		{
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
		if(player.getItemInHand() != null && !player.isSneaking() && player.getLevel() >= 1)
		{
			if(player.getItemInHand().getType() == Material.ENCHANTED_BOOK)
			{
				if(player.getItemInHand().hasItemMeta())
				{
					if(player.getItemInHand().getItemMeta().hasDisplayName())
					{
						if(ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName()).equals("Experience Tome") && player.getItemInHand().getItemMeta().hasLore())
						{
							ItemStack i = player.getItemInHand();
							ItemMeta im = i.getItemMeta();
							List<String> list = im.getLore();
							int temp = 0;
							for(String s:list)
							{
								temp = Integer.parseInt(ChatColor.stripColor(s).replace("EXP: ", ""));
							}
							temp = temp+1;
							list.clear();
							list.add(ChatColor.RED+"EXP: "+ChatColor.GREEN+temp);
							im.setLore(list);
							i.setItemMeta(im);
							player.setLevel(player.getLevel()-1);
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
						}
					}
				}
			}
		}
		if(player.getItemInHand() != null && player.isSneaking())
		{
			if(player.getItemInHand().getType() == Material.ENCHANTED_BOOK)
			{
				if(player.getItemInHand().hasItemMeta())
				{
					if(player.getItemInHand().getItemMeta().hasDisplayName())
					{
						if(ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName()).equals("Experience Tome") && player.getItemInHand().getItemMeta().hasLore())
						{
							ItemStack i = player.getItemInHand();
							ItemMeta im = i.getItemMeta();
							List<String> list = im.getLore();
							int temp = 0;
							for(String s:list)
							{
								temp = Integer.parseInt(ChatColor.stripColor(s).replace("EXP: ", ""));
							}
							if(temp <= 0) return;
							temp = temp-1;
							list.clear();
							list.add(ChatColor.RED+"EXP: "+ChatColor.GREEN+temp);
							im.setLore(list);
							i.setItemMeta(im);
							player.setLevel(player.getLevel()+1);
							player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1, 10);
						}
					}
				}
			}
		}
		}
		}
	}
	
	@EventHandler
	public void Enchantables(EnchantItemEvent event)
	{
		if(getConfig().getBoolean("Enchantables.Enabled"))
		{
			if(event.getEnchantBlock().getType() == Material.ENCHANTMENT_TABLE)
			{
			if(!Resources.addEnchant(Resources.boots,ChatColor.GRAY+"Jump III", 30, event, this))
			if(!Resources.addEnchant(Resources.boots,ChatColor.GRAY+"Jump II", 24, event, this))
			if(Resources.addEnchant(Resources.boots,ChatColor.GRAY+"Jump I", 18, event, this));
			
			if(Resources.addEnchant(Resources.boots,ChatColor.GRAY+"Slow Fall I", 24, event, this));
			
			if(Resources.addEnchant(Resources.helmet,ChatColor.GRAY+"Night Vision I", 30, event, this));
			
			if(!Resources.addEnchant(Resources.chestplate,ChatColor.GRAY+"Brute II", 30, event, this))
			if(Resources.addEnchant(Resources.chestplate,ChatColor.GRAY+"Brute I", 30, event, this));
			
			if(!Resources.addEnchant(Resources.leggings,ChatColor.GRAY+"Speed III", 30, event, this))
			if(!Resources.addEnchant(Resources.leggings,ChatColor.GRAY+"Speed II", 24, event, this))
			if(Resources.addEnchant(Resources.leggings,ChatColor.GRAY+"Speed I", 18, event, this));
			
			if(!Resources.addEnchant(Resources.pickaxe,ChatColor.GRAY+"XP Junkie II", 30, event, this))
			if(Resources.addEnchant(Resources.pickaxe,ChatColor.GRAY+"XP Junkie I", 24, event, this));
			
			if(!Resources.addEnchant(Resources.sword,ChatColor.GRAY+"XP Junkie II", 30, event, this))
			if(Resources.addEnchant(Resources.sword,ChatColor.GRAY+"XP Junkie I", 24, event, this));
			}
		}
	}
	
	@EventHandler
	public void Headhunter(EntityDeathEvent event)
	{
		if(getConfig().getBoolean("Headhunter.Enabled"))
		{
			String percentage = getConfig().getString("Headhunter.Chance");
			percentage = percentage.replace("%", "");
			double chance = Double.parseDouble(percentage)/100;
			if(new Random().nextDouble() <= chance)
			{
				if(event.getEntity().getType() == EntityType.CREEPER)
				{
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.SKULL_ITEM,1,(short) 4));
				}
				if(event.getEntity().getType() == EntityType.ZOMBIE)
				{
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.SKULL_ITEM,1,(short) 2));
				}
				if(event.getEntity().getType() == EntityType.SKELETON)
				{
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.SKULL_ITEM,1,(short) 0));
				}
				if(getConfig().getBoolean("Headhunter.Drop Player Skulls"))
				{
					if(event.getEntity().getType() == EntityType.PLAYER)
					{
					ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(short) 3);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setOwner(((Player)event.getEntity()).getName());
					item.setItemMeta(meta);
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void StopSpawn(CreatureSpawnEvent event)
	{
		if(getConfig().getBoolean("StopSpawn.Enabled"))
		{
			System.out.println(event.getCreatureType().toString().toLowerCase());
			
			for(String string:getConfig().getStringList("StopSpawn.Mobs"))
			{
				System.out.println(string.toLowerCase());
				if(event.getCreatureType().toString().toLowerCase().equalsIgnoreCase(string.toLowerCase()))
				{
					event.setCancelled(true);
					return;
				}
			}
		}
	}


}
