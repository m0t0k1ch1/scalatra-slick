import com.github.m0t0k1ch1.slick._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new Slick, "/*")
  }
}
