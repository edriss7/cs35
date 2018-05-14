import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGenerator {

	static final int FIRST_ID = 10000;
	static final int ID_RAND_INC = 100;
	static final int ID_LENGTH = 12;
	static final String ID_FMT = String.format("%%0%dd", ID_LENGTH);
	static final int UNIQ_RETRIES = 100;

	public static void main(String[] args) throws Exception {
		if (args.length <= 1) {
			System.out.println("Usage: ItemGenerator count file1 [file2 ...]");
			return;
		}

		int count = Integer.parseInt(args[0]);
		String[] ids = generateIds(count);
		shuffle(ids);

		Pool[] pools = new Pool[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			pools[i - 1] = new Pool(read(args[i]));
		}

		Map<String, Integer> existing = new HashMap<>();

		for (int i = 0; i < count; i++) {
			String name = generateName(pools);
			int remaining = UNIQ_RETRIES;
			while (existing.containsKey(name) && remaining > 0) {
				remaining--;
				name = generateName(pools);
			}

			int times = existing.containsKey(name) ? existing.get(name) : 0;
			existing.put(name, times + 1);
			if (times > 0) {
				name = String.format("%s (%d pack)", name, times + 1);
			}

			System.out.printf("%s\t%s\n", ids[i], name);
		}
	}

	static List<String> read(String filename) throws Exception{
		return Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
	}

	static String[] generateIds(int count) {
		String[] ids = new String[count];
		int id = (int) (Math.random() * FIRST_ID);
		for (int i = 0; i < count; i++) {
			ids[i] = String.format(ID_FMT, id);
			id += (int) (Math.random() * ID_RAND_INC);
		}
		return ids;
	}

	static <T> void shuffle(T[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int j = (int) (Math.random() * (i + 1));
			if (i == j) {
				continue;
			}
			T temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}

	static String generateName(Pool[] pools) {
		String[] parts = new String[pools.length];
		for (int j = 0; j < pools.length; j++) {
			parts[j] = pools[j].get();
		}
		String name = join(parts);
		return name.isEmpty() ? "Unknown" : name;
	}

	static String join(String[] parts) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String p : parts) {
			if (p.isEmpty()) {
				continue;
			}
			if (!first) {
				sb.append(' ');
			}
			first = false;
			sb.append(p);
		}
		return sb.toString();
	}

	static class Pool {
		List<String> words;

		public Pool(List<String> words) {
			this.words = words;
		}

		public String get() {
			int i = (int) (Math.random() * words.size());
			return words.get(i);
		}
	}
}