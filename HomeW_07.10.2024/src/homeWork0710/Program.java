package homeWork0710;
import java.util.*;

class ClientOrderManager {
	private Map<String, Set<String>> clientOrders;

    public ClientOrderManager() {
        clientOrders = new HashMap<>();
    }
    
    public void addClient(String clientName, String orderNumber) {
        clientOrders.putIfAbsent(clientName, new HashSet<>());
        clientOrders.get(clientName).add(orderNumber);
    }

    public void addOrderToClient(String clientName, String orderNumber) {
        clientOrders.putIfAbsent(clientName, new HashSet<>());
        clientOrders.get(clientName).add(orderNumber);
    }

    public void displayOrdersForClient(String clientName) {
        Set<String> orders = clientOrders.get(clientName);
        if (orders != null) {
            System.out.println("Замовлення для клієнта " + clientName + ": " + orders);
        } else {
            System.out.println("Клієнт " + clientName + " не знайдений.");
        }
    }

    public void displayAllOrders() {
        for (Map.Entry<String, Set<String>> entry : clientOrders.entrySet()) {
            System.out.println("Клієнт: " + entry.getKey() + ", Замовлення: " + entry.getValue());
        }
    }

    public void removeOrderFromClient(String clientName, String orderNumber) {
        Set<String> orders = clientOrders.get(clientName);
        if (orders != null) {
            if (orders.remove(orderNumber)) {
                System.out.println("Замовлення " + orderNumber + " видалено для клієнта " + clientName);
            } else {
                System.out.println("Замовлення " + orderNumber + " не знайдено для клієнта " + clientName);
            }
        } else {
            System.out.println("Клієнт " + clientName + " не знайдений.");
        }
    }

    public void removeAllOrdersForClient(String clientName) {
        if (clientOrders.remove(clientName) != null) {
            System.out.println("Всі замовлення видалено для клієнта " + clientName);
        } else {
            System.out.println("Клієнт " + clientName + " не знайдений.");
        }
    }
}

public class Program {

	public static void main(String[] args) {
		ClientOrderManager manager = new ClientOrderManager();
		manager.addClient("Іван", "Пицца");
        manager.addClient("Іван", "Сок");
        manager.addClient("Олена", "Сникерс");

        manager.addOrderToClient("Іван", "Пиво");

        manager.displayOrdersForClient("Іван");

        manager.displayAllOrders();

        manager.removeOrderFromClient("Іван", "Сок");

        manager.removeAllOrdersForClient("Олена");

        manager.displayAllOrders();

	}

}
