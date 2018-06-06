package pairs.client

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import pairs.client.phaser._

case class Point(x: Double, y: Double)

class GameState(starCount: Int) extends State {
  override def create(): Unit = {
    val starsGraphics = game.add.graphics(50, 50)

    for (i <- 0 until starCount) {
      val offset = i * 24
      val polygon = makeStarPolygon(offset).toJSArray

      val jsPoints = polygon.map { case Point(x, y) =>
        js.Tuple2(x, y)
      }

      starsGraphics.beginFill(0xFFD700)
      starsGraphics.drawPolygon(jsPoints)
      starsGraphics.endFill()
    }
  }

  private def makeStarPolygon(offset: Double): Seq[Point] = {
    for (i <- 0 until 10) yield {
      val angle = 2*Math.PI/10 * i + Math.PI/2
      val len = if (i % 2 == 0) 10 else 4
      Point(offset + 10 + len*Math.cos(angle), 10 - len*Math.sin(angle))
    }
  }
}

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
    game.state.add("game", new GameState(5))
    game.state.start("game")
  }
}
