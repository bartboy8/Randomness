package bartboy8.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAPI {
	
	public static FileConfiguration createResourcedYML(JavaPlugin plugin, String configName, String directory)
	{
		String title = configName;
		if(!title.contains(".yml"))
		{
			title = title.replace(title, title+".yml");
		}
		File f = new File(plugin.getDataFolder()+"/"+directory, title);
	    if(!f.exists())
	    {
	    	f.getParentFile().mkdirs();
			plugin.saveResource(configName, true);
	    	return YamlConfiguration.loadConfiguration(f);
	    }
		return null;
	}
	
	public static FileConfiguration createNewYML(JavaPlugin plugin, String configName, String directory)
	{
		String title = configName;
		if(!title.contains(".yml"))
		{
			title = title.replace(title, title+".yml");
		}
		File f = new File(plugin.getDataFolder()+"/"+directory, title);
	    if(!f.exists())
	    {
	    	try {
	    		f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				return null;
			}
	    	return YamlConfiguration.loadConfiguration(f);
	    }
		return null;
	}
	
	public static boolean saveYML(JavaPlugin plugin, FileConfiguration fc, String configName, String directory)
	{
		String title = configName;
		if(!title.contains(".yml"))
		{
			title = title.replace(title, title+".yml");
		}
		File f = new File(plugin.getDataFolder()+"/"+directory, title);
			if(!f.exists())
			{
				return false;
			}else{
			try {
				fc.save(f);
				return true;
			} catch (IOException e) {
				return false;
			}
			}
	}
	
	public static FileConfiguration loadYML(JavaPlugin plugin, String configName, String directory)
	{
		String title = configName;
		if(!title.contains(".yml"))
		{
			title = title.replace(title, title+".yml");
		}
		File f = new File(plugin.getDataFolder()+"/"+directory, title);
		if(!f.exists())
		{
			return null;
		}else{
			return YamlConfiguration.loadConfiguration(f);
		}
	}
	
	public static void saveHashMapToYML(File f, HashMap<Object,Object> map)
	{
		if(f == null) return;
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for(Object o:map.keySet())
		{
			fc.set(o.toString(), map.get(o));
		}
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<Object,Object> loadHashMapFromYML(File f)
	{
		if(!f.exists()) return null;
		HashMap<Object, Object> map = new HashMap<Object,Object>();
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for(Object o:fc.getKeys(true))
		{
			map.put(o, fc.get(o.toString()));
		}
		return map;
	}

}
