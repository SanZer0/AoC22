/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Blueprint {
	
	public int blueprintNumber;
	public int oreCostOre;
	public int clayCostOre;
	public int obsidianCostOre;
	public int obsidianCostClay;
	public int geodeCostOre;
	public int geodeCostObsidian;
	/**
	 * 
	 */
	public Blueprint(int blueprintNumber, int oreCostOre, int clayCostOre, int obsidianCostOre, 
					int obsidianCostClay, int geodeCostOre, int geodeCostObsidian) 
	{
		this.blueprintNumber = blueprintNumber;
		this.oreCostOre = oreCostOre;
		this.clayCostOre = clayCostOre;
		this.obsidianCostOre = obsidianCostOre;
		this.obsidianCostClay = obsidianCostClay;
		this.geodeCostOre = geodeCostOre;
		this.geodeCostObsidian = geodeCostObsidian;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Blueprint)) {
			return false;
		}
		Blueprint comp = (Blueprint)o;
		if(this.blueprintNumber != comp.blueprintNumber || this.oreCostOre != comp.oreCostOre
				|| this.clayCostOre != comp.clayCostOre || this.obsidianCostOre != comp.obsidianCostOre
				|| this.obsidianCostClay != comp.obsidianCostClay || this.geodeCostOre != comp.geodeCostOre
				|| this.geodeCostObsidian != comp.geodeCostObsidian) {
			return false;
		}
		return true;
	}

}
