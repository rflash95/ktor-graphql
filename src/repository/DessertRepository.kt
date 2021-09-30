package com.example.repository

import com.example.data.desserts
import com.example.models.Dessert

class DessertRepository : RepositoryInterface<Dessert> {
    override fun getById(id: String): Dessert {
        return try {
            desserts.find { it.id == id } ?: throw Exception("No dessert with that ID exists")
        } catch (e: Exception) {
            throw Exception("Cannot find desserts")
        }
    }

    override fun getAll(): List<Dessert> {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(entry: Dessert): Dessert {
        TODO("Not yet implemented")
    }

    override fun update(entry: Dessert): Dessert {
        TODO("Not yet implemented")
    }
}