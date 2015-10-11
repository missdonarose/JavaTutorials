package com.tutorial.collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

public class ListExample {
	
	public static <E> void swap(List<E> list,int i,int j)
	{
		E temp =  list.get(i);
		list.set(i,list.get(j));
		list.set(j,temp);
	}
	
	public static <E> int indexOf(List<E> list,E e)
	{
		E next = null;
		for(ListIterator<E> li=list.listIterator();li.hasNext();)
		{
			next=li.next();
			if(e!=null?e.equals(next):next==null)
				return li.previousIndex();
		}
		return -1;
	}
	
	public static void testMapIteration()
	{
		Map<String,String> map = new TreeMap<>();
		map.put("3K1","V1");
		map.put("1K2","V2");
		map.put("2K3","V3");
		
		Set<String> keys = map.keySet();
		for(String key:keys)
		{
			if(key!=null)
			{
				System.out.println("Key:"+key+",Vallue="+map.get(key));
			}
		}
		System.out.println(keys);;
	}
	
	/*Suppose you have a Map that represents a collection of attribute-value pairs,
	 *  and two Sets representing required attributes and permissible attributes. 
	 *  (The permissible attributes include the required attributes.) 
	 *  The following snippet determines whether the attribute map conforms to
	 *   these constraints and prints a detailed error message if it doesn't.
*/
static <K, V> boolean validate(Map<K, V> attrMap, Set<K> requiredAttrs, Set<K>permittedAttrs) 
{
	boolean isValid = true;
	
	//keys dont contain all reqd attrs
	Set<K> keysAttr = attrMap.keySet();
	if(!keysAttr.containsAll(requiredAttrs)){
		
		Set<K> missing = new HashSet<>(requiredAttrs);
		missing.removeAll(keysAttr);
		System.out.println("Missng required attributes: ");
		missing.stream().forEach(K -> System.out.println(K));
		isValid = false;
		}
	
	//keys contain more than permitted
	if(!permittedAttrs.containsAll(keysAttr))
	{
		Set<K> illegal = new HashSet<>(keysAttr);
		illegal.removeAll(permittedAttrs);
		System.out.println("Attributes which are note permitted: ");
		illegal.stream().forEach(K -> System.out.println(K));
		isValid = false;
	}
	
	return isValid;
}

//Methods to test validate()
public static void createMapsAndTest()
{
	Map<String,String> attributeMap = new LinkedHashMap<>();
	attributeMap.put("K1", "V1");
	attributeMap.put("K2", "V1");
	attributeMap.put("K3", "V1");
	attributeMap.put("K4", "V1");
	
	Set<String> required = new HashSet<String>(Arrays.asList(new String[]{"K1","K5"}));
		
	Set<String> permitted = new HashSet<String>(Arrays.asList(new String[]{"K1","K5","K4"}));
	
	System.out.println("Valid: "+validate(attributeMap, required, permitted));
	System.out.println(attributeMap.size()+","+required.size()+","+permitted.size());
	
}

	
	public static void main(String...arg)
	{
		createMapsAndTest();
	}

}
