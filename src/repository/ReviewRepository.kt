package com.example.repository

import com.example.models.Review
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ReviewRepository(client: MongoClient) : RepositoryInterface<Review> {
    override lateinit var col: MongoCollection<Review>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Review>("Review")
    }
    // getReviewsByDessertId: List<Review>
    fun getReviewsByDessertId(dessertId: String): List<Review> {
        return try {
            val res = col.find(Review::dessertId eq dessertId)
                ?: throw Exception("No Review with that dessert ID exists")
          res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }

}