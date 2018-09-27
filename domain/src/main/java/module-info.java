module ca.ghandalf.urban.mobility.domain {
    // Ref in pom as groupdId: javax.persistence, artifactId: javax.persistence-api
    // If we use 
    // jar --describe-module --file=/home/ghandalf/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar
    //      java.persistence@2.2 automatic
    requires java.persistence;
    
    exports ca.ghandalf.urban.mobility.domain;
    exports ca.ghandalf.urban.mobility.domain.ng;
}
