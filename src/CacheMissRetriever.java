public abstract class CacheMissRetriever<K, V> {
	public abstract V retrieveMiss(K key);
}