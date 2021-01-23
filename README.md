[![Build Status](https://travis-ci.com/StefanGrad/Gwent-SE.svg?branch=workingVer)](https://travis-ci.com/StefanGrad/Gwent-SE)

[![Coverage Status](https://coveralls.io/repos/github/StefanGrad/Gwent-SE/badge.svg?branch=workingVer)](https://coveralls.io/github/StefanGrad/Gwent-SE?branch=workingVer)

# GWENT
Project for a Software Engineering class

A simplified version of the card game Gwent from the rpg The Witcher 3


##How the Game works:

* Both players get 10 random Cards in their hands
* Players DON'T get new Cards in new rounds
* Cards have a special value, an attack value and a range value
* The players alternate their turns
* Cards can ONLY be laid in their respective range (row)
* There exist 3 weather Cards:
    - Sunshine sets all Cards on their regular attack value
    - Frost sets ALL melee Cards to 0 attack
    - Fog sets ALL ranged Cards to 0 attack
 * Only a single weather Card is active at a time
 * The current player can pass any time
 * The round ends when both players pass
 * When the round ends, the player with the highest sum of attack values wins
 * The first Player to win 2 rounds, wins the game        