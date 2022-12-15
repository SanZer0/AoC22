/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Directory {
	private int m_size;
	private String m_name;
	public Directory(String name) {
		m_size = 0;
		m_name = name;
	}
	
	public void SetSize(int size) {
		m_size = size;
	}
	
	public int GetSize() {
		return m_size;
	}
	
	public void SetName(int size) {
		m_size = size;
	}
	
	public String GetName() {
		return m_name;
	}
}
