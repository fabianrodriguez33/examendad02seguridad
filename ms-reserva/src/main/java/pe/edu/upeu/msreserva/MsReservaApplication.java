package pe.edu.upeu.msreserva;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication @EnableDiscoveryClient
public class MsReservaApplication {
    public static void main(String[] args){ SpringApplication.run(MsReservaApplication.class, args); }
}
