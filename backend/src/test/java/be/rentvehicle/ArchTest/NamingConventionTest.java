package be.rentvehicle.ArchTest;

import be.rentvehicle.web.rest.impl.BaseRestController;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "be.rentvehicle")
public class NamingConventionTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("be.rentvehicle");

    @Test
    public void services_should_be_prefixed() {
        classes()
                .that().resideInAPackage("..service.impl..")
                .and()
                .areAnnotatedWith(Service.class)
                .should()
                .haveSimpleNameEndingWith("ServiceImpl")
                .check(importedClasses);
    }

    @Test
    public void controllers_should_be_suffixed() {

        classes()
                .that().resideInAPackage("..web.rest.impl..")
                .and()
                .areAssignableTo(BaseRestController.class)
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(importedClasses);
    }

    @Test
    public void repositories_should_be_suffixed() {
        classes()
                .that().resideInAPackage("..dao..")
                .should()
                .haveSimpleNameEndingWith("DAO")
                .check(importedClasses);
    }

    @Test
    public void dtos_should_be_suffixed() {
        classes()
                .that().resideInAPackage("..service.dto..")
                .should()
                .haveSimpleNameEndingWith("DTO")
                .check(importedClasses);
    }

    @Test
    public void all_public_methods_in_the_controller_layer_should_return_API_response_wrappers() {
        methods()
                .that()
                .areDeclaredInClassesThat()
                .resideInAPackage("..web.rest.impl..")
                .and().arePublic()
                .should().haveRawReturnType(ResponseEntity.class)
                .because("Controllers should always return response of type ResponseEntity")
                .check(importedClasses);
    }
}
