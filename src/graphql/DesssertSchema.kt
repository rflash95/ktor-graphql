package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.apurebase.kgraphql.schema.model.log
import com.example.models.Dessert
import com.example.models.DessertInput
import com.example.models.User
import com.example.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput> {
        description = "The input of the dessert without the identifier"
    }

    type<Dessert> {
        description = "Dessert object with attribute name, description and imageUrl"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("desserts") {
        resolver { page:Int?, size: Int? ->
            try {
                dessertService.getDessertsPage(page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }


    mutation("createDessert") {
        description = "Create a new dessert"
        resolver { ctx: Context, dessertInput: DessertInput ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.createDessert(dessertInput, userId)
            } catch (e: Exception) {
                log("createDessert : $e")
                null
            }

        }
    }

    //TODO : updateDessert and deleteDessert
    mutation("updateDessert") {
        description = "Update the dessert"
        resolver { ctx: Context, dessertId: String, dessertInput: DessertInput ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.updateDessert(userId, dessertId, dessertInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        description = "Delete the dessert"
        resolver { ctx: Context,dessertId: String ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.deleteDessert(userId, dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

}