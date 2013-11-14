package com.github.m0t0k1ch1.slick.schema

import com.github.m0t0k1ch1.slick.model.Trainer
import scala.slick.driver.MySQLDriver.simple._

object Trainers extends Table[Trainer]("trainers") {
  def id   = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = id.? ~ name <> (Trainer, Trainer.unapply _)
  def autoInc = name returning id
}
