import axios from "axios";

export default {
    methods: {
        async getRequest(url, callbackSuccess, callbackError) {
            let resp = await axios.get(url, this.getConfig())
            this.tratarResposta(resp, callbackSuccess, callbackError)
        },
        async postRequest(url, obj, callbackSuccess, callbackError) {
            let resp = await axios.post(url, obj, this.getConfig())
            this.tratarResposta(resp, callbackSuccess, callbackError);
        },
        getConfig() {
            return {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("access_token")}`
                }
            }
        },
        tratarResposta(resp, callbackSuccess, callbackErro) {
            if (resp.data.success && typeof callbackErro === 'function') {
                callbackSuccess(resp.data);
            } else if (!resp.data.success && typeof callbackErro === 'function') {
                callbackErro(resp.data);
            }
        }
    }
}