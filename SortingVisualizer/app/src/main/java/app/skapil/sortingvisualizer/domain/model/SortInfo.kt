package app.skapil.sortingvisualizer.domain.model

data class SortInfo(
    val currentItem:Int,
    val shouldSwap:Boolean,
    val hadNoEffect:Boolean
)
