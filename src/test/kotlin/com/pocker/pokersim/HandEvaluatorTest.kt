package com.pocker.pokersim

import com.pocker.pokersim.model.*
import com.pocker.pokersim.service.HandEvaluator
import com.pocker.pokersim.service.HandComparator
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

  @Test
  fun testThreeOfAKind() {
    val cards = listOf(
      Card(Rank.TEN, Suit.CLUBS),
      Card(Rank.TEN, Suit.DIAMONDS),
      Card(Rank.TEN, Suit.HEARTS),
      Card(Rank.FIVE, Suit.SPADES),
      Card(Rank.SEVEN, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.THREE_OF_A_KIND, evaluation.category)
  }

  @Test
  fun testTwoPair() {
    val cards = listOf(
      Card(Rank.TEN, Suit.CLUBS),
      Card(Rank.TEN, Suit.DIAMONDS),
      Card(Rank.FIVE, Suit.HEARTS),
      Card(Rank.FIVE, Suit.SPADES),
      Card(Rank.SEVEN, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.TWO_PAIR, evaluation.category)
  }

  @Test
  fun testOnePair() {
    val cards = listOf(
      Card(Rank.TEN, Suit.CLUBS),
      Card(Rank.TEN, Suit.DIAMONDS),
      Card(Rank.FIVE, Suit.HEARTS),
      Card(Rank.SEVEN, Suit.SPADES),
      Card(Rank.EIGHT, Suit.CLUBS),
    )
    val evaluation = HandEvaluator.evaluateHand(cards)
    assertEquals(HandCategory.ONE_PAIR, evaluation.category)
  }

  @Test
  fun testCompareHands() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.ONE_PAIR, 9, 2, listOf(9, 9, 2, 3, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(1, result)
  }

  @Test
  fun testCompareHandsSameCategory() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(0, result)
  }

  @Test
  fun testCompareHandsDifferentCategory() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndSameRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 9, 2, listOf(9, 9, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank2() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 3, 3, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank3() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank4() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank5() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank6() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank7() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank8() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank9() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank10() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank11() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank12() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank13() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank14() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank15() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank16() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank17() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank18() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank19() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }

  @Test
  fun testCompareHandsDifferentCategoryAndDifferentRank20() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    val result = HandComparator.compareHands(hand1, hand2)
    assertEquals(-1, result)
  }
}
