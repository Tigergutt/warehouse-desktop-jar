package se.melsom.warehouse.command;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

/**
 * Base class for UI commands.
 */
public abstract class Command {
	private Map<String, Object> parameters = new HashedMap<>();

    /**
     * Execute command.
     */
    public abstract void execute();

    /**
     * Sets command parameter.
     *
     * @param name  the parameter name
     * @param value the parameter value
     */
    public void setParameter(String name, Object value) {
		parameters.put(name, value);
	}

    /**
     * Gets command parameter.
     *
     * @param name the parameter name
     * @return the parameter value
     */
    public Object getParameter(String name) {
		return parameters.get(name);
	}

    /**
     * Clear all command parameters.
     */
    public void clearParameters() {
		parameters.clear();
	}
}
