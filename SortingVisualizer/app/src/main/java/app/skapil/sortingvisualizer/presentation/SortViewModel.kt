package app.skapil.sortingvisualizer.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.skapil.sortingvisualizer.domain.use_case.BubbleSortUseCase
import app.skapil.sortingvisualizer.presentation.state.ListUiItem
import kotlinx.coroutines.launch
import java.util.Random

class SortViewModel(
    private val bubbleSortUseCase: BubbleSortUseCase = BubbleSortUseCase()
): ViewModel(){

    var listToSort = mutableStateListOf<ListUiItem>()

    init {
        for(i in 0 until 9){
            val rnd = Random()
            listToSort.add(
                ListUiItem(
                id = i,
                isCurrentlyCompared = false,
                value = rnd.nextInt(150),
                color = Color(
                    255,
                    rnd.nextInt(256),
                    rnd.nextInt(256),
                    255)
                )
            )
        }
    }

    fun startSorting(){
        viewModelScope.launch {
            bubbleSortUseCase(listToSort.map { listUiItem ->
                listUiItem.value
            }.toMutableList()).collect{ swapInfo ->
                val currentItemIndex = swapInfo.currentItem
                listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = true)
                listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = true)

                if(swapInfo.shouldSwap){
                    val firstItem = listToSort[currentItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex+1] = firstItem
                }
                if(swapInfo.hadNoEffect){
                    listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = false)
                }
            }
        }
    }


}