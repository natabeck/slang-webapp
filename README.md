# slang-webapp
Web application for slang

Clone the repository and run:

mvn spring-boot:run


This is web application that allows to run Slang content. By default it uses H2 in memory DB but can be configured to use any other DB in:

slang-webapp\src\main\resources\spring\slangWebappContext.xml.

The actions that can be done are:
1. Trigger a Slang flow. The flow must be located on the file system of the server. Full path to the flow is passed during the trigger together with Slang directory and inputs.
2. Get the result of the run of a triggered flow.


In order to do that we have 2 REST APIs:

1. Trigger the flow:

                Method: POST

                URL: /executions

                Example for Body:

                {"slangFilePath":"<LOCATION>\\cloud-slang-content\\content\\io\\cloudslang\\base\\print\\print_text.sl","slangDir":"<LOCATION>\\cloud-slang-content\\content\\io\\cloudslang","runInputs":{"text":"blabla"}}


2. Get the results:

                Method: GET

                URL: /executions/{executionId}


