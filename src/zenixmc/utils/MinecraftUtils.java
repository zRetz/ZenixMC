package zenixmc.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public final class MinecraftUtils {

	public static boolean isMaterial(String mat) {
		return Material.matchMaterial(mat) != null;
	}
	
	public static List<Material> stringToMaterialList(List<String> input) {
		
		List<Material> result = new ArrayList<>();
		
		for (String s : input) {
			if (isMaterial(s)) {
				result.add(Material.matchMaterial(s));
			}
		}
		
		return result;
	}
	
}
