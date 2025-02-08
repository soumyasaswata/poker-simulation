package com.pocker.pokersim.util

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.model.Suit


object PokerUtils {

  fun countRanks(cards: List<Card>): Map<Rank, Int> {
    return cards.groupingBy { it.rank }.eachCount()
  }

  fun countSuits(cards: List<Card>): Map<Suit, Int> {
    return cards.groupingBy { it.suit }.eachCount()
  }

  /**
   * Checks if the given ranks form a straight (Ace can be high or low).
   */
  fun isStraight(ranks: List<Rank>): Boolean {
    // Distinct ranks in descending order
    val distinctVals = ranks.map { it.value }.distinct().sorted()
    if (distinctVals.size != 5) return false

    // Check normal ascending sequence
    var isSequential = (distinctVals.zipWithNext().all { (a, b) -> b == a + 1 })

    // Special case: A, 2, 3, 4, 5
    // (which in sorted form is [1(or 14?), 2, 3, 4, 5] - we handle by: if ranks have 14, check for 2..5)
    if (!isSequential && distinctVals == listOf(2, 3, 4, 5, 14)) {
      // This is A-2-3-4-5
      isSequential = true
    }

    return isSequential
  }

  /**
   * Returns the top rank in a straight.
   * If it's A-2-3-4-5, top rank is 5. Otherwise it's the highest card.
   */
  fun getStraightTopRankValue(ranks: List<Rank>): Int {
    // Sort ascending
    val distinctVals = ranks.map { it.value }.distinct().sorted()
    return if (distinctVals == listOf(2, 3, 4, 5, 14)) {
      5 // Ace is "low" in this scenario
    } else distinctVals.last()
  }

  // Check if the hand is a Royal Flush: 10, J, Q, K, A of the same suit.
  fun isRoyalFlush(sortedCards: List<Card>): Boolean {
    val isFlush = sortedCards.map { it.suit }.distinct().size == 1
    if (!isFlush) return false
    val rankVals = sortedCards.map { it.rank.value }.sorted()
    return rankVals == listOf(10, 11, 12, 13, 14)
  }
}

