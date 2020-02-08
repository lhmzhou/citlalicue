# citlalicue

`citlalicue` handles data-changing, Kafka-based messages by loading streaming data to a Postgres database. Messages holds information about employee demographics. The operation performs an update of records and an insert of new records.
  
## Tech Stack

```
Java 1.8 or later
Spring Tool Suite
Kafka
Postgres
Docker version 17+
```

## Central Components

- `EmployeeProducer` send employee update message

- `MessageController` exposes endpoints to publish employee updates, to bulk create and to load employee data, and to view/delete employee data

- `EmployeeConsumer` listens to message published and writes to database. If message is not applied, consumer logs error and continues processing incoming message

## Build 

Given that all components are containerized, from the base folder, you may use Docker to kickstart kafka and Postgres:
```
docker-compose up -d
```

To build and run the application:
```
mvn clean install
java -jar target/citlalicue.jar
```

To retrieve configuration log output
```
java -jar citlalicue.jar --debug
```

## Usage

This application exposes mulitple endpoints to create data and to publish updates to a kafka topic. Data is generated using the JavaFaker library. The Java Spring Boot Component listens for the kafka topic.

## Endpoints

### Load Data

`GET: http://localhost:8090/api/load`
> This HTTP request will create a batch of employees to be loaded to the postgres database. 

Sample Response
```
{
   "status": 200,
   "data": "Employee accounts are initialized."
}
```

### Publish employee updates to kafka

`GET: http://localhost:8090/api/publish/{count}`
> This HTTP request will publish messages to kafka.

Sample Response
```
{
   "status": 200,
   "data": "Success! Published {10} employee updates."
}
```

### Delete all data

`DELETE: http://localhost:8090/api/employee`
> This HTTP request deletes all the data for employees.

Sample Response
```
{
   "status": 200,
   "data": "Done. All employees have been deleted."
}
```

### View all data

`GET: http://localhost:8090/api/employee/{employeeId}` 
OR
`GET: http://localhost:8090/api/employee`

Sample Response
```
{
  {
        "status": 200,
        "data": {
            "id": 1274,
            "firstname": "Higgs",
            "lastName": "Boson",
            "workDepartment": "Large Hadron Collider",
            "job": "Executive",
            "gender": "Nondescript",
            "hireDate": "2008-09-10",
            "birthDate": "2012-07-04",
            "salary": 137.03599913,
            "skills": [
                {
                    "skillId": 578,
                    "skillName": "force of the natural world"
                },
                {
                    "skillId": 877,
                    "skillName": "tactical"
                },
                {
                    "skillId": 637,
                    "skillName": "energetic"
                },
                {
                    "skillId": 631,
                    "skillName": "stabilizing"
                },
                {
                    "skillId": 559,
                    "skillName": "exacting"
                },
                {
                    "skillId": 988,
                    "skillName": "interactive"
                } 
            ],
            "employeeAddress": {
                "primaryAddress": "Esplanade des Particules 1, P.O. Box 1211 Geneva 23",
                "secondaryAddress": null,
                "workLocationAddress": null
            },
            "projects": [
                {
                    "id": 337,
                    "project": "explore realms outside the parameters of the Standard Model"
                },
                {
                    "id": 304,
                    "project": "electromagnetism and the huge masses of the W and Z particles"
                },
                {
                    ""id": 331,
                    "project": "quantum excitation of the Higgs field"
                }
            ]
        }
    }
}
```

## Contributing

All is not bliss. To elevate this application to production-grade, several features can be fine tuned and/or newly initiated, including but not limited to:

- add encryption process at persistence layer
- create custom deserializers in consumer configuration
- do away with manual ETL jobs by exploring the configuration of a Redshift instance for optimized bulk data transfers and query planner
- tune producer buffer sizes
- create a new Postgres Table with timestamp trigger
- run ad-hoc queries with JOINs
- refetch the same message in the same consumer using seek()
- garbage collection on consumers, brokers
- implement retries on producers
- automate commit records rather than manual offsets?
- and of course: improve test coverage, build health checks

## Resources

[Kafka Docker](https://github.com/wurstmeister/kafka-docker)
</br>
[Postgres Server Download](https://www.postgresql.org/download/)
</br>
[Kafka Download](https://kafka.apache.org/downloads)

## Licensing 

This project is licensed under the [GNU General Public License](https://github.com/lhmzhou/citlalicue/blob/master/LICENSE)