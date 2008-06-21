/*
 
 Copyright 2005 Meniscus Systems Ltd
 
 This file is part of Chellow.

 Chellow is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 Chellow is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Chellow; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 */

package net.sf.chellow.monad;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlTree {
	private Map<String, XmlTree> map = new HashMap<String, XmlTree>();
	
	public XmlTree(String name, XmlTree tree) {
		put(name, tree);
	}
	public XmlTree(String name) {
		put(name);
	}
	
	public XmlTree put(String name, XmlTree tree) {
		map.put(name, tree);
		return this;
	}
	
	public XmlTree put(String name) {
		map.put(name, null);
		return this;
	}
	
	public Iterator<String> keyIterator() {
		return map.keySet().iterator();
	}
	
	public XmlTree get(String name) {
		return map.get(name);
	}
}