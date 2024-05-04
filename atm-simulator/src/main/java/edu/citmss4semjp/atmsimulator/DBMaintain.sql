create table customers (
	cust_id varchar(9) primary key,
    cust_name text not null,
    ph_no varchar(10) not null,
	address text not null
);

create table acc_details (
    acc_no varchar(14) primary key,
    cust_id varchar(9) not null,
    card_no varchar(16) not null,
    exp_date date not null,
    pin varchar(4) not null,
    min_bal int not null,
    balance bigint not null,
    FOREIGN KEY (cust_id) REFERENCES customers(cust_id)
);

CREATE TABLE transac_hist (
    cust_id varchar(9),
    acc_no varchar(14),
    transac_type VARCHAR(50),
    transac_date TIMESTAMP not null,
    transac_amt int,
    transac_acc_no int,
    FOREIGN KEY (acc_no) REFERENCES Customers(acc_no),
    FOREIGN KEY(cust_id) REFERENCES customers(cust_id)
);

CREATE TABLE cash_disp (
    denomination INT PRIMARY KEY,
    amount INT
);
