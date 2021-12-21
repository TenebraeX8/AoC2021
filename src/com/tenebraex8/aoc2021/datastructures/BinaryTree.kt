package com.tenebraex8.aoc2021.datastructures

class BinaryTree<T>(val value: T, var left: BinaryTree<T>? = null, var right: BinaryTree<T>? = null) {
    var parent: BinaryTree<T>? = null

    fun isLeaf() = left == null && right == null
    fun isRoot() = parent == null


    fun leftBranch(leftTree: BinaryTree<T>){
        this.left = leftTree
        leftTree.parent = this
    }

    fun rightBranch(rightTree: BinaryTree<T>){
        this.right = rightTree
        rightTree.parent = this
    }

    fun children(leftTree: BinaryTree<T>, rightTree: BinaryTree<T>){
        this.leftBranch(leftTree)
        this.rightBranch(rightTree)
    }

    fun applyInOrder(procedure: (T)->Unit){
        if(this.left != null) this.left!!.applyInOrder(procedure)
        procedure.invoke(this.value)
        if(this.right != null) this.right!!.applyInOrder(procedure)
    }

    fun applyPreOrder(procedure: (T)->Unit){
        procedure.invoke(this.value)
        if(this.left != null) this.left!!.applyInOrder(procedure)
        if(this.right != null) this.right!!.applyInOrder(procedure)
    }

    fun applyPostOrder(procedure: (T)->Unit){
        if(this.left != null) this.left!!.applyInOrder(procedure)
        if(this.right != null) this.right!!.applyInOrder(procedure)
        procedure.invoke(this.value)
    }

    fun serialize(): List<T>{
        val elements = mutableListOf<T>()
        this.applyInOrder { elements.add(it) }
        return elements
    }
}