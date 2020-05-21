# ds-programs
Data structure programs implemented in Java. Includes the following:

**Arrays**

A Priority Queue implemented using array based data structures. In this structure, generic objects are sorted in both unordered and ordered fashion and inserted/removed to/from an array. Priority of the objects is determined by the Comparable interface. Objects with the highest priority are sorted and removed first.

**Linked Lists**

A Priority Queue implemented using singly Linked List data structures. In this structure, generic objects are sorted in both unordered and ordered fashion and inserted/removed dynamically via nodes containing data and a reference to the next node. Priority of the objects is determined by the Comparable interface. Objects with the highest priority are sorted and removed first.

**Binary Heap**

A Priority Queue implemented using a Min Heap data structure. In this structure, generic Wrapper objects are sorted in both unordered and ordered fashion as tree but are accessed by an array. Priority of the objects is determined by the Comparable interface. Objects with the highest priority are sorted and removed first.

**Hashtable**

A DictionaryADT implemented using a Hashtable with chaining data structure. In this structure, an array of linked lists inserts non-duplicate key elements at a specific index set by a hash code function.

**Binary Search Tree**

A DictionaryADT implemented using a Binary Search Tree data structure. In this structure, nodes are inserted from the root and down the tree as children and readjusted depending on if the node’s data is larger or smaller than the current node’s data.

**Red Black Tree**

A DictionaryADT implemented using a Red Black Tree data structure with Java’s TreeMap API. In this structure, it is initialized with a black root node and red children, and every time a new node is added it is red. If a violation occurs, the tree is readjusted using red black rules.
