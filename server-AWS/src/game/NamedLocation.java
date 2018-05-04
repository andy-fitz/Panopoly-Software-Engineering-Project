package game;

import java.awt.Color;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Identifiable;
import game_interfaces.JSONable;
import game_interfaces.Locatable;

public class NamedLocation implements Identifiable, Locatable, JSONable{

	private String identifier;
	private int location;

	public NamedLocation(String name){
		this.identifier = name;
	}
	@Override
	public String getId() {
		return this.identifier;
	}
	
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		info.put("location", this.getLocation());
		info.put("price", 0);
		info.put("owner", 0);
		info.put("color", Color.RED.getRGB());
		info.put("houses", 0);
		info.put("is_mortgaged", false);
		info.put("hasTrap", false);
		info.put("rent", 0);
		return info;
	}
	@Override
	public int getLocation() {
		return this.location;
	}
	@Override
	public void setLocation(int location) {
		this.location = location;		
	}
}
