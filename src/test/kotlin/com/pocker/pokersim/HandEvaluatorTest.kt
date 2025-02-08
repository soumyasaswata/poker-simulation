package com.pocker.pokersim

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.HandCategory
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.model.Suit
import com.pocker.pokersim.service.HandEvaluator
import kotlin.test.Test
import kotlin.test.assertEquals

class HandEvaluatorTest {

  @Test
  fun testRoyalFlush() {
    val cards = listOf(
      Card(Rank.TEN, Suit.SPADES),
      Card(Rank.JACK, Suit.SPADES),
      Card(Rank.QUEEN, Suit.SPADES),
      Card(Rank.KING, Suit.SPADES),
      Card(Rank.ACE, Suit.SPADES),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.ROYAL_FLUSH, evaluation.category)
  }

  @Test
  fun testFourOfAKind() {
    val cards = listOf(
      Card(Rank.FIVE, Suit.CLUBS),
      Card(Rank.FIVE, Suit.HEARTS),
      Card(Rank.FIVE, Suit.SPADES),
      Card(Rank.FIVE, Suit.DIAMONDS),
      Card(Rank.TEN, Suit.HEARTS)
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.FOUR_OF_A_KIND, evaluation.category)
    assertEquals(Rank.FIVE.value, evaluation.topRank)
  }

  @Test
  fun testNothing() {
    val cards = listOf(
      Card(Rank.TWO, Suit.CLUBS),
      Card(Rank.FOUR, Suit.DIAMONDS),
      Card(Rank.SEVEN, Suit.HEARTS),
      Card(Rank.NINE, Suit.CLUBS),
      Card(Rank.KING, Suit.SPADES)
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.NOTHING, evaluation.category)
  }
}
