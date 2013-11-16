package com.github.m0t0k1ch1.slick.schema

import com.github.m0t0k1ch1.slick.model.TrainerPokemon
import scala.slick.driver.MySQLDriver.simple._

object TrainerPokemons extends Table[TrainerPokemon]("trainer_pokemons") {
  def id         = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def trainerId  = column[Int]("trainer_id")
  def pokemonId  = column[Int]("pokemon_id")
  def isFavorite = column[Boolean]("is_favorite", O.Default(false))
  def * = id.? ~ trainerId ~ pokemonId ~ isFavorite.? <> (TrainerPokemon, TrainerPokemon.unapply _)
  def autoInc = trainerId ~ pokemonId returning id
}
