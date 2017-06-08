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

package bdi4jade.plan;

import bdi4jade.goal.Goal;

/**
 * This interface defines that a {@link PlanBody} provides output for a goal
 * that is being achieved. These outputs that are properties of the goal may be
 * set during the plan body execution, but this interface defines a method for
 * excplicit performing this taks of setting outpust.
 * 
 * @author ingrid
 */
public interface OutputPlanBody {

	/**
	 * Sets the output parameters in the goal.
	 * 
	 * @param goal
	 *            the goal whose output parameters are to be set.
	 */
	public void setGoalOutput(Goal goal);

}