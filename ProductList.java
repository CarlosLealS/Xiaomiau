public class ProductList {
	
	private Product[] products;
	private int max;
	private int quant;
	
	public ProductList(int max) {  // Creates a new list out of max products.
        this.max = max;
        products = new Product[max];
        this.quant = 0;
	}
	
	public boolean add(Product p) {  // Add a product to the product's list.
        if (quant == max) {
            return false;
        }
        else {     
        	products[quant] = p;
            quant++;
            return true;
        }
    }
	
	public Product seek(String name) {  // Find the product's position if it's name is in the product's list.
        for (int i = 0; i < this.quant; i++) {
            if (products[i].getName().equals(name)) {
            	return products[i];
            }
        }
        return null;
    }
	
	public int getQuant() {  // Get the quant of products in the product's list
		return quant;
	}
	
	public Product getProductI(int index) {  // Get the product from the product's list with index.
        if (index >= 0 && index < max) {
        	return products[index];
        }
        return null;
    }
	
	public boolean delete(String name) {  // Delete a product from the product's list.
        int i;
        for (i = 0; i < quant; i++) {
            if (products[i].getName().equals(name)) {
                break;
            }
        }
        if (i == quant) {
            return false;
        }
        for (int j = i; j < quant - 1; j++) {
        	products[j] = products[j + 1];
        }
        quant--;
        return true;
	}
}
