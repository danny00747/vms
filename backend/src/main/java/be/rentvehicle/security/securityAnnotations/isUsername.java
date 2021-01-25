package be.rentvehicle.security.securityAnnotations;

import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method level annotion for validating a username across the whole application.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 4, max = 25)
public @interface isUsername {
}
