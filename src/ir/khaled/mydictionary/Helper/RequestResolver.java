package ir.khaled.mydictionary.Helper;

import ir.khaled.mydictionary.model.Response;
import ir.khaled.mydictionary.model.User;
import ir.khaled.mydictionary.model_requests.UserLogin;
import ir.khaled.mydictionary.model_requests.UserRegister;

/**
 * Created by khaled.bakhtiari on 5/1/2014.
 */
public class RequestResolver {
    private static final String METHOD_SIGN_UP = "signUp";
    private static final String METHOD_SIGN_IN = "signIn";


    public RequestResolver() {
    }

    /**
     * resolves a method from the request.
     *
     * @return {@link ir.khaled.mydictionary.model.Response} with an errorCode or the result of the requested method.
     */
    public Response resolve(Response request) {
        if (request == null)
            return new Response(ErrorHelper.INVALID_REQUEST_OBJECT, "the request object is null and can't be handled");

        if (request.request.equals(METHOD_SIGN_UP)) {
            if (request.result instanceof UserRegister) {
                return User.register((UserRegister) request.result);
            } else {
                return getResponseInvalidObjectType();
            }
        } else if (request.request.equals(METHOD_SIGN_IN)) {
            if (request.result instanceof UserLogin) {
                return User.login((UserLogin) request.result);
            } else {
                return getResponseInvalidObjectType();
            }
        } else {
            return new Response(ErrorHelper.NO_SUCH_METHOD, "no such method exists to handle the request.");
        }
    }


    private Response getResponseInvalidObjectType() {
        return new Response(ErrorHelper.INVALID_REQUEST_OBJECT_TYPE, "the type of the object does not match the request type.");
    }
}