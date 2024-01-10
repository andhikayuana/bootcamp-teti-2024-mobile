package ugm.bootcamp.teti.todo

import android.app.Application
import ugm.bootcamp.teti.todo.di.AppModule

class TodoApp : Application() {

    val appModule = AppModule

    override fun onCreate() {
        super.onCreate()
    }
}