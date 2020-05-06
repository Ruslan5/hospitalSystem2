-- ==============================================================
-- ST4Example DB creation script for MySQL
-- ==============================================================

SET NAMES utf8;

DROP DATABASE IF EXISTS hospitalTest2;
CREATE DATABASE hospitalTest2 CHARACTER SET utf8 COLLATE utf8_bin;

USE hospitalTest2;
-- --------------------------------------------------------------
-- ROLES
-- users roles
-- --------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'doctor');
INSERT INTO roles VALUES(2, 'nurse');
INSERT INTO roles VALUES(3, 'patient');

--------------------------------------------------------------
CREATE TABLE person(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	login VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
	name VARCHAR(20) NOT NULL,
	surname VARCHAR(20) NOT NULL,
	-- role VARCHAR(20) NOT NULL,
    role_id INT NOT NULL references roles(id),
	additional_info VARCHAR(20),
    count_patients int(11) DEFAULT NULL
-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		
);

-- id = 1
INSERT INTO person VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 0, 'additionalInfo1', 0);
-- id = 2
INSERT INTO person VALUES(DEFAULT, 'doctor', 'doctor', 'Nikolay', 'Nikolaev', 1, 'pediatrician', 1);
INSERT INTO person VALUES(DEFAULT, 'doc2', 'doc2', 'Fedor', 'Fedorov', 1, 'traumatologist', 0);
INSERT INTO person VALUES(DEFAULT, 'doc3', 'doc3', 'Taras', 'Tarasov', 1, 'surgeon', 0);

-- id = 3
INSERT INTO person VALUES(DEFAULT, 'nurse', 'nurse', 'Тамара', 'Тамарова', 2, 'additionalInfo3', 0);
-- id = 4
INSERT INTO person VALUES(DEFAULT, 'patient', 'patient', 'Sidor', 'Sidorov', 3, '1999-01-01', 0);
INSERT INTO person VALUES(DEFAULT, 'patient2', 'patient2', 'Pavel', 'Pavlov', 3, '2001-02-02', 0);


-- --------------------------------------------------------------
-- Category
-- --------------------------------------------------------------
CREATE TABLE category(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);
INSERT INTO category VALUES(1, 'pediatrician');
INSERT INTO category VALUES(2, 'traumatologist');
INSERT INTO category VALUES(3, 'surgeon');

CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started 
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO statuses VALUES(0, 'examination');
INSERT INTO statuses VALUES(1, 'treatment');
INSERT INTO statuses VALUES(2, 'discharged');
-- --------------------------------------------------------------
-- Администратор регистрирует в системе пациентов и врачей и назначает пациенту врача.
-- --------------------------------------------------------------

CREATE TABLE patient_card(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	doctor_id INT NOT NULL REFERENCES person(id), 
	patient_id INT NOT NULL REFERENCES person(id),
	nurse_id INT REFERENCES person(id),
  --  role_id INT NOT NULL REFERENCES roles(id),
	
	statuses VARCHAR(20) ,
	diagnosis VARCHAR(20) ,
	final_diagnosis VARCHAR(20) ,
	procedures VARCHAR(20) ,
	drug VARCHAR(20) ,
	operations VARCHAR(20) 
);

-- id = 1
INSERT INTO patient_card VALUES(DEFAULT, 2, 1, 2, 'ststuses1', 'ORVI', 'Zdorov', 'procrdureOne', 'Drug', 'NoOperations');
-- id = 2

SELECT * FROM roles;
SELECT * FROM person;
SELECT * FROM category;
SELECT * FROM patient_card;