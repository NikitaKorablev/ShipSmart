package com.shipsmart.data.dbLogic

import com.shipsmart.domain.repository.DBdao
import com.shipsmart.domain.model.SupabaseUser
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SupabaseDAO : DBdao {
    private var supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://cquvqmwqlyqlntovmele.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNxdXZxbXdxbHlxbG50b3ZtZWxlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTYwMjEyMjksImV4cCI6MjAzMTU5NzIyOX0.DQ-YAQZjXUj6o88VLI2zpg3pLMj4NtDhhAMs_RNvHEw"
    ) {
        install(Postgrest)
    }

    // suspend - асинхронно выполняемая функция
    override suspend fun getUser(email: String): SupabaseUser? = withContext(Dispatchers.IO) {
        val user = supabase.from(table = "Users")
            .select(columns = Columns.list("email", "password")) {
            filter {
                SupabaseUser::email eq email
            }
        }.decodeSingleOrNull<SupabaseUser>()
        return@withContext user
    }

    override suspend fun addUser(user: SupabaseUser) : SupabaseUser? {
        if (getUser(user.email) == null) {
            return supabase.from("Users").insert(user) {
                select()
            }.decodeSingleOrNull<SupabaseUser>()
        }
        else throw IOException("This user already exists")
    }
}