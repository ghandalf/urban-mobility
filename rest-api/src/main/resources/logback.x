<configuration scan="true">

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
  
<!--
  <logger name="org.spring" level="INFO"/>
  <logger name="org.hibernate" level="INFO"/>
  <logger name="ca.ghandalf.urban.mobility" level="DEBUG"/>
  -->

  <root level="info">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
