module ca.ghandalf.urban.mobility.repository {
    requires transitive ca.ghandalf.urban.mobility.domain;
    requires transitive ca.ghandalf.urban.mobility.handler;
    
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires java.persistence;
    requires postgresql;
    
    // Not in pom due to Spring-boot bom but need to be here
    requires org.hibernate.orm.core;
     
    opens ca.ghandalf.urban.mobility.repository;
    exports ca.ghandalf.urban.mobility.repository;
    
}
