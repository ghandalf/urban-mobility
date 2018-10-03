module ca.ghandalf.urban.mobility.handler {
  requires ca.ghandalf.urban.mobility.domain;

  requires spring.context;
  
  opens ca.ghandalf.urban.mobility.handler;
  exports ca.ghandalf.urban.mobility.handler;
}