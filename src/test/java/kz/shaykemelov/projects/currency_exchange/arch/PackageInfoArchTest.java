package kz.shaykemelov.projects.currency_exchange.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.all;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import kz.shaykemelov.projects.currency_exchange.common.AbstractTestCase;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.*;

@AnalyzeClasses(packages = "kz.shaykemelov.projects")
public class PackageInfoArchTest extends AbstractTestCase
{
    @ArchTest
    public static final ArchRule ALL_PACKAGES_MUST_CONTAIN_PACKAGE_INFO = all(packages()).should(containsPackageInfo());

    @ArchTest
    public static final ArchRule ALL_PACKAGE_INFOS_MUST_BE_ANNOTATED_WITH_NON_NULL_API = classes()
            .that()
            .haveSimpleName("package-info")
            .should()
            .beAnnotatedWith(org.springframework.lang.NonNullApi.class);

    private static ClassesTransformer<JavaPackage> packages()
    {
        return new AbstractClassesTransformer<>("packages")
        {
            @Override
            public Iterable<JavaPackage> doTransform(final JavaClasses classes)
            {

                return classes.getPackage("kz.shaykemelov").getSubpackagesInTree();
            }
        };
    }

    private static ArchCondition<JavaPackage> containsPackageInfo()
    {

        return new ArchCondition<>("contain package-info")
        {

            @Override
            public void check(JavaPackage javaPackage, ConditionEvents events)
            {

                if (!javaPackage.getClasses().isEmpty()
                        && !javaPackage.containsClassWithSimpleName("package-info"))
                {

                    final var message = "Package '%s' does not contain a package-info".formatted(
                            javaPackage.getName()
                    );

                    events.add(SimpleConditionEvent.violated(javaPackage, message));
                }
            }
        };
    }
}
