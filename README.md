# Raging Bull Platfor Server

## Prerequisites

### Mac 10.10.3

<pre><code>
sw_vers
</code></pre>

### Homebrew 0.9.5

<pre><code>
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
brew -v
</code></pre>

Notes:

- [http://brew.sh/](http://brew.sh/)
- [http://jameshalsall.co.uk/posts/upgrading-homebrew-packages-on-osx-mavericks](http://jameshalsall.co.uk/posts/upgrading-homebrew-packages-on-osx-mavericks)
- While installing, if you get ``Error: Could not symlink ...``, then use ``sudo chown `whoami` /usr/local/include /usr/local/lib``

### Cask 0.54.1

<pre><code>
brew install caskroom/cask/brew-cask
brew info brew-cask
brew cask doctor
</code></pre>

Notes:

- [http://caskroom.io/](http://caskroom.io/)

### Java 1.8.0_51

<pre><code>
brew cask install java
/usr/libexec/java_home -V
java -version
javac -version
</code></pre>

Notes:

- [https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html/](https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)

### Maven 3.3.3

<pre><code>
brew install maven
mvn -v
</code></pre>


## Developer Quick Start

### Build

<pre><code>
mvn clean install
</code></pre>

Maven will compile and run all the unit tests which create a DB on startup using an in-memory H2 DB.

### Start

<pre><code>
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/RagingBull-1.0-SNAPSHOT.jar server input.yml
</code></pre>

Java will run the server on port 8080 after creating or updating the DB on startup using a local embedded file H2 DB at ./ragingbull_test.mv.db.
Logs will be available in your terminal.


### Stop

<pre><code>
Ctrl-C
</code></pre>

## Using a local MySQL instance

### Install MySQL 5.6.25

<pre><code>
brew install mysql
mysql -V
mysql.server status
</code></pre>

Notes:

- [https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/)

### Start MySQL

<pre><code>
mysql.server start
</code></pre>

Notes:

- To stop MySQL, ``mysql.server stop``

### Create Database

<pre><code>
mysql -u root -e 'CREATE DATABASE ragingbull'
mysql -u root -e 'show schemas'
</code></pre>

### Configure Yaml

<pre><code>
database:
  driverClass: com.mysql.jdbc.Driver
  user: root
  url: jdbc:mysql://localhost:3306/ragingbull
</code></pre>

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


