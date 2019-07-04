import java.util.LinkedHashMap;
import java.util.Map;

public class MyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
 
	private static final long serialVersionUID = 1L;

	private int hits = 0;
    private int misses = 0;
	private int MAX_ENTRIES=6;
    private CacheMissRetriever<K, V> missHandler;
    
    public MyLinkedHashMap(
      int initialCapacity, float loadFactor, boolean accessOrder, CacheMissRetriever<K, V> missHandler) {
        super(initialCapacity, loadFactor, accessOrder);
        MAX_ENTRIES = initialCapacity;
        this.missHandler = missHandler;
    }
 
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }
    
    public void printMe() {
		this.entrySet().forEach(entry -> System.out.print(entry.getKey() + ", "));
		System.out.println();
	}
    
    @Override
    public V get(Object key){
    	V superValue = super.get(key);
    	if (superValue != null) {
    		hits++;
    		return superValue;
    	}
    	
    	misses++;
    	
    	V missedValue = (V) this.missHandler.retrieveMiss((K)key);
    	return this.put((K)key, missedValue);
    }
    public void clear() {
    	super.clear();
    }
    public boolean contains(V value) {
    	return (boolean) super.get(value);
    }
    
    public double getHitRatio() {
    	int totalOps = hits+misses;
    	if(totalOps == 0)
    		return 0;
    	
    	return (1.0 * hits)/(hits+misses);
    }
    public double getMissRatio() {
    	int totalOps = hits+misses;
    	if(totalOps == 0)
    		return 0;
    	
    	return (1.0 * misses)/(hits+misses);
    }
}
