package org.team5499.monkeyLib.path

typealias PathSet = MutableList<Path>

typealias MirroredPathSet = MutableList<MirroredPath>

@Suppress("SpreadOperator")
fun pathSetOf(vararg paths: Path) = mutableListOf<Path>(*paths)

@Suppress("SpreadOperator")
fun mirroredPathSetOf(vararg paths: MirroredPath) = mutableListOf<MirroredPath>(*paths)
