import java.util.Vector;


public class Cluster {
	Vector<Integer> links;
	
	Cluster(Integer link) {
		this.links = new Vector<Integer>();
		this.links.add(link);
	}
	
	Cluster(Vector<Integer> links) {
		this.links = links;
	}
	
	Cluster join(Cluster other) {
		Vector<Integer> links = new Vector<Integer>();
		links.addAll(this.links);
		links.addAll(other.links);
		return new Cluster(links);
	}
	
	void addLink(Integer link) {
		this.links.add(link);
	}
	
	double computeDistance(Integer link) {
		double distance = 0.0;
		for (Integer myLink : links) {
			distance += UtilitarOpenSolutieTriviala.distance[link][myLink];
		}
		return distance / links.size();
	}
}
