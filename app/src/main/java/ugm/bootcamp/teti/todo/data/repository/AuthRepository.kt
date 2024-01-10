package ugm.bootcamp.teti.todo.data.repository

interface AuthRepository {

    suspend fun isAuthenticated(): Boolean

    suspend fun register(
        email: String,
        password: String
    ): Boolean

    suspend fun login(
        email: String,
        password: String
    ): Boolean

    suspend fun logout()

    class Impl : AuthRepository {

        /**
         * describe dummy registered [users]
         */
        private val users = mutableMapOf(
            "jarjit@spam4.me" to "12345678",
            "ismail@spam4.me" to "12345678"
        )

        /**
         * describe [authenticatedUser] to maintain user login
         */
        private var authenticatedUser: String? = null

        override suspend fun isAuthenticated(): Boolean {
            return authenticatedUser.isNullOrBlank()
        }

        override suspend fun register(email: String, password: String): Boolean {
            users[email] = password
            return true
        }

        override suspend fun login(email: String, password: String): Boolean {
            return if (users.containsKey(email)) {
                val result = users[email] == password
                if (result) {
                    authenticatedUser = email
                }
                return result
            } else {
                false
            }
        }

        override suspend fun logout() {
            authenticatedUser = null
        }

    }
}