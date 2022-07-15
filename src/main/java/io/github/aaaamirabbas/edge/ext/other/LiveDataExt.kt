package io.github.aaaamirabbas.edge.ext.other

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.toMutable(): LiveData<T> = this
fun <T> LiveData<T>.toImmutable(): LiveData<T> = this