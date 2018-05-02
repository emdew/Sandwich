package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private JsonUtils() {
    }

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {

            JSONObject baseJsonResponse = new JSONObject(json);

            String name = baseJsonResponse.getString("mainName");

            JSONArray akaArray = baseJsonResponse.getJSONArray("alsoKnownAs");
            List<String> aka = new ArrayList<String>();
            for (int j = 0; j < akaArray.length(); j++) {
                String otherNames = akaArray.getString(j);
                aka.add(otherNames);
            }

            String origin = baseJsonResponse.getString("placeOfOrigin");

            String description = baseJsonResponse.getString("description");

            String url = baseJsonResponse.getString("image");

            JSONArray ingredientsArray = baseJsonResponse.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for (int x = 0; x < ingredientsArray.length(); x++) {
                String diffIngredients = ingredientsArray.getString(x);
                ingredients.add(diffIngredients);
            }

            sandwich = new Sandwich(name, aka, origin, description, url, ingredients);

        } catch (JSONException e) {
            Log.e("JsonUtils", "problem parsing the json");
        }

        return sandwich;
    }
}
