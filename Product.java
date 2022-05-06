public class Product {
	
	private String name;
	private int price;
	private int stock;
	private int sold;
	
	public Product(String name, int price, int stock) {  // Create a new Instance with the information required.
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.sold = 0;
	}
	
	public String getName() {  // Get the product's name.
		return name;
	}
	public void setName(String name) {  // Set the product's name.
		this.name = name;
	}
	public int getPrice() {  // Get the product's price.
		return price;
	}
	public void setPrice(int price) {  // Set the product's price.
		this.price = price;
	}
	public int getStock() {  // Get the available stock of a product.
		return stock;
	}
	public void setStock(int stock) {  // Set the available stock of a product.
		this.stock = stock;
	}
	public int getSold() {  // Get how many products were sold when reading text file.
		return sold;
	}
	public void setSold(int sold) {  // Set how many products were sold (for added products).
		this.sold = sold;
	}
}