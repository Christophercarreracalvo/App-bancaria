-- Script SQL para crear la base de datos y tablas para el proyecto BankApp
CREATE DATABASE IF NOT EXISTS bankapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bankapp;

CREATE TABLE IF NOT EXISTS customer (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS account (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  account_number VARCHAR(50) UNIQUE NOT NULL,
  balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
  FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

-- Datos de ejemplo
INSERT INTO customer (name, email) VALUES ('Juan Perez', 'juan@example.com');
INSERT INTO account (customer_id, account_number, balance) VALUES (1, 'ACC1001', 1000.00);
INSERT INTO account (customer_id, account_number, balance) VALUES (1, 'ACC1002', 500.00);