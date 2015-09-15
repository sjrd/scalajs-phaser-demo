package pairs.server

import akka.actor.ActorSystem
import spray.http.{HttpEntity, MediaTypes}
import spray.routing.SimpleRoutingApp

import scala.util.Properties

object Server extends SimpleRoutingApp {
  private val Index = {
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
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    val port = Properties.envOrElse("PORT", "8080").toInt

    startServer("0.0.0.0", port = port) {
      get {
        pathSingleSlash {
          respondWithMediaType(MediaTypes.`text/html`) {
            complete {
              Index
            }
          }
        } ~
        getFromResourceDirectory("")
      } ~
      path("js" / Rest) { fileName =>
        getFromFile("../client/target/scala-2.11/" + fileName)
      } ~
      pathPrefix("assets") {
        getFromResourceDirectory("assets")
      }
    }
  }
}
