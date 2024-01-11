package ugm.bootcamp.teti.todo.ui.splash

sealed class SplashEvent {

    object OnLoad : SplashEvent()
    object OnGotoLogin : SplashEvent()
    object OnGotoTodoList : SplashEvent()
}