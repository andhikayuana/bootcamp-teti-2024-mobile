package ugm.bootcamp.teti.todo

import android.app.Application
import com.google.firebase.FirebaseApp
import ugm.bootcamp.teti.todo.di.AppModule

class TodoApp : Application() {

    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule
    }
}