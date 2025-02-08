package com.pocker.pokersim.model

data class Card(
  val rank: Rank,
  val suit: Suit
) {
  override fun toString(): String {
    return "${rank.displayName}${suit.symbol}"
  }
}
