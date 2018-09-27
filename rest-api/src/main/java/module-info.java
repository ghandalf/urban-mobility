module ca.ghandalf.urban.mobility.restapi {

    requires ca.ghandalf.urban.mobility.domain;
    requires ca.ghandalf.urban.mobility.handler;
    requires ca.ghandalf.urban.mobility.repository;
    requires ca.ghandalf.urban.mobility.service;

    requires spring.boot.starter;
    requires spring.boot.starter.web;
    requires spring.boot.starter.logging;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires spring.expression;
    requires spring.webmvc;
    requires spring.web;

    requires java.validation;
    requires java.annotation;
    
    requires slf4j.api;
    requires logback.classic;
    requires logback.core;
 
    opens ca.ghandalf.urban.mobility.restapi.configuration;
    opens ca.ghandalf.urban.mobility.restapi.controller;
    exports ca.ghandalf.urban.mobility.restapi.server;
    
//    requires org.apache.logging.slf4j;
    
//    requires spring.data.jpa;
//    requires spring.data.commons;
//    requires spring.orm;
//    requires spring.jdbc;
//    requires spring.aop;
//    requires spring.tx;
//    
//    requires org.aspectj.runtime;
//    requires log4j.api;
//    requires jul.to.slf4j;
//    

//    requires spring.jcl;
//    
//    requires swagger.annotations;
//    requires swagger.models;
//    requires jackson.annotations;
//    requires springfox.swagger.common;
//    requires guava;
//    requires com.fasterxml.classmate;    
//    requires spring.plugin.core;
//    requires spring.plugin.metadata;
//    requires org.mapstruct;
//    requires net.bytebuddy;
    

    
}
