package ir.khaled.mydictionary.Socket;

import ir.khaled.mydictionary.Helper.ErrorHelper;
import ir.khaled.mydictionary.Helper.LogHelper;
import ir.khaled.mydictionary.Helper.RequestResolver;
import ir.khaled.mydictionary.model.Device;
import ir.khaled.mydictionary.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by khaled.bakhtiari on 4/29/2014.
 */
public class SocketConnection extends Thread {
    private Socket mSocket;
    private int mSocketId;
    private InetAddress mInetAddress;
    private ISocketConnectionListener mSocketConnectionListener;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private SenderThread sender;

    /**
     * determines whether is the connection is verified.
     */
    private boolean isConnectionVerified;


    public SocketConnection(Socket socket, int socketId, ISocketConnectionListener connectionClosedListener) {
        mSocket = socket;
        mSocketId = socketId;
        mInetAddress = mSocket.getInetAddress();
        mSocketConnectionListener = connectionClosedListener;
        LogHelper.logD("new client added with id:#" + mSocketId + " and Ip Address: " + mSocket.getInetAddress());
    }

    @Override
    public void run() {
        try {
            onConnectionEstablished();
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.logD("Error handling client#" + mSocketId + " with Ip address: " + mSocket.getInetAddress() + ": " + e);
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                LogHelper.logD("Couldn't close the socket#" + mSocketId + ", what's going on?");
                e.printStackTrace();
            }
            mSocketConnectionListener.onSocketConnectionClosed(this);
            LogHelper.logD("Connection with client#" + mSocketId + " closed");
        }
    }

    public void onConnectionEstablished() throws Exception {
        mSocketConnectionListener.onSocketConnectionEstablished(this);
        objectInputStream = new ObjectInputStream(mSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());

        while (true) {
            Object object = objectInputStream.readObject();
            if (object == null) {
                LogHelper.logD(mSocketId, "the object is invalid and can not be converted to request object.");
                Response.sendResponse(objectOutputStream, new Response(ErrorHelper.INVALID_REQUEST_OBJECT, "the object is invalid and can not be converted to request object."));
                return;
            }

            if (object instanceof Response) {
                Response request = (Response) object;
                if (!verifyConnection(request))
                    continue;

                handleRequest(request);
            } else {
                LogHelper.logD(mSocketId, "object is not an instance of response");
                Response.sendResponse(objectOutputStream, new Response(ErrorHelper.INVALID_REQUEST_OBJECT, "the response is invalid."));
            }
        }
    }

    private void handleRequest(Response request) throws IOException {
        Response response = (new RequestResolver()).resolve(request);
        Response.sendResponse(objectOutputStream, response);
    }

    /**
     * verifies the connection.
     * if the connection is not verified this method will send error to the client.
     *
     * @param response used to check the connection with.
     * @return true if connection is allowed and verified otherwise false.
     * @throws IOException on any failure to send response to client in false condition.
     */
    private boolean verifyConnection(Response response) throws IOException {
        if (isConnectionVerified)
            return true;

        if (Device.isDeviceValid(response.udk)) {
            return true;
        } else {
            Response.sendResponse(objectOutputStream, new Response(ErrorHelper.UNKNOWN_DEVICE, "the device is unknown"));
            return false;
        }
    }

    public void SendMessage(Response response) {
        //TODO implement sending message with pending notification.
    }

    public static interface ISocketConnectionListener {

        public void onSocketConnectionEstablished(SocketConnection socketConnection);

        public void onSocketConnectionClosed(SocketConnection socketConnection);

    }

    /**
     * class sender to send to send data to client without disordering the receiving thread.
     */
    public class SenderThread extends Thread {

        @Override
        public void run() {

        }
    }
}
