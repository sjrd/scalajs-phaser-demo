package pairs.client

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom

import pairs.client.phaser._

class Square(val row: Int, val col: Int, val card: Int,
    val front: Sprite, val back: Sprite)

class GameState extends State {
  private var firstClick: Option[Square] = None
  private var secondClick: Option[Square] = None

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
  }

  private def doClick(square: Square): Unit = {
    (firstClick, secondClick) match {
      case (None, _) =>
        // First click of a pair
        firstClick = Some(square)

      case (Some(first), None) if first.card == square.card =>
        // Found a pair
        firstClick = None

      case (Some(_), None) =>
        // Missing a pair, need to hide it later
        secondClick = Some(square)
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
  }
}

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
    game.state.add("game", new GameState)
    game.state.start("game")
  }
}
