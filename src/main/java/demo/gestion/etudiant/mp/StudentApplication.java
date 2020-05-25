
package demo.gestion.etudiant.mp;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.helidon.common.CollectionsHelper;


@ApplicationScoped
@ApplicationPath("/")
public class StudentApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return CollectionsHelper.setOf(StudentResource.class);
    }
}
