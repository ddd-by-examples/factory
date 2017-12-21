# Missing complete example of Domain-Driven Design enterprise application

## Command Query CRUD Responsibility Segregation
Not every piece of software is equally important...
Not every piece will decide about company / product success or can cause not reversible
negative business consequences like materialise brand risk or money loses.
On the other hand scalability or non functional requirements are different for different activities in software.
To accommodate to those differences, separate architectural patterns are applied:

![Command Query CRUD Responsibility Segregation](https://github.com/michal-michaluk/factory/raw/master/command-query-crud.png)

**Simple Create Read Update Delete functionality** are exposed with leverage of CRUD framework.

Goals of that approach:
- fast initial development,
- fast respond to typical changes (ex. „please add another 2 fields on UI”),
- exposure of high quality API.

**Complex Commands (business processing)** expressed in Domain Model which is embedded in hexagonal architecture.

Goals of that approach:
- enable approach with implementing the Domain Model in the first place, by adding infrastructure adapters later,
- keeping the Domain Model as simple as possible by protecting it from accidental complexity
caused by technological choices or transport models from external services / contexts,
- make the core business of application technology agnostic, enabling continues technology
migration and keeping long living projects up to date with fast evolving frameworks and libraries.

**Complex Query** implemented as direct and simple as possible by:
- fetching persistent read model expected by consumer, the read model is a projection of past domain event,
- read model composed at query execution time build directly from persistent form of Domain Model,
- mix of above: read model composed at query execution time build from pre-calculated persistent projections of domain event.

Additional complex calculations or projections can be partially delegated to the Domain Model if desired.

Goals of that approach:
- encapsulation of the Domain Model complexity by providing (simpler) consumer driven or published language API,
- freeing Domain the Model from exposing data for reads making the Domain Model simpler,
- improves reads performance and enable horizontal scalability.


## Hexagonal Architecture
Only the most valuable part of that enterprise software is embedded in hexagonal architecture -
complex business processing modeled in form of the Domain Model.

![Domain Model embedded in hexagonal architecture](https://github.com/michal-michaluk/factory/raw/master/hexagon.png)

**Application Services** - providing entry point to Domain Model functionality,
Application Services are ports for Primary / Driving Adapters like RESTfull endpoints.

**Domain Model** - Object Oriented (in that case) piece of software modeling business rules, invariants,
calculations and processing variants.
Thanks to hexagon can be as clean and simple as possible - separating essential complexity of pure business
from accidental complexity of technical choices, free of technical and convention constraints.

**Ports** - contract defined by Domain Model expressing expectations from external resources (services, database or other models).
Declared interfaces alongside with IN-OUT parameters are Ports for Secondary / Driven Adapters like repository implementation.

**Adapters** - integration of the technology (REST, database, external services, etc.) with the Domain Model.
Making useful application from the Domain Model and the technology.


## Implementing Domain Model in the first place
In most projects the biggest risk is lack of domain knowledge among developers. We all known Java,
databases and bunch of handy frameworks, but what about: Investment Banking, Automotive Manufacturing or even e-Commerce.

Lets face those risk at first, maintain and explore domain knowledge
with **Model Exploration Whirlpool** and build **Ubiquitous Language** with your executable **Domain Model**,
**Domain Stories** and **Specification by Examples** from day one.
Adding infrastructure and technology later is easy thanks to Hexagonal Architecture.

Starting from ZERO business knowledge through initial domain and opportunity exploration with **Big Picture Event Storming**:
<big-picture-es>

Looking for system boundaries, impacted and required actors and there interactions with system under design:
<actors-and-boundaries>

Estimating depth of domain model and Command Query CRUD segregation:
<command-query-crud>

Design level Event Storming with Domain Stories and Specification by Examples:
<demand-forecasting-design-es>
<adjust-demand.feature>

<shortage-prediction-design-es>
