package com.pocker.pokersim.service

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.HandCategory
import com.pocker.pokersim.model.HandEvaluation
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.util.PokerUtils

object HandEvaluator {

  fun evaluateHand(cards: List<Card>): HandEvaluation {
    require(cards.size == 5) { "A hand must contain exactly 5 cards." }

    val sorted = sortCards(cards)
    val rankCounts = PokerUtils.countRanks(sorted)
    val suitCounts = PokerUtils.countSuits(sorted)

    return when {
      HandRanking.isRoyalFlush(sorted) -> createHand(HandCategory.ROYAL_FLUSH, sorted)
      HandRanking.isStraightFlush(sorted, suitCounts) -> createHand(HandCategory.STRAIGHT_FLUSH, sorted)
      HandRanking.isFourOfAKind(rankCounts) -> createHand(HandCategory.FOUR_OF_A_KIND, sorted, rankCounts)
      HandRanking.isFullHouse(rankCounts) -> createHand(HandCategory.FULL_HOUSE, sorted, rankCounts)
      HandRanking.isFlush(suitCounts) -> createHand(HandCategory.FLUSH, sorted)
      HandRanking.isStraight(sorted) -> createHand(HandCategory.STRAIGHT, sorted)
      HandRanking.isThreeOfAKind(rankCounts) -> createHand(HandCategory.THREE_OF_A_KIND, sorted, rankCounts)
      HandRanking.isTwoPair(rankCounts) -> createHand(HandCategory.TWO_PAIR, sorted, rankCounts)
      HandRanking.isOnePair(rankCounts) -> createHand(HandCategory.ONE_PAIR, sorted, rankCounts)
      else -> createHand(HandCategory.NOTHING, sorted)
    }
  }

  private fun sortCards(cards: List<Card>) =
    cards.sortedWith(compareByDescending<Card> { it.rank.value }.thenByDescending { it.suit.priority })

  private fun createHand(category: HandCategory, sortedCards: List<Card>): HandEvaluation {
    val isAceLow = PokerUtils.isAceLowStraight(sortedCards.map { it.rank })

    val topRank = if (category == HandCategory.STRAIGHT && isAceLow) {
      5 // Ace-low straight should have 5 as the highest rank
    } else {
      sortedCards.first().rank.value
    }

    println("DEBUG: category=$category, isAceLow=$isAceLow, topRank=$topRank")

    return HandEvaluation(category, topRank, sortedCards.first().suit.priority, sortedCards.map { it.rank.value })
  }

  private fun createHand(category: HandCategory, sorted: List<Card>, rankCounts: Map<Rank, Int>): HandEvaluation {
    val topRank = rankCounts.keys.maxByOrNull { it.value }!!
    return HandEvaluation(category, topRank.value, sorted.first().suit.priority, sorted.map { it.rank.value })
  }
}
