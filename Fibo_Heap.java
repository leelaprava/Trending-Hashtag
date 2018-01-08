import java.util.HashMap;

class Fibo_Heap {
	Node Head;                                   //Node with Highest Frequency
	int tree_count;                              // Total Number of trees whose nodes have head pointed to null
	
	/*Initializing the variables of Fibo_Heap*/ 
	public Fibo_Heap(){                          // Constructor for Fibo_Heap
		Head = null;
		//consol_queue = new HashMap<>();
		tree_count = 0;
	}
	
	
	Node Insert(String element, int frequency){   // For Creating and adding Nodes which never existed
		Node t;                                   //  in the Heap unlike Insert_back() later
		
		
		if (Head == null){		                  //  For first entry of heap when (Head == null)
			t = new Node(element, frequency);
			Head = t;
			tree_count++;
			return Head;
		}
		else{                                     //  Adding when there exist a Head 
			Node temp = Head;
			while (temp.right != Head ){		
					temp = temp.right;
			}
			t = new Node(element, frequency);
			tree_count++;
			t.right = temp.right;		
			temp.right.left = t;
			temp.right = t;
			t.left = temp;
			
			if(t.frequency > Head.frequency)	// Changing Head if the frequency of the added 	
				Head = t;                       // node is greater than that of Head 
			return t;

		}
	}
	
	

	void Casc_cut(Node t){                      // Called when a node's frequency needs to updated
		
        if (t.head.head != null){	            
			if (t.head.marked == false){	    // Check if the tree's head is not marked 
				t.head.marked = true;           // If it's not marked mark it true 
				Insert_back(t);                 // and cut the tree t and insert it back to heap
			}
		     else{	
				Node temp = t.head;            // If it is head is marked
				Insert_back(t);                // Cut the tree t and insert it back to heap
				Casc_cut(temp);                // call cut for parent
			}
		}
		
		else{	
			Insert_back(t);
		}
		
	}
	
	void Insert_back(Node t){                     // This function Inserts the trees into the heap
		
		
		if (t.head != null){                      // This checks if the tree needs to be inserted into the circular list containing the head
			
			if(t.head.child == t && t.right != t){	           // Node 't' is pointed by head
				t.head.child = t.right;                        // hence head's child should be updated
				//Updating other children references
				t.left.right = t.right;
				t.right.left = t.left;
			}
			 
			else if (t.head.child == t && t.right == t){      // The node 't' is the only child of its head
				t.head.child = null;                          // hence head's child is set to null
			}
			
			else {                                           // The head has some other child
				t.left.right = t.right;                      // Therefore it's enough to remove the node 't' 
				t.right.left = t.left;
			}
			t.head.degree--;
			t.head = null;	
		}

		
		tree_count++;	                           //       Steps for Inserting the cut tree 't'
		Node temp = Head;
		while (temp.right != Head){
			temp = temp.right;
		}
		
		t.right = Head;	
		Head.left = t;
		temp.right = t;
		t.left = temp;
		
		if(t.frequency > Head.frequency)	     //     Updating Head
			Head = t;
		
	}
	
	
Node remmax(){	                                    // This function is called to evaluate a query
	
	
	HashMap<Integer, Node> consol_queue;            //  Used for pairwise merging of trees with same degree 
	consol_queue = new HashMap<>();
	Node prevRoot = Head;
	Node temp1;
		
	if (Head.child != null && Head.child.right == Head.child ){  // The head has only one child 
			Insert_back(Head.child);                                 // As the head would be removed its child would be added back
		}
		
		else if (Head.child != null){                             // The Head has multiple children
			Node tempChild,child;
			child = Head.child;
			tempChild = child.right;
			
			while (tempChild != child){
				temp1 = tempChild.right;
				Insert_back(tempChild);
				tempChild = temp1; 
			}
			Insert_back(tempChild);	
		}
		
		
		prevRoot.left.right = prevRoot.right;	         //    The head is removed
		prevRoot.right.left = prevRoot.left;
		
		tree_count--;	
		
		int cnt = tree_count;                         // The Heap generated after removal of the Head
		Node temp = Head;	                          //  Is consolidated
		temp = temp.right;
		while (cnt != 0){                             // While consolidation every node in the circular list containing head is accessed atleast once
			temp1 = temp.right;
			
			if (consol_queue.containsKey(temp.degree) == false){      // No tree of the same degree exists
				consol_queue.put(temp.degree, temp);                 // Hence is added into the queue
		}
		else{
				pairwisemerge(consol_queue,consol_queue.get(temp.degree), temp); // If there exist a tree with same degree they are combined
		}
			temp = temp1;
			cnt--;
		}
		
		int max = -1;                             // The new head is found of the changes heap 
		int cn = tree_count;                      // after consolidation
		Node newRoot = null;
		int i = 0;
		while (consol_queue.containsKey(i) == false) { // find any node in the heap which can be used to traverse the circular list containing the nodes with head==null 
			i++;
		}
		Node tem = consol_queue.get(i);
		tem = tem.right;
		while (cn != 0){	
			if (tem.frequency > max){
				max = tem.frequency;
				newRoot = tem;
			}
			tem = tem.right;
			cn--;
		}
		Head = newRoot;                           //  Change the head to max
		
		consol_queue.clear();
		return prevRoot;
	}
	

	
	void pairwisemerge(HashMap<Integer, Node> consol_queue,Node t1, Node t2){  //  This function is used for merging trees with same degree
		if (t1.frequency >= t2.frequency){	                                    // Find who is the head and who is the child
			t1.degree++;
			t2.head = t1;
			
		
			t2.left.right = t2.right;		                                  // Removing t2 from the list containing the Head
			t2.right.left = t2.left;
			t2.right = t2;	
			t2.left = t2;
			
			if (t1.child == null){                                           // Making t2 child of t1
				t1.child = t2;
			}
			else{                                                            //  Adding t2 to the child list of t1 
				
				Node child = t2.head.child;
				Node te = child.right;
			
				while (te.right != child){
					te = te.right;
				}
				
				t2.right = te.right;
				te.right.left = t2;
				te.right = t2;
				t2.left = te;
				
			
			}
			
			tree_count--;                                               //   Reduce the number of trees in the Main List
			consol_queue.remove(t2.degree);                            //    Remove the t2 consolidation queue as it is not a root in the tree           
			
			if (consol_queue.containsKey(t1.degree) == false){         //    Add t1 as degree is updated
				consol_queue.put(t1.degree, t1);
			}
			else{
				pairwisemerge(consol_queue,consol_queue.get(t1.degree), t1);   //  Call the merge for function t1
			}
		}
		else{
			t2.degree++;                                                // tree t2 will be the parent of t1
			t1.head = t2;
			
			
			t1.left.right = t1.right;
			t1.right.left = t1.left;
			t1.right = t1;
			t1.left = t1;

			
			if (t2.child == null){
				t2.child = t1; 
			}
			else{
			  
				Node child = t1.head.child;
				Node te = child.right;
			
				while (te.right != child){
					te = te.right;
				}
				
				t1.right = te.right;
				te.right.left = t1;
				te.right = t1;
				t1.left = te;
				
			
			}	
			
			tree_count--;
			consol_queue.remove(t1.degree);
			
			
			if (consol_queue.containsKey(t2.degree) == false){
				consol_queue.put(t2.degree, t2);
			}
			else{
				pairwisemerge(consol_queue,consol_queue.get(t2.degree), t2);
			}
		}
		
	}
	
}