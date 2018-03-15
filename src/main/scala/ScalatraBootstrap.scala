/**
  * Created by root on 3/13/18.
  */
import org.scalatra.LifeCycle
import javax.servlet.ServletContext

import com.dataguise.nosql.app._
import com.dataguise.nosql.controller.{WebAPIController, WebAPIControllerRestrict}

class ScalatraBootstrap extends LifeCycle {
  val prefix = "/rest/v1/"
  implicit val swagger= new DgNoSqlSwaggerApp
  override def init(context: ServletContext) {
    context.mount(new WebAPIController, prefix+  "*")
    context.mount(new WebAPIControllerRestrict, "/*")
    context.mount(new ResourcesApp,  "/rest/v1/api-docs")
  }
}
