package de.crack.lp.util;

import java.util.ArrayList;
import java.util.List;

public enum Units {
	
	SECOND("Sekunde(n)", 1, "sec"),
	MINUTE("Minute(n)", 60, "min"),
	HOUR("Stunde(n)", 60*60, "hour"),
	DAY("Tag(e)", 24*60*60, "day"),
	WEEK("Woche(n)", 7*24*60*60, "week"),
	MONTH("Monat(e)", 4*7*24*60*60, "month");
	
	private String name;
	private long toSecond;
	private String shortcut;
	
	private Units(String name, long toSecond, String shortcut) {
		this.name = name;
		this.toSecond = toSecond;
		this.shortcut = shortcut;
	}
	

	public long getToSecond() {
		return toSecond;
	}
	
	public String getName() {
		return name;
	}
	
	public String getShortcut() {
		return shortcut;
	}
	
	public static List<String> getUnitsAsString(){
		List<String> units = new ArrayList<String>();
		for(Units unit : Units.values()) {
			units.add(unit.getShortcut().toLowerCase());
		}
		return units;
	}
	
	public static Units getUnit(String unit) {
		for(Units units : Units.values()) {
			if(units.getShortcut().toLowerCase().equals(unit.toLowerCase())) {
				return units;
			}
		}
		return null;
	}
	

}
