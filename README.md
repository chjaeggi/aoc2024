## Advent of Code 2024 Solutions in Kotlin

My solutions for [Advent of Code challenges 2024](https://adventofcode.com/2024) in Kotlin (JVM).

[![Kotlin Version](https://img.shields.io/badge/kotlin-2.0.12-blue.svg)](http://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

Copyright © 2024 by Christian Jaeggi.

Before I start I want to give a big shout-out to [Todd Ginsberg](https://github.com/tginsberg/advent-2024-kotlin) whose
solutions are very concise as well as idiomatic to read.
His repos are always very inspirational! I learned a lot from him.

Also, there was a guy doing stuff in Kotlin I stumbled upon on reddit: Good
stuff: [ClouddJR](https://github.com/ClouddJR/advent-of-code-2024/tree/main/src/main/kotlin/com/clouddjr/advent2024)

Just use the main class and execute either the appropriate `solve` functions in the according `Day` classes or any `day`
function i.a.

**Enjoy!**

#### Daily Solution Index for 2024

| Day | Title                  | Links                                                                                                                      |
|-----|------------------------|----------------------------------------------------------------------------------------------------------------------------|
| 1   | Historian Hysteria     | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day1.kt) [\[AoC\]](http://adventofcode.com/2024/day/1)   |
| 2   | Red-Nosed Reports      | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day2.kt) [\[AoC\]](http://adventofcode.com/2024/day/2)   |
| 3   | Mull It Over           | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day3.kt) [\[AoC\]](http://adventofcode.com/2024/day/3)   |
| 4   | Ceres Search           | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day4.kt) [\[AoC\]](http://adventofcode.com/2024/day/4)   |
| 5   | Print Queue            | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day5.kt) [\[AoC\]](http://adventofcode.com/2024/day/5)   |
| 6   | Guard Gallivant        | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day6.kt) [\[AoC\]](http://adventofcode.com/2024/day/6)   |
| 7   | Bridge Repair          | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day7.kt) [\[AoC\]](http://adventofcode.com/2024/day/7)   |
| 8   | Resonant Collinearity  | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day8.kt) [\[AoC\]](http://adventofcode.com/2024/day/8)   |
| 9   | Disk Fragmented        | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day9.kt) [\[AoC\]](http://adventofcode.com/2024/day/9)   |
| 10  | Hoof It                | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day10.kt) [\[AoC\]](http://adventofcode.com/2024/day/10) |
| 11  | Plutonian Pebbles      | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day11.kt) [\[AoC\]](http://adventofcode.com/2024/day/11) |
| 12  | Garden Groups          | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day12.kt) [\[AoC\]](http://adventofcode.com/2024/day/12) |
| 13  | Claw Contraption       | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day13.kt) [\[AoC\]](http://adventofcode.com/2024/day/13) |
| 14  | Restroom Redoubt       | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day14.kt) [\[AoC\]](http://adventofcode.com/2024/day/14) |
| 15  | Warehouse Woes         | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day15.kt) [\[AoC\]](http://adventofcode.com/2024/day/15) |
| 16  | Reindeer Maze          | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day16.kt) [\[AoC\]](http://adventofcode.com/2024/day/16) |
| 17  | Chronospatial Computer | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day17.kt) [\[AoC\]](http://adventofcode.com/2024/day/17) |
| 18  | RAM Run                | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day18.kt) [\[AoC\]](http://adventofcode.com/2024/day/18) |
| 19  | Linen Layout           | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day19.kt) [\[AoC\]](http://adventofcode.com/2024/day/19) |
| 20  | Race Condition         | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day20.kt) [\[AoC\]](http://adventofcode.com/2024/day/20) |
| 21  | Keypad Conundrum       | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day21.kt) [\[AoC\]](http://adventofcode.com/2024/day/21) |
| 22  | Monkey Market          | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day22.kt) [\[AoC\]](http://adventofcode.com/2024/day/22) |
| 23  | LAN Party              | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day23.kt) [\[AoC\]](http://adventofcode.com/2024/day/23) |
| 24  | Crossed Wires          | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day24.kt) [\[AoC\]](http://adventofcode.com/2024/day/24) |
| 25  | Code Chronicle         | [\[Code\]](https://github.com/chjaeggi/aoc2024/blob/main/src/days/Day25.kt) [\[AoC\]](http://adventofcode.com/2024/day/25) |
