package com.core.data.storage

import android.util.Log
import com.core.domain.UserStorageInterface
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.io.IOException

class SupabaseUserStorage(
    key: String, url: String
): UserStorageInterface {
    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = url,
        supabaseKey = key
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