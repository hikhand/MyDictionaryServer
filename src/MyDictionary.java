import ir.khaled.mydictionary.Helper.DatabaseHelper;
import ir.khaled.mydictionary.Helper.LogHelper;
import ir.khaled.mydictionary.Socket.SocketServer;

/**
 * Created by reveisa on 4/29/2014.
 */
public class MyDictionary {

    public static void main(String[] args) {
        LogHelper.logD("application started starting server.");

        DatabaseHelper.getInstance();

//        SocketServer.getInstance().startServer();
    }
}
