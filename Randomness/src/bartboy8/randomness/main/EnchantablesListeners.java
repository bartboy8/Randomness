package bartboy8.randomness.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EnchantablesListeners implements Listener {
	
	/*
	 * Done
	 * - Slow Fall I
	 * - Speed (ALL)
	 * - Jump (ALL)
	 * - Brute (ALL)
	 * - XP Junkie (ALL)
	 */
	
	Randomness plugin;
	public EnchantablesListeners(Randomness plugin)
	{
		this.plugin = plugin;
	}
	
	public static void startTask(final JavaPlugin plugin)
	{
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run(){
				for(Player p:Bukkit.getOnlinePlayers())
				{
					if(Resources.ItemHasStringInLore(p.getInventory().getHelmet(), "Night Vision I", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.NIGHT_VISION, 160, 0);
					
					if(Resources.ItemHasStringInLore(p.getInventory().getChestplate(), "Brute I", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.HEALTH_BOOST, 160, 0);
					if(Resources.ItemHasStringInLore(p.getInventory().getChestplate(), "Brute II", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.HEALTH_BOOST, 160, 1);
					
					if(Resources.ItemHasStringInLore(p.getInventory().getBoots(), "Jump I", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.JUMP, 160, 0);
					if(Resources.ItemHasStringInLore(p.getInventory().getBoots(), "Jump II", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.JUMP, 160, 1);
					if(Resources.ItemHasStringInLore(p.getInventory().getBoots(), "Jump III", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.JUMP, 160, 2);
					
					if(Resources.ItemHasStringInLore(p.getInventory().getLeggings(), "Speed I", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.SPEED, 160, 0);
					if(Resources.ItemHasStringInLore(p.getInventory().getLeggings(), "Speed II", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.SPEED, 160, 1);
					if(Resources.ItemHasStringInLore(p.getInventory().getLeggings(), "Speed III", plugin)) Resources.addAndRemoveEffect(p, PotionEffectType.SPEED, 160, 2);
				}
			}}, 80L, 80L);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void SlowFall(PlayerMoveEvent event)
	{
		Player p = event.getPlayer();
		if(p.isSneaking() && !p.isOnGround())
		{
			if(Resources.ItemHasStringInLore(p.getInventory().getBoots(), "Slow Fall I", plugin))
			{
				p.setVelocity(new Vector(p.getVelocity().getX(), 0.01, p.getVelocity().getZ()));
			}
		}
	}
	
	@EventHandler
	public void PickaxeXPJunkie(BlockBreakEvent event)
	{
		if(Resources.ItemHasStringInLore(event.getPlayer().getItemInHand(), "XP Junkie I", plugin))
		{
			event.setExpToDrop((int) (event.getExpToDrop() * plugin.getConfig().getDouble("Enchantables.Enchantments.XP Junkie I.XP Multiplyer")));
		}
		if(Resources.ItemHasStringInLore(event.getPlayer().getItemInHand(), "XP Junkie II", plugin))
		{
			event.setExpToDrop((int) (event.getExpToDrop() * plugin.getConfig().getDouble("Enchantables.Enchantments.XP Junkie II.XP Multiplyer")));
		}
	}
	
	@EventHandler
	public void SwordXPJunkie(EntityDeathEvent event)
	{
		if(event.getEntity().getKiller() instanceof Player)
		{
		if(Resources.ItemHasStringInLore(event.getEntity().getKiller().getItemInHand(), "XP Junkie I", plugin))
		{
			event.setDroppedExp((int) (event.getDroppedExp() * plugin.getConfig().getDouble("Enchantables.Enchantments.XP Junkie I.XP Multiplyer")));
		}
		if(Resources.ItemHasStringInLore(event.getEntity().getKiller().getItemInHand(), "XP Junkie II", plugin))
		{
			event.setDroppedExp((int) (event.getDroppedExp() * plugin.getConfig().getDouble("Enchantables.Enchantments.XP Junkie II.XP Multiplyer")));
		}
		}
	}

}
