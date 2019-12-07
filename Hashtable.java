import java.util.*;

public class Hashtable
{ 
	int numBucket;
	int size;
	ArrayList<HashNode> bucket;

	class HashNode
	{
		String key;
		String value;
		HashNode next;

		public HashNode(String a, String b)
		{
			this.key = a;
			this.value = b;
			next = null;
		}
	}

	public Hashtable()
	{
		bucket = new ArrayList<HashNode>();
		numBucket = 5000;
		size = 0;

		for (int i = 0; i < numBucket; i++)
		{
			bucket.add(null);
		}
	}

	//this function returns true if a key/value pair are present
	public boolean containsKey(String key)
	{
		HashNode head = bucket.get(getHash(key)); 

        while (head != null) 
        {
            if (head.key.equals(key))
            {
                return true;
            }
            head = head.next;
        }
        return false;
	}

	//returns the hashcode of the key
	private int getHash(String key)
	{
		return (Math.abs(key.hashCode()) % numBucket);
	}

	//returns the value associated with the key that is passed as an argument
	//returns null if not there
	public String get(String key)
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);

		while(head != null)
		{
			if ((head.key).equals(key))
			{
				return head.value;
			}
			head = head.next;
		}
		return null;
	}

	//adds the key/value pair into the Hashtable
	//if it exists, replaces the stored value with the argument value
	public void put(String key, String value)
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);
		
		while(head != null)
		{
			if ((head.key).equals(key))
			{
				head.value = value;
				return;
			}
			head = head.next;
		}

		head = bucket.get(index);
		HashNode newNode = new HashNode(key, value);
		newNode.next = head;
		bucket.set(index, newNode);
		++size;

		//0.50 is the load threshold
		if (((1.0 * size)/bucket.size()) >= 0.50)
		{
			ArrayList<HashNode> temp = bucket;
			numBucket = 2 * numBucket;
			bucket = new ArrayList<HashNode>(numBucket);
			size = 0;

			for (int i = 0; i < numBucket; i++)
			{
				bucket.add(null);
			} 
			for (HashNode newhead : temp)
			{
				while (newhead != null)
				{
					put(newhead.key, newhead.value);
					newhead = newhead.next;
				}
			}
		}
		
	}

	//removes the key/value pait from the Hashtable
	//returns the value that is associated to that key
	public String remove(String key) throws Exception
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);
		HashNode prev = null;

		while (head != null && head.key.equals(key) == false)
		{
			prev = head;
			head = head.next;
		}

		//if not present, throws an exception
		if (head == null)
		{
			throw new Exception();
		}
		size--;

		if (prev != null)
		{
			prev.next = head.next;
		} 
		else 
		{
			bucket.set(index, head.next);
		}
		return head.value;
	}

	

	
}