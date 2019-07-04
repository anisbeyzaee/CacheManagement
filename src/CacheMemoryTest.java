import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheMemoryTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void SimplePutToMaxLimit() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");
		
		Integer[] expectedOrder = new Integer[] {1, 2, 3, 4, 5};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 0);
	}

	@Test
	void SimplePutPassMaxLimit() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");
		lru.put(6, "V6");
		
		Integer[] expectedOrder = new Integer[] {2, 3, 4, 5, 6};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 0);
	}

	@Test
	void PromoteFromLruToMru() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");

		lru.get(1);
		lru.get(1);
		lru.get(1);
		lru.get(1);
		lru.get(1);
		lru.get(1);
		
		Integer[] expectedOrder = new Integer[] {2, 3, 4, 5, 1};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 1);
	}

	@Test
	void DoSomeCrazyShit() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");

		lru.get(1);
		lru.get(1);
		lru.get(1);
		lru.get(2);
		lru.get(1);
		lru.get(5);

		Integer[] expectedOrder = new Integer[] {3, 4, 2, 1, 5};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 1);
	}

	@Test
	void IMissYouMan() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");
		
		lru.get(7);

		Integer[] expectedOrder = new Integer[] {2, 3, 4, 5, 7};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 0);
	}

	@Test
	void IMissYouManFromTheGetGo() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		
		lru.get(7);

		Integer[] expectedOrder = new Integer[] {7};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), 0);
	}

	@Test
	void IHitRationTest() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");
		
		int hits = 0 , misses = 0;		
		
		lru.get(7); misses++;
		lru.get(1); misses++;
		lru.get(1); hits++;
		lru.get(1); hits++;
		lru.get(2); misses++;
		lru.get(1); hits++;
		lru.get(5); hits++;

		Integer[] expectedOrder = new Integer[] {4, 7, 2, 1, 5};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		//lru.printMe();
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getHitRatio(), (1.0*hits)/(hits+misses));
	}
	void IMissRatioTest() {
		LruCache lru = new LruCache(5, (float)0.75, true, new LruCacheMissRetriever());
		lru.put(1, "V1");
		lru.put(2, "V2");
		lru.put(3, "V3");
		lru.put(4, "V4");
		lru.put(5, "V5");
		
		int hits = 0 , misses = 0;		
		
		lru.get(7); misses++;
		lru.get(1); misses++;
		lru.get(1); hits++;
		lru.get(1); hits++;
		lru.get(2); misses++;
		lru.get(1); hits++;
		lru.get(5); hits++;

		Integer[] expectedOrder = new Integer[] {4, 7, 2, 1, 5};
		Integer[] actualOrder = lru.keySet().toArray(new Integer[0]);
		//lru.printMe();
		assertArrayEquals(expectedOrder, actualOrder);
		assertEquals(lru.getMissRatio(), (1.0*misses)/(hits+misses));
	}
}
