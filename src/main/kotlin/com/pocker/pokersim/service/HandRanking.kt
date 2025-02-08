package com.pocker.pokersim.service

import com.pocker.pokersim.model.*
import com.pocker.pokersim.util.PokerUtils

object HandRanking {

  fun isRoyalFlush(sorted: List<Card>): Boolean =
    PokerUtils.isRoyalFlush(sorted)

  fun isStraightFlush(sorted: List<Card>, suitCounts: Map<Suit, Int>): Boolean =
    suitCounts.any { it.value == 5 } && PokerUtils.isStraight(sorted.map { it.rank })

  fun isFourOfAKind(rankCounts: Map<Rank, Int>): Boolean =
    rankCounts.values.contains(4)

  fun isFullHouse(rankCounts: Map<Rank, Int>): Boolean =
    rankCounts.size == 2 && rankCounts.containsValue(3) && rankCounts.containsValue(2)

  fun isFlush(suitCounts: Map<Suit, Int>): Boolean =
    suitCounts.any { it.value == 5 }

  fun isStraight(sorted: List<Card>): Boolean =
    PokerUtils.isStraight(sorted.map { it.rank })

  fun isThreeOfAKind(rankCounts: Map<Rank, Int>): Boolean =
    rankCounts.containsValue(3)

  fun isTwoPair(rankCounts: Map<Rank, Int>): Boolean =
    rankCounts.values.count { it == 2 } == 2

  fun isOnePair(rankCounts: Map<Rank, Int>): Boolean =
    rankCounts.containsValue(2)
}
