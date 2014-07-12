package planet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Stats {
	private Class[] classes;
	private List<Specie> species;
	
	public Stats(Class[] classes, List<Specie> species) {
		this.classes = classes;
		this.species = species;
	}
	
	public void show() {
		Map<Class, Integer> list = new HashMap<Class, Integer>();
		for (Class className : classes) {
			list.put(className, 0);
		}
		for (Specie specie : species) {
			list.put(specie.getClass(), list.get(specie.getClass()) + specie.getAbilitiySum());
		}
		list = MapUtil.sortByValue(list);
		for (Entry<Class, Integer> entry : list.entrySet()) {
			System.out.println(entry.getValue() + "\t" + entry.getKey().getName());		
		}
	}
}