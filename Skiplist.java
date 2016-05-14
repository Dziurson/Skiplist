/**
 * @author: Jakub Dziwura
 * 
 * Skiplist class. 
 */
package skiplist;
import java.util.Random;
import java.util.ArrayList;
import java.util.*;

public class Skiplist<K extends Comparable<K>,V>
{   
    /* Last element of Skiplist. */
    private Element<K,V> tail;
    /* First element of Skiplist. */
    private Element<K,V> head;
    /* Actual element of Skiplist (used in certain methods). */
    private Element<K,V> actual;
    private Random random;
    /* maximumHeight, 2^maximumHeight should be as close as possible to number of elements */
    private int maximumHeight = 16;
    /* key1 sholud be the lowest possible value, and k2 the highest */
    public Skiplist(K key1,K key2)
    {        
        random = new Random();
        Element<K,V> head_t = new Element<K,V>(key1,null,maximumHeight);
        Element<K,V> tail_t = new Element<K,V>(key2,null,maximumHeight);
        head = head_t;
        tail = tail_t;
        for (int i = 0; i < maximumHeight; i++)
        {
            /* Filling head and tail list on all heights */
            head.setElementNext(tail_t,i);
            head.setElementPrev(null,i);
            tail.setElementPrev(head_t,i);
            tail.setElementNext(null,i);
        }
    }
    /* getHead() method returns head Element (Node). */
    public Element<K,V> getHead()
    {
        return head;
    }
    /* getTail() method returns tail Element (Node). */
    public Element<K,V> getTail()
    {
        return tail;
    }
    /* containsKey(K key) method returns true if Skiplist contains element with key
       and false if not, and sets actual Element. */
    public boolean containsKey(K key)
    {
        Element<K,V> tmp = head;        
        while(true)
        {   
            /* If key of next element is lower skiplist cannot contain key (skiplist is sorted). */
            if (key.compareTo(tmp.getNextEl().get(0).getKey()) < 0)
                return false; 
            for(int i = tmp.getHeight() - 1; i > -1; i--)
            {
                if (key.compareTo(tmp.getNextEl().get(i).getKey()) > 0)
                {
                    tmp = tmp.getNextEl().get(i);
                    break;
                }
                else if (key.compareTo(tmp.getNextEl().get(i).getKey()) == 0)
                {
                    actual = tmp.getNextEl().get(i);
                    return true;
                }
            }            
        }        
    } 
    /* higherKey(K key) method returns closest higher key in skiplist. Mechanism is the same as in previous method. */
    public K higherKey(K key)
    {        
        Element<K,V> tmp = head;
        while(true)
        {              
            for(int i = tmp.getHeight() - 1; i > -1; i--)
            {
                if (key.compareTo(tmp.getNextEl().get(i).getKey()) > 0)
                {
                    tmp = tmp.getNextEl().get(i);
                    break;
                }
                else if (key.compareTo(tmp.getNextEl().get(i).getKey()) == 0)
                {
                    return tmp.getNextEl().get(i).getNextEl().get(0).getKey();
                }                
            }
            if (key.compareTo(tmp.getNextEl().get(0).getKey()) < 0)
                return tmp.getNextEl().get(0).getKey();
        }
    }
    /* Method checks if the Element with key exists and returns value of it. Uses actual Element. */
    public V get(Object key)
    {   
        K key_t = (K)key;
        if(containsKey(key_t))
        {
            return actual.getValue();
        }
        else return null;
    } 
    /* Method the same as higher key, but that method finds lower key. */
    public K lowerKey(K key)
    {
        Element<K,V> tmp = tail;
        while(true)
        {              
            for(int i = tmp.getHeight() - 1; i > -1; i--)
            {
                if (key.compareTo(tmp.getPrevEl().get(i).getKey()) < 0)
                {
                    tmp = tmp.getPrevEl().get(i);
                    break;
                }
                else if (key.compareTo(tmp.getPrevEl().get(i).getKey()) == 0)
                {
                    return tmp.getPrevEl().get(i).getPrevEl().get(0).getKey();
                }                
            }
            if (key.compareTo(tmp.getPrevEl().get(0).getKey()) > 0)
                return tmp.getPrevEl().get(0).getKey();
        }
    }   
    /* Method puts the element in correct place, also sets correct references to NextEl and PrevEl lists. */
    public V put (K key,V value)
    {
        Element<K,V> tmp = head;    
        Element<K,V> e = new Element<K,V>(key,value,getGeoRandom(random,0.5d)/*random.nextInt(maximumHeight-1)+1*/);
        while(true)
        {            
            for(int i = (tmp.getHeight()-1); i > -1; i--)
            {
                if (key.compareTo(tmp.getNextEl().get(i).getKey()) > 0)
                {                    
                    tmp = tmp.getNextEl().get(i);                        
                    break;
                }
                else if (key.compareTo(tmp.getNextEl().get(i).getKey()) == 0)
                {                           
                    return tmp.replace(key, value);
                }                
                if (key.compareTo(tmp.getNextEl().get(0).getKey()) < 0)
                {
                    if (e.getHeight() <= tmp.getHeight())
                    {                        
                        for (int j = 0; j < e.getHeight(); j++)
                        {
                            e.setElementNext(tmp.getNextEl().get(j), j);
                            e.setElementPrev(tmp, j);
                            tmp.getNextEl().get(j).setElementPrev(e,j);
                            tmp.setElementNext(e, j);                                                                
                        }
                        return null;
                    }
                    else 
                    {                 
                        int t_height = tmp.getHeight();
                        for (int j = 0; j < t_height; j++)
                        {
                            e.setElementNext(tmp.getNextEl().get(j), j);
                            e.setElementPrev(tmp, j);
                            tmp.getNextEl().get(j).setElementPrev(e,j);
                            tmp.setElementNext(e, j);                                                                
                        }                        
                        while(true)
                        {                            
                            tmp = tmp.getPrevEl().get(t_height-1);
                            if(t_height < tmp.getHeight())
                            {
                                int t = 0;
                                if (tmp.getHeight() > e.getHeight())
                                {
                                    t = e.getHeight();
                                    for (int j = t_height; j < t; j++)
                                    {
                                        e.setElementNext(tmp.getNextEl().get(j), j);
                                        e.setElementPrev(tmp, j);
                                        tmp.getNextEl().get(j).setElementPrev(e,j);
                                        tmp.setElementNext(e, j);                                                                
                                    }
                                    return null;
                                }
                                for (int j = t_height; j < tmp.getHeight(); j++)
                                {
                                    e.setElementNext(tmp.getNextEl().get(j), j);
                                    e.setElementPrev(tmp, j);
                                    tmp.getNextEl().get(j).setElementPrev(e,j);
                                    tmp.setElementNext(e, j);                                                                
                                } 
                                t_height = tmp.getHeight();
                            }
                            if (t_height == e.getHeight()) return null;
                        }
                    }
                }
            }
        }
    }
    /* Uses actual. Method removes Node from skiplist(). */
    public V remove(Object key)
    {
        K key_t = (K)key;
        if(containsKey(key_t))
        {
            V value_t = actual.getValue();
            for(int i = 0; i < actual.getHeight(); i++)
            {
                actual.getPrevEl().get(i).setElementNext(actual.getNextEl().get(i),i);
                actual.getNextEl().get(i).setElementPrev(actual.getPrevEl().get(i),i);                
            }
            actual.clear();
            return value_t;
        }
        else return null;
    }    
    /* Skiplist is optimal with height of elements randomized with Geometric Distribution */
    public int getGeoRandom(Random r, double p)
    {                        
        int t = (int)(Math.ceil(Math.log(r.nextDouble())/Math.log(1.0-p))); 
        while(t > maximumHeight)
        {
            t = (int)(Math.ceil(Math.log(r.nextDouble())/Math.log(1.0-p)));
        }        
        return t;
    } 
}
