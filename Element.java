package skiplist;
import java.util.ArrayList;

public class Element<K extends Comparable<K>,V>
{
    private V value;
    private K key;
    private int elementLevel;    
    private ArrayList<Element<K,V>> nextEl;
    private ArrayList<Element<K,V>> prevEl;    
    
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
    public V getValue()
    {
        return value;
    }    
    public K getKey()
    {
        return key;
    }
    public int getHeight()
    {
        return nextEl.size();
        //return elementLevel;
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
        /*for (int i = 0; i < elementLevel; i++)
        {
            nextEl.set(i,null);
            prevEl.set(i,null);
        }*/
        nextEl.clear();
        prevEl.clear();
    }
            
}
