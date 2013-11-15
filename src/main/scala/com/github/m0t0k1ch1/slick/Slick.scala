package com.github.m0t0k1ch1.slick

import com.github.m0t0k1ch1.slick.model._
import com.github.m0t0k1ch1.slick.schema._

import org.scalatra._
import scalate.ScalateSupport

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

case class Slick(db: Database) extends ScalatraServlet with SlickRoutes

trait SlickRoutes extends SlickStack
{
  val db: Database
  val ddl = (Trainers.ddl ++ Pokemons.ddl ++ TrainerPokemons.ddl)

  before() {
    contentType = "text/html"
  }

  get("/db/create-tables") {
    db withSession {
      ddl.create
      ddl.createStatements.toList.mkString("<br />")
    }
  }

  get("/db/load-data") {
    db withSession
    {
      Trainers.insert(new Trainer(None, "m0t0k1ch1"))
      Trainers.insert(new Trainer(None, "m0t0k1ch2"))

      Pokemons.insertAll(
        new Pokemon(None, 303, "Mawile"),
        new Pokemon(None, 59, "Arcanine")
      )

      "success"
    }
  }

  get("/db/drop-tables") {
    db withSession {
      ddl.drop
      ddl.dropStatements.toList.mkString("<br />")
    }
  }

  get("/") {
    db withSession {
      val trainers = Query(Trainers).list
      ssp("/index", "trainers" -> trainers)
    }
  }

  get("/trainer/:id") {
    // list of pokemons
  }

  post("/trainer") {
    // update trainer info
    // params: trainer.id trainer.name
  }

  post("/get") {
    // get pokemon
    // params: trainer.id pokemon.id
  }

  post("/release") {
    // release pokemon
    // params: trainer.id pokemon.id
  }
}
