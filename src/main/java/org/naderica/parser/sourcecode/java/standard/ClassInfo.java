package org.naderica.parser.sourcecode.java.standard;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple data structure to hold a class's signature and its members.
 */
public class ClassInfo {
	public String signature;
	public List<String> memberSignatures = new ArrayList<>();
	public List<ClassInfo> innerClasses = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Class Signature: ").append(signature).append("\n");
		if (!memberSignatures.isEmpty()) {
			sb.append("  Members:\n");
			for (String member : memberSignatures) {
				sb.append("    - ").append(member).append("\n");
			}
		}
		if (!innerClasses.isEmpty()) {
			sb.append("  Inner Classes:\n");
			for (ClassInfo inner : innerClasses) {
				// This is a simple representation for clarity
				sb.append("    ").append(inner.toString().replace("\n", "\n    ")).append("\n");
			}
		}
		return sb.toString();
	}
}