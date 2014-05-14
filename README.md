scalatra-slick
==============

an application built with [Scalatra](https://github.com/scalatra/scalatra) + [Slick](https://github.com/slick/slick) + [Scalate](https://github.com/scalate/scalate) + [Furatto](https://github.com/IcaliaLabs/furatto)

## Build & Run

``` sh
$ cd scalatra-slick
$ ./sbt
> container:start
```

Before you open the applicatin, you have to create database named `scalatra-slick` in mysql.  
In order to create tables, access the following URL.

[http://localhost:8080/db/create-tables](http://localhost:8080/db/create-tables)

In order to load the initial data, access the following URL.

[http://localhost:8080/db/load-data](http://localhost:8080/db/load-data)

Then, you can open the application.

[http://localhost:8080](http://localhost:8080)

If you reset the application, access the following URL.

[http://localhost:8080/db/drop-tables](http://localhost:8080/db/drop-tables)

