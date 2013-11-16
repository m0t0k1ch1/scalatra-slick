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
        TrainerPokemons.autoInc.insert(trainerId, pokemonId)
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
        "trainers" -> trainers,
        "isError"  -> false
      )
    }
  }

  get("/trainer/:trainer_id") {
    db withSession {
      val trainerId = params("trainer_id").toInt

      val trainer = Query(Trainers).filter(_.id === trainerId).firstOption
      if (trainer.isEmpty) redirect("/")

      val trainerPokemons = for {
        tp <- TrainerPokemons if tp.trainerId === trainerId
        p  <- Pokemons if p.id === tp.pokemonId
      } yield (tp.id, p)

      val pokemons = Query(Pokemons).list.sortWith(_.number < _.number)

      ssp(
        "/trainer",
        "trainer"         -> trainer.get,
        "trainerPokemons" -> trainerPokemons.list.sortWith(_._2.number < _._2.number),
        "pokemons"        -> pokemons
      )
    }
  }

  post("/trainer") {
    db withSession {
      val name  = params("name")
      val regex = """[a-zA-Z0-9]+""".r

      val isError = name match {
        case regex() => false
        case _       => true
      }
      if (!isError) {
        Trainers.autoInc.insert(name)
      }

      val trainers = Query(Trainers).list
      ssp(
        "/index",
        "trainers" -> trainers,
        "isError"  -> isError
      )
    }
  }

  post("/delete") {
    db withSession {
      val trainerId = params("trainer_id").toInt
      Query(Trainers).filter(_.id === trainerId).delete
      Query(TrainerPokemons).filter(_.trainerId === trainerId).delete
      redirect("/")
    }
  }

  post("/get") {
    db withSession {
      val trainerId = params("trainer_id").toInt
      val pokemonId = params("pokemon_id").toInt
      TrainerPokemons.autoInc.insert(trainerId, pokemonId)
      redirect(s"/trainer/${trainerId}")
    }
  }

  post("/release") {
    db withSession {
      val trainerId        = params("trainer_id")
      val trainerPokemonId = params("trainer_pokemon_id").toInt
      Query(TrainerPokemons).filter(_.id === trainerPokemonId).delete
      redirect(s"/trainer/${trainerId}")
    }
  }
}
