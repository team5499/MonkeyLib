package org.team5499.monkeyLib.path

typealias PathSet = MutableList<Path>

@Suppress("SpreadOperator")
fun pathSetOf(vararg paths: Path) = mutableListOf<Path>(*paths)
