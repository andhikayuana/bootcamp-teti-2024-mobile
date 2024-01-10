package ugm.bootcamp.teti.todo.util

import androidx.navigation.NavDirections

sealed class UiEffect {
    object PopBackStack : UiEffect()
    data class Navigate(val directions: NavDirections) : UiEffect()
    data class NavigateActivity(val activityClassName: String) : UiEffect()
    data class ShowToast(val message: String) : UiEffect()
}