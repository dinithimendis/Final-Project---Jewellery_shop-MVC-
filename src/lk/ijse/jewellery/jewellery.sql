# Dinithi Mendis
# GDSE-62

# creating database for jewelry shop
DROP DATABASE IF EXISTS jewelery_shop;
CREATE DATABASE IF NOT EXISTS jewelery_shop;
SHOW DATABASES;
USE jewelery_shop;


# creating table for manage admin details
DROP TABLE IF EXISTS admin_tbl;
CREATE TABLE IF NOT EXISTS admin_tbl(
    username VARCHAR(20) PRIMARY KEY ,
    password VARCHAR(15)
    );

INSERT INTO admin_tbl (username,password)VALUES ('Admin',1234);
INSERT INTO admin_tbl (username,password)VALUES ('Cashier',1234);

# creating table for manage employee details
DROP TABLE IF EXISTS employee;
CREATE TABLE IF NOT EXISTS employee(
    empId VARCHAR(5) PRIMARY KEY,
    name VARCHAR(20),
    nic VARCHAR(20) UNIQUE,
    salary DOUBLE,
    telNo VARCHAR(12),
    address TEXT,
    jobRole VARCHAR(20)
    );
INSERT INTO employee VALUES('E00-001','Maneesha','200010562019',2500.00,0719332132,'America','cashier01');
INSERT INTO employee VALUES('E00-002','Alwis','200013422019',2500.00,0719022132,'America','cashier02');
INSERT INTO employee VALUES('E00-003','Rahal','20001432019',2500.00,0719332132,'America','cashier03');
INSERT INTO employee VALUES('E00-004','Dasun','200020202019',2500.00,0719022132,'America','cashier04');
INSERT INTO employee VALUES('E00-005','Maneth','200012262019',2500.00,0719332132,'America','cashier05');
INSERT INTO employee VALUES('E00-006','Rahal','200014562019',2500.00,0719022132,'America','cashier06');

DESCRIBE employee;

# creating table for manage customers

DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer(
    cusId VARCHAR(5) PRIMARY KEY,
    title VARCHAR(6),
   cusName VARCHAR(20),
    address TEXT,
    telNo VARCHAR(12),
    province VARCHAR(50),
    nic VARCHAR(20) UNIQUE
    );

INSERT INTO customer VALUES('C00-001','Mr','Namal','Panadura',0712027212,'Western',200010293034);
INSERT INTO customer VALUES('C00-002','Mr','Nuwan','Galle',0715032212,'Southern',200056666034);
INSERT INTO customer VALUES('C00-003','Mr','Rahal','Panadura',0723027212,'Western',200055593034);
INSERT INTO customer VALUES('C00-004','Mr','Pasan','Galle',0715043212,'Southern',200056234034);
INSERT INTO customer VALUES('C00-005','Mr','Saumi','Makubura',0712027212,'Western',200056234033);
INSERT INTO customer VALUES('C00-006','Mr','Pawan','Galle',0715023212,'Southern',200056333034);

DESCRIBE customer;

# creating table for manage item details
DROP TABLE IF EXISTS item;
CREATE TABLE IF NOT EXISTS item(
    itemCode VARCHAR(5) PRIMARY KEY,
    description VARCHAR(20),
    category VARCHAR(30),
    qty int,
    unitPrice DOUBLE,
    type VARCHAR(40)
    );
INSERT INTO item VALUES('I00-001','Rings','Caret 24','4',1000.00,'Jewellery');
INSERT INTO item VALUES('I00-002','Sapphire','Precious','14',2100.00,'Gem');
INSERT INTO item VALUES('I00-003','Rings','Caret 24','4',100.00,'Jewellery');
INSERT INTO item VALUES('I00-004','Sapphire','Precious','14',100.00,'Gem');
INSERT INTO item VALUES('I00-005','Rings','Caret 24','4',150.00,'Jewellery');
INSERT INTO item VALUES('I00-006','Sapphire','Precious','14',200.00,'Gem');
DESCRIBE item;

# creating table for manage supplier details
DROP TABLE IF EXISTS supplier;
CREATE TABLE IF NOT EXISTS supplier(
    supId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20),
    nic VARCHAR(20) UNIQUE,
    address TEXT,
    telNo VARCHAR(12),
    companyName VARCHAR(15)
    );
INSERT INTO supplier VALUES('S00-001','Dinithi',200001231232,'Galle',0719032112,'Voyage');
INSERT INTO supplier VALUES('S00-002','Deumi',200010504050,'Hapugala',0719022232,'Nileka');
INSERT INTO supplier VALUES('S00-003','Dinithi',200001231244,'Galle',0719032112,'Voyage');
INSERT INTO supplier VALUES('S00-004','Deumi',200010504330,'Hapugala',0719022232,'Nileka');
INSERT INTO supplier VALUES('S00-005','Dinithi',200001255232,'Galle',0719032112,'Voyage');
INSERT INTO supplier VALUES('S00-006','Deumi',200010504540,'Hapugala',0719022232,'Nileka');

DESCRIBE supplier;

# creating table for manage orders details
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order`(
    orderId VARCHAR(5),
    cusId VARCHAR(5),
    OrderDate DATE ,
    OrderTime TIME,
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (cusId) REFERENCES customer(cusId) ON DELETE CASCADE  ON UPDATE CASCADE
    );

INSERT INTO `order` VALUES('O-001','C001','2022.06.03','8:45:5');
INSERT INTO `order` VALUES('O-002','C001','2022.06.03','8:45:5');
INSERT INTO `order` VALUES('O-003','C002','2022.06.04','11:20:45');
INSERT INTO `order` VALUES('O-004','C003','2022.06.09','11:20:45');
INSERT INTO `order` VALUES('O-005','C004','2022.06.12','11:20:45');
INSERT INTO `order` VALUES('O-006','C001','2022.06.03','8:45:5');
INSERT INTO `order` VALUES('O-007','C001','2022.06.03','8:45:5');
INSERT INTO `order` VALUES('O-008','C005','2022.06.23','11:20:45');

# creating table for manage stock details
DROP TABLE IF EXISTS stock;
CREATE TABLE IF NOT EXISTS stock(
    supId VARCHAR(5),
    itemCode VARCHAR(5),
    type VARCHAR(40),
    qty int,
    unitPrice DOUBLE,
    CONSTRAINT FOREIGN KEY (supId) REFERENCES supplier(supId) ON DELETE CASCADE  ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES item(itemCode) ON DELETE CASCADE  ON UPDATE CASCADE

);

# creating table for manage customer details
DROP TABLE IF EXISTS customerDetails;
CREATE TABLE IF NOT EXISTS customerDetails(
    cusId VARCHAR(5),
    empId VARCHAR(5),
    customerDate DATE,
    `time` TIME,
    CONSTRAINT FOREIGN KEY (cusId) REFERENCES customer(cusId) ON DELETE CASCADE  ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (empId) REFERENCES employee(empId) ON DELETE CASCADE  ON UPDATE CASCADE

    );

DROP TABLE IF EXISTS orderDetails;
CREATE TABLE IF NOT EXISTS orderDetails(
    orderId VARCHAR(5),
    itemCode VARCHAR(5),
    OrderQty DOUBLE,
    totalAmount double,
    discount double,
    CONSTRAINT PRIMARY KEY (orderId,itemCode),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES item(itemCode) ON DELETE CASCADE  ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES `order`(orderId) ON DELETE CASCADE  ON UPDATE CASCADE
);
SHOW TABLES;


# creating table for manage order details
# DROP TABLE IF EXISTS orderDetails;
# CREATE TABLE IF NOT EXISTS orderDetails(
#     orderId VARCHAR(5),
#     cusId VARCHAR(5),
#     orderDate DATE,
#     `time` TIME,
#     totalAmount DECIMAL,
#     discount DECIMAL,
#     CONSTRAINT FOREIGN KEY (orderId) REFERENCES `order`(orderId) ON DELETE CASCADE  ON UPDATE CASCADE ,
#     CONSTRAINT FOREIGN KEY (cusId) REFERENCES customer(cusId) ON DELETE CASCADE  ON UPDATE CASCADE
#     );
# SHOW TABLES;

# UPDATE customer SET title=? , cusName=? , address=? , telNo=? , province=? , nic=? WHERE cusId=?;
