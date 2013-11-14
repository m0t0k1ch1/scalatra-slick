package com.github.m0t0k1ch1.slick

import com.github.m0t0k1ch1.slick.model._
import com.github.m0t0k1ch1.slick.schema._

import org.scalatra._
import scalate.ScalateSupport

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

case class Slick(db: Database) extends ScalatraServlet with SlickRoutes

trait SlickRoutes extends ScalatraServlet
{
  val db: Database
  val ddl = (Trainers.ddl ++ Pokemons.ddl ++ TrainerPokemons.ddl)

  get("/db/create-tables") {
    db withSession {
      ddl.create
      ddl.createStatements.toList.mkString("\r\n")
    }
  }

  get("/db/load-data") {
    db withSession
    {
      Trainers.insert(new Trainer(None, "m0t0k1ch1"))

      Pokemons.insertAll(
        new Pokemon(None, 303, "Mawile"),
        new Pokemon(None, 59, "Arcanine")
      )

      200
    }
  }

  get("/db/drop-tables") {
    db withSession {
      ddl.drop
      ddl.dropStatements.toList.mkString("\r\n")
    }
  }
}
