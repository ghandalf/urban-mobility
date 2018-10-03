module ca.ghandalf.urban.mobility.restapi {
    requires transitive ca.ghandalf.urban.mobility.domain;
    requires transitive ca.ghandalf.urban.mobility.handler;
    requires transitive ca.ghandalf.urban.mobility.repository;
    requires transitive ca.ghandalf.urban.mobility.service;
    
    // From domain
    requires java.persistence;
    requires com.fasterxml.jackson.annotation;
    
    // From handler
    
    // From repository
    requires spring.data.jpa;
    requires spring.context;
    requires postgresql;

    // From service
    requires spring.beans;

    // Current application
    requires spring.boot;
    requires slf4j.api;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires java.validation;
    requires spring.tx;
    
    opens ca.ghandalf.urban.mobility.restapi;
    exports ca.ghandalf.urban.mobility.restapi;

}
