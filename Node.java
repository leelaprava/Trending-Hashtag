class Node{                                         // Describe the Structure of Node
		String elem;	
		int frequency;
		int degree;
		Node right;
		Node left;
		Node child;
		Node head;
		boolean marked;
		
		public Node(String elem, int frequency){
			this.elem = elem;
			this.frequency = frequency;
			degree = 0;
			right = this;
			left = this;
			child = null;
			head = null;
			marked = false;
		}		
	}
