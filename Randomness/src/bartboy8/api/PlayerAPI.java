package bartboy8.api;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerAPI {
	
	static String header;
	
	public static void sendMessage(Player p, String m)
	{
		p.sendMessage(header+" "+m);
	}
	
	public static void addPotionEffect(Player p, PotionEffectType pet, int seconds, int intensity)
	{
		p.removePotionEffect(pet);
		p.addPotionEffect(new PotionEffect(pet,seconds*20,intensity));
	}
}
