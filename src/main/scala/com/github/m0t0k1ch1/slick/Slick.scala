package com.github.m0t0k1ch1.slick

import org.scalatra._
import scalate.ScalateSupport

class Slick extends SlickStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
  
}
