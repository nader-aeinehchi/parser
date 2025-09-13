package org.naderica.parser.sourcecode.java.standard;

import java.util.ArrayList;

/**
 * A simple data structure to hold a class's signature and its members.
 */
public class ClassInfo {
	ClassInfoData data = new ClassInfoData(new ArrayList<>(), new ArrayList<>());

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Class Signature: ").append(data.signature).append("\n");
		if (!data.memberSignatures.isEmpty()) {
			sb.append("  Members:\n");
			for (String member : data.memberSignatures) {
				sb.append("    - ").append(member).append("\n");
			}
		}
		if (!data.innerClasses.isEmpty()) {
			sb.append("  Inner Classes:\n");
			for (ClassInfo inner : data.innerClasses) {
				// This is a simple representation for clarity
				sb.append("    ").append(inner.toString().replace("\n", "\n    ")).append("\n");
			}
		}
		return sb.toString();
	}
}
