CORS:-  
-
-------------------

This can also be implemented by using
1) Will allow specific URL for CORS configuration
```
@CrossOrigin(origins="http://localhost:4200")
```
2)All incoming requests from any domain
```
@CrossOrigin(origins="*")
```
Changes are done SecurityConfiguration.java


-------------------------------------------------------  

--------------------------------------------------

CSRF
-
----------------------------------------------
Files added or changed
1) SecurityConfiguration.java
2) CSRFFilter.java
