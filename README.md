scalatra-slick
==============

an application built with Scalatra + Slick + Scalate + Furatto

## Build & Run

```sh
$ cd scalatra-slick
$ ./sbt
> container:start
```

before you open the applicatin, you have to create tables and load data

* Create Tables
[http://localhost:8080/db/create-tables](http://localhost:8080/db/create-tables)

* Load Data
[http://localhost:8080/db/load-data](http://localhost:8080/db/load-data)

then, you can open the application

* Index
[http://localhost:8080](http://localhost:8080)

if you reset the application, drop tables 

* Drop Tables
[http://localhost:8080/db/drop-tables](http://localhost:8080/db/drop-tables)

