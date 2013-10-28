package f1app.core;

public enum Circuits {
	AUSTRALIA("Melbourne Circuit"), MALAYSIA("Sepang International Circuit"), CHINA("Shanghai International Circuit"), BAHRAIN("Bahrain International Circuit"), 
	SPAIN("Circuit de Catalunya"), MONACO("Circuit de Monaco"), CANADA("Circuit Gilles Villeneuve"), BRITAIN("Silverstone Circuit"), GERMANY("Nurburgring"), 
	HUNGARY("Hungaroring"), BELGIUM("Circuit de Spa-Francorchamps"), ITALY("Autodromo Nazionale Monza"), SINGAPORE("Marina Bay Street Circuit"), 
	KOREA("Korea International Circuit"), JAPAN("Suzuka Circuit"), INDIA("Buddh International Circuit"), ABU_DHABI("Yas Marina Circuit"), 
	UNITED_STATES("Circuit of the Americas"), BRAZIL("Autodromo Jose Carlose Pace");
	
	private String circuitName;
	
	private Circuits(String circuitName) {
		this.circuitName = circuitName;
	}

	/**
	 * @return the circuitName
	 */
	public String getCircuitName() {
		return circuitName;
	}
	
	/**
	 * @return the country
	 */
	public String getCountry() {
		StringBuilder finishedCountry = new StringBuilder();
		String country = name().toLowerCase();
		country = country.replaceAll("_", " ");
		String[] words = country.split(" ");
		
		for(int i = 0; i < words.length; i++) {
			char[] chars = words[i].toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			finishedCountry.append(chars);

			if(i != (words.length - 1)) {
				finishedCountry.append(" ");
			}
		}
		
		return finishedCountry.toString();
	}

	@Override
	public String toString() {
		return getCountry();
	}
}
