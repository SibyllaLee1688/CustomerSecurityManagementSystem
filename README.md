CustomerSecurityManagementSystem
================================

for my open source Java Web project (mainly focus on backend design and implementation), still working in progress

Travis CI Status
--------------------------------
[![Build Status](https://travis-ci.org/elulian/CustomerSecurityManagementSystem.svg?branch=master)](https://travis-ci.org/elulian/CustomerSecurityManagementSystem)

Structure
--------------------------------
#####Access Layer
Struts2 (Dojo + ExtJs + JSON + JasperReports)
#####Business Layer
Spring4 + Spring security + Drools6
#####Resource Layer
DAO: default: Hibernate + ehcache, optional: OpenJPA 
Database: MySQL

How to set up ENV
--------------------------------
-   *maven3.0+* installed in your env
-   *git* installed in your env
-   mysql is installed, *working in UTF8 and license to port 3306 with user root and null password*
-   *eclipse WTP* is installed
-   *Java 1.6+* is installed
In your machine, check out the code with command: `git clone https://github.com/elulian/CustomerSecurityManagementSystem.git`

How to set up eclipse project
--------------------------------
1. create a new workspace with Eclipse WTP
2. define the `M2_REPO` variable in Eclipse
3. start `mvn eclipse:clean eclipse:eclipse` from the command-line in the root project
4. import exsiting projects in Eclipse (4 sub projects)

How to load build
--------------------------------
   execute `mvn clean install` from the command-line in the root project ( test, intergration test are verfied in install phase)

How to deploy with jetty in standalone mode
--------------------------------
   exeucte `mvn jetty:run` from the command-line in the CustomerSecurityManagementWeb sub project

How to deploy with tomcat in standalone mode
--------------------------------
   execute `mvn cargo:run` from the command-line in the CustomerSecurityManagementWeb sub project
   
How to deploy with tomcat in cluster mode with apache load balance
--------------------------------

How to deploy with tomcat in cluster mode with nginx load balance
--------------------------------
