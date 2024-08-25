package com.sandro.memorygame.models

import com.sandro.memorygame.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {
    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var indexOfSingleSelectedCard: Int? = null
    private var numCardFlips = 0

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        numCardFlips++
        val card = cards[position]
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun restoreCards() {
        cards.forEach {
            if (!it.isMatched)
                it.isFaceUp = false
        }
    }

    private fun checkForMatch(pos1: Int, pos2: Int): Boolean {
        val c1 = cards[pos1]
        val c2 = cards[pos2]
        if (c1.identifier != c2.identifier)
            return false
        c1.isMatched = true
        c2.isMatched = true
        numPairsFound++
        return true
    }

    fun haveWonGame(): Boolean {
        return boardSize.getNumPairs() == numPairsFound
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        return numCardFlips / 2
    }
}