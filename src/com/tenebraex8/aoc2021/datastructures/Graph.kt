package com.tenebraex8.aoc2021.datastructures

class Graph<T>(val value: T, private var ancestor: Graph<T>? = null) {
    private val children: MutableList<Graph<T>> = mutableListOf()

    fun add(element: Graph<T>){
        this.children.add(element)
        element.ancestor = this
    }

    operator fun plusAssign(other: Graph<T>) = add(other)
}