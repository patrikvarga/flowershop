# Intro

This is a possible minimal solution of the Flower Shop exercise as described in [flower_shop.pdf](docs/flower_shop.pdf).

# Build and run

To build the project and create JAR artifact, run `mvn clean install`.
The successfully passing tests will verify behavior for the provided example and some more cases.

# Assumptions

## No individual flowers

> The flower shop has decided to start selling their flowers in bundles
> and charging the customer on a per bundle basis.

Assumption:

We are not selling flowers individually at the moment.
At least the individual prices are unknown from the description.
In any case, it's easy to do so without code change by introducing bundles of one.
(This is also the trivial top candidate for extensibility of the project and it's tested.)

## Bundle combinations

> Given a customer order you are required to determine the cost and bundle breakdown for each product.

Assumption:

We are only delivering orders that can be exactly fulfilled by bundle combinations.
Not more and not less flowers.
Again, this can be resolved by introducing bundles of one in the config as the code is already handling it.

## Input format

> Each order has a series of lines with each line containing the number of items followed by the product code.

I am not making assumptions about the ordering of lines or having one line per flower.
I am simply counting all the ordered flowers.

# Technical decisions and limitations

Things that I considered overkill for production code at this point:

* Exposing REST or any other APIs.
* A CLI was specifically not asked for so it is not provided.
  The tests demonstrate the necessary example as per PDF documentation.
* IoC/DI frameworks. (Otherwise there would be CDI or Spring.)
* Other common application frameworks, e.g. Spring Boot.

Things that I considered overkill for test code at this point:

* 100% test coverage was clearly not the goal.
  Testing is demonstrated for some portions of the JSON catalog reader,
  the order processing (bundling), and order input file reading;
  but **not** for the product catalog or the coin change problem solver itself etc.
* Not using Mockito in unit tests as mocking is still pretty simple using POJOs.

Other considerations:

* Not using interfaces just for the sake of programming to interfaces (unless needed for testing/mocking).
* Trying to get rid of most boilerplate, including unnecessary getters and setters,
  and even get/set prefixes in method names where accessors are used.
* Simplifying class naming, i.e. no "Service" or other suffixes are used. (See "Orders" for example.)
* Error handling is not fully polished. (Referring to the use of RuntimeExceptions for example.)
* Depending on where complexity shifts in the future, it might make sense to do unit testing
  in a more fine-grained manner, i.e. test some (currently private) methods separately.
