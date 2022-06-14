package com.example.myprojectapp.extentions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    itemsToLoad: Int,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(PagingScrollListener(layoutManager, itemsToLoad, onLoadMore))
}

private class PagingScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val itemsToLoad: Int,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (dy != 0 && totalItemCount <= (lastVisibleItem + itemsToLoad)) {
            recyclerView.post(onLoadMore)
        }
    }
}