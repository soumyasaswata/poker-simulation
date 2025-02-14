package com.pocker.pokersim

import com.pocker.pokersim.model.HandEvaluation
import com.pocker.pokersim.model.HandCategory
import com.pocker.pokersim.service.HandComparator
import kotlin.test.Test
import kotlin.test.assertTrue

class HandComparatorTest {

  @Test
  fun testRoyalFlushBeatsStraightFlush() {
    val royalFlush = HandEvaluation(HandCategory.ROYAL_FLUSH, 14, 4, listOf(10, 11, 12, 13, 14))
    val straightFlush = HandEvaluation(HandCategory.STRAIGHT_FLUSH, 10, 4, listOf(6, 7, 8, 9, 10))

    assertTrue(HandComparator.compareHands(royalFlush, straightFlush) > 0)
  }

  @Test
  fun testStraightFlushBeatsFourOfAKind() {
    val straightFlush = HandEvaluation(HandCategory.STRAIGHT_FLUSH, 10, 4, listOf(6, 7, 8, 9, 10))
    val fourOfAKind = HandEvaluation(HandCategory.FOUR_OF_A_KIND, 10, 4, listOf(10, 10, 10, 10, 2))

    assertTrue(HandComparator.compareHands(straightFlush, fourOfAKind) > 0)
  }

  @Test
  fun testFourOfAKindBeatsFullHouse() {
    val fourOfAKind = HandEvaluation(HandCategory.FOUR_OF_A_KIND, 10, 4, listOf(10, 10, 10, 10, 2))
    val fullHouse = HandEvaluation(HandCategory.FULL_HOUSE, 10, 3, listOf(10, 10, 10, 2, 2))

    assertTrue(HandComparator.compareHands(fourOfAKind, fullHouse) > 0)
  }

  @Test
  fun testFullHouseBeatsFlush() {
    val fullHouse = HandEvaluation(HandCategory.FULL_HOUSE, 10, 3, listOf(10, 10, 10, 2, 2))
    val flush = HandEvaluation(HandCategory.FLUSH, 10, 4, listOf(2, 4, 6, 8, 10))

    assertTrue(HandComparator.compareHands(fullHouse, flush) > 0)
  }

  @Test
  fun testFlushBeatsStraight() {
    val flush = HandEvaluation(HandCategory.FLUSH, 10, 4, listOf(2, 4, 6, 8, 10))
    val straight = HandEvaluation(HandCategory.STRAIGHT, 10, 4, listOf(6, 7, 8, 9, 10))

    assertTrue(HandComparator.compareHands(flush, straight) > 0)
  }

  @Test
  fun testStraightBeatsThreeOfAKind() {
    val straight = HandEvaluation(HandCategory.STRAIGHT, 10, 4, listOf(6, 7, 8, 9, 10))
    val threeOfAKind = HandEvaluation(HandCategory.THREE_OF_A_KIND, 10, 3, listOf(10, 10, 10, 2, 3))

    assertTrue(HandComparator.compareHands(straight, threeOfAKind) > 0)
  }

  @Test
  fun testThreeOfAKindBeatsTwoPairs() {
    val threeOfAKind = HandEvaluation(HandCategory.THREE_OF_A_KIND, 10, 3, listOf(10, 10, 10, 2, 3))
    val twoPairs = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 3))

    assertTrue(HandComparator.compareHands(threeOfAKind, twoPairs) > 0)
  }

  @Test
  fun testTwoPairsBeatsOnePair() {
    val twoPairs = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 3))
    val onePair = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))

    assertTrue(HandComparator.compareHands(twoPairs, onePair) > 0)
  }

  @Test
  fun testOnePairBeatsNothing() {
    val onePair = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val nothing = HandEvaluation(HandCategory.NOTHING, 10, 1, listOf(2, 4, 6, 8, 10))

    assertTrue(HandComparator.compareHands(onePair, nothing) > 0)
  }

  @Test
  fun testSameCategoryAndSameRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) == 0)
  }

  @Test
  fun testSameCategoryAndDifferentRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.ONE_PAIR, 9, 2, listOf(9, 9, 2, 3, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) > 0)
  }

  @Test
  fun testDifferentCategory() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndSameRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 9, 2, listOf(9, 9, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank2() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 3, 3, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank3() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank4() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank5() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank6() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank7() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank8() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank9() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank10() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank11() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank12() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank13() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank14() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank15() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }

  @Test
  fun testDifferentCategoryAndDifferentRank16() {
    val hand1 = HandEvaluation(HandCategory.ONE_PAIR, 10, 2, listOf(10, 10, 2, 3, 4))
    val hand2 = HandEvaluation(HandCategory.TWO_PAIR, 10, 2, listOf(10, 10, 2, 2, 4))

    assertTrue(HandComparator.compareHands(hand1, hand2) < 0)
  }
}
