package skiplist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestClass
{
    public static void main(String[] args)
    {        
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> values = new ArrayList<Integer>();
        int size = 10000;
        for (int i = 0; i < size; i++)
        {
            list.add(i);            
        }        
        Collections.shuffle(list);        
        for (int i = 0; i < size; i++)
        {            
            values.add(i);            
        }
        SkiplistWrapper<Integer,Integer> slist = new SkiplistWrapper<Integer,Integer>(-Integer.MAX_VALUE,Integer.MAX_VALUE); //Wartosci head i tail min i max wartosc
        ConcurrentSkipListMap<Integer,Integer> concurrent = new ConcurrentSkipListMap<Integer,Integer>();       
        
        /****************************/
        /* TEST WSTAWIANIA SKIPLIST */
        /****************************/
        long start = System.currentTimeMillis();       
        for (int i = 0; i < size; i++)
        {           
            slist.put(list.get(i),values.get(i));            
        }
        long stop = System.currentTimeMillis();
        System.out.println("Wypelnianie Skiplisty trwało: " + (stop-start) + "ms.");          
        
        /*****************************************/
        /* TEST WSTAWIANIA CONCURRENTSKIPLISTMAP */
        /*****************************************/
        start = System.currentTimeMillis();
        for (int j = 0; j < size; j++)
        {           
            concurrent.put(list.get(j),values.get(j));             
        } 
        stop = System.currentTimeMillis();        
        
        System.out.println("Wypelnianie Skiplisty ConsurrentSkipListMap trwało: " + (stop-start) + "ms.\n");  
        
        /******************************/
        /* TEST WYSZUKIWANIA SKIPLIST */
        /******************************/
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
        {           
            boolean k = slist.containsKey(i);            
        }
        stop = System.currentTimeMillis();
        System.out.println("Wyszukiwanie elementów Skiplisty trwało: " + (stop-start) + "ms.");  
        
        /*******************************************/
        /* TEST WYSZUKIWANIA CONCURRENTSKIPLISTMAP */
        /*******************************************/
        start = System.currentTimeMillis();
        for (int j = 0; j < size; j++)
        {           
            boolean t = concurrent.containsKey(j);            
        } 
        stop = System.currentTimeMillis();
        System.out.println("Wyszukiwanie elementów ConsurrentSkipListMap trwało: " + (stop-start) + "ms.\n");
        
        /*************************************/
        /* TEST POBIERANIA ELEMENTU SKIPLIST */
        /*************************************/
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
        {           
            Integer a = slist.get(i);
        }
        stop = System.currentTimeMillis();
        System.out.println("Pobieranie elementów Skiplisty trwało: " + (stop-start) + "ms.");  
        
        /**************************************************/
        /* TEST POBIERANIA ELEMENTU CONCURRENTSKIPLISTMAP */
        /**************************************************/
        start = System.currentTimeMillis();
        for (int j = 0; j < size; j++)
        {           
            Integer a = concurrent.get(j);            
        } 
        stop = System.currentTimeMillis();
        System.out.println("Pobieranie elementów ConsurrentSkipListMap trwało: " + (stop-start) + "ms.\n");
        
        /******************/
        /* TEST HIGHERKEY */
        /******************/
        boolean check = true;
        for (int i = 1; i!= size-1;i=slist.higherKey(i))
        {   
            if (slist.higherKey(i)-i!=1)
            {
                check = false;
                break;
            }            
        }         
        
        /**************************/
        /* TEST USUWANIA SKIPLIST */
        /**************************/
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
        {      
            Integer a = slist.remove(i);            
        }
        stop = System.currentTimeMillis();
        System.out.println("Usuwanie elementów Skiplisty trwało: " + (stop-start) + "ms.");  
        
        /***************************************/
        /* TEST USUWANIA CONCURRENTSKIPLISTMAP */
        /***************************************/
        start = System.currentTimeMillis();
        for (int j = 0; j < size; j++)
        {           
            Integer a = concurrent.remove(j);            
        } 
        stop = System.currentTimeMillis();        
        System.out.println("Usuwanie elementów ConsurrentSkipListMap trwało: " + (stop-start) + "ms.\n");
        System.out.println("higherKey(x) == x+1 " + check);
    }
}
