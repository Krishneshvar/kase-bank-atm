CREATE TABLE customers (
	cust_id VARCHAR(10) PRIMARY KEY,
    cust_name TEXT NOT NULL,
    ph_no VARCHAR(10) NOT NULL,
    email_id VARCHAR(50) NOT NULL,
	address TEXT NOT NULL
);

CREATE TABLE acc_details (
    acc_no VARCHAR(15) PRIMARY KEY,
    cust_id VARCHAR(10) NOT NULL,
    card_no VARCHAR(16) NOT NULL,
    exp_date DATE NOT NULL,
    pin VARCHAR(4) NOT NULL,
    min_bal INT NOT NULL,
    balance BIGINT NOT NULL,
    FOREIGN KEY (cust_id) REFERENCES customers(cust_id)
);

CREATE TABLE transac_hist (
    cust_id VARCHAR(10),
    acc_no VARCHAR(15),
    transac_type VARCHAR(50) NOT NULL,
    transac_date TIMESTAMP NOT NULL,
    transac_amt INT,
    recepient_acc_no VARCHAR(15),
    FOREIGN KEY (acc_no) REFERENCES acc_details(acc_no),
    FOREIGN KEY(cust_id) REFERENCES customers(cust_id)
);

CREATE TABLE cash_disp (
    denomination INT PRIMARY KEY,
    amount INT NOT NULL
);

CREATE TABLE current_atmuser (
	acc_no VARCHAR(15) PRIMARY KEY,
    otp VARCHAR(4)
);
