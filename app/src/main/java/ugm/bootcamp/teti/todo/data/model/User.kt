package ugm.bootcamp.teti.todo.data.model

data class User(
    val email: String = "Guest"
) {
    fun getName(): String {
        return email.split("@").first()
    }

    fun getImageUrl(): String {
        return "https://api.dicebear.com/7.x/pixel-art/png?seed=${getName()}"
    }

}
