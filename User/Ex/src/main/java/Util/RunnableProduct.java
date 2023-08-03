package Util;

import java.util.Map;

public abstract class RunnableProduct implements Runnable {
    protected String  fieldName;
    protected String value ;
    protected String message;
    protected Map<String, String> errors;

    public void setValue(String value) {
        this.value = value;
    }
}
