package com.core.data.storage

import android.util.Log
import com.core.data.RegistrationParams
import com.core.data.SupabaseUser
import com.core.domain.UserStorageInterface
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.io.IOException

class SupabaseUserStorage(
//    val key: String,
//    val url: String
): UserStorageInterface {
    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://cquvqmwqlyqlntovmele.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNxdXZxbXdxbHlxbG50b3ZtZWxlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTYwMjEyMjksImV4cCI6MjAzMTU5NzIyOX0.DQ-YAQZjXUj6o88VLI2zpg3pLMj4NtDhhAMs_RNvHEw"
    ) {
        install(Postgrest)
    }

    override suspend fun getUser(email: String): SupabaseUser? {
        val supabaseUser = supabase.from(table = "Users")
            .select(columns = Columns.list("email", "password")) {
                filter {
                    SupabaseUser::email eq email
                }
            }
        val test = supabaseUser.decodeSingleOrNull<SupabaseUser>()
        return test
    }

    override suspend fun addUser(user: SupabaseUser): Boolean {
        val dbUser: SupabaseUser?

        try {
            dbUser = getUser(user.email)
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())

            return false
        }

        if (dbUser == null) {
            return try {
                val res = supabase.from("Users").insert(user) {
                    select()
                }.decodeSingleOrNull<SupabaseUser>()

                res != null
            } catch (error: Exception) {
                Log.e(TAG, error.message.toString())

                false
            }
        }
        else throw IOException("This user already exists")
    }

    companion object {
        const val TAG = "SupabaseUserStorage"
    }
}