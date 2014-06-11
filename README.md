CustomerSecurityManagementSystem
================================

for my first public Java Web project

Structure:

Web Layer: Struts2 (Dojo + ExtJs) 
Service Layer: Spring4 + Spring security + Drools6
Persistent Layer: default: Hibernate + ehcache, optional: OpenJPA

How to checkout 
    maven installed in your env
    git installed in your env
    In your machine, check out the code with command: git clone https://github.com/elulian/CustomerSecurityManagementSystem.git

How to set up eclipse project
    create a new workspace with Eclipse WTP
    define the M2_REPO variable in Eclipse
    start mvn eclipse:clean eclipse:eclipse from the command-line in the root project
    import exsiting projects in Eclipse (4 sub projects)
    
How to load build
    start mvn clean install from the command-line in the root project

How to deploy with jetty
    mysql is installed
    exeucte 
    start mvn jetty:run from the command-line in the CustomerSecurityManagementWeb sub project

How to deploy with ...
    
