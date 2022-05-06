public class ClientList {
	
	private Client[] clients;
	private int max;
	private int quant;
	
	public ClientList(int max) {  // Creates a new list out of max clients.
        this.max = max;
        clients = new Client[max];
        this.quant = 0;
    }
	
	public boolean add(Client c) {  // Add a client to the client's list.
        if (quant == max) {
            return false;
        }
        else{     
        	clients[quant] = c;
            quant++;
            return true;
        }
    }
	
	public Client seek(String name) {  // Find the client's position if it's name is in the client's list.
        for (int i = 0; i < this.quant; i++) {
            if (clients[i].getName().equals(name)) {
            	return clients[i];
            }
        }
        return null;
    }
	
	public int getQuant() {  // Get the quant of clients in the client's list.
		return quant;
	}
	
	public Client getClient(int index) {  // Get the client from the client's list with index.
        if (index >= 0 && index < quant) {
        	return clients[index];
        }
        return null;
    }
	
	public boolean delete(String name) {  // Delete a client from the client's list.
        int i;
        for (i = 0; i < quant; i++) {
            if (clients[i].getName().equals(name)) {
                break;
            }
        }
        if (i == quant) {
            return false;
        }
        for (int j = i; j < quant - 1; j++) {
        	clients[j] = clients[j + 1];
        }
        quant--;
        return true;
    }
}
