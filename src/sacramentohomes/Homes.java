package sacramentohomes;

public class Homes {
	
	private String street;
	private String city;
	private Integer zip;
	@Override
	public String toString() {
		return "Homes [street=" + street + ", city=" + city + ", zip=" + zip + ", state=" + state + ", beds=" + beds
				+ ", baths=" + baths + ", sq__ft=" + sq__ft + ", type=" + type + ", sale_date=" + sale_date + ", price="
				+ price + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	private String state;
	private int beds;
	private int baths;
	private double sq__ft;
	private String type;
	private String sale_date;
	private double price;
	private double latitude;
	private double longitude;
	
	public Homes(String street, String city, Integer zip, String state, int beds, int baths, double sq__ft, String type, String sale_date, double price, double latitude, double longitude)
	{
		this.setStreet(street);
		this.setCity(city);
		this.setZip(zip);
		this.setState(state);
		this.setBeds(beds);
		this.setSq__ft(sq__ft);
		this.setType(type);
		this.setSale_date(sale_date);
		this.setPrice(price);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip2) {
		this.zip = zip2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getBaths() {
		return baths;
	}

	public void setBaths(int baths) {
		this.baths = baths;
	}

	public double getSq__ft() {
		return sq__ft;
	}

	public void setSq__ft(double sq__ft) {
		this.sq__ft = sq__ft;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSale_date() {
		return sale_date;
	}

	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
