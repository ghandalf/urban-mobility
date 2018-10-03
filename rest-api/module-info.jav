module ca.ghandalf.urban.mobility.restapi {

    requires ca.ghandalf.urban.mobility.domain;
    requires ca.ghandalf.urban.mobility.handler;
    requires ca.ghandalf.urban.mobility.repository;
    requires ca.ghandalf.urban.mobility.service;

    requires spring.boot.starter.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.webmvc;

    requires java.validation;
    requires java.annotation;
    
    requires slf4j.api;
    requires logback.classic;
    requires logback.core;
 
    opens ca.ghandalf.urban.mobility.restapi.configuration;
    opens ca.ghandalf.urban.mobility.restapi.controller;
    exports ca.ghandalf.urban.mobility.restapi.server;
    
}
