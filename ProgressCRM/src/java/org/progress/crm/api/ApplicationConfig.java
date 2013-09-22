package org.progress.crm.api;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * re-generated by NetBeans REST support to populate given list with all
     * resources defined in the project.
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.progress.crm.api.ApartamentsAPI.class);
        resources.add(org.progress.crm.api.AuthApi.class);
        resources.add(org.progress.crm.api.CallsApi.class);
        resources.add(org.progress.crm.api.CustomersApi.class);
        resources.add(org.progress.crm.api.HelpDeskApi.class);
        resources.add(org.progress.crm.api.LiveSearchApi.class);
        resources.add(org.progress.crm.api.NewsApi.class);
        resources.add(org.progress.crm.api.ReportGeneratorAPI.class);
        resources.add(org.progress.crm.api.UploadFileServiceApi.class);
        resources.add(org.progress.crm.exceptions.CustomExceptionMapper.class);
    }
}
