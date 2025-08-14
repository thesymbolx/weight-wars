package uk.co.weightwars

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeightWarsApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Enable Firebase Database persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        
        // Optional: Configure cache size (default is 100MB)
        // FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)
    }
}
