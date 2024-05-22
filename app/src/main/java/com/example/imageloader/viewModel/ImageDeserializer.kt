package com.example.imageloader.viewModel

import com.example.imageloader.modals.Images
import com.example.imageloader.modals.Urls
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ImageDeserializer : JsonDeserializer<Images> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Images {
        val jsonObject = json.asJsonObject
        return Images(
            id = jsonObject.get("id").asString,
            description = if (jsonObject.has("description") && !jsonObject.get("description").isJsonNull) jsonObject.get("description").asString else null,
            urls = Urls(
                regular = jsonObject.get("urls").asJsonObject.get("regular").asString,
                small = jsonObject.get("urls").asJsonObject.get("small").asString,
                thumb = jsonObject.get("urls").asJsonObject.get("thumb").asString
            )
        )
    }
}