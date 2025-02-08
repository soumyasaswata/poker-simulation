package com.pocker.pokersim.service

import com.pocker.pokersim.model.HandEvaluation

object HandComparator {

  fun compareHands(h1: HandEvaluation, h2: HandEvaluation): Int {
    return when {
      h1.category.priority != h2.category.priority -> h1.category.priority - h2.category.priority
      h1.topRank != h2.topRank -> h1.topRank - h2.topRank
      h1.suitPriority != h2.suitPriority -> h1.suitPriority - h2.suitPriority
      else -> compareKickers(h1, h2)
    }
  }

  private fun compareKickers(h1: HandEvaluation, h2: HandEvaluation): Int {
    for (i in h1.allRanks.indices) {
      val diff = h1.allRanks[i] - h2.allRanks.getOrElse(i) { 0 }
      if (diff != 0) return diff
    }
    return 0
  }
}
