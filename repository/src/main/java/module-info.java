module ca.ghandalf.urban.mobility.repository {
    requires ca.ghandalf.urban.mobility.domain;
    requires ca.ghandalf.urban.mobility.handler;
    
    // Ref in pom as groupdId: javax.persistence, artifactId: javax.persistence-api
    // If we use jar --describe-module --file=/home/ghandalf/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar
    //      java.persistence@2.2 automatic
//    requires java.persistence;
//    requires ucp;
    requires spring.data.jpa;
    requires spring.context;
    requires postgresql;
    
    exports ca.ghandalf.urban.mobility.repository;
    exports ca.ghandalf.urban.mobility.repository.ng;
}
