# Pavilion
A generalized tournament seeding library for Java.

## What it does
Pavilion uses a generalized set of types allowing users to define a seeding order on any type implementing the interface `Matchable`. You can define different dimensions on which to seed entrants, as well as the relative weight of each dimension.

## Basic types
Pavilion uses four basic types exposed to users in order to generate its seeding:

### The `Matchable` interface
The interface specifies a single method returning a `double` from `0.0` to `1.0`, which represents the entrant's skill at the tournament contest. All seedings include this "skill" dimension, which determines how likely an entrant is to defeat another entrant, enabling the evaluation of later rounds in a tournament. The skill dimension's weight can be set to zero if a user does not wish to seed based on it.

### Adding `SeedDimension`s
The `SeedDimension<T>` type represents a single property or dimension on which to seed entrants of type `T`, along with its weight. To create a `SeedDimension`, the user provides a weight, a way to generate values of a property type from the entrant type, and an `AbsoluteComparator` (see below) on the property type.

### Comparing entrants with an `AbsoluteComparator`
Structured similarly to `java.util.Comparator`, an `AbsoluteComparator<U>` defines a relationship on property types `U`. The comparator defines a _matching fitness_ on a property type, represented by a `double` bounded by `0.0` and `1.0`. A fitness of `0.0` represents that the two values of the property are _minimally matchable_, or that all other matches should be preferred to it. Accordingly, a fitness of `1.0` means that the two values are _maximally matchable_, the best match possible. The function implementation must thus be commutative.

This is usually the hard part of defining a good `SeedDimension`. A tournament organizer must know what it means for entrants to be a good or bad match on certain properties, and be able to represent these values numerically. Pavilion includes two built-in comparators for skill and geography, using typical seeding conventions:
* Two players are well-matched in skill if the sum of their skill values is close to `1.0`
* Two players are well-matched in geography if their locations are physically far apart

Most users should find these dimensions sufficient.

### Generating a `Seeding`
The `Seeding` type is the primary way to generate the seeding of players once the appropriate `SeedDimension`s have been defined. Clients build an instance of the class using a standard builder pattern, then call `Seeding.seed( Set<T extends Matchable> entrants )` to return an ordered list of entrants. The order represents matches between adjacent entrants. So entrants 0 and 1 play, and 2 and 3 play, the winners of each play each other, and so on.

## How it works
Pavilion uses a simple simulated annealing process to find the best seeding. It generates a random order of entrants, evaluates the total fitness according to the given `SeedDimension`s, then searches its neighborhood of single flips of the order and repeats the process. All the usual properties of a simulated annealing process are configurable, and the ending condition _must_ be specified at creation time. Ending conditions may be after a certain number of iterations, or after a certain amount of real time has passed.
