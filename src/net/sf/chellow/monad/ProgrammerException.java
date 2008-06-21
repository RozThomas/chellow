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

public class ProgrammerException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProgrammerException(String message) {
		super(message);
	}

	public ProgrammerException(String message, Throwable cause) {
		super(message, cause);
		if (cause == null) {
			throw new IllegalArgumentException("The 'cause' "
					+ "argument must not be null.");
		}
	}

	public ProgrammerException(Throwable cause) {
		super(cause);
		if (cause == null) {
			throw new IllegalArgumentException("The 'nestedException' "
					+ "argument must not be null.");
		}
	}
}