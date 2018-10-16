package br.com.dev.pontointeligente.api.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LuisGustavo
 */
public class Response<T> {

    private T _data;
    private List<String> _errors;

    public Response() {
    }

    public T getData() {
	return _data;
    }

    public void setData(T _data) {
	this._data = _data;
    }

    public List<String> getErrors() {
	if (this._errors == null) {
	    this._errors = new ArrayList<String>();
	}
	return _errors;
    }

    public void setErrors(List<String> _errors) {
	this._errors = _errors;
    }
}
