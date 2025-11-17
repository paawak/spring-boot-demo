# Getting Started

## Running Cassandra using Podman

### Install Podman

```bash
sudo apt-get -y install podman
```

Put __docker.io__ entry in the registry so that images can be pulled with their short-names:

```bash
sudo vi /etc/containers/registries.conf
```

This is the line to put:

```vi
# # An array of host[:port] registries to try when pulling an unqualified image, in order.
 unqualified-search-registries = ["docker.io"]
```

### Start Cassandra from image
Start Cassandra on __localhost__, port __9042__

```bash
# No volumes
podman run -it --name local_cassandra -p 9042:9042 docker.io/library/cassandra:5.0.5

# With volume to keep data
# 2 volumes are mounted: 
# 1. Directory containing the .cql scripts for table creation
# 2. Local directory to preserve the Cassandra data
podman run -it -v /source/spring-boot-demo/testcontainer-cassandra-demo/src/main/cql/:/home -v /home/my-drive/local-data-temp/:/var/lib/cassandra --name local_cassandra -p 9042:9042 docker.io/library/cassandra:5.0.5
```

### Connect to cqlsh

```bash
podman exec -it local_cassandra bash -c cqlsh
```


### Create Keyspace using cqlsh

```
-- Create a keyspace
CREATE KEYSPACE IF NOT EXISTS testcontainer_demo WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Use the keyspace
use testcontainer_demo;
```

### Create tables and insert data using Cqlsh

```
SOURCE '/home/create_tables.cql'
SOURCE '/home/bank_detail_data.cql'
```

## Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.5/gradle-plugin/packaging-oci-image.html)
* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/3.5.5/reference/testing/testcontainers.html#testing.testcontainers)
* [Testcontainers Cassandra Module Reference Guide](https://java.testcontainers.org/modules/databases/cassandra/)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.5/reference/web/servlet.html)
* [Spring Data for Apache Cassandra](https://docs.spring.io/spring-boot/3.5.5/reference/data/nosql.html#data.nosql.cassandra)
* [Testcontainers](https://java.testcontainers.org/)
* [Testcontainers at development time](https://docs.spring.io/spring-boot/3.5.5/reference/features/dev-services.html#features.dev-services.testcontainers)
* [Cassandra official Docker image](https://hub.docker.com/_/cassandra)
* [Cassandra quick start](https://cassandra.apache.org/_/quickstart.html)
