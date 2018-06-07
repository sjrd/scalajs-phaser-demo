package stars

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import stars.phaser._

final case class Point(x: Double, y: Double)

class GameState(starCount: Int) extends State {
  override def create(): Unit = {
    val gr = game.add.graphics(50, 50)
    for (i <- 0 until starCount)
      drawStar(gr, i * 24)
  }

  private def drawStar(gr: Graphics, offset: Double): Unit = {
    gr.beginFill(0xFFD700)
    gr.drawPolygon(makeStarPolygon(offset).toJSArray.map {
      case Point(x, y) => js.Tuple2(x, y)
    })
    gr.endFill()
  }

  private def makeStarPolygon(offset: Double): Seq[Point] = {
    for (i <- 0 until 10) yield {
      val angle = 2*Math.PI/10 * i + Math.PI/2
      val len = if (i % 2 == 0) 10 else 4
      Point(offset + 10 + len*Math.cos(angle),
          10 - len*Math.sin(angle))
    }
  }
}

object Stars {
  def main(args: Array[String]): Unit = {
    val game = new Game(width = 300, height = 124,
        parent = "pairs-container")
    game.state.add("game", new GameState(5))
    game.state.start("game")
  }
}
