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
      val trainerId = Trainers.autoInc.insert("m0t0k1ch1")

      val pokemons = List((303, "Mawile"), (59, "Arcanine"))
      val pokemonIds = for {
        pokemon <- pokemons
        id = Pokemons.autoInc.insert(pokemon._1, pokemon._2)
      } yield id

      for (pokemonId <- pokemonIds) {
        TrainerPokemons.insert(new TrainerPokemon(None, trainerId, pokemonId))
      }

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
      ssp(
        "/index",
        "trainers" -> trainers
      )
    }
  }

  get("/trainer/:id") {
    db withSession {
      val id = params("id").toInt

      val trainer = Query(Trainers).filter(_.id === id).firstOption
      if (trainer.isEmpty) redirect("/")

      val pokemons = for {
        tp <- TrainerPokemons if tp.trainerId === id
        p  <- Pokemons if p.id === tp.pokemonId
      } yield p

      ssp(
        "/trainer",
        "trainer"  -> trainer.get,
        "pokemons" -> pokemons.list.sortWith(_.number < _.number)
      )
    }
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
