-- Create Database
CREATE DATABASE IF NOT EXISTS bloodbankdb;
USE bloodbankdb;

-- Donor Table
CREATE TABLE IF NOT EXISTS Donor (
    donor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    weight DECIMAL(5,2),
    blood_group VARCHAR(5),
    health_condition VARCHAR(50),
    contact VARCHAR(50),
    location VARCHAR(100)
);

-- Hospital Table
CREATE TABLE IF NOT EXISTS Hospital (
    hospital_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    location VARCHAR(100),
    username VARCHAR(50),
    password VARCHAR(50)
);

-- Request Table
CREATE TABLE IF NOT EXISTS Request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_id INT,
    blood_group VARCHAR(5),
    quantity INT,
    request_date DATE,
    FOREIGN KEY (hospital_id) REFERENCES Hospital(hospital_id)
);

-- Inventory Table
CREATE TABLE IF NOT EXISTS Inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    blood_group VARCHAR(5),
    units_available INT
);

-- Insert initial inventory values (optional but recommended)
INSERT INTO Inventory (blood_group, units_available) VALUES
('A+', 10),
('A-', 8),
('B+', 12),
('B-', 6),
('O+', 15),
('O-', 7),
('AB+', 5),
('AB-', 4);
