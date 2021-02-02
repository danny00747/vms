package be.rentvehicle.ArchTest;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@AnalyzeClasses(packages = "be.rentvehicle")
public class LayerDependencyRulesTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("be.rentvehicle");

    @Test
    @DisplayName("services and repositories should not depend on web layer")
    void services_and_repositories_should_not_depend_on_web_layer() {

        noClasses()
                .that()
                .resideInAnyPackage("be.rentvehicle.service..")
                .or()
                .resideInAnyPackage("be.rentvehicle.dao..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..be.rentvehicle.web..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }

    @Test
    @DisplayName("persistence should not access services")
    public void persistence_should_not_access_services() {
        noClasses()
                .that()
                .resideInAPackage("..dao..")
                .should().accessClassesThat()
                .resideInAPackage("..service..")
                .because("repositories should not depend on service layer")
                .check(importedClasses);
    }

    @Test
    @DisplayName("domain should not depend on services")
    public void domain_should_not_depend_on_services() {
        noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("..service..")
                .because("domain should not depend on service layer")
                .check(importedClasses);
    }

}
