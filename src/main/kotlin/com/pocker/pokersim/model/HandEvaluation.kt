package com.pocker.pokersim.model

data class HandEvaluation(
  val category: HandCategory,
  val topRank: Int,       // used for tie-break (e.g. rank of the four-of-kind)
  val suitPriority: Int,  // used for tie-break
  val allRanks: List<Int> // in sorted order if needed
)
