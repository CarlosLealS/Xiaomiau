import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) throws IOException {
		
		Scanner read = new Scanner(System.in); 
		ClientList clients = new ClientList(999);
		ProductList products = new ProductList(999);
		
		fileReading(clients, products);
		
		boolean admin = false;
		Client u = null;
		
		System.out.println("|| Welcome to 'XIAOMIAU STORE' ||");
		System.out.println("Enter a username:");
		String user = read.nextLine();
		if (user.equals("ADMIN")) {
			System.out.println("Enter password:");
			String pass = read.nextLine();
			while(!pass.equals("NYAXIO")) {
				System.out.println("Wrong password, please enter a valid password");
				pass = read.nextLine();
			}
			admin = true;
		}
		else {	
			u = clients.seek(user);
			while(u == null) {
				System.out.println("User not found, please enter a valid user");
				user = read.nextLine();
				u = clients.seek(user);
			}
			System.out.println("Enter password");
			String pass = read.nextLine();
		
			while(!pass.equals(u.getPassword())) {
				System.out.println("Wrong password, please enter a valid password");
				pass = read.nextLine();
			}
		}
		if(!admin) {
			clientMenu(read, u, products);
		}
		else {
			adminMenu(read, clients, products);
		}
		fileWriting(clients, products);
	}
	
	// File reading for the three file texts.
	public static void fileReading(ClientList clients,ProductList products) throws FileNotFoundException{
		Scanner arch0 = new Scanner(new File("Clientes.txt"));
		while(arch0.hasNextLine()) {
			String[] parts0 = arch0.nextLine().split(",");
			String name = parts0[0];
			String pass = parts0[1];
			int money = Integer.parseInt(parts0[2]);
			String email = parts0[3];
			Client c = new Client(name, pass, money, email);
			clients.add(c);
		}
		
		Scanner arch1 = new Scanner(new File("Productos.txt"));
		while(arch1.hasNextLine()) {
			String[] parts1 = arch1.nextLine().split(", ");
			String name = parts1[0];
			int price = Integer.parseInt(parts1[1]);
			int stock = Integer.parseInt(parts1[2]);
			Product p = new Product(name, price, stock);
			products.add(p);
		}
		
		Scanner arch2 = new Scanner(new File("Ventas.txt"));
		while(arch2.hasNextLine()) {
			String[] parts2 = arch2.nextLine().split(",");			
			String name = parts2[0];
			int sold = Integer.parseInt(parts2[1]);
			Product p = products.seek(name);
			p.setSold(sold);
		}
	}
	
	// Client menu, only available if you input a correct user and password.
	public static void clientMenu(Scanner read, Client u, ProductList products) {
		
		boolean exit = false;
		System.out.println("----------------------------");
		System.out.println("--- Welcome " + u.getName() + " ---");
		
		while(!exit) {
			System.out.println("----------------------------");
			System.out.println("--- [1] Choose a product ---");
			System.out.println("--- [2]  Change Password ---");
			System.out.println("--- [3]   See Catalog    ---");
			System.out.println("--- [4]    See Balance   ---");
			System.out.println("--- [5]    Add Balance   ---");
			System.out.println("--- [6] See Shopping Cart---");
			System.out.println("--- [7]  Remove Product  ---");
			System.out.println("--- [8] Pay Shopping Cart---");
			System.out.println("--- [9]      | EXIT |    ---");
			System.out.println("----------------------------");
			
			int option = Integer.parseInt(read.nextLine());
			switch(option) {
			case 1:
				chooseProduct(read, products, u);
				break;
			case 2:
				changePassword(read,u);
				break;
			case 3:
				showCatalog(products);
				break;
			case 4:
				showBalance(u);
				break;
			case 5:
				addBalance(read, u);
				break;
			case 6:
				showShoppingCart(u);
				break;
			case 7:
				removeProduct(u, read, products);
				break;
			case 8:
				payProducts(u, read, products);
				break;
			case 9:
				exit = true;
				System.out.println("See you later :)");
				break;
			}
		}
		
	}
	
	// Pick a product to buy if there's stock available.
	public static void chooseProduct(Scanner read, ProductList products, Client u) {

		System.out.println("Enter product name");
		String name = read.nextLine();
		Product p = products.seek(name);
		while(p == null) {
			System.out.println("Product not found, please enter a valid product");
			name = read.nextLine();
			p = products.seek(name);
		}
		
		if (p.getStock() > 0) {
			int stock = p.getStock(); 
			System.out.println("How many do you want to take?");
			int units = Integer.parseInt(read.nextLine());
			while(units > stock) {
				System.out.println("Not enough stock. Enter a smaller number");
				units = Integer.parseInt(read.nextLine());
			}
			ProductList cart = u.getShoppingCart();
			if (cart.seek(p.getName()) == null) {
				Product pCart = new Product(p.getName(), p.getPrice(), units);
				cart.add(pCart);
				p.setStock(stock - units);
			}
			else {
				Product pCart = cart.seek(p.getName());
				pCart.setStock(pCart.getStock() + units);
				p.setStock(stock-units);
			}
			
			System.out.println("The product was added to the cart");
		}
		else {
			System.out.println("Product out of stock :(");
		}
			
	}
	
	// Change the client's password only if you know your actual one.
	public static void changePassword(Scanner read, Client u) {
		
		System.out.println("Enter password");
		String pass = read.nextLine();
	
		while(!pass.equals(u.getPassword())) {
			System.out.println("Wrong password, please enter a valid password");
			pass = read.nextLine();
		}
		
		System.out.println("Enter a new password");
		String newPass = read.nextLine();
		
		while(newPass.length() >= 10 && newPass.length() < 1) {
			System.out.println("Error -- The password must be between 1 and 10 characters");
			newPass = read.nextLine();
		}
		u.setPassword(newPass);
		System.out.println("The password has been changed successfully");
	}
	
	// Show the entire products catalog, including the product's name, stock and price.
	public static void showCatalog(ProductList products) {
		
		for(int i = 0; i < products.getQuant(); i++) {
			Product p = products.getProductI(i);
			if(p.getStock() > 0) {
				String name = p.getName();
				int stock = p.getStock();
				int price = p.getPrice();
				System.out.println("Product: " + name + " | Stock: " + stock + " | Price: " + price + "$");
			}
		}
	}
	
	// Show the client's balance.
	public static void showBalance(Client u){
		
		int balance = u.getBalance();
		System.out.println("Your balance is: " + balance + "$");
	}
	
	// Add money to the client's balance.
	public static void addBalance(Scanner read, Client u) {
		
		int balance = u.getBalance();
		System.out.println("How much money do you want to add?");
		int money = Integer.parseInt(read.nextLine());
		u.setBalance(balance + money);
		System.out.println("The balance was added successfully");
	}
	
	// Show the client's shopping cart.
	public static void showShoppingCart(Client u) {
		
		ProductList cart = u.getShoppingCart();
		if (cart.getQuant() == 0) {
			System.out.println("Your cart is empty :c");
		}
		else {
			for (int i = 0; i < cart.getQuant(); i++) {
				System.out.println("Product: " + cart.getProductI(i).getName()+ " | "+cart.getProductI(i).getPrice()+ "$ | X" + cart.getProductI(i).getStock()); 
			}
		}
	}
	
	// Remove products from the client's shopping cart.
	public static void removeProduct(Client u, Scanner read, ProductList products) {
		
		ProductList cart = u.getShoppingCart();
		System.out.println("What product do you want to remove from the cart?");
		String name = read.nextLine();
		Product cartProduct = cart.seek(name);
		
		if (cartProduct != null) {
			Product product = products.seek(name);
			int units = cartProduct.getStock();
			int stock = product.getStock();
			product.setStock(stock + units);
			System.out.println("Product removed successfully ");
		}
		else {
			System.out.println("Product doesn't exist / Your cart does not contain that product");
		}
	}
	
	// Pay the shopping cart with all the products you chose.
	public static void payProducts(Client u, Scanner read, ProductList products) {
		
		ProductList cart = u.getShoppingCart();
		if (cart.getQuant() == 0) {
			System.out.println("Your cart is empty :c");
		}
		else {
			int pay = 0;
			
			for (int i = 0; i < cart.getQuant(); i++) {
				pay += (cart.getProductI(i).getPrice() * cart.getProductI(i).getStock());
			}
			
			System.out.println("The total value of your cart is: " + pay + "$");
			System.out.println("Do you want to pay the cart? [Y]/[N]");
			String option = read.nextLine();
			
			while (!option.equals("Y") && !option.equals("N") ) {
				System.out.println("Do you want to pay the cart? [Y]/[N]");
				option = read.nextLine();
			}
			if (option.equals("Y")) {
				int balance = u.getBalance();
				if (balance > pay) {
					for (int i = 0; i < cart.getQuant(); i++) {
						Product pCart = cart.getProductI(i);
						Product product = products.seek(pCart.getName());
						int sold = product.getSold();
						product.setSold(sold + pCart.getStock());
						pCart = null;
					}				
					u.setBalance(balance - pay);
				}
				else {
					System.out.println("You don't have enough money to pay");
					System.out.println("Remove a product or add money to your account");
				}
			}
		}
	}
	
	// Administrator menu, only available if the user is "ADMIN" and the password is "NYAXIO".
	public static void adminMenu(Scanner read, ClientList clients, ProductList products) {
		
		boolean exit = false;
		System.out.println("----------------------------");
		System.out.println("---    Welcome ADMIN     ---");
			while(!exit) {
			
			System.out.println("----------------------------");
			System.out.println("--- [1]    Block User    ---");
			System.out.println("--- [2] Shopping History ---");
			System.out.println("--- [3]    Add Product   ---");
			System.out.println("--- [4]     Add Stock    ---");
			System.out.println("--- [5]   Change price   ---");
			System.out.println("--- [6]      | EXIT |    ---");
			System.out.println("----------------------------");
			
			int option = Integer.parseInt(read.nextLine());
			switch(option){
			case 1:
				blockUser(read, clients);
				break;
			case 2:
				showHistory(products);
				break;
			case 3:
				addProduct(read, products);
				break;
			case 4:
				addStock(read, products);
				break;
			case 5:
				changePrice(read, products);
				break;
			case 6:
				exit = true;
				System.out.println("See you later :)");
				break;
			}
		}
	}
	
	// If the user exists, it gets deleted.
	public static void blockUser(Scanner read, ClientList clients) {
		
		System.out.println("Enter the user you want to block");
		String userB = read.nextLine();
		if (clients.delete(userB)) {
			System.out.println("The user has been successfully blocked");
		}
		else {
			System.out.println("User doesn't exist");
		}
	}
	
	// Show history of all the products sold.
	public static void showHistory(ProductList products) {
		
		System.out.println("-------- Sales history --------");
		for (int i = 0; i < products.getQuant(); i++) {
			if (products.getProductI(i).getSold() > 0) {
				System.out.println("Product: " + products.getProductI(i).getName() + " | " + products.getProductI(i).getSold());
			}
		}
	}
	
	// Add product to the product's available list.
	public static void addProduct(Scanner read, ProductList products) {
		
		System.out.println("Enter the product name: ");
		String name = read.nextLine();
		System.out.println("Enter the product price: ");
		int price = Integer.parseInt(read.nextLine());
		System.out.println("Enter the product stock: ");
		int stock = Integer.parseInt(read.nextLine());
		Product product = new Product(name, price, stock);
		products.add(product);
	}
	
	// Add stock for an existent product.
	public static void addStock(Scanner read, ProductList products) {
		
		System.out.println("Enter a product name: ");
		String name = read.nextLine();
		Product product = products.seek(name);
		if(product != null) {
			System.out.println("Enter how many units will be added");
			int units = Integer.parseInt(read.nextLine());
			product.setStock(product.getStock() + units);
			System.out.println("Units added successfully");
		}
		else {
			System.out.println("Product doesn't exist");
		}
	}
	
	// If the product exists, input the new price.
	public static void changePrice(Scanner read, ProductList products) {
		System.out.println("Enter a product name: ");
		String name = read.nextLine();
		Product product = products.seek(name);
		if(product != null) {
			System.out.println("Enter the new price");
			int price = Integer.parseInt(read.nextLine());
			product.setPrice(price);
			System.out.println("Price changed successfully");
		}
		else {
			System.out.println("Product doesn't exist");
		}
	}
	
	// File overwriting. When the program ends, the text files are saved automatically.
	public static void fileWriting(ClientList clients, ProductList products) throws IOException {
		
		FileWriter save0 = new FileWriter(new File("Clientes.txt"));
		for (int i = 0; i < clients.getQuant(); i++) {
			save0.write(clients.getClient(i).getName() + "," + clients.getClient(i).getPassword() + "," + String.valueOf(clients.getClient(i).getBalance()) + "," + clients.getClient(i).getEmail());
			save0.write("\r\n");
		}
		save0.close();
		
		FileWriter save1 = new FileWriter(new File("Productos.txt"));
		for (int i = 0; i < products.getQuant(); i++) {
			save1.write(products.getProductI(i).getName() + ", " + String.valueOf(products.getProductI(i).getPrice()) + ", " + String.valueOf(products.getProductI(i).getStock()));
			save1.write("\r\n");
		}
		save1.close();
		
		FileWriter save2 = new FileWriter(new File("Ventas.txt"));
		for (int i = 0; i < products.getQuant(); i++) {
			save2.write(products.getProductI(i).getName() + "," + String.valueOf(products.getProductI(i).getSold()));
			save2.write("\r\n");
		}
		save2.close();
	}	
}
