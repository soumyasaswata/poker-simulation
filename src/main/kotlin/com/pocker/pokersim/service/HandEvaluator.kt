package com.pocker.pokersim.service

import com.pocker.pokersim.model.Card
import com.pocker.pokersim.model.Rank
import com.pocker.pokersim.model.Suit
import com.pocker.pokersim.model.HandCategory
import com.pocker.pokersim.model.HandEvaluation
import com.pocker.pokersim.util.PokerUtils

object HandEvaluator {

  fun evaluateHand(cards: List<Card>): HandEvaluation {
    require(cards.size == 5) { "A hand must contain exactly 5 cards." }

    // Sort by rank (descending).
    val sorted = cards.sortedWith(compareByDescending<Card> { it.rank.value }
      .thenByDescending { it.suit.priority })

    val rankCounts = PokerUtils.countRanks(sorted)
    val suitCounts = PokerUtils.countSuits(sorted)
    val isFlush = suitCounts.any { it.value == 5 }
    val isStraight = PokerUtils.isStraight(sorted.map { it.rank })
    val highSuit = sorted.maxByOrNull { it.suit.priority }?.suit ?: Suit.CLUBS
    val highRankValue = sorted.first().rank.value

    // 1) Check Royal Flush
    if (PokerUtils.isRoyalFlush(sorted)) {
      return HandEvaluation(
        HandCategory.ROYAL_FLUSH,
        topRank = Rank.ACE.value,
        suitPriority = highSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 2) Check Straight Flush
    if (isFlush && isStraight) {
      return HandEvaluation(
        HandCategory.STRAIGHT_FLUSH,
        topRank = PokerUtils.getStraightTopRankValue(sorted.map { it.rank }),
        suitPriority = highSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 3) Four of a Kind
    val fourOfAKind = rankCounts.entries.find { it.value == 4 }
    if (fourOfAKind != null) {
      return HandEvaluation(
        HandCategory.FOUR_OF_A_KIND,
        topRank = fourOfAKind.key.value,
        suitPriority = 0, // suit tie-break not needed for Four of a Kind
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 4) Full House (3 + 2)
    if (rankCounts.size == 2 && rankCounts.any { it.value == 3 } && rankCounts.any { it.value == 2 }) {
      // Highest suit is determined by the triple
      val tripleRank = rankCounts.entries.find { it.value == 3 }!!.key
      // Find the triple's suit (the highest among that rank)
      val tripleCards = sorted.filter { it.rank == tripleRank }
      val tripleHighSuit = tripleCards.maxByOrNull { it.suit.priority }!!.suit
      return HandEvaluation(
        HandCategory.FULL_HOUSE,
        topRank = tripleRank.value,
        suitPriority = tripleHighSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 5) Flush
    if (isFlush) {
      return HandEvaluation(
        HandCategory.FLUSH,
        topRank = highRankValue,
        suitPriority = highSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 6) Straight
    if (isStraight) {
      return HandEvaluation(
        HandCategory.STRAIGHT,
        topRank = PokerUtils.getStraightTopRankValue(sorted.map { it.rank }),
        suitPriority = highSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 7) Three of a Kind
    val threeOfAKind = rankCounts.entries.find { it.value == 3 }
    if (threeOfAKind != null) {
      return HandEvaluation(
        HandCategory.THREE_OF_A_KIND,
        topRank = threeOfAKind.key.value,
        suitPriority = 0,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 8) Two Pair
    if (rankCounts.values.count { it == 2 } == 2) {
      // Tiebreak: highest pair's rank, then highest suit of that rank
      val pairs = rankCounts.filter { it.value == 2 }.keys.sortedByDescending { it.value }
      val topPair = pairs.first()
      // Find the highest suit among that topPair
      val topPairSuit = sorted.filter { it.rank == topPair }.maxByOrNull { it.suit.priority }!!.suit

      return HandEvaluation(
        HandCategory.TWO_PAIR,
        topRank = topPair.value,
        suitPriority = topPairSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 9) One Pair
    if (rankCounts.values.count { it == 2 } == 1) {
      val pairRank = rankCounts.entries.find { it.value == 2 }!!.key
      val pairSuit = sorted.filter { it.rank == pairRank }.maxByOrNull { it.suit.priority }!!.suit
      return HandEvaluation(
        HandCategory.ONE_PAIR,
        topRank = pairRank.value,
        suitPriority = pairSuit.priority,
        allRanks = sorted.map { it.rank.value }
      )
    }

    // 10) Nothing
    return HandEvaluation(
      HandCategory.NOTHING,
      topRank = highRankValue,
      suitPriority = highSuit.priority,
      allRanks = sorted.map { it.rank.value }
    )
  }

  /**
   * Compare two HandEvaluation results and decide who wins.
   * Returns a positive integer if `h1` is better, negative if `h2` is better, or zero if tie.
   */
  fun compareHands(h1: HandEvaluation, h2: HandEvaluation): Int {
    // Compare category priority
    val categoryDiff = h1.category.priority.compareTo(h2.category.priority)
    if (categoryDiff != 0) return categoryDiff

    // Category is the same, compare topRank
    val rankDiff = h1.topRank.compareTo(h2.topRank)
    if (rankDiff != 0) return rankDiff

    // Compare suit priority if the category demands it
    val suitDiff = h1.suitPriority.compareTo(h2.suitPriority)
    if (suitDiff != 0) return suitDiff

    // If still tied, compare the next relevant ranks in sorted order if needed
    // (Some variations might do a deeper tie-break with the 'kickers')
    // For demonstration, we'll compare allRanks lexicographically
    for (i in h1.allRanks.indices) {
      val diff = h1.allRanks[i].compareTo(h2.allRanks.getOrElse(i) { 0 })
      if (diff != 0) return diff
    }

    return 0
  }
}
