scalatra-slick
==============

an application built with Scalatra + Slick + Scalate + Furatto

## Build & Run

``` sh
$ cd scalatra-slick
$ ./sbt
> container:start
```

Before you open the applicatin, you have to create tables and load data.

* create tables
[http://localhost:8080/db/create-tables](http://localhost:8080/db/create-tables)

* load data
[http://localhost:8080/db/load-data](http://localhost:8080/db/load-data)

Then, you can open the application.

* show index
[http://localhost:8080](http://localhost:8080)

If you reset the application, drop tables.

* drop tables
[http://localhost:8080/db/drop-tables](http://localhost:8080/db/drop-tables)

