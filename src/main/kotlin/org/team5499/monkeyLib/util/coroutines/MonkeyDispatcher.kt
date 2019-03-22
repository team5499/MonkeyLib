package org.team5499.monkeyLib.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

val MonkeylibDispatcher: CoroutineDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
