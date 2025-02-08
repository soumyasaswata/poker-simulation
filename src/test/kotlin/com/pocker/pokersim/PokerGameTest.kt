package com.pocker.pokersim

import com.pocker.pokersim.service.PokerGame
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PokerGameTest {

  @Test
  fun testGameSetup() {
    val game = PokerGame(4)
    val results = game.playGame()
    assertEquals(4, results.size)
    results.forEach {
      assertEquals(5, it.hand.size)
    }
  }

  @Test
  fun testWinnerNotNull() {
    val game = PokerGame(3)
    val results = game.playGame()
    val winner = game.findWinner(results)
    assertTrue(winner != null, "Game must produce a winner with 3 players")
  }
}
