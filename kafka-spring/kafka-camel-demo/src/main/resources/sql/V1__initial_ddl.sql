
CREATE TABLE message_outbox (
  correlation_id varchar(50) PRIMARY KEY NOT NULL,
  topic_name varchar(200) NOT NULL,
  partition_id smallint NOT NULL,
  offset bigint NOT NULL,
  insert_time timestamp NOT NULL DEFAULT NOW()
);

