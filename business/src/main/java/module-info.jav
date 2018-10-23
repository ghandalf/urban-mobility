module ca.ghandalf.urban.mobility.service {
    requires transitive ca.ghandalf.urban.mobility.domain;
    requires transitive ca.ghandalf.urban.mobility.handler;
    requires transitive ca.ghandalf.urban.mobility.repository;
    
    requires spring.context;
    requires spring.beans;
    
    opens ca.ghandalf.urban.mobility.service;
    exports ca.ghandalf.urban.mobility.service;

}
