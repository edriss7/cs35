Programs for generating fake data.

### ItemGenerator

Generates a catalogue of items. Names of items are generated from text files
containing input words. Item names are made as unique as possible. Each item is
associated with a unique ID. Each row will be a tab-delimited record containing
the ID and the item name.

Run example:

    javac ItemGenerator.java
		java ItemGenerator 100 a.txt b.txt c.txt

### InventoryGenerator

TBD...