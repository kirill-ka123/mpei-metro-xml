package ru.mpei.metro.presentation.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module

@Module
interface ActivityModule {
    @Binds
    fun bindActivity(i: AppCompatActivity): Activity
}
