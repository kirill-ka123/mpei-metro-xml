package ru.mpei.metro.common.observer

interface Observable<Observer> {
    fun addObserver(observer: Observer)
    fun removeObserver(observer: Observer)
}
