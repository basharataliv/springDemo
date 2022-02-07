# springDemo
prerequisite for this project
1. need java 11
2. need internet to call the third party endpoints

# Project Details
this project has three endpoints
1. POST: http://localhost:8080/login?user=abc123&password=123456
    a. user=abc123 and password=123456 is static for now.
2. GET: http://localhost:8080/country?countryName=any  can pass any country for details
3. GET: http://localhost:8080/audit this is for to see the audit details (optional not required)
these all end points requied token in the header with bearer.
