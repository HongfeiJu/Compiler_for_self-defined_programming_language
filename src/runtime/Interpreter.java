package packagename;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

	public Interpreter() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		File file = new File("/Users/david/Desktop/input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String str;
		String[] strstr = new String[100];
		int i = 0;
		while ((str = br.readLine()) != null) {
//			System.out.println(str);
			strstr[i] = str;
			i += 1;
		}
		
		HashMap<String, Integer> hm = new HashMap<>();
		int line = 0;
		
		while (strstr[line] != null) {
			int type = 0;
			String[] splited = strstr[line].split("\\s+");
			
			for (int j = 0; j < splited.length; j++) {
				if (j == 1 && splited[j].equals("goto")) {
					type = 4;
					break;
				}
				if (splited[j].equals("if") || splited[j].equals("while")) {
					type = 3;
					break;
				}
				if (splited[j].equals("+") ||
						splited[j].equals("-") ||
						splited[j].equals("*") ||
						splited[j].equals("/")) {
					type = 1;
					break;
				}
			}
			if (type != 1 && type != 3 && type != 4 && splited.length == 5) {
				type = 2; // Two operand and one operator
			}
//			System.out.println("line: " + line + ", type: " + type);
			
			switch (type) {			
			case 1:
				int value1 = 0;
				int value2 = 0;
				
				if (hm.containsKey(splited[3])) {
					value1 = hm.get(splited[3]);
				} else {
					try {
						value1 = Integer.parseInt(splited[3]);
					} catch (Exception ex) {
						value1 = 0;
						hm.put(splited[3], 0); // Is it possible?
					}
				}
				if (hm.containsKey(splited[5])) {
					value2 = hm.get(splited[5]);
				} else {
					try {
						value2 = Integer.parseInt(splited[5]);
					} catch (Exception ex) {
						value2 = 0;
						hm.put(splited[5], 0); // Is it possible?
					}
				}
							
//				System.out.println("operator: " + splited[4]);
				
				if (splited[4].equals("+")) {
					hm.put(splited[1], value1 + value2);
				}
				else if (splited[4].equals("-")) {
					hm.put(splited[1], value1 - value2);
				}
				else if (splited[4].equals("*")) {
					hm.put(splited[1], value1 * value2);
				}
				else if (splited[4].equals("/")) {
					hm.put(splited[1], value1 / value2);
				}
				else {
					System.out.println("line: " + line + ", somthing wrong!");
				}
				
				break;
			case 2:
				int value = 0;
				
				if (!hm.containsKey(splited[1])) {
					hm.put(splited[1], 0);
				}
//				if (!hm.containsKey(splited[3])) {
//					hm.put(splited[3], 0); // Is it possible?
//				}
				if (hm.containsKey(splited[3])) {
					value = hm.get(splited[3]);
					hm.put(splited[1], value);
				} else {
					value = Integer.parseInt(splited[3]);
					hm.put(splited[1], value);
				}
				
				break;
			case 3:
				int conditionLeft = 0;
				int conditionRight = 0;
				
				if (hm.containsKey(splited[2])) {
					conditionLeft = hm.get(splited[2]);
				} else {
					try {
						conditionLeft = Integer.parseInt(splited[2]);
					} catch (Exception ex) {
						System.out.println("No declaration!");
					}
				}
				if (hm.containsKey(splited[4])) {
					conditionRight = hm.get(splited[4]);
				} else {
					try {
						conditionRight = Integer.parseInt(splited[4]);
					} catch (Exception ex) {
						System.out.println("No declaration!");
					}
				}
				
				if (splited[3].equals(">=")) {
					if (conditionLeft >= conditionRight) {
						String targetStr = splited[6] + ":";
						int k = 0;
						while (strstr[k] != null) {
							if (strstr[k].indexOf(targetStr) == 0) {
								line = k - 1;
								break;
							}
							k += 1;
						}
					}
				} else if (splited[3].equals("<=")) {
					if (conditionLeft <= conditionRight) {
						String targetStr = splited[6] + ":";
						int k = 0;
						while (strstr[k] != null) {
							if (strstr[k].indexOf(targetStr) == 0) {
								line = k - 1;
								break;
							}
							k += 1;
						}
					}
				} else if (splited[3].equals(">")) {
					if (conditionLeft > conditionRight) {
						String targetStr = splited[6] + ":";
						int k = 0;
						while (strstr[k] != null) {
							if (strstr[k].indexOf(targetStr) == 0) {
								line = k - 1;
								break;
							}
							k += 1;
						}
					}
				} else if (splited[3].equals("<")) {
					if (conditionLeft < conditionRight) {
						String targetStr = splited[6] + ":";
						int k = 0;
						while (strstr[k] != null) {
							if (strstr[k].indexOf(targetStr) == 0) {
								line = k - 1;
								break;
							}
							k += 1;
						}
					}
				} else if (splited[3].equals("==")) {
					if (conditionLeft == conditionRight) {
						String targetStr = splited[6] + ":";
						int k = 0;
						while (strstr[k] != null) {
							if (strstr[k].indexOf(targetStr) == 0) {
								line = k - 1;
								break;
							}
							k += 1;
						}
					}
				} else {
					System.out.println("line: " + line + ", somthing wrong!");
				}
				
				break;
			case 4:
//				System.out.println("goto");
//				System.out.println(splited[2]);
				
				String targetStr = splited[2] + ":";
				int k = 0;
				
				while (strstr[k] != null) {
					if (strstr[k].indexOf(targetStr) == 0) {
						line = k - 1;
						break;
					}
					k += 1;
				}
				
				break;
			default:
//				System.out.println("Empty!");
				
				break;
			}
			
			line += 1;
			type = 0;
		}
		
		System.out.println("\nHashMap Content:");
		for (Map.Entry<String, Integer> m:hm.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
	}

}
