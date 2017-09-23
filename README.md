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
(This is also the trivial top candidate for extensibility of the project.)

## Bundle combinations

> Given a customer order you are required to determine the cost and bundle breakdown for each product.

Assumption:

We are only delivering orders that can be exactly fulfilled by bundle combinations.
Not more and not less flowers. Again, this can be resolved by introducing bundles of one.

## Input format

> Each order has a series of lines with each line containing the number of items followed by the product code.

I am not making assumptions about the ordering of lines or having one line per flower.
