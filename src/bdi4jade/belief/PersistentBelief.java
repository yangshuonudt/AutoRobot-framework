//----------------------------------------------------------------------------
// Copyright (C) 2011  Ingrid Nunes, et al.
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
// 
// To contact the authors:
// http://www.inf.puc-rio.br/~ionunes/
//
//----------------------------------------------------------------------------

package bdi4jade.belief;

import jade.content.Concept;

import java.io.Serializable;

/**
 * @author ingrid
 * 
 */
public class PersistentBelief<T> extends Belief<T> implements Serializable,
		Concept {

	private static final long serialVersionUID = 2893517209462636003L;

	protected T value;

	/**
	 * Initializes a belief with its name.
	 * 
	 * @param name
	 *            the belief name.
	 */
	public PersistentBelief(String name) {
		super(name);
	}

	/**
	 * @see bdi4jade.belief.Belief#setValue(java.lang.Object)
	 */
	public void setValue(T value) {
		// XXX PersistentBelief.setValue(T value)
	}

	/**
	 * @see bdi4jade.belief.Belief#getValue()
	 */
	@Override
	public T getValue() {
		// XXX PersistentBelief.getValue()
		return null;
	}

}
