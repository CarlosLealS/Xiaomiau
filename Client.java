public class Client {
	
	private String name;
	private String password;
	private int money;
	private String email;
	private ProductList shoppingCart;
	
	public Client(String name,String password,int money, String email) {  // Create a new Instance with the information required.
		this.name = name;
		this.password = password;
		this.money = money;
		this.email = email;
		this.shoppingCart = new ProductList(99);
	}
	
	public String getName() {  // Get the client's name.
		return name;
	}

	public void setUserName(String name) {  // Set the client's name.
		this.name = name;
	}

	public String getPassword() {  // Get the client's password.
		return password;
	}

	public void setPassword(String password) {  // Set the client's password.
		this.password = password;
	}

	public int getBalance() {  // Get the client's balance.
		return money;
	}

	public void setBalance(int money) {  // Set the client's balance.
		this.money = money;
	}
	
	public String getEmail() {  // Get the client's email.
		return email;
	}
	
	public void setEmail(String email) {  // Set the client's email.
		this.email = email;
	}
	
	public ProductList getShoppingCart() {  // Get the client's shopping cart.
		return shoppingCart;
	}

	public void setShoppingCart(ProductList shoppingCart) {  // Set the client's shopping cart.
		this.shoppingCart = shoppingCart;
	}
}
