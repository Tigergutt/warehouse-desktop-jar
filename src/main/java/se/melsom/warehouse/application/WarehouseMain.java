package se.melsom.warehouse.application;

import java.awt.EventQueue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import se.melsom.logging.ConfigurationUtility;
import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.event.ModelEventBroker;

public class WarehouseMain implements Runnable {
	private ApplicationController applicationController = null;
	ModelEventBroker eventBroker = new ModelEventBroker();

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
