/**
 * @author: Jakub Dziwura
 * 
 * Node class for skiplist class. 
 */
package skiplist;
import java.util.ArrayList;

public class Element<K extends Comparable<K>,V>
{
    /* Value stored in node. */
    private V value;
    /* Key, to comapre elements. */
    private K key;
    /* Height of Element (Node). */
    private int elementLevel;  
    /* List of elements with higher key. */
    private ArrayList<Element<K,V>> nextEl;
    /* List of elements with lower key. */
    private ArrayList<Element<K,V>> prevEl;    
    
    /* Constructor gets key, value, and height, and create empty 
       lists of previous and next nodes. */
    public Element(K key_t, V value_t, int elementLevel_t)
    {           
        value = value_t;
        key = key_t;        
        elementLevel = elementLevel_t;
        nextEl =  new ArrayList<Element<K,V>>(elementLevel);  
        prevEl =  new ArrayList<Element<K,V>>(elementLevel);
        for (int i = 0; i < elementLevel; i++)
        {
            nextEl.add(null);
            prevEl.add(null);
        }
    } 
    /* getValue() method returns value of Element (Node). */
    public V getValue()
    {
        return value;
    }   
    /* getKey() method returns key of Element (Node). */
    public K getKey()
    {
        return key;
    }
    /* getHeight() method returns height of Element (Node). */
    public int getHeight()
    {
        return nextEl.size();
    }
    public ArrayList<Element<K,V>> getPrevEl()
    {
        return prevEl;
    }
    public ArrayList<Element<K,V>> getNextEl()
    {
        return nextEl;
    }
    public int getMaxHeight()
    {
        return elementLevel;
    }
    public void setElementNext(Element<K,V> e, int i)
    {
        nextEl.set(i,e);
    }
    public void setElementPrev(Element<K,V> e, int i)
    {
        prevEl.set(i,e);
    }
    public V replace(K key_t, V value_t)
    {
        V vt = value;
        key = key_t;
        value = value_t;
        return vt;
    }
    public void clear()
    {
        value = null;
        key =  null;
        nextEl.clear();
        prevEl.clear();
    }
            
}
