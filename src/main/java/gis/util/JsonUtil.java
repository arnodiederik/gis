package nl.technolution.wvp.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;


/**
 * Helper class for formatting JSON objects.
 */
// SUPPRESS CHECKSTYLE DataAbstractionCoupling - no problem here
public final class JsonUtil {

    private JsonUtil() {
        // Utility class
    }

    /**
     * Formats a JSON object as a pretty string.
     */
    public static String prettyPrint(JsonObject obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }

    /**
     * Convert an object of type T to a json string
     */
    public static <T> String objectToJson(T object) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>() {
            @Override
            public JsonElement serialize(DateTime json, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(ISODateTimeFormat.dateTime().print(json));
            }
        }).create();
        return gson.toJson(object);
    }

    /**
     * Convert a list of objects of type T to a list of json strings. A null object results in an empty list
     */
    public static <T> List<String> objectsToJsons(List<T> objects) {
        if (objects == null) {
            return new ArrayList<String>();
        }

        List<String> strings = new ArrayList<>();
        for (Object object : objects) {
            strings.add(objectToJson(object));
        }

        return strings;
    }

    /**
     * Convert a json string to a object of class type T. An empty or null string results in null
     */
    public static <T> T jsonToObject(Class<T> type, String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>() {
            @Override
            public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                    throws JsonParseException {
                return new DateTime(json.getAsString());
            }
        }).create();
        return gson.fromJson(string, type);
    }

    /**
     * Read a json object of class type type from the file filename
     *
     * @param type The class type
     * @param filename The filename were the json object has to be read from
     */
    public static <T> T jsonFileToObject(Class<T> type, String filename) {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            T object = gson.fromJson(reader, type);
            reader.close();
            return object;
        } catch (FileNotFoundException e) {
            LOG.error(String.format("The JSON file %s was not found. ", filename) + e);
            return null;
        } catch (IOException e) {
            LOG.error(String.format("IOException while reading JSON file %s. ", filename) + e);
            return null;
        }
    }

    /**
     * Write the object T as json object to the file with the name filename
     *
     * @param object The object to be written to file
     * @param filename The name of the file
     * @return true on success, false when an error occurred
     */
    public static <T> boolean objectToJsonFile(T object, String filename) {
        Gson gson = new Gson();

        try {
            JsonWriter writer = new JsonWriter(new FileWriter(filename));
            gson.toJson(object, object.getClass(), writer);
            writer.close();
            return true;
        } catch (IOException e) {
            LOG.error(String.format("The JSON file %s was not found. ", filename) + e);
            return false;
        }
    }

    /**
     * Convert a list of strings to a list of objects of the type T. An empty or null list results in an empty list
     */
    public static <T> List<T> jsonsToObjects(Class<T> type, List<String> strings) {
        if (strings == null) {
            return new ArrayList<T>();
        }

        List<T> objects = new ArrayList<>();
        for (String string : strings) {
            objects.add(jsonToObject(type, string));
        }

        return objects;
    }
}
