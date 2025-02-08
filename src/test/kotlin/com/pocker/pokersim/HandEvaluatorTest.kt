package com.pocker.pokersim

import com.pocker.pokersim.model.*
import com.pocker.pokersim.service.HandEvaluator
import kotlin.test.Test
import kotlin.test.assertEquals

class HandEvaluatorTest {

  @Test
  fun testRoyalFlush() {
    val cards = listOf(
      Card(Rank.TEN, Suit.HEARTS),
      Card(Rank.JACK, Suit.HEARTS),
      Card(Rank.QUEEN, Suit.HEARTS),
      Card(Rank.KING, Suit.HEARTS),
      Card(Rank.ACE, Suit.HEARTS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)

    println("DEBUG: Evaluated hand -> ${evaluation.category}")
    assertEquals(HandCategory.ROYAL_FLUSH, evaluation.category)
  }

  @Test
  fun testStraightFlush() {
    val cards = listOf(
      Card(Rank.SIX, Suit.DIAMONDS),
      Card(Rank.SEVEN, Suit.DIAMONDS),
      Card(Rank.EIGHT, Suit.DIAMONDS),
      Card(Rank.NINE, Suit.DIAMONDS),
      Card(Rank.TEN, Suit.DIAMONDS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.STRAIGHT_FLUSH, evaluation.category)
  }

  @Test
  fun testFourOfAKind() {
    val cards = listOf(
      Card(Rank.NINE, Suit.SPADES),
      Card(Rank.NINE, Suit.HEARTS),
      Card(Rank.NINE, Suit.CLUBS),
      Card(Rank.NINE, Suit.DIAMONDS),
      Card(Rank.ACE, Suit.HEARTS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.FOUR_OF_A_KIND, evaluation.category)
  }

  @Test
  fun testFullHouse() {
    val cards = listOf(
      Card(Rank.KING, Suit.SPADES),
      Card(Rank.KING, Suit.HEARTS),
      Card(Rank.KING, Suit.DIAMONDS),
      Card(Rank.QUEEN, Suit.SPADES),
      Card(Rank.QUEEN, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.FULL_HOUSE, evaluation.category)
  }

  @Test
  fun testFlush() {
    val cards = listOf(
      Card(Rank.TWO, Suit.CLUBS),
      Card(Rank.FOUR, Suit.CLUBS),
      Card(Rank.SEVEN, Suit.CLUBS),
      Card(Rank.NINE, Suit.CLUBS),
      Card(Rank.KING, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.FLUSH, evaluation.category)
  }

  @Test
  fun testStraight() {
    val cards = listOf(
      Card(Rank.FIVE, Suit.SPADES),
      Card(Rank.SIX, Suit.HEARTS),
      Card(Rank.SEVEN, Suit.CLUBS),
      Card(Rank.EIGHT, Suit.DIAMONDS),
      Card(Rank.NINE, Suit.SPADES),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.STRAIGHT, evaluation.category)
  }

  @Test
  fun testAceLowStraight() {
    val cards = listOf(
      Card(Rank.ACE, Suit.CLUBS),
      Card(Rank.TWO, Suit.DIAMONDS),
      Card(Rank.THREE, Suit.HEARTS),
      Card(Rank.FOUR, Suit.SPADES),
      Card(Rank.FIVE, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)

    println("DEBUG: Evaluated hand -> ${evaluation.category}, Top Rank -> ${evaluation.topRank}")

    assertEquals(HandCategory.STRAIGHT, evaluation.category)
    assertEquals(5, evaluation.topRank)  // Ace-low straight should have 5 as the top rank
  }


  @Test
  fun testNothing() {
    val cards = listOf(
      Card(Rank.TWO, Suit.CLUBS),
      Card(Rank.FOUR, Suit.DIAMONDS),
      Card(Rank.SEVEN, Suit.HEARTS),
      Card(Rank.NINE, Suit.CLUBS),
      Card(Rank.KING, Suit.SPADES),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.NOTHING, evaluation.category)
  }
}
