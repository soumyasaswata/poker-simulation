package com.pocker.pokersim

import com.pocker.pokersim.service.PokerGame

fun main() {
  println("Welcome to the Poker Simulation!")
  print("Enter number of players: ")
  val numberOfPlayers = readLine()?.toIntOrNull() ?: 2  // default to 2 if invalid
  val pokerGame = PokerGame(numberOfPlayers)
  val results = pokerGame.playGame()

  // Show each player's cards and hand rank
  results.forEach { result ->
    println("--- Player ${result.playerIndex} ---")
    println("Cards: ${result.hand.joinToString(", ")}")
    println("Evaluation: ${result.evaluation.category} (topRank=${result.evaluation.topRank})")
    println()
  }

  val winner = pokerGame.findWinner(results)
  winner?.let {
    println("Winner: Player ${it.playerIndex} with ${it.evaluation.category}")
  }
}
