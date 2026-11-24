package noahHashGame;

import java.util.Map;
import java.util.HashMap;
import java.awt.*;

public class Room {
	
	private String name;
	private Color floorColor;
	
	private Map<String, String> doorExits;
	
	public Room(String name, Color floorColor)	{
		this.name = name;
		this.floorColor = floorColor;
		
		this.doorExits = new HashMap<>();
	}
	
	public void addDoor(String direction, String neighborName)	{
		doorExits.put(direction, neighborName);
	}
	
	public String getName()	{
		return name;
	}
	
	public Color getFloorColor()	{
		return floorColor;
	}
	
	public Map<String,String> getDoorExits()	{
		return doorExits;
	}

}
