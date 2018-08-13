--
-- Table structure for table bank_details
--

CREATE TABLE bank_details (
  id bigint PRIMARY KEY NOT NULL,
  age smallint DEFAULT NULL,
  job varchar(50) DEFAULT NULL,
  marital varchar(50) DEFAULT NULL,
  education varchar(50) DEFAULT NULL,
  defaulted varchar(50) DEFAULT NULL,
  balance bigint(10) DEFAULT NULL,
  housing varchar(3) DEFAULT NULL,
  loan varchar(3) DEFAULT NULL,
  contact varchar(50) DEFAULT NULL,
  day smallint DEFAULT NULL,
  month varchar(50) DEFAULT NULL,
  duration smallint DEFAULT NULL,
  campaign smallint DEFAULT NULL,
  pdays smallint DEFAULT NULL,
  previous smallint DEFAULT NULL,
  poutcome varchar(50) DEFAULT NULL,
  y varchar(3) DEFAULT NULL
);

