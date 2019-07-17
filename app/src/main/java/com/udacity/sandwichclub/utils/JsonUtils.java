package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwichObject = new JSONObject(json);

            JSONObject jsonSandwichNameObject = jsonSandwichObject.getJSONObject("name");
            String mainSandwichName = jsonSandwichNameObject.getString("mainName");
            List<String> alsoKnownAsSandwichName = toList(jsonSandwichNameObject.getJSONArray("alsoKnownAs"));

            String sandwichPlaceOfOrigin = jsonSandwichObject.getString("placeOfOrigin");
            String sandwichDescription = jsonSandwichObject.getString("description");
            String sandwichImage = jsonSandwichObject.getString("image");
            List<String> sandwichIngredients = toList(jsonSandwichObject.getJSONArray("ingredients"));

            return new Sandwich(mainSandwichName, alsoKnownAsSandwichName, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage, sandwichIngredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> toList(JSONArray currentJSONArray) {
        List<String> currentList = new ArrayList<>();
        for (int i = 0; i < currentJSONArray.length(); i++) {
            try {
                currentList.add(currentJSONArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return currentList;
    }
}