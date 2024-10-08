package com.shipsmart.data.storage

import com.shipsmart.data.storage.model.SupabaseUser
import com.shipsmart.domain.model.RegistrationParams
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.io.IOException

class SupabaseUserStorage: UserStorageInterface {
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
                    RegistrationParams::email eq email
                }
            }.decodeSingleOrNull<SupabaseUser>()
        return supabaseUser
    }

    override suspend fun addUser(user: SupabaseUser): Boolean {
        if (getUser(user.email) == null) {
            val res = supabase.from("Users").insert(user) {
                select()
            }.decodeSingleOrNull<SupabaseUser>()

            return res != null
        }
        else throw IOException("This user already exists")
    }


}