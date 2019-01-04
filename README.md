Generate random string array and apply hash function. Outputs runtime in  milliseconds

Hash string - from Elastic 

Alternative Murmur3Hash - from Lucene

Compile:
<code>
javac Murmur3HashFunction.java
</code>

Run:
<code>
java Murmur3HashFunction [string length] [string array size]
</code>

Example:
<code>
java Murmur3HashFunction 700 10000000
</code>

