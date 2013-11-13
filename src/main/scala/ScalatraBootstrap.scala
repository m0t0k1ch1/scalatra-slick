import com.github.m0t0k1ch1.slick._
import org.scalatra._
import javax.servlet.ServletContext

import org.slf4j.LoggerFactory

import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.driver.MySQLDriver.simple._

class ScalatraBootstrap extends LifeCycle
{
  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  override def init(context: ServletContext) {
    val db = Database.forDataSource(cpds)
    context.mount(new Slick(db), "/*")
  }

  private def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection
  }
}
