package com.nhlstendent.productmanagement.util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhlstendent.productmanagement.model.Product;

import java.io.FileReader;
import java.util.List;

public class JsonUtil
{
    private static final Gson gson = new Gson();

    public static List<Product> parseProduct(String filePath)
    {
        try (FileReader reader = new FileReader(filePath))
        {
            return gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
