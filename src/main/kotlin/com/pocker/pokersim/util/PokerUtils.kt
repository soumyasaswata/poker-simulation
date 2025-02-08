package com.pocker.pokersim.util

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.model.Suit

object PokerUtils {

  fun countRanks(cards: List<Card>): Map<Rank, Int> =
    cards.groupingBy { it.rank }.eachCount()

  fun countSuits(cards: List<Card>): Map<Suit, Int> =
    cards.groupingBy { it.suit }.eachCount()

  fun isStraight(ranks: List<Rank>): Boolean {
    val sortedRanks = ranks.map { it.value }.distinct().sorted()
    if (sortedRanks.size != 5) return false

    val isRegularStraight = sortedRanks.last() - sortedRanks.first() == 4
    val isAceLowStraight = sortedRanks == listOf(2, 3, 4, 5, 14)

    println("DEBUG: sortedRanks=$sortedRanks, isRegularStraight=$isRegularStraight, isAceLowStraight=$isAceLowStraight")

    return isRegularStraight || isAceLowStraight
  }


  fun isRoyalFlush(sortedCards: List<Card>): Boolean {
    val ranks = sortedCards.map { it.rank.value }.sorted() // 🔹 Ensure ascending order
    return isFlush(sortedCards) && ranks == listOf(10, 11, 12, 13, 14)
  }

  fun isAceLowStraight(ranks: List<Rank>): Boolean {
    val rankValues = ranks.map { it.value }.distinct().sorted()
    return rankValues == listOf(2, 3, 4, 5, 14)
  }

  private fun isFlush(cards: List<Card>): Boolean =
    cards.groupingBy { it.suit }.eachCount().any { it.value == 5 }
}
