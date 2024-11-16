import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("***************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda =]");

        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/274f010c4bbb6e55206cfb00/latest/USD";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            ApiResponse response = gson.fromJson(content.toString(), ApiResponse.class);

            Scanner scanner = new Scanner(System.in);
            int opcion;
            do {
                System.out.println("\n1) Dólar => Peso argentino");
                System.out.println("2) Peso argentino => Dólar");
                System.out.println("3) Dólar => Real brasileño");
                System.out.println("4) Real brasileño => Dólar");
                System.out.println("5) Dólar => Peso colombiano");
                System.out.println("6) Peso colombiano => Dólar");
                System.out.println("7) Salir");
                System.out.print("Elija una opción válida: ");
                System.out.println("\n***************************************");

                opcion = scanner.nextInt();

                double cantidad;
                double cantidadConvertida;

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese la cantidad en Dólares: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "USD", "ARS", response.conversion_rates);
                        System.out.println(cantidad + " USD son " + cantidadConvertida + " ARS");
                        break;
                    case 2:
                        System.out.print("Ingrese la cantidad en Pesos Argentinos: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "ARS", "USD", response.conversion_rates);
                        System.out.println(cantidad + " ARS son " + cantidadConvertida + " USD");
                        break;
                    case 3:
                        System.out.print("Ingrese la cantidad en Dólares: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "USD", "BRL", response.conversion_rates);
                        System.out.println(cantidad + " USD son " + cantidadConvertida + " BRL");
                        break;
                    case 4:
                        System.out.print("Ingrese la cantidad en Reales Brasileños: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "BRL", "USD", response.conversion_rates);
                        System.out.println(cantidad + " BRL son " + cantidadConvertida + " USD");
                        break;
                    case 5:
                        System.out.print("Ingrese la cantidad en Dólares: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "USD", "COP", response.conversion_rates);
                        System.out.println(cantidad + " USD son " + cantidadConvertida + " COP");
                        break;
                    case 6:
                        System.out.print("Ingrese la cantidad en Pesos Colombianos: ");
                        cantidad = scanner.nextDouble();
                        cantidadConvertida = convertirMoneda(cantidad, "COP", "USD", response.conversion_rates);
                        System.out.println(cantidad + " COP son " + cantidadConvertida + " USD");
                        break;
                    case 7:
                        System.out.println("Saliendo del programa. ¡Gracias por usar el conversor de monedas!");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } while (opcion != 7);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double convertirMoneda(double cantidad, String monedaOrigen, String monedaDestino, Map<String, Double> tasas) {
        if (tasas.containsKey(monedaOrigen) && tasas.containsKey(monedaDestino)) {
            double tasaOrigen = tasas.get(monedaOrigen);
            double tasaDestino = tasas.get(monedaDestino);
            return cantidad * (tasaDestino / tasaOrigen);
        } else {
            return -1; // Error en caso de moneda no válida
        }
    }

    public static class ApiResponse {
        String result;
        String base_code;
        Map<String, Double> conversion_rates;
    }
}


