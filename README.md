# Raging Bull Platform Server

## Prerequisites

### Mac 10.10.3


```
sw_vers

```

### Homebrew 0.9.5

```
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
brew -v
```
Notes:

- [http://brew.sh/](http://brew.sh/)
- [http://jameshalsall.co.uk/posts/upgrading-homebrew-packages-on-osx-mavericks](http://jameshalsall.co.uk/posts/upgrading-homebrew-packages-on-osx-mavericks)
- While installing, if you get ``Error: Could not symlink ...``, then use ``sudo chown `whoami` /usr/local/include /usr/local/lib``

### Cask 0.54.1

```
brew install caskroom/cask/brew-cask
brew info brew-cask
brew cask doctor

```
Notes:

- [http://caskroom.io/](http://caskroom.io/)

### Java 1.8.0_51

```
brew cask install java
/usr/libexec/java_home -V
java -version
javac -version

```
Notes:

- [https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html/](https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)

### Maven 3.3.3

```
brew install maven
mvn -v

```

## Developer Quick Start

### Build

```
mvn clean install
```
Maven will compile and run all the unit tests which create a DB on startup using an in-memory H2 DB.

### Start

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/RagingBull-1.0-SNAPSHOT.jar server input.yml
```

Java will run the server on port 8080 after creating or updating the DB on startup using a local embedded file H2 DB at ./ragingbull_test.mv.db.
Logs will be available in your terminal.


### Stop

```
Ctrl-C
```

## Using a local MySQL instance

### Install MySQL 5.6.25

```
brew install mysql
mysql -V
mysql.server status
```
Notes:

- [https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/)

### Start MySQL

```
mysql.server start
```
Notes:

- To stop MySQL, ``mysql.server stop``

### Create Database

```
mysql -u root -e 'CREATE DATABASE ragingbull'
mysql -u root -e 'show schemas'
```

### Configure Yaml

```
database:
  driverClass: com.mysql.jdbc.Driver
  user: root
  url: jdbc:mysql://localhost:3306/ragingbull
```

Now restart the server using your latest yaml configuration.

### View Database

Install MySQL Workbench 6.3

[https://www.mysql.com/products/workbench/](https://www.mysql.com/products/workbench/)

- Database > Connect To Database
- Hostname: 127.0.0.1
- Port: 3306
- Username: root
- Default Schema: ragingbull

Notes:

- [https://dev.mysql.com/doc/workbench/en/wb-installing-mac.html](https://dev.mysql.com/doc/workbench/en/wb-installing-mac.html)

### Architecture

![Screen Shot 2015-10-25 at 7.32.06 pm.png](https://bitbucket.org/repo/nn8X8z/images/2661630430-Screen%20Shot%202015-10-25%20at%207.32.06%20pm.png)