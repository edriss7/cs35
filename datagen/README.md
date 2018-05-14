Programs for generating fake data.

### ItemGenerator

Generates a catalogue of items. Names of items are generated from text files
containing input words. Item names are made as unique as possible. Each item is
associated with a unique ID. Each row will be a tab-delimited record containing
the ID and the item name. Item IDs are arbitrary and will be used to cross-
reference.

Run example:

    javac ItemGenerator.java
    java ItemGenerator 100 a.txt b.txt c.txt

### StoreGenerator

Generates data for store locations. Takes names at random from a text file, in a
uniform distribution. Coordinates are to be as distant from each other as
possible using a randomized approach. Each row will be a tab-delimited record
containing the store ID (a unique integer), the vendor name, and its location.
Store IDs are arbitrary and will be used to cross-reference.

Run example:

    javac StoreGenerator.java
    java StoreGenerator 20 34,118-74,40 vendors.txt

The above coordinates are approximately those of Los Angeles and New York City.
