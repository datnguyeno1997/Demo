package Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

public class AppUtil {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
    }
    public static Object getObject(HttpServletRequest request, Class clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            if(AppConstant.ACTION.equals(paramName)){
                continue;
            }
            System.out.println(request.getParameter(paramName));
            try {
                String paramValue = mapper.writeValueAsString(request.getParameter(paramName));
                Field field = clazz.getDeclaredField(paramName);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                var value = mapper.readValue(paramValue,fieldType);
                field.set(object, value);
            } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

        }
        return object;
    }
    public static Object getObjectWithValidation(HttpServletRequest request, Class clazz, Map<String, RunnableProduct> validators) {
        Object object;
        try {
            object = clazz.newInstance();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if(AppConstant.ACTION.equals(paramName)){
                continue;
            }
            System.out.println(request.getParameter(paramName));
            try {
                String paramValue = mapper.writeValueAsString(request.getParameter(paramName));
                RunnableProduct validator = validators.get(paramName);
                if(validator != null){
                    validator.setValue(request.getParameter(paramName));
                    validator.run();
                }
                Field field = clazz.getDeclaredField(paramName);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                var value = mapper.readValue(paramValue,fieldType);
                field.set(object, value);
            } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

        }
        return object;
    }
}

