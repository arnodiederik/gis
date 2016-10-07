package gis.util;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;


/**
 * Some generic REST function calls
 */
public final class RestUtil {

    private RestUtil() {
        // intentionally left empty, so the static class cannot be initiated
    }

    /**
     * Make an uri for a given host, port and path
     *
     * @param host The host to connect to
     * @param port The port on the host to connect to
     * @param path The rest of the path
     * @return The composed uri
     */
    public static String makeUri(String host, int port, String path) {
        return String.format("http://%s:%d/%s", host, port, path);
    }

    /**
     * Send a REST POST request to the given uri, and expect a response of the type responseType
     *
     * @param uri The uri of the request
     * @param postData The data to be posted
     * @param responseType The class type of the expected result
     * @return The received result if successful, null otherwise
     */
    public static <T, V> T postRestRequest(String uri, V postData, Class<T> responseType) {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(uri);
        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        try {
            return request.post(Entity.entity(JsonUtil.objectToJson(postData), MediaType.APPLICATION_JSON),
                    responseType);
        } catch (ProcessingException e) {
            LOG.error(String.format("REST request POST %s failed. ", uri) + e);
            return null;
        }
    }

    /**
     * Send a REST POST request to the given uri, only OK is expected as a result
     *
     * @param uri The uri of the request
     * @param postData The data to be posted
     * @return true when successful, false otherwise
     */
    public static <T> boolean postRestRequest(String uri, T postData) {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(uri);
        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        try {
            Response response = request
                    .post(Entity.entity(JsonUtil.objectToJson(postData), MediaType.APPLICATION_JSON));

            if (response.getStatus() != 200) {
                LOG.error(String.format("REST request POST %s failed with status %s", uri,
                        response.getStatusInfo().toString()));
                return false;
            }
            return true;
        } catch (ProcessingException e) {
            LOG.error(String.format("REST request POST %s failed. ", uri) + e);
            return false;
        }
    }

    /**
     * Send a REST POST request to the given uri, only OK is expected as a result
     *
     * @param uri The uri of the request
     * @param postData The data to be posted
     * @return true when successful, false otherwise
     */
    public static boolean postRestRequest(String uri, String postData) {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(updateUri(uri));
        Builder request = resource.request();
        request.accept(MediaType.TEXT_PLAIN);

        try {
            Response response = request.post(Entity.entity(postData, MediaType.APPLICATION_XML));

            if (response.getStatus() != 200) {
                LOG.error(String.format("REST request POST %s failed with status %s", uri,
                        response.getStatusInfo().toString()));
                return false;
            }
            return true;
        } catch (ProcessingException e) {
            LOG.error(String.format("REST request POST %s failed. ", uri) + e);
            return false;
        }
    }

    private static String updateUri(String uri) {
        String newUri = uri;
        if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
            newUri = "http://" + uri;
        }

        return newUri;
    }

    /**
     * Send a REST GET request to the given uri, and expect a response of the type responseType
     *
     * @param uri The uri of the request
     * @param responseType
     * @return
     */
    public static <T> T getRestRequest(String uri, Class<T> responseType) {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(uri);
        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        try {
            return request.get(responseType);
        } catch (ProcessingException e) {
            LOG.error(String.format("REST request get %s failed. ", uri) + e);
            return null;
        }
    }
}
