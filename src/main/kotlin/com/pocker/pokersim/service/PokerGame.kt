package com.pocker.pokersim.service

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.Deck
import com.pocker.pokersim.model.HandEvaluation
import com.pocker.pokersim.service.HandEvaluator.evaluateHand
import com.pocker.pokersim.service.HandEvaluator.compareHands

class PokerGame(private val numberOfPlayers: Int) {
  private val deck = Deck()

  data class PlayerResult(
    val playerIndex: Int,
    val hand: List<Card>,
    val evaluation: HandEvaluation
  )

  fun playGame(): List<PlayerResult> {
    require(numberOfPlayers in 2..10) {
      "Number of players must be between 2 and 10 for a standard deck game."
    }
    deck.shuffle()

    val results = mutableListOf<PlayerResult>()
    repeat(numberOfPlayers) { playerIndex ->
      val hand = deck.deal(5)
      val evaluation = evaluateHand(hand)
      results.add(PlayerResult(playerIndex + 1, hand, evaluation))
    }
    return results
  }

  fun findWinner(results: List<PlayerResult>): PlayerResult? {
    return results.maxWithOrNull { p1, p2 ->
      compareHands(p1.evaluation, p2.evaluation)
    }
  }
}
