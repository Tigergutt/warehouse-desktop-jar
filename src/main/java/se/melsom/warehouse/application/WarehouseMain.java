package se.melsom.warehouse.application;

import java.awt.EventQueue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import se.melsom.logging.ConfigurationUtility;
import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.event.ModelEventBroker;

/**
 * This is the main entry point of the Warehouse Application.
 */
public class WarehouseMain implements Runnable {
	private ApplicationController applicationController = null;
    /**
     * The Event broker.
     */
    ModelEventBroker eventBroker = new ModelEventBroker();

    /**
     * The entry point of application.
     *
     * @param args command line arguments and flags (currently none).
     */
    public static void main(String[] args) {
		ConfigurationUtility.loadConfiguration();
		Logger.getLogger("org.apache").setLevel(Level.OFF);;

		WarehouseMain main = new WarehouseMain();
		WarehouseDatabase.singleton().connect();
		
		main.start();	
	}

	private void start() {
		eventBroker = new ModelEventBroker();
		applicationController = new ApplicationController(eventBroker);
 
		EventQueue.invokeLater(this);
	}

	@Override
	public void run() {
		applicationController.applicationStart();
	}
}
