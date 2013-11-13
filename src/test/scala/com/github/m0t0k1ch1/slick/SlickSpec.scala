package com.github.m0t0k1ch1.slick

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class SlickSpec extends ScalatraSpec { def is =
  "GET / on Slick"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[Slick], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
