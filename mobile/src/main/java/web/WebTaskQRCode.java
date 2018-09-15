package web;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.aulapos.unirv.controlefinanceiro.R;
import model.Despesa;


public class WebTaskQRCode extends WebTaskBase {
    private String chave;
    private String UF;

    public WebTaskQRCode(Context context, String chave, String UF) {
        super(context, "QRCode");
        this.chave = chave;
        this.UF = UF;
    }

    @Override
    public String getRequestBody() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("chave", chave);
        parametros.put("UF", UF);

        JSONObject jsonParametros = new JSONObject(parametros);
        return jsonParametros.toString();

    }

    @Override
    public void handleResponse(String response) {

        try {
            JSONObject nfce = new JSONObject(response);
            Despesa despesa = new Despesa();
            despesa.setValor(Float.parseFloat(nfce.getString("Valor")));
            EventBus.getDefault().post(despesa);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.error_request)));
        }

    }


}
