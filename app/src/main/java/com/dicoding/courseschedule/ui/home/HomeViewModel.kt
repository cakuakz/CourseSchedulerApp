package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType
import com.dicoding.courseschedule.util.SortType

class HomeViewModel( repository: DataRepository ): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    val nearestSchedule = Transformations.switchMap(_queryType) {
        repository.getNearestSchedule(it)
    }

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

}
