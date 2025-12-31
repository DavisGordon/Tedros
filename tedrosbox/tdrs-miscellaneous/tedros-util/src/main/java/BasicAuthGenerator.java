
import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * Classe utilitária para gerar o cabeçalho Basic Authorization.
 */
public class BasicAuthGenerator {

    /**
     * Gera o valor completo do cabeçalho Authorization no formato Basic.
     *
     * @param clientId O Client ID (username).
     * @param clientSecret O Client Secret (password).
     * @return A string do cabeçalho, ex: "Basic <key>"
     */
    public static String generateBasicAuthHeader(String clientId, String clientSecret) {
        // 1. Formatar a credencial: "clientId:clientSecret"
        String credentials = clientId + ":" + clientSecret;

        // 2. Codificar a string combinada em Base64
        // É essencial usar a codificação UTF-8 para garantir a compatibilidade
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));
        String base64Credentials = new String(encodedBytes, StandardCharsets.UTF_8);

        // 3. Construir o header completo
        return "Basic " + base64Credentials;
    }

    public static void main(String[] args) {
        // Exemplo usando o Client ID e Secret da sua requisição original:
        String clientId = "";
        String clientSecret = "";

        String authHeaderValue = generateBasicAuthHeader(clientId, clientSecret);

        System.out.println("Client ID: " + clientId);
        System.out.println("Client Secret: " + clientSecret);
        System.out.println("Basic Auth Header: " + authHeaderValue);

        
    }
}