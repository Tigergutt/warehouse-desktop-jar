package se.melsom.warehouse.model;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Entity name.
 */
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
    /**
     * The constant NULL_ID.
     */
    public static final int NULL_ID = -1;
    /**
     * The constant ITEM_NUMBER.
     */
    public static final String ITEM_NUMBER = "F-bet/artikel";
    /**
     * The constant ITEM_NAME.
     */
    public static final String ITEM_NAME = "Benämning";
    /**
     * The constant ITEM_PACKAGING.
     */
    public static final String ITEM_PACKAGING = "Enhet";

    /**
     * The constant INVENTORY_SOURCE.
     */
    public static final String INVENTORY_SOURCE = "Källa";
    /**
     * The constant INVENTORY_NOMINAL_QUANTIY.
     */
    public static final String INVENTORY_NOMINAL_QUANTIY = "Antal";
    /**
     * The constant INVENTORY_ACTUAL_QUANTITY.
     */
    public static final String INVENTORY_ACTUAL_QUANTITY = "Invent.";
    /**
     * The constant INVENTORY_IDENTITY.
     */
    public static final String INVENTORY_IDENTITY = "Individ";
    /**
     * The constant INVENTORY_ANNOTATION.
     */
//	public static final String INSTANCE_METER_COUNT = "Mätarvärde";
	public static final String INVENTORY_ANNOTATION = "Anteckning";
    /**
     * The constant INVENTORY_LAST_UPDATED_TIMESTAMP.
     */
    public static final String INVENTORY_LAST_UPDATED_TIMESTAMP = "Senast ändrad";


    /**
     * The constant STOCK_POINT_DESIGNATION.
     */
    public static final String STOCK_POINT_DESIGNATION  = "Lagerställe";
    /**
     * The constant STOCK_LOCATION_DESIGNATION.
     */
    public static final String STOCK_LOCATION_DESIGNATION  = "Lagerplats";
    /**
     * The constant STOCK_LOCATION_DESIGNATION_SECTION.
     */
    public static final String STOCK_LOCATION_DESIGNATION_SECTION = "Sektion";
    /**
     * The constant STOCK_LOCATION_DESIGNATION_SLOT.
     */
    public static final String STOCK_LOCATION_DESIGNATION_SLOT = "Fack";

    /**
     * The constant HOLDING_UNIT.
     */
    public static final String HOLDING_UNIT = "Ansvar";
    /**
     * The constant ORGANIZATIONAL_UNIT.
     */
    public static final String ORGANIZATIONAL_UNIT = "Enhet";
    /**
     * The constant ORGANIZATIONAL_SUPERIOR_UNIT.
     */
    public static final String ORGANIZATIONAL_SUPERIOR_UNIT = "Överordnad enhet";

    /**
     * The constant INVENTORY_STOCK_ON_HAND.
     */
    public static final String INVENTORY_STOCK_ON_HAND = "Lagersaldo";

    /**
     * The constant tableEvenRowColor.
     */
    public static final Color tableEvenRowColor = new Color(229, 238, 220);
    /**
     * The constant tableOddRowColor.
     */
    public static final Color tableOddRowColor = new Color(250, 250, 250);

    /**
     * The constant dateFormat.
     */
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

    /**
     * Gets current date.
     *
     * @return the current date
     */
    public static String getCurrentDate() {
	    return dateFormat.format(new Date());	    
	}

    /**
     * Gets date.
     *
     * @param date the date
     * @return the date
     */
    public static String getDate(Date date) {
	    return dateFormat.format(date);	    
	}

    /**
     * Gets date.
     *
     * @param text the text
     * @return the date
     */
    public static Date getDate(String text) {
		Date date = new Date(0);
		
		try {
			date = dateFormat.parse(text);
		} catch (ParseException e) {
		}
		
		return date;
	}

}
