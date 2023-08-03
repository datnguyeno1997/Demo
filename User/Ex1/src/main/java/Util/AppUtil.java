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
        // tạo object bằng contructor không tham số.
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
            // Use reflection to set the parameter value to the corresponding field in the User class
            try {
                // lấy value ra
                String paramValue = mapper.writeValueAsString(request.getParameter(paramName));
                Field field = clazz.getDeclaredField(paramName);
                field.setAccessible(true); // Set accessible, as the fields may be private
                Class<?> fieldType = field.getType();

                var value = mapper.readValue(paramValue,fieldType);
                field.set(object, value);
                //set cho tung field
                // Add more type conversions as needed for other field types (e.g., boolean, double, etc.)
            } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException e) {
                // Handle exceptions as needed
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

        }
        return object;
    }
    public static Object getObjectWithValidation(HttpServletRequest request, Class clazz, Map<String, RunnableCustom> validators) {
        // tạo object bằng contructor không tham số.
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
            // Use reflection to set the parameter value to the corresponding field in the User class
            try {
                // lấy value ra
                String paramValue = mapper.writeValueAsString(request.getParameter(paramName));
                RunnableCustom validator = validators.get(paramName);
                if(validator != null){
                    validator.setValue(request.getParameter(paramName));
                    validator.run();
                }
                Field field = clazz.getDeclaredField(paramName);
                field.setAccessible(true); // Set accessible, as the fields may be private
                Class<?> fieldType = field.getType();

                var value = mapper.readValue(paramValue,fieldType);
                field.set(object, value);
                //set cho tung field
                // Add more type conversions as needed for other field types (e.g., boolean, double, etc.)
            } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException e) {
                // Handle exceptions as needed
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

        }
        return object;
    }

}