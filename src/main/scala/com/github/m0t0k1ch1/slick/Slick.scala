package com.github.m0t0k1ch1.slick

import org.scalatra._
import scalate.ScalateSupport

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

case class Trainer (
  id:   Option[Int],
  name: String
)
object Trainers extends Table[Trainer]("trainers") {
  def id   = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = id.? ~ name <> (Trainer, Trainer.unapply _)
  def autoInc = name returning id
}

case class Pokemon (
  id:     Option[Int],
  number: Int,
  name:   String
)
object Pokemons extends Table[Pokemon]("pokemons") {
  def id     = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def number = column[Int]("number")
  def name   = column[String]("name")
  def * = id.? ~ number ~ name <> (Pokemon, Pokemon.unapply _)
  def autoInc = number ~ name returning id
}

case class TrainerPokemon (
  id:        Option[Int],
  trainerId: Int,
  pokemonId: Int
)
object TrainerPokemons extends Table[TrainerPokemon]("trainer_pokemons") {
  def id        = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def trainerId = column[Int]("trainer_id")
  def pokemonId = column[Int]("pokemon_id")
  def * = id.? ~ trainerId ~ pokemonId <> (TrainerPokemon, TrainerPokemon.unapply _)
  def autoInc = trainerId ~ pokemonId returning id
}

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
      val trainer = new Trainer(None, "m0t0k1ch1")
      Trainers.insert(trainer)

      val pokemon1 = new Pokemon(None, 303, "Mawile")
      val pokemon2 = new Pokemon(None, 59, "Arcanine")
      Pokemons.insertAll(pokemon1, pokemon2)

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
