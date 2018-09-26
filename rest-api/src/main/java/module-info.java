module ca.ghandalf.urban.mobility.restapi {

    requires ca.ghandalf.urban.mobility.domain;
    requires ca.ghandalf.urban.mobility.handler;
    requires ca.ghandalf.urban.mobility.repository;
    requires ca.ghandalf.urban.mobility.service;

    requires spring.boot.starter.web;
    requires spring.context;
    requires spring.webmvc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.beans;
 
    opens ca.ghandalf.urban.mobility.restapi.configuration;
    opens ca.ghandalf.urban.mobility.restapi.controller;
    exports ca.ghandalf.urban.mobility.restapi.server;
    
//    requires spring.boot.starter;
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//    requires spring.boot.starter.logging;
//    requires logback.classic;
//    requires logback.core;
//    requires org.apache.logging.slf4j;
    
//    requires spring.data.jpa;
//    requires spring.data.commons;
//    requires spring.orm;
//    requires spring.jdbc;
//    requires spring.aop;
//    requires spring.tx;
//    
//    requires org.aspectj.runtime;
//    requires spring.context;
//    requires spring.expression;
    
//    requires log4j.api;
//    requires jul.to.slf4j;
//    requires java.annotation;
//    requires spring.core;
//    requires spring.jcl;
//    
//    requires swagger.annotations;
//    requires swagger.models;
//    requires jackson.annotations;
//    requires springfox.swagger.common;
//    requires guava;
//    requires com.fasterxml.classmate;
//    requires slf4j.api;
//    requires spring.plugin.core;
//    requires spring.plugin.metadata;
//    requires org.mapstruct;
//    requires net.bytebuddy;
    

    
}
