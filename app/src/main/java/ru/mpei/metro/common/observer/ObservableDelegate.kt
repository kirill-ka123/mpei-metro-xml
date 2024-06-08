package ru.mpei.metro.common.observer

import javax.inject.Inject

class ObservableDelegate<Observer> @Inject constructor() : Observable<Observer> {
    private val observers = mutableListOf<Observer>()

    /**
     * @return `true` if there are no observers.
     */
    val isEmpty: Boolean get() = observers.isEmpty()

    override fun addObserver(observer: Observer) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer) {
        if (observers.contains(observer)) {
            observers.remove(observer)
        }
    }

    /**
     * Notify each observer.
     */
    fun notify(block: Observer.() -> Unit) {
        observers.forEach(block)
    }
}
