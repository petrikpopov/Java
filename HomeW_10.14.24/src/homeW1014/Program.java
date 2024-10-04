package homeW1014;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Program {

	public static void main(String[] args) {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Задача завершена!";
        });

        future.thenAccept(result -> System.out.println(result));

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

	}

}
