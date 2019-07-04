import java.util.Map;

public class LruCache extends MyLinkedHashMap<Integer, String> {

	private static final long serialVersionUID = 1L;

	public LruCache(int initialCapacity, float loadFactor, boolean accessOrder, LruCacheMissRetriever missHandler ) {
		super(initialCapacity, loadFactor, accessOrder, missHandler);
	}

	public class LruEntry implements Map.Entry<Integer, String> {

		private Integer key;
		private String value;
		
		public LruEntry(Integer key, String value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public Integer getKey() {
			return this.key;
		}

		@Override
		public String getValue() {
			return this.value;
		}

		@Override
		public String setValue(String value) {
			return this.value = value;
		}
	}
}

