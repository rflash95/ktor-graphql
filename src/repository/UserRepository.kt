package com.example.repository

import com.example.models.User
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection


class UserRepository(client: MongoClient) : RepositoryInterface<User> {
    override lateinit var col: MongoCollection<User>

    init{
        val database = client.getDatabase("test")
        col = database.getCollection<User>("User")
    }
    //getUserByEmail
    fun getUserByEmail(email: String? = null): User?{
        return try {
            col.findOne(User::email eq email)
        } catch (t: Throwable) {
            throw Exception("Cannot get user with that email")
        }
    }


}