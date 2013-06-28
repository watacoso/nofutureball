package nofutureball;

import java.awt.Window.Type;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

public class PropsBuilder {

	@SuppressWarnings("unused")
	private static Gson gsonBuilder = new GsonBuilder().registerTypeAdapter(
			Object.class, new NaturalDeserializer()).create();

	@SuppressWarnings("unchecked")
	public static LinkedTreeMap<String, ?> loadProp(String src) {

		JsonReader reader = null;
		try {
			reader = new JsonReader(new InputStreamReader(new FileInputStream(
					"assets/json/" + src)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();

		return (LinkedTreeMap<String, ?>) gson.fromJson(reader, Object.class);

	}
}

class HashDeserializer implements JsonDeserializer<Object> {

	public Object deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) {
		if (json.isJsonNull())
			return null;
		else if (json.isJsonPrimitive())
			return handlePrimitive(json.getAsJsonPrimitive());
		else
			return null;
	}

	private Object handlePrimitive(JsonPrimitive json) {
		if (json.isBoolean())
			return json.getAsBoolean();
		else if (json.isString())
			return json.getAsString();
		else {
			BigDecimal bigDec = json.getAsBigDecimal();
			// Find out if it is an int type
			try {
				bigDec.toBigIntegerExact();
				try {
					return bigDec.intValueExact();
				} catch (ArithmeticException e) {
				}
				return bigDec.longValue();
			} catch (ArithmeticException e) {
			}
			// Just return it as a double
			return bigDec.doubleValue();
		}
	}

	@Override
	public Object deserialize(JsonElement arg0, java.lang.reflect.Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}
}
