package com.pocker.pokersim.service


import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.model.Suit
import kotlin.random.Random

class Deck {
  private val cards = mutableListOf<Card>()

  init {
    Suit.values().forEach { suit ->
      Rank.values().forEach { rank ->
        cards.add(Card(rank, suit))
      }
    }
  }

  fun shuffle() {
    cards.shuffle(Random(System.currentTimeMillis()))
  }

  fun deal(numCards: Int): List<Card> {
    require(numCards <= cards.size) { "Not enough cards left in the deck." }
    return List(numCards) { cards.removeAt(cards.lastIndex) }
  }
}
