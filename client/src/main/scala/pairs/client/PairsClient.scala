package pairs.client

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom

import pairs.client.phaser._

@ScalaJSDefined
class GameState extends State {
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
    }
  }
}

object PairsClient extends js.JSApp {
  def main(): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
    game.state.add("game", new GameState)
    game.state.start("game")
  }
}
