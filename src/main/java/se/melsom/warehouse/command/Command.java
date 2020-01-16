package se.melsom.warehouse.command;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public abstract class Command {
	private Map<String, Object> parameters = new HashedMap<>();
	
	public abstract void execute();
	
	public void setParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}
	
	public void clearParameters() {
		parameters.clear();
	}
}
