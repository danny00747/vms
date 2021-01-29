package be.rentvehicle.security.securityAnnotations;

import be.rentvehicle.security.RolesConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks admin privileges across the whole application.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
public @interface isAdmin {
}
