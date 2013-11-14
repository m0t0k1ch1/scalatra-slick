package com.github.m0t0k1ch1.slick.schema

import com.github.m0t0k1ch1.slick.model.Pokemon
import scala.slick.driver.MySQLDriver.simple._

object Pokemons extends Table[Pokemon]("pokemons") {
  def id     = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def number = column[Int]("number")
  def name   = column[String]("name")
  def * = id.? ~ number ~ name <> (Pokemon, Pokemon.unapply _)
  def autoInc = number ~ name returning id
}
