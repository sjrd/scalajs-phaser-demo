package pairs.client

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom

import pairs.client.phaser._

class GameState extends State {

}

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
    game.state.add("game", new GameState)
    game.state.start("game")
  }
}
