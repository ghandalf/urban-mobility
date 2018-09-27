module ca.ghandalf.urban.mobility.service {
    requires ca.ghandalf.urban.mobility.domain;
    requires ca.ghandalf.urban.mobility.handler;
    requires ca.ghandalf.urban.mobility.repository;
    
    requires spring.context;
    requires spring.beans;
    
    exports ca.ghandalf.urban.mobility.service.montreal;
    exports ca.ghandalf.urban.mobility.service.ng;
    exports ca.ghandalf.urban.mobility.service.ng.exception;
}
