package misc;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import entities.InvisibleJson;

public class HiddenAnnotationExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> c) {
    	//return c.getAnnotation(InvisibleJson.class) != null;
    	return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes c) {
        return c.getAnnotation(InvisibleJson.class) != null;
    }
}