package mate.academy.bookstore.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import mate.academy.bookstore.annotation.FieldMatch;

public class FieldMatchValidator
        implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch annotation) {
        firstFieldName = annotation.first();
        secondFieldName = annotation.second();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }
        try {
            Object firstValue = getRecordOrClassValue(object, firstFieldName);
            Object secondValue = getRecordOrClassValue(object, secondFieldName);
            return firstValue == null && secondValue == null
                    || firstValue != null && firstValue.equals(secondValue);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getRecordOrClassValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        if (clazz.isRecord()) {
            Method method = clazz.getMethod(fieldName);
            return method.invoke(object);
        }
        java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
