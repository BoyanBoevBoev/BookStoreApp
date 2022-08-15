import org.bosch.intern.core.EngineImpl;
import org.bosch.intern.service.BookStoreService;

public class Main {
    public static void main(String[] args) {
       BookStoreService bookStoreService = new BookStoreService();
       EngineImpl engine = new EngineImpl();
       engine.run2();
    }
}
