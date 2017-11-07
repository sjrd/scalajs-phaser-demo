package pairs.client

import scala.scalajs.js
import org.scalajs.dom

import pairs.client.phaser._

object Game {
  final val TileSize = 130

  sealed abstract class GuessState
  case object NoGuess extends GuessState
  case class OneCard(index: Int) extends GuessState
  case object WrongGuess extends GuessState
}

class Game extends Phaser.State {
  import Game._

  private var images: IndexedSeq[Phaser.Sprite] = _
  private var cards: IndexedSeq[Phaser.Sprite] = _
  private var score: Int = 0

  def preload(): Unit = {
    load.image("back", "assets/back.png")
    for (i <- 0 until 10)
      load.image(i.toString, s"assets/$i.png")
  }

  def create(): Unit = {
    val allCardNumbers = (0 until 10) ++ (0 until 10)
    val imagesNotShuffled = allCardNumbers.map { i =>
      game.add.sprite(0, 0, i.toString())
    }
    images = scala.util.Random.shuffle(imagesNotShuffled)
    cards = images.map { _ =>
      game.add.sprite(0, 0, "back")
    }

    for (i <- 0 until 4; j <- 0 until 5) {
      val idx = i*5 + j
      val left = j * TileSize
      val top = i * TileSize

      val image = images(idx)
      image.x = left
      image.y = top
      image.visible = false

      val card = cards(idx)
      card.x = left
      card.y = top
      card.inputEnabled = true
      card.events.onInputDown.add(() => doClick(idx))
      card.events.onInputOver.add { () =>
        card.alpha = 0.5
      }
      card.events.onInputOut.add { () =>
        card.alpha = 1.0
      }
    }
  }

  private var guessState: GuessState = NoGuess

  private def doClick(idx: Int): Unit = {
    val card = cards(idx)
    val image = images(idx)

    guessState match {
      case NoGuess =>
        guessState = OneCard(idx)

      case OneCard(first) =>
        if (images(first).key == images(idx).key) {
          score += 50
          guessState = NoGuess
        } else {
          score -= 5
          guessState = WrongGuess
          js.timers.setTimeout(1000) {
            images(first).visible = false
            images(idx).visible = false
            cards(first).visible = true
            cards(idx).visible = true
            guessState = NoGuess
          }
        }

      case WrongGuess =>
        return
    }

    card.visible = false
    image.visible = true
  }

  def update(): Unit = {}

  def render(): Unit = {
    game.asInstanceOf[js.Dynamic].debug.text(
        s"Score: $score", 660, 20)
  }
}

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Phaser.Game(width = 800, height = 520,
        parent = "pairs-game")
    game.state.add("game", js.constructorOf[Game])
    game.state.start("game")
  }
}
