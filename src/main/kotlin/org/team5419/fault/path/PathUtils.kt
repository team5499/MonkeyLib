package org.team5419.fault.path

typealias PathSet = MutableList<Path>

typealias MirroredPathSet = MutableList<MirroredPath>

@Suppress("FunctionNaming")
public fun PathSet() = pathSetOf()

@Suppress("FunctionNaming")
public fun MirroredPathSet() = mirroredPathSetOf()

@Suppress("SpreadOperator")
fun pathSetOf(vararg paths: Path) = mutableListOf<Path>(*paths)

@Suppress("SpreadOperator")
fun mirroredPathSetOf(vararg paths: MirroredPath) = mutableListOf<MirroredPath>(*paths)
