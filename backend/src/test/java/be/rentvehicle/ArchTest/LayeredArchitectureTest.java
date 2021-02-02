package be.rentvehicle.ArchTest;

import be.rentvehicle.security.DomainUserDetailsService;
import be.rentvehicle.dao.UserDAO;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("be.rentvehicle");

    @Test
    public void layer_dependencies_are_respected() {

        layeredArchitecture()
                .layer("Controllers").definedBy("be.rentvehicle.web..")
                .layer("Services").definedBy("be.rentvehicle.service..")
                .layer("Persistence").definedBy("be.rentvehicle.dao..")

                .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
                .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services")
                .ignoreDependency(DomainUserDetailsService.class, UserDAO.class)
                .check(importedClasses);
    }

    @Test
    public void controllers_should_not_have_Gui_in_name() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Gui")
                .check(importedClasses);
    }

}
