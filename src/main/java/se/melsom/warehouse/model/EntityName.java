package se.melsom.warehouse.model;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Warehouse............Lagerställe: I19VNGGRP162IKL
 * 
 * Designation..........Förrådsbeteckning: FBET/ARTIKEL
 * Name.................Förrådsbenämning BENÄMNING
 * Packaging Quantity...Förpackningskvantitet (?): NV (siffra)
 * Unit.................Enhet: SORT (ST, ...)
 * Packaging............?: ANTAL (siffra, /S i benämning kan indikera att det rör sig om en sats)
 * Quantity.............Antal i lager: INVENT ANTAL
 * Condition............Materielstatus: MA-STAT (NY,BEG)
 * Identity.............Identitet: PT/S/U/IND (tex registreringsnummer på fordon)
 * Meter count..........Mätarvärde: MÄTAR VÄRDE
 * ?....................?: FNIVÅ-ÄR
 * Annotation...........Anteckning: ANTECKNING
 */
public class EntityName {
	public static final int NULL_ID = -1;
	public static final String ITEM_NUMBER = "F-bet/artikel";
	public static final String ITEM_NAME = "Benämning";
	public static final String ITEM_PACKAGING = "Enhet";
	
	public static final String INVENTORY_SOURCE = "Källa";
	public static final String INVENTORY_NOMINAL_QUANTIY = "Antal";
	public static final String INVENTORY_ACTUAL_QUANTITY = "Invent.";
	public static final String INVENTORY_IDENTITY = "Individ";
//	public static final String INSTANCE_METER_COUNT = "Mätarvärde";
	public static final String INVENTORY_ANNOTATION = "Anteckning";
	public static final String INVENTORY_LAST_UPDATED_TIMESTAMP = "Senast ändrad";
	

	public static final String STOCK_POINT_DESIGNATION  = "Lagerställe";
	public static final String STOCK_LOCATION_DESIGNATION  = "Lagerplats";
	public static final String STOCK_LOCATION_DESIGNATION_SECTION = "Sektion";
	public static final String STOCK_LOCATION_DESIGNATION_SLOT = "Fack";
	
	public static final String HOLDING_UNIT = "Ansvar";
	public static final String ORGANIZATIONAL_UNIT = "Enhet";
	public static final String ORGANIZATIONAL_SUPERIOR_UNIT = "Överordnad enhet";
			 
	public static final String INVENTORY_STOCK_ON_HAND = "Lagersaldo";
	
	public static final Color tableEvenRowColor = new Color(229, 238, 220);
	public static final Color tableOddRowColor = new Color(250, 250, 250);
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

	public static String getCurrentDate() {
	    return dateFormat.format(new Date());	    
	}

	public static String getDate(Date date) {
	    return dateFormat.format(date);	    
	}
	
	public static Date getDate(String text) {
		Date date = new Date(0);
		
		try {
			date = dateFormat.parse(text);
		} catch (ParseException e) {
		}
		
		return date;
	}

}
