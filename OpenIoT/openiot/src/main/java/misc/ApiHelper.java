package misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiHelper {

	public static Gson getGson() {
	    return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setExclusionStrategies(new HiddenAnnotationExclusionStrategy())
                .create();
	}
}
