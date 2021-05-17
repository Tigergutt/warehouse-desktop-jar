
-- Create table for actual Inventory (quantity at specific stock location).
DROP TABLE IF EXISTS actual_inventory;
CREATE TABLE actual_inventory (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    stock_location_id INT NOT NULL,
    quantity INT DEFAULT '1' NOT NULL,
    identity CHAR(16) DEFAULT '' NOT NULL,
    annotation CHAR(50) DEFAULT '' NOT NULL,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create table for
DROP TABLE IF EXISTS master_inventory;
CREATE TABLE master_inventory (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    source CHAR(16) DEFAULT '' NOT NULL,
    stock_point CHAR(16) DEFAULT '' NOT NULL,
    quantity INT DEFAULT '1' NOT NULL,
    identity CHAR(16) DEFAULT '' NOT NULL,
    annotation CHAR(50) DEFAULT '' NOT NULL,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create table for Holding (defining owner of a stock location).#
DROP TABLE IF EXISTS holdings;
CREATE TABLE holdings (
    unit_id INT NOT NULL,
    stock_location_id INT NOT NULL,
    PRIMARY KEY(unit_id, stock_location_id)
);

-- Create table for Article Application.
DROP TABLE IF EXISTS item_application;
CREATE TABLE item_application (
    unit_id INT NOT NULL,
    item_id INT NOT NULL,
    category CHAR(50) DEFAULT 'RU'  NOT NULL,
    quantity INT DEFAULT '1' NOT NULL,
    PRIMARY KEY(unit_id, item_id, category)
);

-- Create table for Organizational Units.
DROP TABLE IF EXISTS organizational_units;
CREATE TABLE organizational_units (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    callsign CHAR(2) NOT NULL,
    name CHAR(30) NOT NULL,
    level INT NOT NULL,
    superior_unit_id INT NOT NULL
);

-- Create table for Items.
DROP TABLE IF EXISTS items;
CREATE TABLE items (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    number CHAR(16) NOT NULL,
    name CHAR(50) NOT NULL,
    packaging CHAR(16) DEFAULT '' NOT NULL,
    description CHAR(128) DEFAULT '' NOT NULL
);

-- Create table for Stock Locations
DROP TABLE IF EXISTS stock_locations;
CREATE TABLE stock_locations (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    section CHAR(10) NOT NULL,
    slot CHAR(10) NOT NULL,
    description CHAR(128) DEFAULT '' NOT NULL
);



