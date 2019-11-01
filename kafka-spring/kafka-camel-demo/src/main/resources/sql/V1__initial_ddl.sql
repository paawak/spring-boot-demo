
CREATE TABLE message_outbox (
  correlation_id varchar(20) PRIMARY KEY NOT NULL,
  partition_id varchar(20) NOT NULL,
  offset bigint NOT NULL,
  insert_time timestamp NOT NULL DEFAULT NOW()
);

