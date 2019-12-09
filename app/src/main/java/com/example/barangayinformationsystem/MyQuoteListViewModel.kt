package com.example.barangayinformationsystem

import androidx.lifecycle.ViewModel

class MyQuoteListViewModel: ViewModel() {
    internal val selectItemEvent = SingleLiveEvent<Quote?>()
}