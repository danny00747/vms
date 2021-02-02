package be.rentvehicle.ArchTest;

import javax.persistence.Entity;
import java.sql.SQLException;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

public class DaoRulesTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("be.rentvehicle");

    @Test
    public void DAOs_must_reside_in_a_dao_package() {
        classes()
                .that()
                .haveNameMatching(".*DAO")
                .should().resideInAPackage("..dao..")
                .as("DAOs should reside in a package '..dao..'")
                .check(importedClasses);
    }

    @Test
    public void entities_must_reside_in_a_domain_package() {
        classes()
                .that()
                .areAnnotatedWith(Entity.class)
                .should().resideInAPackage("..domain..")
                .as("Entities should reside in a package '..domain..'")
                .check(importedClasses);
    }

    @Test
    public void DAOs_must_not_throw_SQLException() {
        noMethods()
                .that()
                .areDeclaredInClassesThat()
                .haveNameMatching(".*DAO")
                .should()
                .declareThrowableOfType(SQLException.class)
                .check(importedClasses);
    }
}
