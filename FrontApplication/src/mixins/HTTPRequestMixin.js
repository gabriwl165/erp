import axios from "axios";
const params = {
    url: process.env.NODE_ENV === 'production' ? '187.22.223.105' : 'localhost',
}

export default {
    methods: {
        async getRequest(url, callbackSuccess, callbackError) {
            url = `http://${params.url}:8080/${this.tratarURL(url).replace("/", "")}`
            let resp = await axios.get(url, this.getConfig())
            this.tratarResposta(resp, callbackSuccess, callbackError)
        },
        async postRequest(url, obj, callbackSuccess, callbackError, customHeader = {}) {
            url = `http://${params.url}:8080/${this.tratarURL(url).replace("/", "")}`

            let resp = await axios.post(url, obj, this.getConfig(customHeader))
            this.tratarResposta(resp, callbackSuccess, callbackError);
        },
        getConfig(customHeader ) {
            return {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("access_token")}`,
                    ...customHeader
                }
            }
        },
        tratarURL(url){
            let regex = /\/\/[^\/]+\/([^\/]+)/
            let match = url.match(regex)
            if(match){
                return match[1]
            } else return url
        },
        tratarResposta(resp, callbackSuccess, callbackErro) {
            if ((resp.data.success || resp.status === 200) && typeof callbackErro === 'function') {
                callbackSuccess(resp.data);
            } else if (!resp.data.success && typeof callbackErro === 'function') {
                callbackErro(resp.data);
            }
        }
    }
}