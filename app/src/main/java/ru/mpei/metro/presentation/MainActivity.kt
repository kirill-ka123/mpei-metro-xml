package ru.mpei.metro.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.mpei.metro.MetroApplication
import ru.mpei.metro.R
import ru.mpei.metro.presentation.di.ActivityComponent
import ru.mpei.metro.presentation.di.DaggerActivityComponent

class MainActivity : AppCompatActivity() {
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        val applicationComponent = (applicationContext as MetroApplication).applicationComponent
        activityComponent = DaggerActivityComponent.builder()
            .applicationComponent(applicationComponent)
            .activity(this)
            .build()

        setContentView(R.layout.activity_main)
    }
}
