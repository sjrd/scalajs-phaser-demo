package pairs.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

import scala.util.Properties

object Server {
  private val Index = """
    <html>
      <head>
        <title>Pairs</title>
      </head>
      <body>
        <h1>Pairs</h1>
        <div id="pairs-container"/>
        <script type="application/javascript" src="//cdnjs.cloudflare.com/ajax/libs/phaser/2.4.3/phaser.min.js"></script>
        <script type="application/javascript" src="/js/pairs-client-fastopt.js"></script>
        <script type="application/javascript" src="/js/pairs-client-launcher.js"></script>
      </body>
    </html>
  """

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route = {
      pathPrefix("js") { //fileName =>
        //getFromFile("../client/target/scala-2.11/" + fileName)
        getFromDirectory("./client/target/scala-2.12")
      } ~
      pathPrefix("assets") {
        getFromResourceDirectory("assets")
      } ~
      pathSingleSlash {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, Index))
        } ~
        getFromResourceDirectory("")
      }
    }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
