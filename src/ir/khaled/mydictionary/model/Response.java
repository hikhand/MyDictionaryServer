package ir.khaled.mydictionary.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by khaled.bakhtiari on 4/29/2014.
 */
public class Response implements Serializable {
    /**
     * values to fill from client
     */
    public String request;
    public String udk;
    public String userAgent;

    /**
     * values to fill from server
     */
    public boolean success;
    public int errorCode;
    public String errorMessage;
    public Object result;

    public Response() {

    }

    /**
     * creates an instance witch only has the success boolean flag set.
     * @param success whether to set the success flag to true or fasle
     */
    public Response(boolean success) {
        this.success = success;
    }

    /**
     * creates an instance of {@link ir.khaled.mydictionary.model.Response} this could be used to send responses back to client.
     *
     * @param errorCode    to be used in response
     * @param errorMessage to be used in response
     */
    public Response(int errorCode, String errorMessage) {
        Response response = new Response();
        response.errorCode = errorCode;
        response.errorMessage = errorMessage;
        response.success = false;
    }


    /**
     * send object to the outputStream throws exception on any failure.
     *
     * @param outputStream the outputStream to write response to.
     * @param response     the response to be sent to outputStream.
     * @throws IOException          this exception will be thrown on any failure in writing object to outputStream.
     * @throws NullPointerException this exception will be thrown whether outputStream or response is null.
     */
    public static void sendResponse(ObjectOutputStream outputStream, Response response) throws IOException, NullPointerException {
        if (outputStream == null)
            throw new NullPointerException("ObjectOutputStream parameter is null.");

        if (response == null)
            throw new NullPointerException("Response parameter is null.");

        outputStream.writeObject(response);
    }
}
