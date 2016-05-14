package skiplist;
import java.util.Random;
import java.util.ArrayList;
import java.util.*;

public class Skiplist<K extends Comparable<K>,V>
{   
    private Element<K,V> tail;
    private Element<K,V> head;
    private Element<K,V> actual;
    private Random random;
    private int maximumHeight = 16;
    public Skiplist(K key1,K key2)
    {        
        random = new Random();
        Element<K,V> head_t = new Element<K,V>(key1,null,maximumHeight);
        Element<K,V> tail_t = new Element<K,V>(key2,null,maximumHeight);
        head = head_t;
        tail = tail_t;
        for (int i = 0; i < maximumHeight; i++)
        {
            head.setElementNext(tail_t,i);
            head.setElementPrev(null,i);
            tail.setElementPrev(head_t,i);
            tail.setElementNext(null,i);
        }
    }
    public Element<K,V> getHead()
    {
        return head;
    }
    public Element<K,V> getTail()
    {
        return tail;
    }
    public boolean containsKey(K key)
    {
        Element<K,V> tmp = head;        
        while(true)
        {   
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
    public V get(Object key)
    {   
        K key_t = (K)key;
        if(containsKey(key_t))
        {
            return actual.getValue();
        }
        else return null;
    } 
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
    public int getGeoRandom(Random r, double p)
    {                        
        int t = (int)(Math.ceil(Math.log(r.nextDouble())/Math.log(1.0-p))); //Wzor: en.wikipedia.org/wiki/Geometric_distribution
        while(t > maximumHeight)
        {
            t = (int)(Math.ceil(Math.log(r.nextDouble())/Math.log(1.0-p)));
        }        
        return t;
    } 
}
