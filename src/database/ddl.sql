CREATE DATABASE "backend-challenge";

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    age INT,
    identification VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20)
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    person_id INT UNIQUE NOT NULL REFERENCES person(id) ON DELETE CASCADE,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    initial_balance DECIMAL(10,2) NOT NULL,
    status BOOLEAN NOT NULL,
    client_id INT NOT NULL REFERENCES client(id) ON DELETE CASCADE,
    CONSTRAINT unique_account_per_client UNIQUE (account_number, client_id)
);


CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    account_id INT NOT NULL REFERENCES account(id) ON DELETE CASCADE
);
