package com.example.repository

import com.example.models.Model
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

interface RepositoryInterface<T> {
    val col: MongoCollection<T>

    fun getById(id: String): T {
        return try {
            col.findOne(Model::id eq id) ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot find item")
        }
    }

    fun getAll(): List<T> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all items")
        }
    }

    fun delete(id: String): Boolean {
        return try {
            col.findOneAndDelete(Model::id eq id) ?: throw Exception("Not item with that ID exists")
            true
        } catch (t: Throwable) {
            throw Exception("Cannot delete item")
        }
    }

    fun add(entry: T): T{
        return try {
            col.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add item : $t")
        }
    }
    fun update(entry: Model): T {
        return try {
            col.updateOne(Model::id eq entry.id, entry)
            col.findOne(Model::id eq entry.id) ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot update item")
        }
    }
}