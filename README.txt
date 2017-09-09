WebAppDemo

Developer: Olegk Sotnikov (alex28_501@hotmail.com)

1. Introduction

This project begun as my university thesis.
Its aim is to provide simple examples for the most important Java EE Specifications.

It provides examples of:

i.    Servlets & JSP
ii.   Security (Confidentiality and Access Control through Container Managed Security)
iii.  JDBC
iv.   JNDI
v.    JPA
vi.   EJB
vii.  JTA
viii. CDI
ix.   Webservices
x.    JMS

The combination of these technologies can create a robust enterprise application and provide all the
essential QoS guarantees as those technologies cover the topics of persistence, ORM, Dependecy Injection,
Transactions as well as synchronous and asynchronous remote communication.

2. Setup

Personally I used the following technologies during the development and the manuals that I provide refer only to these
technologies, but of course, with some effort it could be set up in whatever way you see fit.

IDE: Eclipse Mars
Application Server: Weblogic 12C (12.2.1)
DB: Oracle 11G & MySQL

This project is composed of two eclipse Dynamic Web Projects. 
DemoWebApp, which is a web portal and 
demoWebAppWs which is two Webservice endpoints (JAX-WS).

For more details refer to the manuals inside the MANUALS folder.

3. Video Presentation

A short video demonstration of the execution of this project can be found here:

https://www.dropbox.com/s/8zukymtuqfh4mpf/University%20Thesis%20Presentation.flv?dl=0

The demonstration was created on 7-Sep-2017 so it matches the corresponding project version.

4. GIT

Files are commited to repository without CRLF to LF conversion so your editors will have to handle it
because the code was written on a Windows machine (the vast majority of linux editors will not have a problem with it).
