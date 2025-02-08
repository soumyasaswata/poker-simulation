package com.pocker.pokersim.model

enum class Rank(val displayName: String, val value: Int) {
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("Jack", 11),
  QUEEN("Queen", 12),
  KING("King", 13),
  ACE("Ace", 14);

  companion object {
    fun fromValue(value: Int): Rank? = values().find { it.value == value }
  }
}
