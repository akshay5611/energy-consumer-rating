Welcome to PowerDale
PowerDale is a small town with around 100 residents. Most houses have a smart meter installed that can save and send information about how much power a house is drawing/using.

There are three major providers of energy in town that charge different amounts for the power they supply.

Dr Evil's Dark Energy
The Green Eco
Power for Everyone
Introducing JOI Energy
JOI Energy is a new start-up in the energy industry. Rather than selling energy they want to differentiate themselves from the market by recording their customers' energy usage from their smart meters and recommending the best supplier to meet their needs.

You have been placed into their development team, whose current goal is to produce an API which their customers and smart meters will interact with.

Unfortunately, two members of the team are on annual leave, and another one has called in sick! You are left with another ThoughtWorker to progress with the current user stories on the story wall. This is your chance to make an impact on the business, improve the code base and deliver value.

Story Wall
At JOI energy the development team use a story wall or Kanban board to keep track of features or "stories" as they are worked on.

The wall you will be working from today has 7 columns:

Backlog
Ready for Dev
In Dev
Ready for Testing
In Testing
Ready for sign off
Done
Examples can be found here https://leankit.com/learn/kanban/kanban-board/

Users
To trial the new JOI software 5 people from the JOI accounts team have agreed to test the service and share their energy data.

User	Smart Meter ID	Power Supplier
Sarah	smart-meter-0	Dr Evil's Dark Energy
Peter	smart-meter-1	The Green Eco
Charlie	smart-meter-2	Dr Evil's Dark Energy
Andrea	smart-meter-3	Power for Everyone
Alex	smart-meter-4	The Green Eco
These values are used in the code and in the following examples too.

Requirements
The project requires Java 1.8 or higher.

The project makes use of Gradle and uses the Gradle wrapper, which means you don't need Gradle installed.

Useful Gradle commands
The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building the project or running it.

List all Gradle tasks
List all the tasks that Gradle can do, such as build and test.

$ ./gradlew tasks
Build the project
Compiles the project, runs the test and then creates an executable JAR file

$ ./gradlew build
Run the application using Java and the executable JAR file produced by the Gradle build task. The application will be listening to port 8080.

$ java -jar build/libs/joi-energy.jar
Run the tests
There are two types of tests, the unit tests and the functional tests. These can be executed as follows.

Run unit tests only

$ ./gradlew test
Run functional tests only

$ ./gradlew functionalTest
Run both unit and functional tests

$ ./gradlew check
Run the application
Run the application which will be listening on port 8080.

$ ./gradlew bootRun
API
Below is a list of API endpoints with their respective input and output. Please note that the application needs to be running for the following endpoints to work. For more information about how to run the application, please refer to run the application section above.

Store Readings
Endpoint

POST /readings/store
Example of body

{
  "smartMeterId": <smartMeterId>,
  "electricityReadings": [
    {
      "time": <time>,
      "reading": <reading>
    }
  ]
}
Parameters

Parameter	Description
smartMeterId	One of the smart meters' id listed above
time	The date/time (as epoch) when the reading was taken
reading	The consumption in kW at the time of the reading
Example readings

Date (GMT)	Epoch timestamp	Reading (kW)
2020-11-29 8:00	1606636800	0.0503
2020-11-29 8:01	1606636860	0.0621
2020-11-29 8:02	1606636920	0.0222
2020-11-29 8:03	1606636980	0.0423
2020-11-29 8:04	1606637040	0.0191
In the above example, the smart meter sampled readings, in kW, every minute. Note that the reading is in kW and not kWH, which means that each reading represents the consumption at the reading time. If no power is being consumed at the time of reading, then the reading value will be 0. Given that 0 may introduce new challenges, we can assume that there is always some consumption, and we will never have a 0 reading value. These readings are then sent by the smart meter to the application using REST. There is a service in the application that calculates the kWH from these readings.

The following POST request, is an example request using CURL, sends the readings shown in the table above.

$ curl \
  -X POST \
  -H "Content-Type: application/json" \
  "http://localhost:8080/readings/store" \
  -d '{"smartMeterId":"smart-meter-0","electricityReadings":[{"time":1606636800,"reading":0.0503},{"time":1606636860,"reading":0.0621},{"time":1606636920,"reading":0.0222},{"time":1606636980,"reading":0.0423},{"time":1606637040,"reading":0.0191}]}'
The above command does not return anything.

Get Stored Readings
Endpoint

GET /readings/read/<smartMeterId>
Parameters

Parameter	Description
smartMeterId	One of the smart meters' id listed above
Retrieving readings using CURL

$ curl "http://localhost:8080/readings/read/smart-meter-0"
Example output

[
  {
    "time": "2020-11-29T08:00:00Z",
    "reading": 0.0503
  },
  {
    "time": "2020-11-29T08:01:00Z",
    "reading": 0.0621
  },
  {
    "time": "2020-11-29T08:02:00Z",
    "reading": 0.0222
  },
  {
    "time": "2020-11-29T08:03:00Z",
    "reading": 0.0423
  },
  {
    "time": "2020-11-29T08:04:00Z",
    "reading": 0.0191
  }
]
View Current Price Plan and Compare Usage Cost Against all Price Plans
Endpoint

GET /price-plans/compare-all/<smartMeterId>
Parameters

Parameter	Description
smartMeterId	One of the smart meters' id listed above
Retrieving readings using CURL

$ curl "http://localhost:8080/price-plans/compare-all/smart-meter-0"
Example output

{
  "pricePlanComparisons": {
    "price-plan-2": 0.0002,
    "price-plan-1": 0.0004,
    "price-plan-0": 0.002
  },
  "pricePlanId": "price-plan-0"
}
View Recommended Price Plans for Usage
Endpoint

GET /price-plans/recommend/<smartMeterId>[?limit=<limit>]
Parameters

Parameter	Description
smartMeterId	One of the smart meters' id listed above
limit	(Optional) limit the number of plans to be displayed
Retrieving readings using CURL

$ curl "http://localhost:8080/price-plans/recommend/smart-meter-0?limit=2"
Example output

[
  {
    "price-plan-2": 0.0002
  },
  {
    "price-plan-1": 0.0004
  }
]
Get cost of last week usage
As an electricity consumer, I want to be able to view my usage cost of the last week so that I can monitor my spending

Acceptance Criteria:

Given I have a smart meter ID with price plan attached to it and usage data stored, when I request the usage cost then I am shown the correct cost of last week's usage
Given I have a smart meter ID without a price plan attached to it and usage data stored, when I request the usage cost then an error message is displayed
How to calculate usage cost

Unit of meter readings : kW (KilloWatt)
Unit of Time : Hour (h)
Unit of Energy Consumed : kW x Hour = kWh
Unit of Tariff : $ per kWh (ex 0.2 $ per kWh)
To calculate the usage cost for a duration (D) in which lets assume we have captured N electricity readings (er1,er2,er3....erN)

Average reading in KW = (er1.reading + er2.reading + ..... erN.Reading)/N Usage time in hours = Duration(D) in hours Energy consumed in kWh = average reading x usage time Cost = tariff unit prices x energy consumed.

Endpoint

GET /cost/smart-meter-0
Example output

171.6250

To-Do

Error Handling
Code Improvements
APIs/Service - Instead of NULL, it should return empty object. Then it would be helpful for developers to integrate and improve code reusability
Error Handling can be improved
Instead of INTERNAL_SERVER_ERROR, we should throw proper error.
We should have custom Response entity with standard message, status, content. And it should be used throughout the APIs response.
isMeterReadingsValid should be part of service, instead of controller.
Service can be auto wired in the Controller
Code Readability - Instead of stream, filter etc. we can simply write code in for loops
EndpointTest - Reusable functions can be in another service - getStringHttpEntity, populateMeterReadingsForMeter
