package se.melsom.warehouse.application;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public abstract class Command {
	private final Map<String, Object> parameters = new HashedMap<>();

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
