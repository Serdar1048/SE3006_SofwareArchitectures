package tr.edu.mu.se3006;
import tr.edu.mu.se3006.business.OrderService;
import tr.edu.mu.se3006.persistence.ProductRepository;
import tr.edu.mu.se3006.presentation.OrderController;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 System Starting...\n");
        
        ProductRepository repository = new ProductRepository();
        
        OrderService service = new OrderService(repository);
        
        OrderController controller = new OrderController(service);
        
        System.out.println("--- Test Scenarios ---");
        controller.handleUserRequest(1L, 2);
        controller.handleUserRequest(2L, 15);
        controller.handleUserRequest(1L, 10);
        controller.handleUserRequest(99L, 1);
    }
}
