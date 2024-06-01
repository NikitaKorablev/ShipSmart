package com.shipsmart.dbLogic

import androidx.room.Dao
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseDAO {
    private var supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://cquvqmwqlyqlntovmele.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNxdXZxbXdxbHlxbG50b3ZtZWxlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTYwMjEyMjksImV4cCI6MjAzMTU5NzIyOX0.DQ-YAQZjXUj6o88VLI2zpg3pLMj4NtDhhAMs_RNvHEw"
    ) {
        install(Postgrest)
    }

    // suspend - асинхронно выполняемая функция
    suspend fun getUser(email: String): SupabaseUser? = withContext(Dispatchers.IO) {
        val user = supabase.from(table = "Users")
            .select(columns = Columns.list("email", "password")) {
            filter {
                SupabaseUser::email eq email
            }
        }.decodeSingleOrNull<SupabaseUser>()
        return@withContext user
    }

    suspend fun newUser(user: SupabaseUser) {
        if (getUser(user.email) == null) supabase.from("Users").insert(user)
        else throw IOException("This user already exists")
    }
}