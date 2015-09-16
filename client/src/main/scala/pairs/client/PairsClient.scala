package pairs.client

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import org.scalajs.dom

import pairs.client.phaser._

class Square(val row: Int, val col: Int, val card: Int,
    val front: Sprite, val back: Sprite)

class GameState extends State {
  private var firstClick: Option[Square] = None
  private var secondClick: Option[Square] = None

  private var score: Int = 0
  private var scoreText: js.Dynamic = null
  private var scoreGraphics: Graphics = null

  override def preload(): Unit = {
    load.image("back", "assets/back.png")
    for (i <- 0 to 9)
      load.image(i.toString(), s"assets/$i.png")
  }

  override def create(): Unit = {
    val allCards =
      for (i <- 0 to 9; _ <- 1 to 2) yield i // two copies of each card
    val shuffledCards = scala.util.Random.shuffle(allCards)

    val allPositions =
      for (row <- 0 until 4; col <- 0 until 5) yield (row, col)

    for (((row, col), card) <- allPositions zip shuffledCards) yield {
      val TileSize = 130
      val (x, y) = (col * TileSize, row * TileSize)
      val front = game.add.sprite(x, y, key = card.toString())
      val back = game.add.sprite(x, y, key = "back")

      // Initially, the back is visible
      front.visible = false

      // Setup click event
      val square = new Square(row, col, card, front, back)
      back.inputEnabled = true
      back.events.onInputDown.add((sprite: Sprite) => doClick(square))
    }

    scoreText = game.asInstanceOf[js.Dynamic].add.text(
        660, 20, "Score: 0",
        js.Dynamic.literal(fontSize = "24px", fill = "#fff"))

    scoreGraphics = game.add.graphics(660, 50)
  }

  private def doClick(square: Square): Unit = {
    (firstClick, secondClick) match {
      case (None, _) =>
        // First click of a pair
        firstClick = Some(square)

      case (Some(first), None) if first.card == square.card =>
        // Found a pair
        firstClick = None
        score += 50

      case (Some(_), None) =>
        // Missing a pair, need to hide it later
        secondClick = Some(square)
        score -= 5
        js.timers.setTimeout(1000) {
          assert(firstClick.isDefined && secondClick.isDefined)
          for (square <- Seq(firstClick.get, secondClick.get)) {
            square.front.visible = false
            square.back.visible = true
          }
          firstClick = None
          secondClick = None
        }

      case (Some(_), Some(_)) =>
        // Third click, cancel (have to wait for the deadline to elapse)
        return
    }

    square.back.visible = false
    square.front.visible = true

    scoreText.text = s"Score: $score"

    scoreGraphics.clear()
    for (i <- 0 until score / 100) {
      val offset = i * 24
      def pt(x0: Double, y0: Double): PointLike = new PointLike {
        val x = x0
        val y = y0
      }

      val points = for (i <- (0 until 10).toJSArray) yield {
        val angle = 2*Math.PI/10 * i + Math.PI/2
        val len = if (i % 2 == 0) 10 else 4
        pt(offset + 10 + len*Math.cos(angle), 10 - len*Math.sin(angle))
      }

      scoreGraphics.beginFill(0xFFD700)
      scoreGraphics.drawPolygon(points)
      scoreGraphics.endFill()
    }
  }
}

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
    game.state.add("game", new GameState)
    game.state.start("game")
  }
}
