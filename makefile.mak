
JCC = javac


default: TrendingHashtag.class Fibo_Heap.class Node.class 


TrendingHashtag.class: TrendingHashtag.java
	$(JCC) $(JFLAGS) TrendingHashtag.java

Fibo_Heap.class: Fibo_Heap.java
	$(JCC) $(JFLAGS) Fibo_Heap.java

Node.class: Node.java
	$(JCC) $(JFLAGS) Node.java


clean: 
	$(RM) *.class
	$(RM) *.class