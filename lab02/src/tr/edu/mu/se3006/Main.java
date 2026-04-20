package tr.edu.mu.se3006;
import tr.edu.mu.se3006.catalog.CatalogFactory;
import tr.edu.mu.se3006.catalog.CatalogService;
import tr.edu.mu.se3006.orders.OrderController;
import tr.edu.mu.se3006.orders.OrdersFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 System Starting in Modular Monolith Mode...");
        System.out.println("-" + "-".repeat(45) + "\n");
        
        // Create the Catalog Module via its Factory
        CatalogService catalog = CatalogFactory.create();
        
        // Create the Orders Module via its Factory, passing the catalog module
        OrderController controller = OrdersFactory.create(catalog);
        
        System.out.println("--- Test Scenarios ---");
        // Test the system
        controller.handleUserRequest(1L, 2);
        System.out.println();
        controller.handleUserRequest(2L, 5);
        System.out.println();
        controller.handleUserRequest(1L, 10);
        System.out.println();
        controller.handleUserRequest(999L, 1);
    }
}
