package org.example;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Main {

    public static String fetchData(String destUrl, String to, Double value) throws IOException {
        URL url = new URL(destUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");

        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();

        int responseCode = conn.getResponseCode();
        String response = "";

        if (responseCode == 200) {
            StringBuilder responseBuilder = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNextLine()) {
                responseBuilder.append(scanner.nextLine());
            }

            response = responseBuilder.toString();

            // Parsear el JSON
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            // Obtener el objeto "rates"
            JsonObject rates = jsonObject.getAsJsonObject("rates");

            // Obtener el valor de la moneda "to"
            if (rates.has(to)) {
                double rate = rates.get(to).getAsDouble() * value;
                response = String.valueOf(rate);
            } else {
                response = "Moneda no encontrada: " + to;
            }

        } else {
            System.out.println("Error: " + responseCode);
            response = "Error: " + responseCode;
        }

        conn.disconnect();
        return response;
    }


    public static void main(String[] args) throws IOException {

        boolean exit = false;
        String message = """
                ******************************************
                Bienvenido/a al conversor de monedas 2025!
                ------------------------------------------
                1) Dolar            => Peso Argentino
                2) Peso Argentino   => Dolar
                3) Dolar            => Real Brasileño
                4) Real Brasileño   => Dolar
                5) Dolar            => Peso Colombiano
                6) Peso Colombiano  => Dolar
                7) Salir            =>
                ------------------------------------------
                Elige una opción de sálida
                ******************************************
                """;
        String input_message = "Ingresa la operacion que deseas realizar: ";
        String output_message = "";
        Scanner scanner = new Scanner(System.in);
        String input;
        String url = "https://api.exchangerate-api.com/v4/latest/";
        String from = "";
        String to = "";
        double value;

        while (!exit) {
            System.out.println(message + "\n" + input_message);

            input = scanner.nextLine();
            switch (input) {
                case "7" -> {
                    exit = true;
                    break;
                }
                case "1" -> {
                    from = "USD";
                    to = "ARS";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + from, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                case "2" -> {
                    from = "ARS";
                    to = "USD";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + to, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                case "3" -> {
                    from = "USD";
                    to = "BRL";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + from, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                case "4" -> {
                    from = "BRL";
                    to = "USD";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + to, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                case "5" -> {
                    from = "USD";
                    to = "COP";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + from, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                case "6" -> {
                    from = "COP";
                    to = "USD";
                    System.out.println("Ingresa un valor a convertir");
                    value = scanner.nextDouble();
                    String valor = fetchData(url + to, to, value);
                    output_message = "El valor de 1 " + from + " equivale al valor final de : " + valor + " " + to + "\n";
                    System.out.printf(output_message);
                    break;
                }
                default -> {
                    System.out.println("Opción no válida. Por favor, elige una opción del menú.");
                    break;
                }
            }
        }

    }


}