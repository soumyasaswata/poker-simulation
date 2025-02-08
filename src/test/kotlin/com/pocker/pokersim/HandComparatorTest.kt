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
}
