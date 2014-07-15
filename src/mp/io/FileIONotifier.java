package mp.io;

/**
 * Notifies the parser that the new chunk of data was read
 * @author Evgeny Mitichkin
 *
 */
public interface FileIONotifier {
	
	/**
	 * Happenes when a file chunk is read
	 * @param str
	 */
	public void onChunkRead(String[] str);
}
