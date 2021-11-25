package com.example.repository

import com.example.models.Dessert
import com.example.models.DessertsPage
import com.example.models.PagingInfo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class DessertRepository(client: MongoClient) : RepositoryInterface<Dessert> {
    override lateinit var col: MongoCollection<Dessert>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Dessert>("Dessert")
    }

    fun getDessertPage(page:Int,size: Int): DessertsPage{
        try {
            //val skip = (2*5) =  10
            //val res = start from index 10
            //val totalDesserts = 100
            //val totalPage = 100/5 = 20
            //next = 2 +1 =3
            //prev = 2 -1 = 1
            val skips = page * size
            val res = col.find().skip(skips).limit(size)
            val results = res.asIterable().map { it }
            // count, next, prev, limit
            val totalDesserts = col.estimatedDocumentCount()
            val totalPage = (totalDesserts / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPage.toInt(), next, prev)
            return DessertsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get Dessert page")
        }

    }

    fun getDessertsByUserId(userId: String): List<Dessert> {
        return try {
            col.find(Dessert::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user desserts")
        }
    }


    /*override fun getById(id: String): Dessert {
        return try {
            desserts.find { it.id == id } ?: throw  Exception("No dessert with that ID exists")
        } catch (e: Exception) {
            throw Exception("Cannot find desserts")
        }
    }

    override fun getAll(): List<Dessert> {
        return desserts
    }

    override fun delete(id: String): Boolean {
        return try {
            val dessert = desserts.find { it.id == id } ?: throw Exception("No dessert with that ID exists")
            desserts.remove(dessert)
            return true
        } catch (e: Exception) {
            throw Exception("Cannot find dessert")
        }
    }

    override fun add(entry: Dessert): Dessert {
        desserts.add(entry)
        return entry
    }

    override fun update(entry: Dessert): Dessert {
        return try {
            val dessert = desserts.find { it.id == entry.id }?.apply {
                name = entry.name
                description = entry.description
                imageUrl = entry.imageUrl
            } ?: throw Exception("No dessert with that ID exists")
            dessert
        } catch (e: Exception) {
            throw Exception("Cannot find dessert")
        }
    }*/
}