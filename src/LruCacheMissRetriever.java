
public class LruCacheMissRetriever extends CacheMissRetriever<Integer, String> {
		
		@Override
		public String retrieveMiss(Integer key) {
			In in = new In("Database");
			
			String[] contents = in.readAllLines();
			
			for (int i = 0; i < contents.length; i++)
			{
				String[] splitLine = contents[i].split(" ");
				Integer missedKey = Integer.parseInt(splitLine[0]);
				
				if (key == missedKey) {
					return splitLine[1];
				}
			}

			return null;
		}		
	}